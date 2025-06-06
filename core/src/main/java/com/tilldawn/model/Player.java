package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    public Game game;
    private int XP;
    private int level;
    private Texture playerTexture;
    private Texture lightTexture;
    private Sprite playerSprite;
    private Sprite lightSprite;
    private float posX = 0;
    private float posY = 0;
    private float playerHealth;
    private int kills;
    private CollisionRect rect ;
    private float time = 0;
    private float speed;

    private float width;
    private float height;

    private boolean isSpeedBoosted = false;
    private float speedBoostTimer = 0f;
    private final float SPEED_BOOST_DURATION = 10f;
    private final float SPEED_MULTIPLIER = 2f;

    private boolean invincible = false; // آیا فعلاً دمیج نمی‌خوره؟
    private float invincibleTimer = 0f; // چقدر از زمان بی‌دفاع بودن مونده؟
    private final float INVINCIBLE_DURATION = 1f;
    private final Sound damageSound;

    private float lastHitTime = 0;

    public float getSpeed() {
        return speed;
    }

    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;

    public Player(Game game) {
        this.game = game;
        this.kills = 0;
        this.level = 0;
        this.speed = setSpeed();
        this.playerTexture  = setPlayerTexture();
        this.playerSprite = new Sprite(playerTexture);
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        rect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight(), playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        this.width = setWidth();
        this.height = setHeight();
        this.damageSound = Gdx.audio.newSound(Gdx.files.internal("musics/punch-140236.mp3"));
        this.lightTexture = new Texture("sprite/T/T_Circle.png");
        this.lightSprite = new Sprite(lightTexture);
        lightSprite.setSize(playerSprite.getWidth() * 7f, playerSprite.getHeight() * 6f); // هاله بزرگ‌تر از خود بازیکن
        lightSprite.setOriginCenter();
        setHp();
    }

    public int getXP() {
        return XP;
    }

    public void addXP(int xp) {
        XP += xp;
    }

    public void setXP(int xp) {
        XP = xp;
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

    public int getKills() {
        return kills;
    }

    public void addKills(int kills) {
        this.kills += kills;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel(int level) {
        this.level += level;
    }

    public float getLastHitTime() {
        return lastHitTime;
    }

    public void setLastHitTime(float time) {
        lastHitTime = time;
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

    public Sprite getSprite() { return playerSprite; }

    public Sprite getLightSprite() { return lightSprite; }

    public boolean isInvincible() {
        return invincible;
    }

    public float getInvincibleTimer() {
        return invincibleTimer;
    }

    public float getINVINCIBLE_DURATION() {
        return INVINCIBLE_DURATION;
    }

    public void setInvincibleTimer(float invincibleTimer) {
        this.invincibleTimer = invincibleTimer;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
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

    public void activateSpeedBoost() {
        isSpeedBoosted = true;
        speedBoostTimer = SPEED_BOOST_DURATION;
    }

    public void updateSpeedBoost(float delta) {
        if (isSpeedBoosted) {
            speedBoostTimer -= delta;
            if (speedBoostTimer <= 0) {
                isSpeedBoosted = false;
                speedBoostTimer = 0;
            }
        }
    }

    public float getEffectiveSpeed() {
        return isSpeedBoosted ? speed * SPEED_MULTIPLIER : speed;
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

    public void setHp() {
        if (game.getHeroNumber() == 0) {
            playerHealth = 2;
        } else if (game.getHeroNumber() == 1) {
            playerHealth = 7;
        } else if (game.getHeroNumber() == 2) {
            playerHealth = 5;
        } else if (game.getHeroNumber() == 3) {
            playerHealth = 3;
        } else {
            playerHealth = 4;
        }
    }

    public void addHP() {
        this.playerHealth += 1;
    }

    public void cheatHP() {
        this.playerHealth = 10;
    }

    public void takeDamage(float damage) {
        if (!invincible) {
            playerHealth -= damage;
            invincible = true;
            invincibleTimer = INVINCIBLE_DURATION;
            // اینجا می‌تونی یه صدا یا افکت هم بزاری مثلاً:
            if (App.getApp().isSoundEffect()) {
                damageSound.play(1.0f);
            }
            // playDamageSound();
        }
    }

}
