package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    public Game game;
    private Texture playerTexture;
    private Sprite playerSprite;
    private float posX = 0;
    private float posY = 0;
    private float playerHealth = 100;
    private CollisionRect rect ;
    private float time = 0;
    private float speed;

    private float width;
    private float height;

    public float getSpeed() {
        return speed;
    }

    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;

    public Player(Game game) {
        this.game = game;
        this.speed = setSpeed();
        this.playerTexture  = setPlayerTexture();
        this.playerSprite = new Sprite(playerTexture);
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        rect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight(), playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        this.width = setWidth();
        this.height = setHeight();
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerTexture(Texture playerTexture) {
        this.playerTexture = playerTexture;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }

    public float getWidth() { return this.width; }

    public float getHeight() { return this.height; }

    public boolean isPlayerIdle() {
        return isPlayerIdle;
    }

    public void setPlayerIdle(boolean playerIdle) {
        isPlayerIdle = playerIdle;
    }

    public boolean isPlayerRunning() {
        return isPlayerRunning;
    }

    public void setPlayerRunning(boolean playerRunning) {
        isPlayerRunning = playerRunning;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public Texture setPlayerTexture() {

        if (game.getHeroNumber() == 0) {
            return new Texture("Heros/Dasher/idle/Idle_0 #8325.png");
        } else if (game.getHeroNumber() == 1) {
            return new Texture("Heros/Diamond/idle/Idle_0 #8328.png");
        } else if (game.getHeroNumber() == 2) {
            return new Texture("Heros/Lilith/idle/Idle_0 #8333.png");
        } else if (game.getHeroNumber() == 3) {
            return new Texture("Heros/Scarlet/idle/Idle_0 #8327.png");
        } else {
            return new Texture("Heros/Shana/idle/Idle_0 #8330.png");
        }
    }

    public int setSpeed() {
        if (game.getHeroNumber() == 0) {
            return 700;
        } else if (game.getHeroNumber() == 1) {
            return 70;
        } else if (game.getHeroNumber() == 2) {
            return 210;
        } else if (game.getHeroNumber() == 3) {
            return 350;
        } else {
            return 280;
        }
    }

    public float setWidth() {
        if (game.getHeroNumber() == 0) {
            return new Texture("Heros/Dasher/idle/Idle_1 #8355.png").getWidth();
        } else if (game.getHeroNumber() == 1) {
            return new Texture("Heros/Diamond/idle/Idle_1 #8358.png").getWidth();
        } else if (game.getHeroNumber() == 2) {
            return new Texture("Heros/Lilith/idle/Idle_1 #8363.png").getWidth();
        } else if (game.getHeroNumber() == 3) {
            return new Texture("Heros/Scarlet/idle/Idle_1 #8357.png").getWidth();
        } else {
            return new Texture("Heros/Shana/idle/Idle_1 #8360.png").getWidth();
        }
    }

    public float setHeight() {
        if (game.getHeroNumber() == 0) {
            return new Texture("Heros/Dasher/idle/Idle_1 #8355.png").getWidth();
        } else if (game.getHeroNumber() == 1) {
            return new Texture("Heros/Diamond/idle/Idle_1 #8358.png").getHeight();
        } else if (game.getHeroNumber() == 2) {
            return new Texture("Heros/Lilith/idle/Idle_1 #8363.png").getHeight();
        } else if (game.getHeroNumber() == 3) {
            return new Texture("Heros/Scarlet/idle/Idle_1 #8357.png").getHeight();
        } else {
            return new Texture("Heros/Shana/idle/Idle_1 #8360.png").getHeight();
        }
    }

}
