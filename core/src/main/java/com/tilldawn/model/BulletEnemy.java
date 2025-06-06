package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class BulletEnemy {
    private Vector2 position;
    private Vector2 direction;
    private float speed;
    private Texture texture;
    private boolean dead = false;
    private CollisionRect rect;

    public BulletEnemy(float x, float y, Vector2 direction, float speed) {
        this.position = new Vector2(x, y);
        this.direction = direction;
        this.speed = speed;
        this.texture = new Texture("Ebyte monster/T_EyeBat_EM.png");
        this.rect = new CollisionRect(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float delta) {
        position.x += direction.x * speed * delta;
        position.y += direction.y * speed * delta;
        rect.move(position.x, position.y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public boolean collidesWith(CollisionRect other) {
        return rect.collidesWith(other);
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }
}
