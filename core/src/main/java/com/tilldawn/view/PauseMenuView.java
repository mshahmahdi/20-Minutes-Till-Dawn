package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.tilldawn.controller.PauseMenuController;
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
import java.util.ArrayList;

public class PauseMenuView implements Screen {
    private float stateTime = 0.0f;
    private Stage stage;
    private final PauseMenuController controller;
    private final Label pauseMenuLabel;
    private final TextButton resumeButton;
    private final TextButton exitButton;
    private final TextButton giveUpButton;
    private final Label abilityLabel;
    private final Label abilities;
    private final Label blackAndWhiteLabel;
    private final CheckBox blackAndWhiteCheckBox;
    private final Label cheatLabel;
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
    private final ShaderProgram grayShader;
    private final FrameBuffer fbo;
    public Table table;
    private final Music music;

    public PauseMenuView(PauseMenuController controller, Skin skin, String abilities) {
        this.controller = controller;
        this.pauseMenuLabel = new Label("Pause Menu :", skin);
        this.resumeButton = new TextButton("Resume Game", skin);
        this.exitButton = new TextButton("Exit And Save Game", skin);
        this.giveUpButton = new TextButton("Give Up", skin);
        this.abilityLabel = new Label("Ability Acquired :", skin);
        this.abilities = new Label(abilities, skin);
        this.blackAndWhiteLabel = new Label("Black and White :", skin);
        this.blackAndWhiteCheckBox = new CheckBox("Black and White", skin);
        if (App.getApp().isBlackAndWhiteMode()) {
            this.blackAndWhiteCheckBox.setChecked(true);
        }
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
        this.table = new Table(skin);
        this.music = App.getApp().getMusic();
        this.grayShader = new ShaderProgram(Gdx.files.internal("default.vert"), Gdx.files.internal("gray.frag"));
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        this.table = new Table();
        controller.setView(this);
    }

    @Override
    public void show() {
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        music.setLooping(true);
        music.play();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.top().center().padTop(120);

        // عنوان Pause Menu
        pauseMenuLabel.setFontScale(3.2f);
        pauseMenuLabel.setColor(Color.valueOf("0A9396"));
        table.add(pauseMenuLabel).colspan(5).center().padBottom(80);
        table.row();

        // دکمه‌های اصلی
        resumeButton.setColor(Color.valueOf("E9D8A6"));
        exitButton.setColor(Color.valueOf("CA6702"));
        giveUpButton.setColor(Color.valueOf("AE2012"));

        float buttonWidth = 600f;
        float buttonHeight = 110f;

        table.add(resumeButton).colspan(5).width(buttonWidth).height(buttonHeight).padBottom(35);
        table.row();
        table.add(exitButton).colspan(5).width(buttonWidth).height(buttonHeight).padBottom(35);
        table.row();
        table.add(giveUpButton).colspan(5).width(buttonWidth).height(buttonHeight).padBottom(80);
        table.row();


        // نمایش قابلیت فعلی (وسط‌چین با table تو در تو)
        Table abilityRow = new Table();
        abilityLabel.setFontScale(2f);
        abilityLabel.setColor(Color.valueOf("E9D8A6"));
        abilities.setFontScale(2f);
        abilities.setColor(Color.valueOf("EE9B00"));
        abilityRow.add(abilityLabel).right().padRight(20);
        abilityRow.add(abilities).left();
        table.add(abilityRow).colspan(5).center().padBottom(40);
        table.row();

        // حالت سیاه و سفید (وسط‌چین با table تو در تو)
        Table bwRow = new Table();
        blackAndWhiteLabel.setFontScale(2f);
        blackAndWhiteLabel.setColor(Color.valueOf("E9D8A6"));
        blackAndWhiteCheckBox.setColor(Color.valueOf("E9D8A6"));
        bwRow.add(blackAndWhiteLabel).right().padRight(20);
        bwRow.add(blackAndWhiteCheckBox).left();
        table.add(bwRow).colspan(5).center().padBottom(60);
        table.row();

        // عنوان چیت‌ها
        cheatLabel.setFontScale(1.4f);
        cheatLabel.setColor(Color.valueOf("E9D8A6"));
        table.add(cheatLabel).colspan(5).center().padBottom(25);
        table.row();

        // لیبل‌های چیت
        Color cheatColor = Color.valueOf("CA6702");
        cheatTimeLabel.setColor(cheatColor);
        cheatLevelLabel.setColor(cheatColor);
        cheatHpLabel.setColor(cheatColor);
        cheatBossFightLabel.setColor(cheatColor);
        cheatKillAllEnemiesLabel.setColor(cheatColor);

        table.add(cheatTimeLabel).pad(10);
        table.add(cheatLevelLabel).pad(10);
        table.add(cheatHpLabel).pad(10);
        table.add(cheatBossFightLabel).pad(10);
        table.add(cheatKillAllEnemiesLabel).pad(10);
        table.row();

        // آیکون‌های چیت
        float iconSize = 60f;
        cheatTimeImage.setSize(iconSize, iconSize);
        cheatLevelImage.setSize(iconSize, iconSize);
        cheatHpImage.setSize(iconSize, iconSize);
        cheatBossFightImage.setSize(iconSize, iconSize);
        cheatKillAllEnemiesImage.setSize(iconSize, iconSize);

        table.add(cheatTimeImage).width(iconSize).height(iconSize).pad(10);
        table.add(cheatLevelImage).width(iconSize).height(iconSize).pad(10);
        table.add(cheatHpImage).width(iconSize).height(iconSize).pad(10);
        table.add(cheatBossFightImage).width(iconSize).height(iconSize).pad(10);
        table.add(cheatKillAllEnemiesImage).width(iconSize).height(iconSize).pad(10);
        table.row().padBottom(40);

        // اضافه کردن به stage
        stage.addActor(table);
        controller.handlePause();

    }

    @Override
    public void render(float v) {
        Main.getBatch().setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

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

    public TextButton getResumeButton() {
        return resumeButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public TextButton getGiveUpButton() {
        return giveUpButton;
    }

    public CheckBox getBlackAndWhiteCheckBox() {
        return blackAndWhiteCheckBox;
    }
}
