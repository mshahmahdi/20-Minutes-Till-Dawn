package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;

public class ShotGunWeapon extends Weapon{
    public ShotGunWeapon(Game game, Texture smgTexture, int ammo, int reloadDuration) {
        super(game, smgTexture, ammo, reloadDuration);
    }
}
