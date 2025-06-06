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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.SignupMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;

import javax.swing.event.ChangeListener;

public class SignupMenuView implements Screen {
    private float stateTime = 0.0f;
    private Stage stage;
    private final Image gameTitle;
    private final TextButton signupButton;
    private final TextButton guestButton;
    private final TextButton loginButton;
    private final Label signupLabel;
    private final Label usernameLabel;
    private final Label passwordLabel;
    private final Label securityLabel;
    private Label usernameLabelMessage;
    private Label passwordLabelMessage;
    private Label securityLabelMessage;
    private TextField usernameField;
    private TextField passwordField;
    private TextField securityField;
    private final Music music;
    private final ShaderProgram grayShader;
    private final FrameBuffer fbo;
    private final TextureRegion fboRegion;
    public Table table;
    private final SignupMenuController controller;

    public SignupMenuView(SignupMenuController controller, Skin skin) {
        this.controller = controller;
        this.gameTitle = new Image(new Texture("sprite/T/T_20Logo.png"));
        this.signupButton = new TextButton("Sign Up", skin);
        this.signupButton.addListener((ClickListener) new ClickListener() {});
        this.guestButton = new TextButton("Guest", skin);
        this.guestButton.addListener((ClickListener) new ClickListener() {});
        this.loginButton = new TextButton("Login", skin);
        this.loginButton.addListener((ClickListener) new ClickListener() {});
        this.signupLabel = new Label("Sign up :", skin);
        this.usernameLabel = new Label("Username :", skin);
        this.usernameLabelMessage = new Label("", skin);
        this.passwordLabel = new Label("Password :", skin);
        this.passwordLabelMessage = new Label("", skin);
        this.securityLabel = new Label("Security question : What would you grab first in a zombie apocalypse ? ^_+", skin);
        this.securityLabelMessage = new Label("", skin);
        this.usernameField = new TextField("enter username", skin);
        this.passwordField = new TextField("enter password", skin);
        this.securityField = new TextField("enter answer", skin);
        this.music = App.getApp().getMusic();
        this.grayShader = new ShaderProgram(Gdx.files.internal("default.vert"), Gdx.files.internal("gray.frag"));
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        this.fboRegion = new TextureRegion(fbo.getColorBufferTexture());
        this.table = new Table();
        controller.setView(this);
    }


    public void show() {

        music.setLooping(true);
        music.play();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        gameTitle.setSize(gameTitle.getWidth() * 1.5f, gameTitle.getHeight() * 1.5f) ;
        gameTitle.setPosition(
            Gdx.graphics.getWidth() / 2f - gameTitle.getWidth() / 2f, // وسط چین افقی
            Gdx.graphics.getHeight() - gameTitle.getHeight() - 50     // فاصله از بالا (هرچقدر خواستی)
        );

        table.setFillParent(true);
        table.top();
        table.center();
        table.padTop(150);

        signupLabel.setFontScale(2f);
        signupLabel.setColor(Color.valueOf("0A9396"));
        table.add(signupLabel).colspan(2).center().padBottom(30);
        table.row().pad(50, 0, 10, 0);

        table.add(usernameLabel).colspan(2);
        usernameLabel.setColor(Color.valueOf("94D2BD"));
        table.row().pad(10, 0, 10, 0);
        table.add(usernameField).width(600).colspan(2);
        table.row().pad(10, 0, 10, 0);
        usernameLabelMessage.setColor(Color.valueOf("AE2012"));
        table.add(usernameLabelMessage).colspan(2);
        table.row().pad(10, 0, 10, 0);

        table.add(passwordLabel).colspan(2);
        passwordLabel.setColor(Color.valueOf("94D2BD"));
        table.row().pad(10, 0, 10, 0);
        table.add(passwordField).width(600).colspan(2);
        table.row().pad(10, 0, 10, 0);
        passwordLabelMessage.setColor(Color.valueOf("AE2012"));
        table.add(passwordLabelMessage).colspan(2);
        table.row().pad(10, 0, 10, 0);

        table.add(securityLabel).colspan(2);
        securityLabel.setColor(Color.valueOf("94D2BD"));
        table.row().pad(10, 0, 10, 0);
        table.add(securityField).width(600).colspan(2);
        table.row().pad(10, 0, 10, 0);
        securityLabelMessage.setColor(Color.valueOf("AE2012"));
        table.add(securityLabelMessage).colspan(2);
        table.row().pad(10, 0, 10, 0);

        signupButton.setColor(Color.GREEN);
        table.add(signupButton).colspan(2);
        table.row().pad(10, 0, 10, 0);

        guestButton.setColor(Color.BLUE);
        loginButton.setColor(Color.BLUE);
        table.add(guestButton).padRight(20);
        table.add(loginButton).padLeft(20);
        table.row().pad(0, 10, 0, 10);

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

        controller.handleSignupMenu();
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

    public TextField getSecurityField() {
        return securityField;
    }

    public TextButton getSignupButton() {
        return signupButton;
    }

    public TextButton getGuestButton() {
        return guestButton;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public Stage getStage() {
        return stage;
    }

    public Label getUsernameLabelMessage() {
        return usernameLabelMessage;
    }

    public Label getPasswordLabelMessage() {
        return passwordLabelMessage;
    }

    public Label getSecurityLabelMessage() {
        return securityLabelMessage;
    }

    public void setUsernameField(TextField usernameField) {
        this.usernameField = usernameField;
    }

    public void setPasswordField(TextField passwordField) {
        this.passwordField = passwordField;
    }

    public void setSecurityField(TextField securityField) {
        this.securityField = securityField;
    }


}
