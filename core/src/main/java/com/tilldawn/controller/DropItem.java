package com.tilldawn.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Main;
import com.tilldawn.model.CollisionRect;
import com.tilldawn.model.Player;

public class DropItem {
    private Sprite sprite;
    private CollisionRect rect;
    private boolean isCollected = false;

    public DropItem(float x, float y) {
        Texture texture = new Texture("items/health_seed.png");
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(32, 32);
        rect = new CollisionRect(x, y, 32, 32);
    }

    public void update(Player player) {
        if (!isCollected && rect.collidesWith(player.getRect())) {
            isCollected = true;
            player.setPlayerHealth(player.getPlayerHealth() + 3); // افزایش جون
        }
    }

    public void render() {
        if (!isCollected) {
            sprite.draw(Main.getBatch());
        }
    }

    public boolean isCollected() {
        return isCollected;
    }
}
