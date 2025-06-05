package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.controller.GameController;
import com.tilldawn.Main;
import com.tilldawn.model.Enums.KeysController;
import com.tilldawn.model.Game;

public class GameView implements Screen, InputProcessor {
    public Game game;
    private Stage stage;
    private GameController controller;
    public OrthographicCamera camera;
    private Texture mapTexture;
    private Sprite mapSprite;

    public GameView(GameController controller, Skin skin, Game game) {
        this.controller = controller;
        this.game = game;
        controller.setView(this);
    }

    @Override
    public void show() {
        mapTexture = new Texture("map/map (4).png");
        mapSprite = new Sprite(mapTexture);
        mapSprite.setPosition(0, 0);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
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
        mapSprite.draw(Main.getBatch());
        controller.updateGame(delta, camera, mapSprite);
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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
}
