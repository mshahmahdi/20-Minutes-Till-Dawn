package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.TalentMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.Enums.KeysController;
import com.tilldawn.model.MenuGameAssetManager;

public class TalentMenuView implements Screen {
    private float stateTime = 0.0f;
    private Stage stage;
    private final TalentMenuController controller;
    private final Label talentLabel;
    private final Label heroHintLabel;
    private final Label hero1Label;
    private final Label hero2Label;
    private final Label hero3Label;
    private final Label hero4Label;
    private final Label hero5Label;
    private final Image hero1Image;
    private final Image hero2Image;
    private final Image hero3Image;
    private final Image hero4Image;
    private final Image hero5Image;
    private final Label hero1DecriptionLabel;
    private final Label hero2DecriptionLabel;
    private final Label hero3DecriptionLabel;
    private final Label hero4DecriptionLabel;
    private final Label hero5DecriptionLabel;
    private final Label controllerLabel;
    private final Label moveUpLabel;
    private final Label moveDownLabel;
    private final Label moveLeftLabel;
    private final Label moveRightLabel;
    private final Label shootLabel;
    private final Label cheatLabel;
    private final Image moveUpImage;
    private final Image moveDownImage;
    private final Image moveLeftImage;
    private final Image moveRightImage;
    private final Image shootImage;
    private final Label cheatTimeLabel;
    private final Label cheatHpLabel;
    private final Label cheatLevelLabel;
    private final Label cheatBossFightLabel;
    private final Label cheatKillAllEnemiesLabel;
    private final Image cheatTimeImage;
    private final Image cheatHpImage;
    private final Image cheatLevelImage;
    private final Image cheatBossFightImage;
    private final Image cheatKillAllEnemiesImage;
    private final Label abilitiesLabel;
    private final Label vitalityLabel;
    private final Label damagerLabel;
    private final Label procreaseLabel;
    private final Label amocreaseLabel;
    private final Label speedyLabel;
    private final TextButton backButton;
    private final ShaderProgram grayShader;
    private final FrameBuffer fbo;
    public Table table;
    private final Music music;

