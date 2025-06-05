package com.tilldawn.model;

import com.badlogic.gdx.utils.TimeUtils;

public class Enemy {
    protected float health;
    protected float lastDamageTime = -10;
    protected boolean invincible = false;

    public Enemy(float initialHealth) {
        this.health = initialHealth;
    }

    public void takeDamage(float amount, Player player) {
        float currentTime = TimeUtils.nanoTime() / 1_000_000_000f;
        if (!invincible) {
            health -= amount;
            lastDamageTime = currentTime;
            invincible = true;
        }
    }

    public void update(float delta) {
        float currentTime = TimeUtils.nanoTime() / 1_000_000_000f;
        if (invincible && currentTime - lastDamageTime > 1f) {
            invincible = false;
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }
}
