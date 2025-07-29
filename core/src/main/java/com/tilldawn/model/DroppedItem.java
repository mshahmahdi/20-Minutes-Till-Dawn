package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DroppedItem {
    private Sprite sprite;
    private CollisionRect rect;

    public DroppedItem(Texture texture, float x, float y) {
        this.sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(15, 15); // اندازه دلخواه
        this.rect = new CollisionRect(x, y, sprite.getWidth()*4f, sprite.getHeight()*4f);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public CollisionRect getRect() {
        return rect;
    }
}
