package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.LoginMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;

import java.awt.*;

public class LoginMenuView implements Screen {
    private float stateTime = 0.0f;
    private Stage stage;
    private final Image gameTitle;
    private final Label loginMenu;
    private final Label usernameLabel;
    private final Label passwordLabel;
    private Label usernameMessage;
    private Label passwordMessage;
    private TextField usernameField;
    private TextField passwordField;
    private final TextButton loginButton;
    private final TextButton forgetPasswordButton;
    private final TextButton backButton;
    private final Music music;
    private final ShaderProgram grayShader;
    private final FrameBuffer fbo;
    private final TextureRegion fboRegion;
    public Table table;
    private final LoginMenuController controller;


    public LoginMenuView(LoginMenuController controller, Skin skin) {
        this.controller = controller;
        this.gameTitle = new Image(new Texture("sprite/T/T_20Logo.png"));
        this.usernameLabel = new Label("Username :", skin);
        this.passwordLabel = new Label("Password :", skin);
        this.loginMenu = new Label("Login :", skin);
        this.usernameField = new TextField("enter your username", skin);
        this.passwordField = new TextField("enter your password", skin);
        this.usernameMessage = new Label("", skin);
        this.passwordMessage = new Label("", skin);
        this.loginButton = new TextButton("Login", skin);
        this.loginButton.addListener((ClickListener) new ClickListener() {});
        this.forgetPasswordButton = new TextButton("Forget password", skin);
        this.forgetPasswordButton.addListener((ClickListener) new ClickListener() {});
        this.backButton = new TextButton("back", skin);
        this.backButton.addListener((ClickListener) new ClickListener() {});
        this.music = App.getApp().getMusic();
        this.grayShader = new ShaderProgram(Gdx.files.internal("default.vert"), Gdx.files.internal("gray.frag"));
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        this.fboRegion = new TextureRegion(fbo.getColorBufferTexture());
        this.table = new Table();
        controller.setView(this);
    }


    @Override
    public void show() {
        music.setLooping(true);
        music.play();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        gameTitle.setSize(gameTitle.getWidth() * 1.5f, gameTitle.getHeight() * 1.5f);
        gameTitle.setPosition(
            Gdx.graphics.getWidth() / 2f - gameTitle.getWidth() / 2f, // وسط چین افقی
            Gdx.graphics.getHeight() - gameTitle.getHeight() - 50     // فاصله از بالا (هرچقدر خواستی)
        );

        table.setFillParent(true);
        table.top();
        table.center();

        loginMenu.setFontScale(2f);
        loginMenu.setColor(Color.valueOf("0A9396"));
        table.add(loginMenu).colspan(2).center().padBottom(30);
        table.row().pad(50, 0, 10, 0);

        table.add(usernameLabel).colspan(2);
        usernameLabel.setColor(Color.valueOf("94D2BD"));
        table.row().pad(10, 0, 10, 0);
        table.add(usernameField).width(600).colspan(2);
        table.row().pad(10, 0, 10, 0);
        usernameMessage.setColor(Color.valueOf("AE2012"));
        table.add(usernameMessage).colspan(2);
        table.row().pad(10, 0, 10, 0);

        table.add(passwordLabel).colspan(2);
        passwordLabel.setColor(Color.valueOf("94D2BD"));
        table.row().pad(10, 0, 10, 0);
        table.add(passwordField).width(600).colspan(2);
        table.row().pad(10, 0, 10, 0);
        passwordMessage.setColor(Color.valueOf("AE2012"));
        table.add(passwordMessage).colspan(2);
        table.row().pad(10, 0, 10, 0);


        table.add(loginButton).width(300).colspan(2);
        loginButton.setColor(Color.GREEN);
        table.row().pad(100, 0, 10, 0);

        table.add(forgetPasswordButton).width(500).colspan(2);
        forgetPasswordButton.setColor(Color.BLUE);
        table.row().pad(10, 0, 10, 0);


        backButton.setPosition(10, Gdx.graphics.getHeight() - backButton.getHeight() - 10);
        backButton.setColor(Color.ROYAL);
        stage.addActor(backButton);

        stage.addActor(gameTitle);
        stage.addActor(table);
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
            fbo.begin();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Main.getBatch().setShader(null); // بدون shader
            Main.getBatch().begin();
            stage.draw();
            Main.getBatch().end();
            fbo.end();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Main.getBatch().setShader(grayShader);
            Main.getBatch().begin();
            Main.getBatch().draw(fboRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Main.getBatch().draw(currentFrame, 0, 0, 2304, 1440);
            Main.getBatch().end();
            Main.getBatch().setShader(null);

        } else {
            ScreenUtils.clear(0, 0, 0, 1);
            Main.getBatch().setShader(null);
            Main.getBatch().begin();
            Main.getBatch().draw(currentFrame, 0, 0, 2304, 1440);
            Main.getBatch().end();
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }

        controller.handleLoginMenu();
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

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getForgetPasswordButton() {
        return forgetPasswordButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public Label getUsernameMessage() {
        return usernameMessage;
    }

    public Label getPasswordMessage() {
        return passwordMessage;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public void setPasswordField(TextField passwordField) {
        this.passwordField = passwordField;
    }
}
