package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.tilldawn.Main;

public class TreeMonster extends Enemy{
    private Animation<Texture> animation;
    private Sprite sprite;
    private CollisionRect rect;

    public TreeMonster(float x, float y) {
        super(1); // مثلا هر درخت یه HP داشته باشه
        this.sprite = new Sprite(new Texture("tree_monster/T_TreeMonster_2.png"));
        this.sprite.setPosition(x, y);
        this.rect = new CollisionRect(x, y, sprite.getWidth(), sprite.getHeight());
        this.animation = MenuGameAssetManager.getMenuGameAssetManager().TreeMonster;
    }

    public void render(float delta) {
        update(delta); // برای invincibility
        Texture currentFrame = animation.getKeyFrame(TimeUtils.nanoTime() / 1_000_000_000f, true);
        sprite.setRegion(currentFrame);
        sprite.draw(Main.getBatch());
    }

    public CollisionRect getRect() {
        return rect;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
