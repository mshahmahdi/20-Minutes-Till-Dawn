package com.tilldawn.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tilldawn.Main;
import com.tilldawn.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeaponController {
    private Weapon weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Sound gunShotSound;
    private Sound gunShotGunSound;
    private Sound damagedSound;
    private Animation<Texture> deleteMonsterAnimation;
    float animTime = 5f;
    boolean showAnim = true;
    private int gunNumber;

    public WeaponController(Weapon weapon, int gunNumber) {
        this.weapon = weapon;
        this.gunNumber = gunNumber;
        this.gunShotSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire-single-shot-colt-peacemaker-94951.mp3"));
        this.gunShotGunSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shotgun-firing-3-14483.mp3"));
        this.damagedSound = Gdx.audio.newSound(Gdx.files.internal("musics/080998_bullet-hit-39870.mp3"));
        this.deleteMonsterAnimation = MenuGameAssetManager.getMenuGameAssetManager().delete_monster;
    }

    public Weapon getWeapon() {
        return this.weapon;
    }

    public void update(float playerX, float playerY, float deltaTime, Player player) {
        weapon.updateReload(deltaTime);

        Sprite weaponSprite = weapon.getSpriteWeapon();
        float offsetX = 30f, offsetY = 10f;
        weaponSprite.setPosition(playerX + offsetX, playerY + offsetY);
        weaponSprite.draw(Main.getBatch());

        updateBullets(deltaTime, player);
    }

    public void handleWeaponRotation(int x, int y, float playerX, float playerY, Camera camera) {
        Sprite weaponSprite = weapon.getSpriteWeapon();

        // تبدیل مختصات موس از صفحه (Screen) به مختصات دنیای بازی (World)
        Vector3 mouseWorld = new Vector3(x, y, 0);
        camera.unproject(mouseWorld);

        float worldMouseX = mouseWorld.x;
        float worldMouseY = mouseWorld.y;

        // موقعیت اسلحه با فاصله از بازیکن
        float offsetX = 30f;
        float offsetY = 10f;

        float weaponX = playerX + offsetX;
        float weaponY = playerY + offsetY;

        weaponSprite.setPosition(weaponX, weaponY);

        // زاویه بین اسلحه و موس
        float angle = MathUtils.atan2(worldMouseY - weaponY, worldMouseX - weaponX);
        weaponSprite.setRotation(angle * MathUtils.radiansToDegrees);
    }

    public void handleWeaponShoot(int screenX, int screenY, Camera camera, boolean isShotgun) {
        if (weapon.isReloading()) return;
        if (weapon.getAmmo() <= 0 && !App.getApp().isAutoReload()) {
            return;
        }
        if (App.getApp().isAutoReload() && weapon.getAmmo() <= 0) {
            weapon.startReload();
        }
        Sprite weaponSprite = weapon.getSpriteWeapon();
        float bulletStartX = weaponSprite.getX() + weaponSprite.getWidth() / 2;
        float bulletStartY = weaponSprite.getY() + weaponSprite.getHeight() / 2;

        Vector3 mouseWorld = new Vector3(screenX, screenY, 0);
        camera.unproject(mouseWorld);

        Vector2 baseDirection = new Vector2(mouseWorld.x - bulletStartX, mouseWorld.y - bulletStartY).nor();

        if (isShotgun) {
            int pelletCount = weapon.getPelletCount() + 4;
            float spread = 15f;
            for (int i = 0; i < pelletCount; i++) {
                float angleOffset = (i - (pelletCount - 1) / 2f) * spread;
                Vector2 rotatedDirection = baseDirection.cpy().rotateDeg(angleOffset);
                bullets.add(new Bullet(bulletStartX, bulletStartY, rotatedDirection, gunNumber));
            }
            weapon.setAmmo(weapon.getAmmo() - 1);
            if (App.getApp().isSoundEffect()) {
                gunShotGunSound.play(1.0f); // حجم ۱.۰ یعنی صدای کامل
            }
        } else {
            int pelletCount = weapon.getPelletCount();
            float spread = 15f;
            for (int i = 0; i < pelletCount; i++) {
                float angleOffset = (i - (pelletCount - 1) / 2f) * spread;
                Vector2 rotatedDirection = baseDirection.cpy().rotateDeg(angleOffset);
                bullets.add(new Bullet(bulletStartX, bulletStartY, rotatedDirection, gunNumber));
            }
            weapon.setAmmo(weapon.getAmmo() - 1);
            if (App.getApp().isSoundEffect()) {
                gunShotSound.play(1.0f); // حجم ۱.۰ یعنی صدای کامل
            }
        }
    }

    public void updateBullets(float delta, Player player) {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<PumpkinMonster> monstersToRemove = new ArrayList<>();
        List<EyebatMonster> eyebatMonstersToRemove = new ArrayList<>();

        for (Bullet b : bullets) {
            b.update();
            for (PumpkinMonster pumpkin : weapon.game.getPumpkinMonsters()) {
                if (b.getRect().collidesWith(pumpkin.getRect())) {
                    pumpkin.takeDamage(b.getDamage());
                    pumpkin.getSprite().setPosition(
                        pumpkin.getSprite().getX() + (Math.signum(pumpkin.getSprite().getX() - player.getPosX())) * 30,
                        pumpkin.getSprite().getY() + (Math.signum(pumpkin.getSprite().getY() - player.getPosY())) * 30);
                    pumpkin.getRect().move(pumpkin.getSprite().getX(), pumpkin.getSprite().getY());
                    if (pumpkin.isDead()) {
                        player.addKills(1);
                        Texture itemTexture = new Texture("sprite/T/T_ChargeUp_0.png"); // جایگزین عکس مورد نظر
                        DroppedItem item = new DroppedItem(itemTexture,
                            pumpkin.getSprite().getX() + 10, // کمی کنارشه
                            pumpkin.getSprite().getY());
                        weapon.game.getDroppedItems().add(item);
                        pumpkin.dead();
                        animTime += Gdx.graphics.getDeltaTime();
                        Texture currentFrame = deleteMonsterAnimation.getKeyFrame(animTime, false);
                        Main.getBatch().draw(currentFrame, pumpkin.getSprite().getX(), pumpkin.getSprite().getY(),
                            pumpkin.getSprite().getWidth(),
                            pumpkin.getSprite().getHeight()); // مختصات و اندازه مورد نظر
                        monstersToRemove.add(pumpkin);
                    }
                    if (App.getApp().isSoundEffect()) {
                        damagedSound.play(1.0f);
                    }
                    bulletsToRemove.add(b);
                    break;
                }
            }

            for (EyebatMonster eyebatMonster : weapon.game.getEyebatMonsters()) {
                if (b.getRect().collidesWith(eyebatMonster.getRect())) {
                    eyebatMonster.takeDamage(b.getDamage());
                    eyebatMonster.getSprite().setPosition(
                        eyebatMonster.getSprite().getX() + (Math.signum(eyebatMonster.getSprite().getX() - player.getPosX())) * 30,
                        eyebatMonster.getSprite().getY() + (Math.signum(eyebatMonster.getSprite().getY() - player.getPosY())) * 30);
                    eyebatMonster.getRect().move(eyebatMonster.getSprite().getX(), eyebatMonster.getSprite().getY());
                    if (eyebatMonster.isDead()) {
                        player.addKills(1);
                        Texture itemTexture = new Texture("sprite/T/T_ChargeUp_0.png"); // جایگزین عکس مورد نظر
                        DroppedItem item = new DroppedItem(itemTexture,
                            eyebatMonster.getSprite().getX() + 10, // کمی کنارشه
                            eyebatMonster.getSprite().getY());
                        weapon.game.getDroppedItems().add(item);
                        eyebatMonster.dead();
                        animTime += Gdx.graphics.getDeltaTime();
                        Texture currentFrame = deleteMonsterAnimation.getKeyFrame(animTime, false);
                        Main.getBatch().draw(currentFrame, eyebatMonster.getSprite().getX(), eyebatMonster.getSprite().getY(),
                            eyebatMonster.getSprite().getWidth(),
                            eyebatMonster.getSprite().getHeight()); // مختصات و اندازه مورد نظر
                        eyebatMonstersToRemove.add(eyebatMonster);
                    }
                    if (App.getApp().isSoundEffect()) {
                        damagedSound.play(1.0f);
                    }
                    bulletsToRemove.add(b);
                    break;
                }
            }

            if (getWeapon().game.getFinalBoss() != null) {
                if (b.getRect().collidesWith(getWeapon().game.getFinalBoss().getRect())) {
                    getWeapon().game.getFinalBoss().takeDamage(b.getDamage(), player);
                    getWeapon().game.getFinalBoss().getSprite().setPosition(
                        getWeapon().game.getFinalBoss().getSprite().getX() + (Math.signum(getWeapon().game.getFinalBoss().getSprite().getX() - player.getPosX())) * 30,
                        getWeapon().game.getFinalBoss().getSprite().getY() + (Math.signum(getWeapon().game.getFinalBoss().getSprite().getY() - player.getPosY())) * 30);
                    getWeapon().game.getFinalBoss().getRect().move(getWeapon().game.getFinalBoss().getSprite().getX(), getWeapon().game.getFinalBoss().getSprite().getY());
                    if (getWeapon().game.getFinalBoss().isDead()) {
                        player.addKills(1);
                        Texture itemTexture = new Texture("sprite/T/T_ChargeUp_0.png"); // جایگزین عکس مورد نظر
                        DroppedItem item = new DroppedItem(itemTexture,
                            getWeapon().game.getFinalBoss().getSprite().getX() + 10, // کمی کنارشه
                            getWeapon().game.getFinalBoss().getSprite().getY());
                        weapon.game.getDroppedItems().add(item);
                        getWeapon().game.getFinalBoss().dead();
                        animTime += Gdx.graphics.getDeltaTime();
                        Texture currentFrame = deleteMonsterAnimation.getKeyFrame(animTime, false);
                        Main.getBatch().draw(currentFrame, getWeapon().game.getFinalBoss().getSprite().getX(), getWeapon().game.getFinalBoss().getSprite().getY(),
                            getWeapon().game.getFinalBoss().getSprite().getWidth(),
                            getWeapon().game.getFinalBoss().getSprite().getHeight()); // مختصات و اندازه مورد نظر
                        weapon.game.setFinalBoss(null);
                    }
                    if (App.getApp().isSoundEffect()) {
                        damagedSound.play(1.0f);
                    }
                    bulletsToRemove.add(b);
                    break;
                }
            }

            b.getSprite().draw(Main.getBatch());
        }
        bullets.removeAll(bulletsToRemove);
        weapon.game.getPumpkinMonsters().removeAll(monstersToRemove);
        weapon.game.getEyebatMonsters().removeAll(eyebatMonstersToRemove);
    }
}