    public TalentMenuView(TalentMenuController controller, Skin skin) {
        this.controller = controller;
        this.talentLabel = new Label("Talent & Game Hints", skin);
        this.heroHintLabel = new Label("Character Briefing", skin);
        this.hero1Label = new Label("DASHER", skin);
        this.hero2Label = new Label("DIAMOND", skin);
        this.hero3Label = new Label("LILITH", skin);
        this.hero4Label = new Label("SCARLET", skin);
        this.hero5Label = new Label("SHANA", skin);
        this.hero1Image = MenuGameAssetManager.getMenuGameAssetManager().dasherHero;
        this.hero2Image = MenuGameAssetManager.getMenuGameAssetManager().diamondHero;
        this.hero3Image = MenuGameAssetManager.getMenuGameAssetManager().lilithHero;
        this.hero4Image = MenuGameAssetManager.getMenuGameAssetManager().scarletHero;
        this.hero5Image = MenuGameAssetManager.getMenuGameAssetManager().shanaHero;
        this.hero1DecriptionLabel = new Label("HP = 2   Speed = 10", skin);
        this.hero2DecriptionLabel = new Label("HP = 7   Speed = 1", skin);
        this.hero3DecriptionLabel = new Label("HP = 5   Speed = 3", skin);
        this.hero4DecriptionLabel = new Label("HP = 5   Speed = 3", skin);
        this.hero5DecriptionLabel = new Label("HP = 4   Speed = 4", skin);
        this.controllerLabel = new Label("Controller Keys", skin);
        this.moveUpLabel = new Label("Move Up", skin);
        this.moveDownLabel = new Label("Move Down", skin);
        this.moveLeftLabel = new Label("Move Left", skin);
        this.moveRightLabel = new Label("Move Right", skin);
        this.shootLabel = new Label("Shooting", skin);
        if (KeysController.UP.getKey() == Input.Keys.UP) {
            this.moveUpImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/arrow-up_12233486.png"))));
        } else {
            this.moveUpImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/letter-w_12233605.png"))));
        }
        if (KeysController.DOWN.getKey() == Input.Keys.DOWN) {
            this.moveDownImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/arrow-down_12233484.png"))));
        } else {
            this.moveDownImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/letter-s_12233595.png"))));
        }
        if (KeysController.LEFT.getKey() == Input.Keys.LEFT) {
            this.moveLeftImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/arrow-back_12233488.png"))));
        } else {
            this.moveLeftImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/letter-a_12233547.png"))));
        }
        if (KeysController.RIGHT.getKey() == Input.Keys.RIGHT) {
            this.moveRightImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/arrow-right_12233492.png"))));
        } else {
            this.moveRightImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/letter-d_12233556.png"))));
        }
        if (KeysController.SHOOT.getKey() == Input.Keys.SPACE) {
            this.shootImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/space_12233469.png"))));
        } else {
            this.shootImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/mouse-clicker_6524730.png"))));
        }

        // Image image = new Image(new TextureRegionDrawable(new TextureRegion(texture)));

        this.cheatLabel = new Label("Cheats :", skin);
        this.cheatTimeLabel = new Label("cheat Time", skin);
        this.cheatTimeImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/letter-t_12233597.png"))));
        this.cheatLevelLabel = new Label("Cheat Level", skin);
        this.cheatLevelImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/letter-l_12233580.png"))));
        this.cheatHpLabel = new Label("cheat HP", skin);
        this.cheatHpImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/letter-h_12233568.png"))));
        this.cheatBossFightLabel = new Label("cheat Boss Fight", skin);
        this.cheatBossFightImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/letter-b_12233550.png"))));
        this.cheatKillAllEnemiesLabel = new Label("cheat Kill Enemies", skin);
        this.cheatKillAllEnemiesImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("keys/letter-k_12233577.png"))));
        this.abilitiesLabel = new Label("Abilities :", skin);
        this.vitalityLabel = new Label("Vitality : Max HP increased by 1", skin);
        this.damagerLabel = new Label("Damager : Weapon damage increased by 25% for 10 seconds", skin);
        this.procreaseLabel = new Label("Procrease : Adds 1 extra projectile", skin);
        this.amocreaseLabel = new Label("Amocrease : Increases max ammo by 5", skin);
        this.speedyLabel = new Label("Speedy : Doubles player movement speed for 10 seconds", skin);
        this.backButton = new TextButton("Back", skin);
        this.table = new Table(skin);
        this.music = App.getApp().getMusic();
        this.grayShader = new ShaderProgram(Gdx.files.internal("default.vert"), Gdx.files.internal("gray.frag"));
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        controller.setView(this);
    }

    @Override
    public void show() {
        Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();
        music.setLooping(true);
        music.play();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.top().center();
        table.padBottom(50); // کمی کمتر تا بالا فضا کم‌تر مصرف شه

        // عنوان اصلی
        talentLabel.setFontScale(2.5f); // کمی کوچک‌تر برای جا گرفتن بهتر
        talentLabel.setColor(Color.valueOf("0A9396"));
        talentLabel.setAlignment(Align.center);
        table.add(talentLabel).colspan(5).center();
        table.row().padTop(40);

        // زیرعنوان
        heroHintLabel.setFontScale(1.3f);
        heroHintLabel.setColor(Color.valueOf("E9D8A6"));
        table.add(heroHintLabel).colspan(5).center();
        table.row().pad(10, 0, 20, 0);

        // لیبل‌های اسم عکس‌ها
        hero1Label.setColor(Color.valueOf("CA6702"));
        hero2Label.setColor(Color.valueOf("CA6702"));
        hero3Label.setColor(Color.valueOf("CA6702"));
        hero4Label.setColor(Color.valueOf("CA6702"));
        hero5Label.setColor(Color.valueOf("CA6702"));

        table.add(hero1Label).pad(20).center();
        table.add(hero2Label).pad(20).center();
        table.add(hero3Label).pad(20).center();
        table.add(hero4Label).pad(20).center();
        table.add(hero5Label).pad(20).center();
        table.row();

        // عکس‌ها
        hero1Image.setSize(50, 50);
        hero2Image.setSize(50, 50);
        hero3Image.setSize(50, 50);

        table.add(hero1Image).pad(20).center();
        table.add(hero2Image).pad(20).center();
        table.add(hero3Image).pad(20).center();
        table.add(hero4Image).pad(20).center();
        table.add(hero5Image).pad(20).center();
        table.row().pad(5, 0, 5, 0);

        // توضیحات
        hero1DecriptionLabel.setColor(Color.valueOf("EE9B00"));
        hero2DecriptionLabel.setColor(Color.valueOf("EE9B00"));
        hero3DecriptionLabel.setColor(Color.valueOf("EE9B00"));
        hero4DecriptionLabel.setColor(Color.valueOf("EE9B00"));
        hero5DecriptionLabel.setColor(Color.valueOf("EE9B00"));

        table.add(hero1DecriptionLabel).pad(20).center();
        table.add(hero2DecriptionLabel).pad(20).center();
        table.add(hero3DecriptionLabel).pad(20).center();
        table.add(hero4DecriptionLabel).pad(20).center();
        table.add(hero5DecriptionLabel).pad(20).center();

        table.row().pad(10, 0, 30, 0);

        // ------------------ کنترل‌ها ------------------
        controllerLabel.setFontScale(1.3f);
        controllerLabel.setColor(Color.valueOf("E9D8A6"));
        table.add(controllerLabel).colspan(5).center(); // برای 5 آیتم
        table.row().pad(10, 0, 10, 0);

        // لیبل‌ها
        moveUpLabel.setColor(Color.valueOf("CA6702"));
        moveDownLabel.setColor(Color.valueOf("CA6702"));
        moveLeftLabel.setColor(Color.valueOf("CA6702"));
        moveRightLabel.setColor(Color.valueOf("CA6702"));
        shootLabel.setColor(Color.valueOf("CA6702"));

        table.add(moveUpLabel).pad(10);
        table.add(moveDownLabel).pad(10);
        table.add(moveLeftLabel).pad(10);
        table.add(moveRightLabel).pad(10);
        table.add(shootLabel).pad(10);
        table.row();

        // آیکون‌ها
        table.add(moveUpImage).width(60).height(60).pad(10);
        table.add(moveDownImage).width(60).height(60).pad(10);
        table.add(moveLeftImage).width(60).height(60).pad(10);
        table.add(moveRightImage).width(60).height(60).pad(10);
        table.add(shootImage).width(60).height(60).pad(10);
        table.row().pad(30, 0, 20, 0);

        // ------------------ تقلب‌ها ------------------
        cheatLabel.setFontScale(1.3f);
        cheatLabel.setColor(Color.valueOf("E9D8A6"));
        table.add(cheatLabel).colspan(5).center();
        table.row().pad(10, 0, 10, 0);

        cheatTimeLabel.setColor(Color.valueOf("CA6702"));
        cheatLevelLabel.setColor(Color.valueOf("CA6702"));
        cheatHpLabel.setColor(Color.valueOf("CA6702"));
        cheatBossFightLabel.setColor(Color.valueOf("CA6702"));
        cheatKillAllEnemiesLabel.setColor(Color.valueOf("CA6702"));

        table.add(cheatTimeLabel).pad(10);
        table.add(cheatLevelLabel).pad(10);
        table.add(cheatHpLabel).pad(10);
        table.add(cheatBossFightLabel).pad(10);
        table.add(cheatKillAllEnemiesLabel).pad(10);
        table.row();

        cheatTimeImage.setSize(60, 60);
        cheatLevelImage.setSize(60, 60);
        cheatHpImage.setSize(60, 60);
        cheatBossFightImage.setSize(60, 60);
        cheatKillAllEnemiesImage.setSize(60, 60);

        table.add(cheatTimeImage).width(60).height(60).pad(10);
        table.add(cheatLevelImage).width(60).height(60).pad(10);
        table.add(cheatHpImage).width(60).height(60).pad(10);
        table.add(cheatBossFightImage).width(60).height(60).pad(10);
        table.add(cheatKillAllEnemiesImage).width(60).height(60).pad(10);
        table.row().pad(30, 0, 20, 0);

        abilitiesLabel.setFontScale(1.3f);
        abilitiesLabel.setColor(Color.valueOf("E9D8A6"));
        table.add(abilitiesLabel).colspan(5).center();
        table.row();
        vitalityLabel.setColor(Color.valueOf("ff7b00"));
        damagerLabel.setColor(Color.valueOf("ff8800"));
        procreaseLabel.setColor(Color.valueOf("ff9500"));
        amocreaseLabel.setColor(Color.valueOf("ffa200"));
        speedyLabel.setColor(Color.valueOf("ffaa00"));
        table.add(vitalityLabel).colspan(5).center();
        table.row();
        table.add(damagerLabel).colspan(5).center();
        table.row();
        table.add(procreaseLabel).colspan(5).center();
        table.row();
        table.add(amocreaseLabel).colspan(5).center();
        table.row();
        table.add(speedyLabel).colspan(5).center();

        backButton.setPosition(10, Gdx.graphics.getHeight() - backButton.getHeight() - 10);
        backButton.setColor(Color.ROYAL);
        stage.addActor(backButton);

        stage.addActor(table);
        controller.handleTalentMenu();

    }

    @Override
    public void render(float v) {
        stateTime += v;
        float cycle = 5f;
        float animDuration = MenuGameAssetManager.getMenuGameAssetManager().menuAnimation.getAnimationDuration();
        TextureRegion currentFrame;
        if (stateTime % cycle < animDuration) {
            currentFrame = MenuGameAssetManager.getMenuGameAssetManager().menuAnimation.getKeyFrame(stateTime % cycle, false);
        } else {
            currentFrame = MenuGameAssetManager.getMenuGameAssetManager().menuAnimation.getKeyFrame(0, false);
        }

        if (App.getApp().isBlackAndWhiteMode()) {
            TextureRegion fboRegion = new TextureRegion(fbo.getColorBufferTexture());
            fboRegion.flip(false, true);
            fbo.begin();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Main.getBatch().setShader(null); // بدون shader
            Main.getBatch().begin();
            Main.getBatch().draw(currentFrame, 0, 0, 2304, 1440);
            Main.getBatch().end();
            stage.draw();
            fbo.end();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Main.getBatch().setShader(grayShader);
            Main.getBatch().begin();
            Main.getBatch().draw(fboRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Main.getBatch().end();
            Main.getBatch().setShader(null);
            stage.act(Gdx.graphics.getDeltaTime());

        } else {
            ScreenUtils.clear(0, 0, 0, 1);
            Main.getBatch().begin();
            Main.getBatch().draw(currentFrame, 0, 0, 2304, 1440);
            Main.getBatch().end();
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public TextButton getBackButton() {
        return backButton;
    }
}
