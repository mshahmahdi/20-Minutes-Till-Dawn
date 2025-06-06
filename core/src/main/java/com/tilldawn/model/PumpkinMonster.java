package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.tilldawn.controller.DropItem;

import java.util.ArrayList;

public class PumpkinMonster extends Enemy {
    private Sprite sprite;
    private CollisionRect rect;
    private float x, y;
    private float speed = 100f;
    private Texture texture;

    private final Sound dieSound;


    private Animation<TextureRegion> animation;
    private float animationTime = 0f;
    private boolean facingRight = true;


    public PumpkinMonster(float x, float y) {
        super(25); // HP = 25;
        this.x = x;
        this.y = y;
        this.texture = new Texture("T_PumpkinMonster/T_PumpkinMonster_0.png"); // مسیر تکسچر
        this.rect = new CollisionRect(x, y, new Texture("T_PumpkinMonster/T_PumpkinMonster_0.png").getWidth(),
            new Texture("T_PumpkinMonster/T_PumpkinMonster_0.png").getHeight());
        this.sprite = new Sprite(texture);
        this.sprite.setSize(texture.getWidth() * 1.5f, texture.getHeight() * 1.5f);
        this.sprite.setPosition(x, y);
        this.animation = MenuGameAssetManager.getMenuGameAssetManager().pumpkinMonster;
        this.animation.setPlayMode(Animation.PlayMode.LOOP);
        this.dieSound = Gdx.audio.newSound(Gdx.files.internal("musics/monster-death-grunt-131480.mp3"));
    }

    public void update(float delta, Player player) {
        // محاسبه جهت به پلیر
        Vector2 direction = new Vector2(
            player.getPosX() - sprite.getX(),
            player.getPosY() - sprite.getY()
        ).nor();

        // تغییر موقعیت انمی
        float speed = 100f;
        sprite.setX(sprite.getX() + direction.x * delta * speed);
        sprite.setY(sprite.getY() + direction.y * delta * speed);

        // به‌روزرسانی مستطیل برخورد
        rect.move(sprite.getX(), sprite.getY());

        // تنظیم جهت نگاه
        if (direction.x > 0 && !facingRight) {
            facingRight = true;
        } else if (direction.x < 0 && facingRight) {
            facingRight = false;
        }

        // برو به فریم بعدی
        animationTime += delta;

    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(animationTime);

        // بررسی کن آیا باید Flip بشه یا نه
        if ((facingRight && currentFrame.isFlipX()) || (!facingRight && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }

        sprite.setRegion(currentFrame);
        sprite.draw(batch);
    }

    public CollisionRect getRect() {
        return rect;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void takeDamage(int damage) {
        super.health -= damage;
    }

    public void dead() {
        if (App.getApp().isSoundEffect()) {
            dieSound.play(1.0f);
        }
    }




}
