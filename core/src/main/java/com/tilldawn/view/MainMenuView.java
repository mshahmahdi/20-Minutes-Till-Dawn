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
import com.tilldawn.controller.MainMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.User;

public class MainMenuView implements Screen {
    private float stateTime = 0.0f;
    private Stage stage;
    private final Image gameTitle;
    private final Image mainMenuBorderLeft = MenuGameAssetManager.getMenuGameAssetManager().mainMenuBorder;
    private final Image mainMenuBorderRight = MenuGameAssetManager.getMenuGameAssetManager().mainMenuBorder2;
    private final Image avatar;
    private final Label usernameLabel;
    private final Label scoreLabel;
    private final TextButton continueButton;
    private final TextButton newGameButton;
    private final TextButton settingsButton;
    private final TextButton profileButton;
    private final TextButton scoreBoardButton;
    private final TextButton hintsButton;
    private final TextButton logoutButton;
    private final TextButton exitButton;
    private final Music music;
    public Table table;
    Table topLeftTable;
    private final ShaderProgram grayShader;
    private final FrameBuffer fbo;
    private final TextureRegion fboRegion;
    private final MainMenuController controller;
    User user = App.getApp().getLoggedInUser();

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.controller = controller;
        this.gameTitle = new Image(new Texture("sprite/T/T_20Logo.png"));
        this.avatar = user.getAvatar();
        this.usernameLabel = new Label("Username : " + user.getUsername(), skin);
        this.scoreLabel = new Label("Score : " + user.getScore(), skin);
        this.continueButton = new TextButton("Continue Last Game", skin);
        this.newGameButton = new TextButton("New Game", skin);
        this.settingsButton = new TextButton("Settings", skin);
        this.profileButton = new TextButton("Profile", skin);
        this.scoreBoardButton = new TextButton("Score Board", skin);
        this.hintsButton = new TextButton("Hints", skin);
        this.logoutButton = new TextButton("Logout", skin);
        this.exitButton = new TextButton("Exit", skin);
        this.continueButton.addListener((ClickListener) new ClickListener() {
        });
        this.newGameButton.addListener((ClickListener) new ClickListener() {
        });
        this.settingsButton.addListener((ClickListener) new ClickListener() {
        });
        this.profileButton.addListener((ClickListener) new ClickListener() {
        });
        this.scoreBoardButton.addListener((ClickListener) new ClickListener() {
        });
        this.hintsButton.addListener((ClickListener) new ClickListener() {
        });
        this.logoutButton.addListener((ClickListener) new ClickListener() {
        });
        this.exitButton.addListener((ClickListener) new ClickListener() {
        });
        this.grayShader = new ShaderProgram(Gdx.files.internal("default.vert"), Gdx.files.internal("gray.frag"));
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        this.fboRegion = new TextureRegion(fbo.getColorBufferTexture());
        this.fboRegion.flip(false, true);
        this.table = new Table(skin);
        this.topLeftTable = new Table(skin);
        this.music = App.getApp().getMusic();
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

        gameTitle.setSize(gameTitle.getWidth() * 1.5f, gameTitle.getHeight() * 1.5f);
        gameTitle.setPosition(
            Gdx.graphics.getWidth() / 2f - gameTitle.getWidth() / 2f, // وسط چین افقی
            Gdx.graphics.getHeight() - gameTitle.getHeight() - 50     // فاصله از بالا (هرچقدر خواستی)
        );
        stage.addActor(gameTitle);

        mainMenuBorderLeft.setSize(720, 1440);
        float windowHeight = Gdx.graphics.getHeight();
        float imageHeight = mainMenuBorderLeft.getHeight();
        mainMenuBorderLeft.setPosition(0, windowHeight - imageHeight);
        stage.addActor(mainMenuBorderLeft);

        mainMenuBorderRight.setSize(720, 1440);
        mainMenuBorderRight.setScaleX(-1);
        float windowWidth = Gdx.graphics.getWidth();
        mainMenuBorderRight.setPosition(windowWidth, windowHeight - imageHeight);
        stage.addActor(mainMenuBorderRight);

        topLeftTable.top().left();
        topLeftTable.setFillParent(true);
        topLeftTable.padLeft(550);
        topLeftTable.padTop(350);
        topLeftTable.add(usernameLabel).left();
        usernameLabel.setColor(Color.valueOf("669bbc"));
        topLeftTable.row().pad(10, 0, 10, 0);
        scoreLabel.setColor(Color.valueOf("ffba08"));
        topLeftTable.add(scoreLabel).left();

        avatar.setPosition(1530, 950);

        table.top().padTop(390);
        continueButton.setColor(Color.valueOf("E9D8A6"));
        table.add(continueButton).width(550);
        table.row().pad(10, 0, 10, 0);
        newGameButton.setColor(Color.valueOf("E9D8A6"));
        table.add(newGameButton).width(550);
        table.row().pad(10, 0, 10, 0);
        settingsButton.setColor(Color.valueOf("E9D8A6"));
        table.add(settingsButton).width(550);
        table.row().pad(10, 0, 10, 0);
        profileButton.setColor(Color.valueOf("E9D8A6"));
        table.add(profileButton).width(550);
        table.row().pad(10, 0, 10, 0);
        scoreBoardButton.setColor(Color.valueOf("E9D8A6"));
        table.add(scoreBoardButton).width(550);
        table.row().pad(10, 0, 10, 0);
        hintsButton.setColor(Color.valueOf("E9D8A6"));
        table.add(hintsButton).width(550);
        table.row().pad(10, 0, 10, 0);
        logoutButton.setColor(Color.valueOf("E9D8A6"));
        table.add(logoutButton).width(550);
        table.row().pad(10, 0, 10, 0);
        exitButton.setColor(Color.valueOf("E9D8A6"));
        table.add(exitButton).width(550);

        stage.addActor(avatar);
        stage.addActor(topLeftTable);
        stage.addActor(table);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void render(float v) {
        Main.getBatch().setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

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
            Main.getBatch().end();
            Main.getBatch().setShader(null);

        } else {
            ScreenUtils.clear(0, 0, 0, 1);
            Main.getBatch().setShader(null);
            Main.getBatch().begin();
            Main.getBatch().end();
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }

        controller.handleMainMenu();
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

    public TextButton getContinueButton() {
        return continueButton;
    }

    public TextButton getNewGameButton() {
        return newGameButton;
    }

    public TextButton getSettingsButton() {
        return settingsButton;
    }

    public TextButton getProfileButton() {
        return profileButton;
    }

    public TextButton getScoreBoardButton() {
        return scoreBoardButton;
    }

    public TextButton getHintsButton() {
        return hintsButton;
    }

    public TextButton getLogoutButton() {
        return logoutButton;
    }

    public TextButton getExitButton() { return exitButton; }

    public Music getMusic() {
        return music;
    }

}
