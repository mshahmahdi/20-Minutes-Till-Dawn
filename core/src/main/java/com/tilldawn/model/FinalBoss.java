package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class FinalBoss extends Enemy{
    private Sprite sprite;
    private CollisionRect rect;
    private float speed = 50f;
    private float dashSpeed = 400f;
    private Vector2 direction;

    private float x, y;
    private Animation<TextureRegion> animation;
    private float animationTime = 0f;
    private boolean facingRight = true;
    private final Sound dieSound;

    private float dashTimer = 0f;
    private float dashCooldown = 5f; // هر ۵ ثانیه یک بار دش
    private boolean dashing = false;
    private float dashDuration = 0.5f; // دش برای ۰.۵ ثانیه ادامه داره
    private float dashDurationTimer = 0f;

    public FinalBoss(float x, float y) {
        super(400); // HP باس
        this.x = x;
        this.y = y;
        this.direction = new Vector2(0, 0);
        Texture texture = new Texture("Elder Brain monster/T_Yog_3.png"); // یک تکسچر ساده برای شروع
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
        this.sprite.setSize(texture.getWidth(), texture.getHeight());
        this.rect = new CollisionRect(x, y, sprite.getWidth(), sprite.getHeight());
        this.animation = MenuGameAssetManager.getMenuGameAssetManager().finalBossAnim;
        this.animation.setPlayMode(Animation.PlayMode.LOOP);
        this.dieSound = Gdx.audio.newSound(Gdx.files.internal("musics/monster-death-grunt-131480.mp3"));
    }

    public void update(float delta, Player player) {
        dashTimer += delta;

        direction = new Vector2(
            player.getPosX() - sprite.getX(),
            player.getPosY() - sprite.getY()
        ).nor();

        // شروع دش
        if (!dashing && dashTimer >= dashCooldown) {
            dashing = true;
            dashTimer = 0f;
            dashDurationTimer = 0f;
        }

        if (dashing) {
            dashDurationTimer += delta;

            sprite.setX(sprite.getX() + direction.x * delta * dashSpeed);
            sprite.setY(sprite.getY() + direction.y * delta * dashSpeed);

            if (dashDurationTimer >= dashDuration) {
                dashing = false;
            }
        } else {
            sprite.setX(sprite.getX() + direction.x * delta * speed);
            sprite.setY(sprite.getY() + direction.y * delta * speed);
        }

        // تنظیم جهت نگاه (رو به کاراکتر)
        if (direction.x > 0 && !facingRight) {
            facingRight = true;
        } else if (direction.x < 0 && facingRight) {
            facingRight = false;
        }

        // برخورد و انیمیشن
        rect.move(sprite.getX(), sprite.getY());
        animationTime += delta;
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(animationTime);

        // بررسی فلیپ
        if ((facingRight && currentFrame.isFlipX()) || (!facingRight && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }

        sprite.setRegion(currentFrame);
        sprite.draw(batch);
    }

    public CollisionRect getRect() {
        return rect;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void dead() {
        dieSound.play(1.0f);
    }
}
