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
import com.tilldawn.controller.ForgetPasswordMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;

public class ForgetPasswordView implements Screen {
    private float stateTime = 0.0f;
    private Stage stage;
    private final Image gameTitle;
    private final Label forgetPasswordTitle;
    private final Label usernameLabel;
    private final Label securityQuestionLabel;
    private Label usernameMessageLabel;
    private Label securityMessageLabel;
    private Label forgetPasswordMessageLabel;
    private Label forgetPasswordLabel;
    private final TextField passwordField;
    private final TextField usernameField;
    private final TextField securityQuestionField;
    private final TextButton loginButton;
    private final TextButton backButton;
    private final Music music;
    private final ShaderProgram grayShader;
    private final FrameBuffer fbo;
    private final TextureRegion fboRegion;
    public Table table;
    private final ForgetPasswordMenuController controller;

    public ForgetPasswordView(ForgetPasswordMenuController controller, Skin skin) {
        this.controller = controller;
        this.gameTitle = new Image(new Texture("sprite/T/T_20Logo.png"));
        this.forgetPasswordTitle = new Label("Forget password : ", skin);
        this.usernameLabel = new Label("Username : ", skin);
        this.securityQuestionLabel = new Label("Security question : ", skin);
        this.usernameMessageLabel = new Label("", skin);
        this.securityMessageLabel = new Label("", skin);
        this.forgetPasswordMessageLabel = new Label("", skin);
        this.forgetPasswordLabel = new Label("New Password : ", skin);
        this.passwordField = new TextField("enter your new password", skin);
        this.usernameField = new TextField("enter your username", skin);
        this.securityQuestionField = new TextField("enter your answer", skin);
        this.loginButton = new TextButton("Go to login", skin);
        this.loginButton.addListener((ClickListener) new ClickListener() {});
        this.backButton = new TextButton("Back", skin);
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

        gameTitle.setSize(gameTitle.getWidth() * 1.5f, gameTitle.getHeight() * 1.5f) ;
        gameTitle.setPosition(
            Gdx.graphics.getWidth() / 2f - gameTitle.getWidth() / 2f, // وسط چین افقی
            Gdx.graphics.getHeight() - gameTitle.getHeight() - 50     // فاصله از بالا (هرچقدر خواستی)
        );

        table.setFillParent(true);
        table.top();
        table.center();

        table.padTop(150);

        forgetPasswordTitle.setFontScale(2f);
        forgetPasswordTitle.setColor(Color.valueOf("0A9396"));
        table.add(forgetPasswordTitle).colspan(2).center().padBottom(30);
        table.row().pad(50, 0, 10, 0);

        table.add(usernameLabel).colspan(2);
        usernameLabel.setColor(Color.valueOf("94D2BD"));
        table.row().pad(10, 0, 10, 0);
        table.add(usernameField).width(600).colspan(2);
        table.row().pad(10, 0, 10, 0);
        usernameMessageLabel.setColor(Color.valueOf("AE2012"));
        table.add(usernameMessageLabel).colspan(2);
        table.row().pad(10, 0, 10, 0);

        table.add(securityQuestionLabel).colspan(2);
        securityQuestionLabel.setColor(Color.valueOf("94D2BD"));
        table.row().pad(10, 0, 10, 0);
        table.add(securityQuestionField).width(600).colspan(2);
        table.row().pad(10, 0, 10, 0);
        table.add(securityMessageLabel).colspan(2);
        securityMessageLabel.setColor(Color.valueOf("AE2012"));
        table.row().pad(10, 0, 10, 0);

        table.add(forgetPasswordLabel).colspan(2);
        forgetPasswordLabel.setColor(Color.valueOf("94D2BD"));
        table.row().pad(10, 0, 10, 0);
        table.add(passwordField).width(600).colspan(2);
        table.row().pad(10, 0, 10, 0);
        forgetPasswordMessageLabel.setColor(Color.valueOf("AE2012"));
        table.add(forgetPasswordMessageLabel).colspan(2);
        table.row().pad(10, 0, 100, 0);

        table.add(loginButton).width(350).colspan(2);
        loginButton.setColor(Color.GREEN);
        table.row().pad(100, 0, 10, 0);

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

        controller.handleForgetPasswordMenu();
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


    public Label getUsernameMessageLabel() {
        return usernameMessageLabel;
    }

    public Label getSecurityMessageLabel() {
        return securityMessageLabel;
    }

    public Label getForgetPasswordMessageLabel() {
        return forgetPasswordMessageLabel;
    }

    public Label getForgetPasswordLabel() {
        return forgetPasswordLabel;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getSecurityQuestionField() {
        return securityQuestionField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public void setUsernameMessageLabel(Label usernameMessageLabel) {
        this.usernameMessageLabel = usernameMessageLabel;
    }

    public void setSecurityMessageLabel(Label securityMessageLabel) {
        this.securityMessageLabel = securityMessageLabel;
    }

    public void setForgetPasswordLabel(Label forgetPasswordLabel) {
        this.forgetPasswordLabel = forgetPasswordLabel;
    }
}
