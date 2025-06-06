package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.AbilityMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AbilityMenuView implements Screen {
    private float stateTime = 0.0f;
    private final AbilityMenuController controller;
    private Stage stage;
    private final Label menuLabel;
    private final Label choseAbilityLabel;
    private ArrayList<String> abilityList;
    private final SelectBox<String> abilitySelectBox;
    private final TextButton backToGameButton;
    private final ShaderProgram grayShader;
    private final FrameBuffer fbo;
    public Table table;
    private Music music;

    public AbilityMenuView(AbilityMenuController controller, Skin skin) {
        this.controller = controller;
        this.abilityList = getAbilityList(getThreeUniqueRandoms());
        this.menuLabel = new Label("Ability Menu", skin);
        this.choseAbilityLabel = new Label("Chose Your Ability :", skin);
        this.abilitySelectBox = new SelectBox<>(skin);
        this.abilitySelectBox.setItems(abilityList.get(0), abilityList.get(1), abilityList.get(2));
        this.abilitySelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
            }
        });
        this.backToGameButton = new TextButton("Back To Game", skin);
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

        table.clear();
        table.setFillParent(true);
        table.center().top().padTop(100); // فاصله از بالا

        // عنوان اصلی منو
        menuLabel.setFontScale(3.2f);
        menuLabel.setColor(Color.valueOf("0A9396"));
        table.add(menuLabel).center().padBottom(400);
        table.row();

        // متن راهنمای انتخاب قابلیت
        choseAbilityLabel.setFontScale(2.2f);
        choseAbilityLabel.setColor(Color.valueOf("E9D8A6"));
        table.add(choseAbilityLabel).center().padBottom(40);
        table.row();

        // SelectBox برای انتخاب قابلیت‌ها
        table.add(abilitySelectBox).width(500).height(80).padBottom(70);
        table.row();

        // دکمه بازگشت به بازی
        backToGameButton.setColor(Color.valueOf("E9D8A6")); // سبز ملایم
        table.add(backToGameButton).width(500).height(100);
        table.row();

        stage.addActor(table);
        controller.handleAbilityMenu();


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

    public static List<Integer> getThreeUniqueRandoms() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Collections.shuffle(numbers);
        return numbers.subList(0, 3);
    }

    public ArrayList<String> getAbilityList(List<Integer> numbers) {
        ArrayList<String> abilityList = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) == 1) {
                abilityList.add("VITALITY");
            } else if (numbers.get(i) == 2) {
                abilityList.add("DAMAGER");
            } else if (numbers.get(i) == 3) {
                abilityList.add("PROCREASE");
            } else if (numbers.get(i) == 4) {
                abilityList.add("AMOCREASE");
            } else {
                abilityList.add("SPEEDY");
            }
        }
        return abilityList;
    }

    public SelectBox<String> getAbilitySelectBox() {
        return abilitySelectBox;
    }

    public TextButton getBackToGameButton() {
        return backToGameButton;
    }
}
