package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Weapon {
    public Game game;
    private final Texture smgTexture;
    private Sprite spriteWeapon;
    private int ammoMax;
    private int ammo;
    private boolean isReloading = false;
    private float reloadTimer = 0f;
    private float reloadDuration = 1f; // 1 ثانیه
    private Sound reloadSound;


    public Weapon(Game game, Texture smgTexture, int ammo) {
        this.smgTexture = smgTexture;
        this.spriteWeapon = new Sprite(this.smgTexture);
        spriteWeapon.setX((float) Gdx.graphics.getWidth() / 2 );
        spriteWeapon.setY((float) Gdx.graphics.getHeight() / 2);
        spriteWeapon.setSize(50,50);
        this.game = game;
        this.ammo = ammo;
        this.ammoMax = ammo;
        this.reloadSound = Gdx.audio.newSound(Gdx.files.internal("sounds/1911-reload-6248.mp3"));
    }


    public void updateReload(float deltaTime) {
        if (isReloading) {
            reloadTimer += deltaTime;
            if (reloadTimer >= reloadDuration) {
                ammo = 10; // یا هر مقدار خشاب
                reloadTimer = 0;
                isReloading = false;
            }
        }
    }

    public void startReload() {
        if (!isReloading) {
            isReloading = true;
            reloadTimer = 0;
            reloadSound.play();
        }
    }

    public boolean isReloading() {
        return isReloading;
    }

    public Sprite getSpriteWeapon() {
        return spriteWeapon;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getAmmoMax() { return ammoMax; }

    public void setAmmo(int ammo){
        this.ammo = ammo;
    }
}
