package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Texture texture = new Texture("guns/bullet.png");
    private Sprite sprite = new Sprite(texture);
    private int damage;
    private Vector2 direction;
    private CollisionRect rect;

    private boolean isCollided = false;

    public Bullet(float startX, float startY, Vector2 direction, int gunNumber){
        setDamage(gunNumber);
        sprite.setSize(20, 20);
        sprite.setPosition(startX, startY);
        this.direction = direction.nor();
        this.rect = new CollisionRect(startX, startY, sprite.getWidth(), sprite.getHeight());
    }

    public void update() {
        sprite.translate(direction.x * 5, direction.y * 5);
        rect.move(sprite.getX(), sprite.getY());
    }

    public Sprite getSprite() {
        return sprite;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isCollided() {
        return isCollided;
    }

    public void setCollided(boolean collided) {
        isCollided = collided;
    }

    public void setDamage(int gunNumber) {
        if (gunNumber == 0) {
            damage = 20;
        } else if (gunNumber == 1) {
            damage = 10;
        } else {
            damage = 8;
        }
    }
}
