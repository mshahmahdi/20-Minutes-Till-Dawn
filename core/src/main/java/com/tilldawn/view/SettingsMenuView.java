package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;

import com.tilldawn.controller.SettingsMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.Enums.KeysController;
import com.tilldawn.model.MenuGameAssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;

import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SettingsMenuView implements Screen {
    private float stateTime = 0.0f;
    private Stage stage;
    private final Image gameTitle;
    private final Label settingsLabel;
    private final Label volumeLabel;
    private final Slider volumeSlider;
    private final Label choseMusicLabel;
    private final SelectBox<String> musicSelectBox;
    private final Label sfxLabel;
    private final CheckBox sfxCheckbox;
    private final Label choseControllerLabel;
    private final Label chosenControllerLabel2;
    private final SelectBox<String> controllerSelectBox;
    private final Image controllerImage;
    private final Image controllerImage2;
    private final SelectBox<String> controllerSelectBox2;
    private final Image controllerImage3;
    private final Image controllerImage4;
    private final Label realoadControllerLabel;
    private final Image reloadController;
    private final Image reloadController2;
    private final SelectBox reloadControllerSelectBox;
    private final Label autoAimeLabel;
    private final Image autoAime;
    private final Image autoAime2;
    private final SelectBox autoAimeSelectBox;
    private final Label autoReloadLabel;
    private final CheckBox autoReloadCheckbox;
    private final Label blackAndWhiteLabel;
    private final CheckBox blackAndWhiteCheckbox;
    private final TextButton backButton;
    private final ShaderProgram grayShader;
    private final FrameBuffer fbo;
    public Table table;
    private Music music;
    private final SettingsMenuController controller;


    public SettingsMenuView(SettingsMenuController controller, Skin skin) {
        this.controller = controller;
        this.gameTitle = new Image(MenuGameAssetManager.getMenuGameAssetManager().gameTitle);
        this.settingsLabel = new Label("Settings : ", skin);
        this.volumeLabel = new Label("Music Volume : ", skin);
        this.volumeSlider = new Slider(0, 10, 1, false, skin);
        this.choseMusicLabel = new Label("Select Music : ", skin);
        this.musicSelectBox = new SelectBox<>(skin);
        this.musicSelectBox.setItems("Music 1", "Music 2", "Music 3", "Music 4");
        this.musicSelectBox.setSelectedIndex(2);
        this.sfxLabel = new Label("Effects Sound : ", skin);
        this.sfxCheckbox = new CheckBox("Sound Effects", skin);
        this.choseControllerLabel = new Label("Select Controller for movement : ", skin);
        this.chosenControllerLabel2 = new Label("Select Controller for shooting : ", skin);
        this.controllerSelectBox = new SelectBox<>(skin);
        this.controllerSelectBox.setItems("WASD keys", "Arrow Keys");
        this.controllerImage = new Image(MenuGameAssetManager.getMenuGameAssetManager().controller1);
        this.controllerImage2 = new Image(MenuGameAssetManager.getMenuGameAssetManager().controller2);
        this.controllerImage3 = new Image(MenuGameAssetManager.getMenuGameAssetManager().controller3);
        this.controllerImage4 = new Image(MenuGameAssetManager.getMenuGameAssetManager().controllerE);
        this.autoAimeLabel = new Label("Select Controller for auto aim :  ", skin);
        this.autoAime = new Image(MenuGameAssetManager.getMenuGameAssetManager().controller4);
        this.autoAime2 = new Image(MenuGameAssetManager.getMenuGameAssetManager().controllerF);
        this.reloadControllerSelectBox = new SelectBox<>(skin);
        this.reloadControllerSelectBox.setItems("R Key", "Q Key");
        this.realoadControllerLabel = new Label("Select Controller for reload : ", skin);
        this.reloadController = new Image(MenuGameAssetManager.getMenuGameAssetManager().controllerR);
        this.reloadController2 = new Image(MenuGameAssetManager.getMenuGameAssetManager().controllerQ);
        this.autoAimeSelectBox = new SelectBox<>(skin);
        this.autoAimeSelectBox.setItems("Space Key", "F Key");
        this.controllerSelectBox2 = new SelectBox<>(skin);
        this.controllerSelectBox2.setItems("Left Click", "Space");
        this.autoReloadLabel = new Label("Auto Reload : ", skin);
        this.autoReloadCheckbox = new CheckBox("Auto Reload", skin);
        this.blackAndWhiteLabel = new Label("Black & White Mode :  ", skin);
        this.blackAndWhiteCheckbox = new CheckBox("Black & White Mode", skin);
        this.backButton = new TextButton("Back", skin);
        this.music = App.getApp().getMusic();
        this.grayShader = new ShaderProgram(Gdx.files.internal("default.vert"), Gdx.files.internal("gray.frag"));
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        this.table = new Table();
        controller.setView(this);
    }


    @Override
    public void show() {
        music.setLooping(true);
        music.play();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.top();
        table.center();

        settingsLabel.setFontScale(3f);
        settingsLabel.setPosition(1000, 1350);
        settingsLabel.setColor(Color.valueOf("0A9396"));
        stage.addActor(settingsLabel);
        table.row().pad(50, 0, 10, 0);

        volumeLabel.setColor(Color.valueOf("e09f3e"));
        table.add(volumeLabel);
        volumeSlider.setValue(App.getApp().getMusic().getVolume() * 10);
        table.add(volumeSlider).width(600);
        table.row().pad(50, 0, 10, 0);

        table.add(choseMusicLabel);
        choseMusicLabel.setColor(Color.valueOf("e09f3e"));
        table.add(musicSelectBox);
        table.row().pad(50, 0, 10, 0);

        table.add(sfxLabel);
        if (App.getApp().isSoundEffect()) {
            sfxCheckbox.setChecked(true);
        }
        sfxLabel.setColor(Color.valueOf("e09f3e"));
        table.add(sfxCheckbox);
        table.row().pad(50, 0, 10, 0);

        table.add(choseControllerLabel);
        choseControllerLabel.setColor(Color.valueOf("e09f3e"));
        if (KeysController.UP.getKey() == Input.Keys.W) {
            controllerSelectBox.setSelectedIndex(0);
        } else {
            controllerSelectBox.setSelectedIndex(1);
        }
        Table imageTable = new Table();
        imageTable.left();
        imageTable.add(controllerImage).width(100).height(100).padRight(100);
        imageTable.add(controllerImage2).width(100).height(100);
        table.add(imageTable);
        table.add(controllerSelectBox);
        table.row().pad(50, 0, 10, 0);

        table.add(chosenControllerLabel2);
        chosenControllerLabel2.setColor(Color.valueOf("e09f3e"));
        if (KeysController.SHOOT.getKey() == Input.Buttons.LEFT) {
            controllerSelectBox2.setSelectedIndex(0);
        } else {
            controllerSelectBox2.setSelectedIndex(1);
        }
        Table imageTable2 = new Table();
        imageTable2.left();
        imageTable2.add(controllerImage3).width(100).height(100).padRight(100);
        imageTable2.add(controllerImage4).width(100).height(100);
        table.add(imageTable2);
        table.add(controllerSelectBox2);
        table.row().pad(50, 0, 10, 0);

        table.add(realoadControllerLabel);
        realoadControllerLabel.setColor(Color.valueOf("e09f3e"));
        if (KeysController.RELOAD.getKey() == Input.Keys.R) {
            reloadControllerSelectBox.setSelectedIndex(0);
        } else {
            reloadControllerSelectBox.setSelectedIndex(1);
        }
        Table imageTable3 = new Table();
        imageTable3.left();
        imageTable3.add(reloadController).width(100).height(100).padRight(100);
        imageTable3.add(reloadController2).width(100).height(100);
        table.add(imageTable3);
        table.add(reloadControllerSelectBox);
        table.row().pad(50, 0, 10, 0);

        table.add(autoAimeLabel);
        autoAimeLabel.setColor(Color.valueOf("e09f3e"));
        if (KeysController.AUTO_AIM.getKey() == Input.Keys.SPACE) {
            autoAimeSelectBox.setSelectedIndex(0);
        } else {
            autoAimeSelectBox.setSelectedIndex(1);
        }
        Table imageTable4 = new Table();
        imageTable4.left();
        imageTable4.add(autoAime).width(100).height(100).padRight(100);
        imageTable4.add(autoAime2).width(100).height(100);
        table.add(imageTable4);
        table.add(autoAimeSelectBox);
        table.row().pad(50, 0, 10, 0);

        table.add(autoReloadLabel);
        autoReloadLabel.setColor(Color.valueOf("e09f3e"));
        table.add(autoReloadCheckbox);
        table.row().pad(50, 0, 10, 0);

        table.add(blackAndWhiteLabel);
        if (App.getApp().isBlackAndWhiteMode()) {
            blackAndWhiteCheckbox.setChecked(true);
        }
        blackAndWhiteLabel.setColor(Color.valueOf("e09f3e"));
        table.add(blackAndWhiteCheckbox);
        table.row().pad(50, 0, 10, 0);

        backButton.setPosition(10, Gdx.graphics.getHeight() - backButton.getHeight() - 10);
        if (App.getApp().isAutoReload()) {
            autoReloadCheckbox.setChecked(true);
        }
        backButton.setColor(Color.ROYAL);
        stage.addActor(backButton);

        stage.addActor(table);

        controller.handleSettingsMenu();

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

    // Auxiliary functions :

    public float getStateTime() {
        return stateTime;
    }

    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    public SelectBox<String> getMusicSelectBox() {
        return musicSelectBox;
    }

    public CheckBox getSfxCheckbox() {
        return sfxCheckbox;
    }

    public SelectBox<String> getControllerSelectBox() {
        return controllerSelectBox;
    }

    public SelectBox<String> getControllerSelectBox2() {
        return controllerSelectBox2;
    }

    public CheckBox getAutoReloadCheckbox() {
        return autoReloadCheckbox;
    }

    public CheckBox getBlackAndWhiteCheckbox() {
        return blackAndWhiteCheckbox;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public Music getMusic() {
        return music;
    }

    public SelectBox getReloadControllerSelectBox() {
        return reloadControllerSelectBox;
    }

    public SelectBox getAutoAimeSelectBox() {
        return autoAimeSelectBox;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
