package com.tilldawn.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.tilldawn.Main;
import com.tilldawn.model.Enums.KeysController;
import com.tilldawn.model.Game;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.Player;
import com.tilldawn.model.TreeMonster;

public class PlayerController {
    public Game game;
    private Player player;

    public PlayerController(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    public void update(float delta, OrthographicCamera camera, Sprite mapSprite) {
        handlePlayerInput(delta);

        // نگه‌داشتن بازیکن در محدوده نقشه
        float playerHalfWidth = player.getPlayerSprite().getWidth() / 2f;
        float playerHalfHeight = player.getPlayerSprite().getHeight() / 2f;

        float clampedX = MathUtils.clamp(player.getPosX(), playerHalfWidth, mapSprite.getWidth() - playerHalfWidth);
        float clampedY = MathUtils.clamp(player.getPosY(), playerHalfHeight, mapSprite.getHeight() - playerHalfHeight);

        player.setPosX(clampedX);
        player.setPosY(clampedY);

        // مرکز دوربین روی بازیکن
        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();

        // رسم بازیکن
        player.getPlayerSprite().setPosition(player.getPosX() - playerHalfWidth, player.getPosY() - playerHalfHeight);
        player.getPlayerSprite().draw(Main.getBatch());


        if (player.isPlayerIdle()) {
            idleAnimation();
        }

        for (TreeMonster tree : game.getTreeMonsters()) {
            if (tree.getRect().collidesWith(player.getRect())) {
                player.takeDamage(1);
                System.out.println(player.getPlayerHealth());// دمیج بخوره
            }
        }

        if (player.isInvincible()) {
            player.setInvincibleTimer(player.getInvincibleTimer() - delta);
            if (player.getInvincibleTimer() <= 0) {
                player.setInvincible(false);
            }
        }

        player.getRect().move(
            player.getPosX() - player.getPlayerSprite().getWidth() / 2f,
            player.getPosY() - player.getPlayerSprite().getHeight() / 2f
        );

        handlePlayerInput(delta);
    }


    public void handlePlayerInput(float delta) {
        float speed = player.getSpeed() * delta;

        if (Gdx.input.isKeyPressed(KeysController.UP.getKey())) {
            player.setPosY(player.getPosY() + speed);
        }
        if (Gdx.input.isKeyPressed(KeysController.DOWN.getKey())) {
            player.setPosY(player.getPosY() - speed);
        }
        if (Gdx.input.isKeyPressed(KeysController.RIGHT.getKey())) {
            player.setPosX(player.getPosX() + speed);
            player.getPlayerSprite().setFlip(false, false); // سمت راست
        }
        if (Gdx.input.isKeyPressed(KeysController.LEFT.getKey())) {
            player.setPosX(player.getPosX() - speed);
            player.getPlayerSprite().setFlip(true, false); // سمت چپ
        }
    }


    public void idleAnimation() {
        Animation<Texture> animation = setAnimation();
        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        } else {
            player.setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Animation<Texture> setAnimation() {
        MenuGameAssetManager menu = MenuGameAssetManager.getMenuGameAssetManager();
        if (game.getHeroNumber() == 0) {
            return menu.hero1_idl;
        } else if (game.getHeroNumber() == 1) {
            return menu.hero2_idl;
        } else if (game.getHeroNumber() == 2) {
            return menu.hero3_idl;
        } else if (game.getHeroNumber() == 3) {
            return menu.hero4_idl;
        } else {
            return menu.hero5_idl;
        }
    }


}
