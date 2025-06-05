package com.tilldawn.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.tilldawn.model.*;
import com.tilldawn.view.GameView;

public class GameController {
    public Game game;
    private GameView view;
    private PlayerController playerController;
    private WeaponController weaponController;

    private float tentacleSpawnTimer = 0;

    public GameController(Game game) {
        this.game = game;
    }

    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController(new Player(game), view.game);
        weaponController = new WeaponController(setWeapon(), game.getGunNumber());
    }

    public void updateGame(float delta, OrthographicCamera camera, Sprite mapSprite) {
        if (view != null) {
            tentacleSpawnTimer += delta;

            playerController.update(delta, camera, mapSprite);
            weaponController.update(playerController.getPlayer().getPosX(), playerController.getPlayer().getPosY(),
                delta, playerController.getPlayer());

            if (TimeUtils.timeSinceMillis((long) game.getElapsedTime()) > 30000) { // بعد از ثانیه ۳۰
                if (tentacleSpawnTimer >= 3f) {
                    tentacleSpawnTimer = 0;
                    float x = MathUtils.random(0, mapSprite.getWidth() - 64);
                    float y = MathUtils.random(0, mapSprite.getHeight() - 64);
                    game.getPumpkinMonsters().add(new PumpkinMonster(x, y));
                }
            }
        }


    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public Weapon setWeapon() {
        if (view.game.getGunNumber() == 0) {
            return new RvolverWeapon(view.game, new Texture("guns/T_CloseQuarters_Gun.png"), 6);
        } else if (view.game.getHeroNumber() == 1) {
            return new ShotGunWeapon(view.game, new Texture("guns/T_BatgunVamp_Gun.png"), 2);
        } else {
            return new SMGWeapon(view.game, new Texture("guns/T_Trickshot_Gun.png"), 24);
        }
    }
//            this.gun1Label = new Label("REVOLVER", skin);
//        this.gun2Label = new Label("SHOTGUN", skin);
//        this.gun3Label = new Label("SMGs DUAL", skin);
//        this.gun1Image = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("guns/T_CloseQuarters_Gun.png"))));
//        this.gun2Image = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("guns/T_BatgunVamp_Gun.png"))));
//        this.gun3Image = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("guns/T_Trickshot_Gun.png"))));
}
