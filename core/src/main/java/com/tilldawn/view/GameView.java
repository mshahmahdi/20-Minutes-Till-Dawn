package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.controller.*;
import com.tilldawn.Main;
import com.tilldawn.model.*;
import com.tilldawn.model.Enums.KeysController;

public class GameView implements Screen, InputProcessor {
    boolean isPaused = false;
    public Game game;
    private Stage stage;
    private GameController controller;
    public OrthographicCamera camera;
    private Texture mapTexture;
    private Sprite mapSprite;
    private ShaderProgram grayscaleShader;
    Pixmap pixmap = new Pixmap(Gdx.files.internal("sprite/T/T_CursorSprite.png"));

    private final Image hpImage;
    private Label hpLabel;
    private Label time;
    private Label timeLabel;
    private final Image ammoImage;
    private Label ammoLabel;
    private final Image kill;
    private Label killLabel;
    private Label levelLabel;
    private ProgressBar xpBar;
    private Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);


    public GameView(GameController controller, Skin skin, Game game) {
        this.controller = controller;
        this.game = game;
        controller.setView(this);
        this.ammoImage = new Image(new Texture("game element/T_AmmoIcon.png"));
        this.hpImage = new Image(new Texture("game element/T_HeartPickup.png"));
        this.kill = new Image((new Texture("game element/T_CurseFX_0.png")));
        this.hpLabel = new Label(" * " + (int)controller.getPlayerController().getPlayer().getPlayerHealth(), skin);
        this.ammoLabel = new Label(" * " + controller.getWeaponController().getWeapon().getAmmo(), skin);
        this.xpBar = new ProgressBar(0f, 21f,   1f, false, skin);
        this.levelLabel = new Label("LEVEL : " + controller.getPlayerController().getPlayer().getLevel(), skin);
        this.killLabel = new Label(" * " + controller.getPlayerController().getPlayer().getKills(), skin);
        this.time = new Label(game.getTimeText(), skin);
        this.timeLabel = new Label("SURVIVED!", skin);
    }

    @Override
    public void show() {

        // نقطه فعال: بالا چپ
        Gdx.graphics.setCursor(customCursor);
        // Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);

        ShaderProgram.pedantic = false;
        grayscaleShader = new ShaderProgram(Gdx.files.internal("vertex.glsl"), Gdx.files.internal("fragment.glsl"));
        if (!grayscaleShader.isCompiled()) {
            System.err.println("Shader compile error: " + grayscaleShader.getLog());
        }

        mapTexture = new Texture("map/map (4).png");
        mapSprite = new Sprite(mapTexture);
        mapSprite.setPosition(0, 0);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new ScreenViewport());
        xpBar.setPosition(70,1380);
        xpBar.setValue(0); // مقدار اولیه، مثلاً 25 درصد
        xpBar.setAnimateDuration(0.25f); // انیمیشن نرمه
        xpBar.setWidth(400); // عرض نوار
        xpBar.setHeight(20);
        hpImage.setSize(70, 70);
        ammoImage.setSize(70, 70);
        kill.setSize(70, 70);
        hpImage.setPosition(100, 1270);
        hpLabel.setPosition(170, 1290);
        hpLabel.setFontScale(4f);
        ammoLabel.setFontScale(4f);
        levelLabel.setFontScale(4f);
        killLabel.setFontScale(4f);
        timeLabel.setFontScale(4f);
        time.setFontScale(10f);
        hpLabel.setColor(Color.valueOf("EF233C"));
        ammoLabel.setColor(Color.valueOf("EF233C"));
        levelLabel.setColor(Color.valueOf("EE9B00"));
        killLabel.setColor(Color.valueOf("E9D8A6"));
        timeLabel.setColor(Color.valueOf("D5F2E3"));
        time.setColor(Color.valueOf("D5F2E3"));
        ammoImage.setPosition(320, 1270);
        ammoLabel.setPosition(370, 1290);
        levelLabel.setPosition(520, 1290);
        kill.setPosition(800, 1270);
        killLabel.setPosition(860, 1290);
        time.setPosition(2000, 1360);
        timeLabel.setPosition(2000, 1280);
        stage.addActor(hpImage);
        stage.addActor(hpLabel);
        stage.addActor(ammoImage);
        stage.addActor(ammoLabel);
        stage.addActor(xpBar);
        stage.addActor(levelLabel);
        stage.addActor(kill);
        stage.addActor(killLabel);
        stage.addActor(timeLabel);
        stage.addActor(time);
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {

        Gdx.graphics.setCursor(customCursor);
        if (isPaused) {
            // مثلا فقط صفحه Pause بکشه
            return;
        }

        if (game.getTime() <= 0f) {
            Main.getMain().setScreen(new FinalMenuView(new FinalMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin(), true));
            isPaused = true;
        }

        if (controller.getPlayerController().getPlayer().getPlayerHealth() == 0) {
            Main.getMain().setScreen(new FinalMenuView(new FinalMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin(), false));
        }

        game.setTime(game.getTime() - delta, delta);

        hpLabel.setText(" * " + (int)controller.getPlayerController().getPlayer().getPlayerHealth());
        ammoLabel.setText(" * " + controller.getWeaponController().getWeapon().getAmmo());
        levelLabel.setText("LEVEL : " + controller.getPlayerController().getPlayer().getLevel());
        killLabel.setText(" * " + controller.getPlayerController().getPlayer().getKills());
        time.setText(game.getTimeText());
        xpBar.setValue(controller.getPlayerController().getPlayer().getXP());

        if (xpBar.getValue() == xpBar.getMaxValue()) {
            xpBar.setRange(0f, xpBar.getMaxValue() * 2f);
            xpBar.setValue(0f);
            controller.getPlayerController().getPlayer().addLevel(1);
            controller.getPlayerController().getPlayer().setXP(0);
            isPaused = false;
            Main.getMain().setScreen(new AbilityMenuView(new AbilityMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        }

        game.update(delta);

        if (App.getApp().isBlackAndWhiteMode()) {
            ScreenUtils.clear(0, 0, 0, 1);

            // تنظیم ماتریس دوربین برای batch
            Main.getBatch().setProjectionMatrix(camera.combined);

            // فعال کردن شیدر سیاه‌وسفید
            Main.getBatch().setShader(grayscaleShader);

            // جلوگیری از اینکه دوربین از لبه‌های نقشه بزنه بیرون
            float cameraHalfWidth = camera.viewportWidth / 2f;
            float cameraHalfHeight = camera.viewportHeight / 2f;

            camera.position.x = MathUtils.clamp(
                camera.position.x,
                cameraHalfWidth,
                mapSprite.getWidth() - cameraHalfWidth
            );
            camera.position.y = MathUtils.clamp(
                camera.position.y,
                cameraHalfHeight,
                mapSprite.getHeight() - cameraHalfHeight
            );
            camera.update();

            Main.getBatch().begin();

            // رسم همه چیز به صورت سیاه و سفید
            mapSprite.setAlpha(0.5f);
            mapSprite.draw(Main.getBatch());
            controller.updateGame(delta, camera, mapSprite);

            for (TreeMonster tree : game.getTreeMonsters()) {
                tree.update(delta);
                tree.render(delta);
            }

            for (PumpkinMonster monster : game.getPumpkinMonsters()) {
                monster.update(delta, controller.getPlayerController().getPlayer());
                monster.render(Main.getBatch());
            }

            for (DroppedItem item : game.getDroppedItems()) {
                item.render(Main.getBatch());
            }

            for (DragonMonster dragonMonster : game.getDragonMonsters()) {
                dragonMonster.update(delta, controller.getPlayerController().getPlayer());
                dragonMonster.render(Main.getBatch());
            }

            for (EyebatMonster eyebat : game.getEyebatMonsters()) {
                eyebat.update(delta, controller.getPlayerController().getPlayer());
                eyebat.render(Main.getBatch());
            }

            if (game.getFinalBoss() != null && !game.getFinalBoss().isDead()) {
                game.getFinalBoss().render(Main.getBatch());
                // می‌تونی دیوار حفاظتی رو با یه دایره یا کادر بکش
            }

            if (game.getFinalBoss() != null && !game.getFinalBoss().isDead()) {
                game.getBossRingSprite().draw(Main.getBatch());
            }


            Main.getBatch().end();

            // غیرفعال کردن شیدر برای رسم UI رنگی
            Main.getBatch().setShader(null);

            // رسم stage (که ممکنه شامل دکمه‌ها و متن‌های رنگی باشه)
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

            stage.draw();


        } else {
            ScreenUtils.clear(0, 0, 0, 1);
            Main.getBatch().setProjectionMatrix(camera.combined);
            Main.getBatch().begin();
            // جلوگیری از اینکه دوربین از لبه‌های نقشه بزنه بیرون
            float cameraHalfWidth = camera.viewportWidth / 2f;
            float cameraHalfHeight = camera.viewportHeight / 2f;

            camera.position.x = MathUtils.clamp(
                camera.position.x,
                cameraHalfWidth,
                mapSprite.getWidth() - cameraHalfWidth
            );
            camera.position.y = MathUtils.clamp(
                camera.position.y,
                cameraHalfHeight,
                mapSprite.getHeight() - cameraHalfHeight
            );
            // به‌روزرسانی دوربین
            camera.update();
            mapSprite.setAlpha(0.5f);
            mapSprite.draw(Main.getBatch());
            controller.updateGame(delta, camera, mapSprite);

            for (TreeMonster tree : game.getTreeMonsters()) {
                tree.update(delta);
                tree.render(delta);
            }

            for (PumpkinMonster monster : game.getPumpkinMonsters()) {
                monster.update(delta, controller.getPlayerController().getPlayer());
                monster.render(Main.getBatch());
            }

            for (DragonMonster dragonMonster : game.getDragonMonsters()) {
                dragonMonster.update(delta, controller.getPlayerController().getPlayer());
                dragonMonster.render(Main.getBatch());
            }

            for (DroppedItem item : game.getDroppedItems()) {
                item.render(Main.getBatch());
            }

            for (EyebatMonster eyebat : game.getEyebatMonsters()) {
                eyebat.update(delta, controller.getPlayerController().getPlayer());
                eyebat.render(Main.getBatch());
            }

            if (game.getFinalBoss() != null && !game.getFinalBoss().isDead()) {
                game.getFinalBoss().render(Main.getBatch());
                // می‌تونی دیوار حفاظتی رو با یه دایره یا کادر بکش
            }

            if (game.getFinalBoss() != null && !game.getFinalBoss().isDead()) {
                game.getBossRingSprite().draw(Main.getBatch());
            }

            Main.getBatch().end();
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();
        isPaused = true;
        Main.getMain().setScreen(new PauseMenuView(new PauseMenuController(), skin, game.getAbilities()));
    }

    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (Gdx.input.isKeyJustPressed(KeysController.RELOAD.getKey())) {
            if (!controller.getWeaponController().getWeapon().isReloading()) {
                controller.getWeaponController().getWeapon().startReload();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            if (!isPaused) {
                pause();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            game.setTime(game.getTime() - 60f, 60f);
            game.setElapsedTime(game.getElapsedTime() + 60f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            controller.getPlayerController().getPlayer().setXP((int)xpBar.getMaxValue());
            xpBar.setValue(xpBar.getMaxValue());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            if (controller.getPlayerController().getPlayer().getPlayerHealth() == 1) {
                controller.getPlayerController().getPlayer().cheatHP();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            game.getPumpkinMonsters().removeAll(game.getPumpkinMonsters());
            game.getEyebatMonsters().removeAll(game.getEyebatMonsters());
            game.getDragonMonsters().removeAll(game.getDragonMonsters());
            game.setFinalBoss(null);
            game.getFinalBoss().setHealth(0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            game.setTime(game.getTime() - 300f, 300f);
            game.setElapsedTime(game.getElapsedTime() + 300f);
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean isShotgun = game.getGunNumber() == 1;
        controller.getWeaponController().handleWeaponShoot(screenX, screenY, camera, isShotgun);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY,
            controller.getPlayerController().getPlayer().getPosX(), controller.getPlayerController().getPlayer().getPosY(), camera);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public GameController getController() {
        return controller;
    }

    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public Sprite getMapSprite() {
        return mapSprite;
    }
}
