package com.tilldawn.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.User;
import com.tilldawn.view.ScoreboardMenuController;

import java.util.ArrayList;

public class ScoreboardMenuView implements Screen {
    private float stateTime = 0.0f;
    private Stage stage;
    private final ScoreboardMenuController controller;
    private final Label sortbyLabel;
    private final Label rankLabel;
    private final Label scoreBoardLabel;
    private final Label usernameLabel;
    private final Label scoreLabel;
    private final Label killsLabel;
    private final Label survivedTimeLabel;
    private final ArrayList<String> usernames;
    private final SelectBox<String> sortbySelectBox;
    private TextButton backButton;
    private final ShaderProgram grayShader;
    private final FrameBuffer fbo;
    public Table table;
    private final Music music;

    public ScoreboardMenuView(ScoreboardMenuController scoreboardMenuController, Skin skin, ArrayList<String> usernames) {
        this.controller = scoreboardMenuController;
        this.scoreBoardLabel = new Label("Leaderboard Menu", skin);
        this.rankLabel = new Label("Rank", skin);
        this.usernameLabel = new Label("Username", skin);
        this.sortbyLabel = new Label("Sort By :", skin);
        this.scoreLabel = new Label("Score", skin);
        this.killsLabel = new Label("Kills", skin);
        this.survivedTimeLabel = new Label("Survived Time", skin);
        this.usernames = usernames;
        this.sortbySelectBox = new SelectBox<>(skin);
        this.sortbySelectBox.setItems("Score", "Username", "Kills", "Survived Time");
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
        table.top().center(); // حذف table.center() اضافه به بالا باعث میشه فشرده نشه
        table.padTop(130);

        scoreBoardLabel.setFontScale(3f); // بزرگ‌تر برای عنوان
        scoreBoardLabel.setColor(Color.valueOf("0A9396"));
        scoreBoardLabel.setAlignment(Align.center);
        scoreBoardLabel.setPosition(Gdx.graphics.getWidth() / 2f - 150, 1350); // وسط صفحه
        stage.addActor(scoreBoardLabel);

        float headerFontScale = 2f;
        float columnPad = 150f;

        rankLabel.setColor(Color.valueOf("E9D8A6"));
        rankLabel.setFontScale(headerFontScale);
        table.add(rankLabel).padRight(columnPad);

        usernameLabel.setColor(Color.valueOf("E9D8A6"));
        usernameLabel.setFontScale(headerFontScale);
        table.add(usernameLabel).padRight(columnPad);

        scoreLabel.setColor(Color.valueOf("E9D8A6"));
        scoreLabel.setFontScale(headerFontScale);
        table.add(scoreLabel).padRight(columnPad);

        killsLabel.setColor(Color.valueOf("E9D8A6"));
        killsLabel.setFontScale(headerFontScale);
        table.add(killsLabel).padRight(columnPad);

        survivedTimeLabel.setColor(Color.valueOf("E9D8A6"));
        survivedTimeLabel.setFontScale(headerFontScale);
        table.add(survivedTimeLabel);

        table.row().pad(40, 0, 40, 0); // فاصله بین هدر و اولین ردیف

        for (int i = 0; i < 10; i++) {
            Label count = new Label(i + 1 + " )", skin);
            Label username = new Label(usernames.get(i), skin);
            Label score = new Label(String.valueOf(getPlayerByUsername(usernames.get(i)).getScore()), skin);
            Label kills = new Label(String.valueOf(getPlayerByUsername(usernames.get(i)).getKills()), skin);
            Label surviveTime = new Label(String.valueOf(getPlayerByUsername(usernames.get(i)).getLongestSurvivalTime()), skin);

            // تنظیم سایز فونت ردیف‌ها
            float rowFontScale = 1.7f;
            count.setFontScale(rowFontScale);
            username.setFontScale(rowFontScale);
            score.setFontScale(rowFontScale);
            kills.setFontScale(rowFontScale);
            surviveTime.setFontScale(rowFontScale);

            if (App.getApp().getLoggedInUser().getUsername().equals(usernames.get(i))) {
                // رنگ خاص برای یوزر لاگین‌شده
                Color currentUserColor = Color.valueOf("94D2BD"); // سبز نعنایی آرام
                count.setColor(currentUserColor);
                username.setColor(currentUserColor);
                score.setColor(currentUserColor);
                kills.setColor(currentUserColor);
                surviveTime.setColor(currentUserColor);

                // برجسته کردن متن (مثلاً با علامت ★ یا bold)
                username.setText("* " + username.getText() + " *");
                username.setFontScale(rowFontScale + 0.3f);
            } else if (i == 0) {
                Color gold = Color.GOLD;
                count.setColor(gold);
                username.setColor(gold);
                score.setColor(gold);
                kills.setColor(gold);
                surviveTime.setColor(gold);
            } else if (i == 1) {
                Color silver = Color.valueOf("C0C0C0");
                count.setColor(silver);
                username.setColor(silver);
                score.setColor(silver);
                kills.setColor(silver);
                surviveTime.setColor(silver);
            } else if (i == 2) {
                Color bronze = Color.valueOf("CD7F32");
                count.setColor(bronze);
                username.setColor(bronze);
                score.setColor(bronze);
                kills.setColor(bronze);
                surviveTime.setColor(bronze);
            } else {
                Color defaultColor = Color.valueOf("E9D8A6");
                count.setColor(defaultColor);
                username.setColor(defaultColor);
                score.setColor(defaultColor);
                kills.setColor(defaultColor);
                surviveTime.setColor(defaultColor);
            }

            table.add(count).padRight(columnPad);
            table.add(username).padRight(columnPad);
            table.add(score).padRight(columnPad);
            table.add(kills).padRight(columnPad);
            table.add(surviveTime);

            table.row().pad(25, 0, 25, 0); // فاصله بین ردیف‌ها
        }

        table.row();
        sortbyLabel.setColor(Color.valueOf("8ecae6"));
        sortbyLabel.setFontScale(headerFontScale);
        sortbyLabel.setPosition(500, 70);
        stage.addActor(sortbyLabel);
        table.add("");//(columnPad);
        table.add(sortbySelectBox).colspan(5).center();

        backButton.setPosition(10, Gdx.graphics.getHeight() - backButton.getHeight() - 10);
        backButton.setColor(Color.ROYAL);
        stage.addActor(backButton);

        stage.addActor(table);
        controller.handleScoreboardMenu();

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

    // Auxiliary functions :



    private User getPlayerByUsername(String username) {
        for (User user : App.getApp().getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public SelectBox<String> getSortbySelectBox() {
        return sortbySelectBox;
    }

    public TextButton getBackButton() {
        return backButton;
    }
}

