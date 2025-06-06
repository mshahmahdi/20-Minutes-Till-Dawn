package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.FinalMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;

public class FinalMenuView implements Screen {
    private float stateTime = 0.0f;
    private Stage stage;
    private final FinalMenuController controller;
    private final Label menuLabel;
    private final Label usernameLabel;
    private final Label surviveTimeLabel;
    private final Label killsLabel;
    private final Label scoreLabel;
    private final TextButton playAgainButton;
    private final TextButton mainMenuButton;
    private final ShaderProgram grayShader;
    private final FrameBuffer fbo;
    public Table table;
    private final Music music;


    public FinalMenuView(FinalMenuController controller, Skin skin, boolean win) {
        this.controller = controller;
        if (win) {
            this.menuLabel = new Label("YOU WIN !", skin);
        } else {
            this.menuLabel = new Label("GAME OVER !", skin);
        }
        this.usernameLabel = new Label("Your UserName : " + App.getApp().getLoggedInUser().getUsername(), skin);
        this.surviveTimeLabel = new Label("Survive Time : " +
            (int)App.getApp().getCurrentGameView().getController().game.getSurvivalTime() / 60 + " min", skin);
        this.killsLabel = new Label("Your Kills : " +
            App.getApp().getCurrentGameView().getController().getPlayerController().getPlayer().getKills(), skin);
        this.scoreLabel = new Label("Your Score : " +
            App.getApp().getCurrentGameView().getController().getPlayerController().getPlayer().getLevel() * 100, skin);
        this.playAgainButton = new TextButton("Play Again", skin);
        this.mainMenuButton = new TextButton("Go Main Menu", skin);
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

        table.clear();
        table.setFillParent(true);
        table.center().top().padTop(100); // فاصله از بالا

        // عنوان اصلی (برد یا باخت)
        menuLabel.setFontScale(3.5f);
        menuLabel.setColor(Color.valueOf("0A9396")); // آبی خاص
        table.add(menuLabel).center().padBottom(70);
        table.row();

        // یوزرنیم
        usernameLabel.setFontScale(2.2f);
        usernameLabel.setColor(Color.valueOf("E9D8A6"));
        table.add(usernameLabel).center().padBottom(40);
        table.row();

        // زمان زنده ماندن
        surviveTimeLabel.setFontScale(2.2f);
        surviveTimeLabel.setColor(Color.valueOf("E9D8A6"));
        table.add(surviveTimeLabel).center().padBottom(40);
        table.row();

        // تعداد کیل‌ها
        killsLabel.setFontScale(2.2f);
        killsLabel.setColor(Color.valueOf("E9D8A6"));
        table.add(killsLabel).center().padBottom(40);
        table.row();

        // امتیاز
        scoreLabel.setFontScale(2.2f);
        scoreLabel.setColor(Color.valueOf("EE9B00")); // زرد-نارنجی برای جذابیت
        table.add(scoreLabel).center().padBottom(80);
        table.row();

        // دکمه‌ها
        float buttonWidth = 400f;
        float buttonHeight = 100f;

        playAgainButton.setColor(Color.valueOf("94D2BD")); // سبز ملایم
        table.add(playAgainButton).width(buttonWidth).height(buttonHeight).padBottom(30);
        table.row();

        mainMenuButton.setColor(Color.valueOf("CA6702")); // نارنجی تیره
        table.add(mainMenuButton).width(buttonWidth).height(buttonHeight);
        table.row();

        stage.addActor(table);
        controller.handleFinalMenu();

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

    public TextButton getPlayAgainButton() {
        return playAgainButton;
    }

    public TextButton getMainMenuButton() {
        return mainMenuButton;
    }
}
