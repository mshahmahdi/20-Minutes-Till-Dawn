package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Texture texture = new Texture("guns/bullet.png");
    private Sprite sprite = new Sprite(texture);
    private int damage = 5;
    private Vector2 direction;

    public Bullet(float startX, float startY, Vector2 direction){
        sprite.setSize(20, 20);
        sprite.setPosition(startX, startY);
        this.direction = direction.nor(); // جهت نرمال‌شده
    }

    public void update() {
        sprite.translate(direction.x * 5, direction.y * 5);
    }

    public Sprite getSprite() {
        return sprite;
    }
}
