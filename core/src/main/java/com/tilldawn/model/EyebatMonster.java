package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class EyebatMonster extends Enemy {
    private Sprite sprite;
    private CollisionRect rect;
    private float x, y;
    private float speed = 80f; // کمی کندتر از پامکین
    private Texture texture;
    private Animation<TextureRegion> animation;
    private float animationTime = 0f;
    private boolean facingRight = true;

    private final Sound dieSound;
    private final Sound damageSound;
    private final ArrayList<BulletEnemy> BulletEnemys = new ArrayList<>();

    private float lastShotTime = 0f;

    public EyebatMonster(float x, float y) {
        super(50); // HP = 50
        this.x = x;
        this.y = y;
        this.texture = new Texture("Ebyte monster/T_EyeBat_0.png");
        this.damageSound = Gdx.audio.newSound(Gdx.files.internal("musics/080884_bullet-hit-39872.mp3"));
        this.sprite = new Sprite(texture);
        this.sprite.setSize(texture.getWidth() * 1.5f, texture.getHeight() * 1.5f);
        this.sprite.setPosition(x, y);
        this.rect = new CollisionRect(x, y, sprite.getWidth(), sprite.getHeight());
        this.animation = MenuGameAssetManager.getMenuGameAssetManager().eyeBat_monster;
        this.animation.setPlayMode(Animation.PlayMode.LOOP);
        this.dieSound = Gdx.audio.newSound(Gdx.files.internal("musics/monster-death-grunt-131480.mp3"));
    }

    public void update(float delta, Player player) {
        super.update(delta); // برای بی‌صدمه شدن
        Vector2 direction = new Vector2(player.getPosX() - sprite.getX(), player.getPosY() - sprite.getY()).nor();

        sprite.setX(sprite.getX() + direction.x * delta * speed);
        sprite.setY(sprite.getY() + direction.y * delta * speed);
        rect.move(sprite.getX(), sprite.getY());

        // شلیک گلوله هر ۳ ثانیه
        float now = TimeUtils.nanoTime() / 1_000_000_000f;
        if (now - lastShotTime > 3f) {
            BulletEnemys.add(new BulletEnemy(sprite.getX(), sprite.getY(), direction, 200f)); // سرعت گلوله
            lastShotTime = now;
        }

        for (BulletEnemy BulletEnemy : BulletEnemys) {
            BulletEnemy.update(delta);
            // اگر به پلیر خورد:
            if (BulletEnemy.collidesWith(player.getRect())) {
                player.takeDamage(1); // دمیج تیر
                if (App.getApp().isSoundEffect()) {
                    damageSound.play();
                }
                BulletEnemy.setDead(true);
            }
        }

        // حذف گلوله‌های مرده
        BulletEnemys.removeIf(BulletEnemy::isDead);

        // تنظیم انیمیشن
        if ((direction.x > 0 && !facingRight) || (direction.x < 0 && facingRight)) {
            facingRight = !facingRight;
        }

        animationTime += delta;
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(animationTime);
        if ((facingRight && currentFrame.isFlipX()) || (!facingRight && !currentFrame.isFlipX())) {
            currentFrame.flip(true, false);
        }

        sprite.setRegion(currentFrame);
        sprite.draw(batch);

        for (BulletEnemy BulletEnemy : BulletEnemys) {
            BulletEnemy.render(batch);
        }
    }

    public void takeDamage(int damage) {
        super.health -= damage;
    }

    public void dead() {
        if (App.getApp().isSoundEffect()) {
            dieSound.play(1.0f);
        }
    }

    public CollisionRect getRect() {
        return rect;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
