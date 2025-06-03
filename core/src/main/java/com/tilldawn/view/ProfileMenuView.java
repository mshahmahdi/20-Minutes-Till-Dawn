package com.tilldawn.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.ProfileMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.model.Service.DesktopFileChooser;

public class ProfileMenuView implements Screen {

    private float stateTime = 0.0f;
    private Stage stage;
    private final ProfileMenuController controller;
    private final Label profileMenuLabel;
    private final Label currentUsername;
    private final Label changeUsername;
    private final TextField changeUsernameField;
    private final Label changeUsernameMessage;
    private final TextButton changeUsernameButton;
    private final Label changePasswordLabel;
    private final TextField changePasswordField;
    private final Label changePasswordMessage;
    private final TextButton changePasswordButton;
    private final Label currentAvatar;
    private Image currentAvatarImage;
    private final Label choseAvatar;
    private final SelectBox<String> avatarsSelectBox;
    private final TextButton avatarButton;
    private final TextButton deleteAccountButton;
    private final TextButton backButton;
    private final ShaderProgram grayShader;
    private final FrameBuffer fbo;
    public Table table;
    private final Music music;

    public ProfileMenuView(ProfileMenuController profileMenuController, Skin skin) {
        this.controller = profileMenuController;
        this.profileMenuLabel = new Label("Profile Menu :", skin);
        this.currentUsername = new Label("Current Username : " + App.getApp().getLoggedInPlayer().getUsername(), skin);
        this.changeUsername = new Label("Change Username : ", skin);
        this.changeUsernameField = new TextField("enter your new username", skin);
        this.changeUsernameButton = new TextButton("Change Username", skin);
        this.changeUsernameMessage = new Label("", skin);
        this.changePasswordLabel = new Label("Change Password : ", skin);
        this.changePasswordField = new TextField("enter your new password", skin);
        this.changePasswordButton = new TextButton("Change Password", skin);
        this.changePasswordMessage = new Label("", skin);
        this.currentAvatar = new Label("Current Avatar : ", skin);
        this.currentAvatarImage = App.getApp().getLoggedInPlayer().getAvatar();
        this.choseAvatar = new Label("Chose Avatar : ", skin);
        this.avatarsSelectBox = new SelectBox<>(skin);
        this.avatarsSelectBox.setItems("Avatar1", "Avatar2", "Avatar3", "Avatar4", "Avatar5", "Avatar6");
        this.deleteAccountButton = new TextButton("Delete Account", skin);
        this.backButton = new TextButton("Back", skin);
        this.avatarButton = new TextButton("Chose Avatar", skin);
        this.table = new Table(skin);
        this.music = App.getApp().getMusic();
        this.grayShader = new ShaderProgram(Gdx.files.internal("default.vert"), Gdx.files.internal("gray.frag"));
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        controller.setView(this);
        avatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chooseImage();
            }
        });
    }

    public void updateAvatar(FileHandle fileHandle) {
        Texture tex = new Texture(fileHandle);
        currentAvatarImage.setDrawable(new TextureRegionDrawable(new TextureRegion(tex)));
    }

    private void chooseImage() {
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            Gdx.app.postRunnable(() -> {
                DesktopFileChooser.openImageFile(controller); // دستی به controller می‌ده
            });
        }
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

        profileMenuLabel.setFontScale(3f);
        profileMenuLabel.setPosition(900, 1350);
        profileMenuLabel.setColor(Color.valueOf("0A9396"));
        table.add(profileMenuLabel);
        stage.addActor(profileMenuLabel);
        table.row().pad(50, 0, 10, 0);

        Table table0 = new Table();
        table0.left();
        currentUsername.setColor(Color.valueOf("55a630"));
        currentUsername.setFontScale(2f);
        table0.add(currentUsername);
        table.add(table0).padRight(270);
        //table.add(currentUsername).padLeft(380);

        Table table1 = new Table();
        table1.left();
        table.row().pad(25, 0, 0, 0);
        changeUsername.setColor(Color.valueOf("94D2BD"));
        table1.add(changeUsername).padRight(70);
        table1.add(changeUsernameField).width(600).padRight(48);
        table1.row().pad(10, 0, 10, 0);
        changeUsernameMessage.setColor(Color.valueOf("AE2012"));
        table1.row().pad(70, 0, 100, 0);
        table.add(table1).padRight(10);

        changeUsernameButton.setColor(Color.GREEN);
        table.add(changeUsernameButton);
        table.row().pad(10, 0, 10, 0);
        table.add(changeUsernameMessage).padLeft(250);
        table.row().pad(70, 0, 10, 0);

        Table table2 = new Table();
        table2.left();
        changePasswordLabel.setColor(Color.valueOf("94D2BD"));
        table2.add(changePasswordLabel).padRight(70);
        table2.add(changePasswordField).width(600);
        table2.row().pad(10, 0, 10, 0);
        changePasswordMessage.setColor(Color.valueOf("AE2012"));
        table2.row().pad(70, 0, 100, 0);
        table.add(table2).padRight(70);
        changePasswordButton.setColor(Color.GREEN);
        table.add(changePasswordButton);
        table.row().pad(10, 0, 10, 0);
        table.add(changePasswordMessage).padLeft(250);
        table.row().pad(25, 0, 0, 0);

        currentAvatar.setFontScale(2f);
        currentAvatar.setColor(Color.valueOf("55a630"));
        table.add(currentAvatar).padRight(550);
        table.pad(0, 0, 0, 50);
        table.add(currentAvatarImage).width(200);
        table.row().pad(15, 0, 10, 0);

        Table table3 = new Table();
        table3.left();
        choseAvatar.setColor(Color.valueOf("94D2BD"));
        table3.add(choseAvatar).padLeft(100);//.padRight(800);
        table3.add(avatarsSelectBox).padLeft(100);
        //table3.row().pad(10, 0, 10, 0);
        table3.add(avatarButton).padLeft(100);
        table.add(table3);
        table.row().pad(50, 0, 10, 0);

        deleteAccountButton.setColor(Color.valueOf("ff1654"));
        table.add(deleteAccountButton).width(600).padLeft(450);

        backButton.setPosition(10, Gdx.graphics.getHeight() - backButton.getHeight() - 10);
        backButton.setColor(Color.ROYAL);
        stage.addActor(backButton);

        stage.addActor(table);
        controller.handleProfileMenu();
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

    public TextField getChangeUsernameField() {
        return changeUsernameField;
    }

    public TextButton getChangeUsernameButton() {
        return changeUsernameButton;
    }

    public TextField getChangePasswordField() {
        return changePasswordField;
    }

    public TextButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public SelectBox<String> getAvatarsSelectBox() {
        return avatarsSelectBox;
    }

    public TextButton getAvatarButton() {
        return avatarButton;
    }

    public TextButton getDeleteAccountButton() {
        return deleteAccountButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public Label getChangeUsernameMessage() {
        return changeUsernameMessage;
    }

    public Label getChangePasswordMessage() {
        return changePasswordMessage;
    }

    public Label getCurrentUsername() {
        return currentUsername;
    }

    public Image getCurrentAvatarImage() {
        return currentAvatarImage;
    }

    public Music getMusic() {
        return music;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public void setCurrentAvatarImage(Image currentAvatarImage) {
        this.currentAvatarImage = currentAvatarImage;
    }
}
