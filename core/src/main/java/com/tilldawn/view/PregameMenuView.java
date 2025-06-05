package com.tilldawn.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.tilldawn.controller.PregameMenuController;
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

import java.awt.*;

public class PregameMenuView implements Screen {
    private float stateTime = 0.0f;
    private Stage stage;
    private final PregameMenuController controller;
    private final Label pregameLabel;
    private final Label heroSelectionLabel;
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
    private final SelectBox<String> heroesSelect;
    private final Label gunSelectionLabel;
    private final Label gun1Label;
    private final Label gun2Label;
    private final Label gun3Label;
    private final Image gun1Image;
    private final Image gun2Image;
    private final Image gun3Image;
    private final SelectBox<String> gunesSelect;
    private final Label timeSelectionLabel;
    private final Label time1Label;
    private final Label time2Label;
    private final Label time3Label;
    private final Label time4Label;
    private final SelectBox<String> timeSelection;
    private final TextButton startGameButton;
    private final TextButton backButton;
    private final ShaderProgram grayShader;
    private final FrameBuffer fbo;
    public Table table;
    private final Music music;

    public PregameMenuView(PregameMenuController pregameMenuController, Skin skin) {
        this.controller = pregameMenuController;
        this.pregameLabel = new Label("Pregame Menu", skin);
        this.heroSelectionLabel = new Label("Chose your Hero :", skin);
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
        this.heroesSelect = new SelectBox<>(skin);
        this.heroesSelect.setItems("DASHER", "DIAMOND", "LILITH", "SCARLET", "SHANA");
        this.gunSelectionLabel = new Label("Chose your Gun :", skin);
        this.gun1Label = new Label("REVOLVER", skin);
        this.gun2Label = new Label("SHOTGUN", skin);
        this.gun3Label = new Label("SMGs DUAL", skin);
        this.gun1Image = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("guns/T_CloseQuarters_Gun.png"))));
        this.gun2Image = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("guns/T_BatgunVamp_Gun.png"))));
        this.gun3Image = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("guns/T_Trickshot_Gun.png"))));
        this.gunesSelect = new SelectBox<>(skin);
        this.gunesSelect.setItems("REVOLVER", "SHOTGUN", "SMGs DUAL");
        this.timeSelectionLabel = new Label("Chose your Play Time :", skin);
        this.time1Label = new Label("20 minutes", skin);
        this.time2Label = new Label("10 minutes", skin);
        this.time3Label = new Label("5 minutes", skin);
        this.time4Label = new Label("2 minutes", skin);
        this.timeSelection = new SelectBox<>(skin);
        this.timeSelection.setItems("20 minutes", "10 minutes", "5 minutes", "2 minutes");
        this.startGameButton = new TextButton("Start Game", skin);
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
        table.padBottom(50);
        //table.padTop(50); // فاصله از بالای صفحه

        // عنوان اصلی
        pregameLabel.setFontScale(3f);
        pregameLabel.setColor(Color.valueOf("0A9396"));
        table.add(pregameLabel).colspan(5).center();
        table.row().padTop(40);

        // بخش انتخاب قهرمان
        heroSelectionLabel.setFontScale(1.5f);
        heroSelectionLabel.setColor(Color.valueOf("E9D8A6"));
        table.add(heroSelectionLabel).colspan(5).center();
        table.row().padTop(20);

        // لیبل‌های قهرمان
        hero1Label.setColor(Color.valueOf("CA6702"));
        hero2Label.setColor(Color.valueOf("CA6702"));
        hero3Label.setColor(Color.valueOf("CA6702"));
        hero4Label.setColor(Color.valueOf("CA6702"));
        hero5Label.setColor(Color.valueOf("CA6702"));
        table.add(hero1Label).pad(30);
        table.add(hero2Label).pad(30);
        table.add(hero3Label).pad(30);
        table.add(hero4Label).pad(30);
        table.add(hero5Label).pad(30);
        table.row();

        // عکس‌های قهرمان
        hero1Image.setSize(80, 80);
        hero2Image.setSize(80, 80);
        hero3Image.setSize(80, 80);
        hero4Image.setSize(80, 80);
        hero5Image.setSize(80, 80);
        table.add(hero1Image).pad(30);
        table.add(hero2Image).pad(30);
        table.add(hero3Image).pad(30);
        table.add(hero4Image).pad(30);
        table.add(hero5Image).pad(30);
        table.row().padTop(20);

        // منوی انتخاب قهرمان
        table.add(heroesSelect).colspan(5).center();
        table.row().padTop(40);

        // بخش انتخاب اسلحه
        gunSelectionLabel.setFontScale(1.5f);
        gunSelectionLabel.setColor(Color.valueOf("E9D8A6"));
        table.add(gunSelectionLabel).colspan(5).center();
        table.row().padTop(20);

        // لیبل‌های اسلحه
        gun1Label.setColor(Color.valueOf("CA6702"));
        gun2Label.setColor(Color.valueOf("CA6702"));
        gun3Label.setColor(Color.valueOf("CA6702"));
        table.add(gun1Label).pad(10).colspan(1).center();
        table.add(gun2Label).pad(10).colspan(1).center();
        table.add(gun3Label).pad(10).colspan(1).center();
        table.row();

        // عکس‌های اسلحه
        table.add(gun1Image).width(100).height(100).pad(10);
        table.add(gun2Image).width(100).height(100).pad(10);
        table.add(gun3Image).width(100).height(100).pad(10);
        table.row().padTop(20);

        // منوی انتخاب اسلحه
        table.add(gunesSelect).colspan(5).center();
        table.row().padTop(40);

        // بخش انتخاب زمان
        timeSelectionLabel.setFontScale(1.5f);
        timeSelectionLabel.setColor(Color.valueOf("E9D8A6"));
        table.add(timeSelectionLabel).colspan(5).center();
        table.row().padTop(20);

        // لیبل‌های زمان
        time1Label.setColor(Color.valueOf("CA6702"));
        time2Label.setColor(Color.valueOf("CA6702"));
        time3Label.setColor(Color.valueOf("CA6702"));
        time4Label.setColor(Color.valueOf("CA6702"));
        table.add(time1Label).pad(10);
        table.add(time2Label).pad(10);
        table.add(time3Label).pad(10);
        table.add(time4Label).pad(10);
        table.row().padTop(20);

        // انتخاب زمان از DropDown
        table.add(timeSelection).colspan(5).center();
        table.row().padTop(50);

        // دکمه شروع بازی
        startGameButton.setColor(Color.valueOf("E9D8A6"));
        table.add(startGameButton).colspan(5).center().width(500).height(110);


        backButton.setPosition(10, Gdx.graphics.getHeight() - backButton.getHeight() - 10);
        backButton.setColor(Color.ROYAL);
        stage.addActor(backButton);

        stage.addActor(table);

        controller.handlePregameMenu();
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

    public SelectBox<String> getTimeSelection() {
        return timeSelection;
    }

    public SelectBox<String> getGunesSelect() {
        return gunesSelect;
    }

    public TextButton getStartGameButton() {
        return startGameButton;
    }

    public SelectBox<String> getHeroesSelect() {
        return heroesSelect;
    }
}
