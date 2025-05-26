package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class MenuGameAssetManager {
    private static MenuGameAssetManager menuGameAssetManager;
    private final Skin menuSkin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    //public static final Image gameTitle = new Image(new Texture(Gdx.files.internal("sprite/T/T_20Logo.png")));
    public final Texture gameTitle = new Texture("sprite/T/T_20Logo.png");

    public final Image mainMenuBorder = new Image(new Texture(Gdx.files.internal("sprite/T/T_TitleLeaves.png")));
    public final Image mainMenuBorder2 = new Image(new Texture(Gdx.files.internal("sprite/T/T_TitleLeaves.png")));

    public Music music1 = Gdx.audio.newMusic(Gdx.files.internal("musics/01. Pretty Dungeon.mp3"));
    public Music music2 = Gdx.audio.newMusic(Gdx.files.internal("musics/02. Wasteland Combat.mp3"));
    public Music music3 = Gdx.audio.newMusic(Gdx.files.internal("musics/Pretty Dungeon LOOP.mp3"));
    public Music music4 = Gdx.audio.newMusic(Gdx.files.internal("musics/Wasteland Combat Loop.mp3"));

    public final Image avatar1 = new Image(new Texture(Gdx.files.internal("sprite/T/T_Dasher_Portrait.png")));
    public final Image avatar2 = new Image(new Texture(Gdx.files.internal("sprite/T/T_Diamond_Portrait.png")));
    public final Image avatar3 = new Image(new Texture(Gdx.files.internal("sprite/T/T_Lilith_Portrait.png")));
    public final Image avatar4 = new Image(new Texture(Gdx.files.internal("sprite/T/T_Scarlett_Portrait.png")));
    public final Image avatar5 = new Image(new Texture(Gdx.files.internal("avatars/Avatar5.png")));
    public final Image avatar6 = new Image(new Texture(Gdx.files.internal("sprite/T/T_Shana_Portrait.png")));

    public final Texture controller1 = new Texture(Gdx.files.internal("keys/arrows_1782564.png"));
    public final Texture controller2 = new Texture(Gdx.files.internal("keys/keyboard_6150823.png"));
    public final Texture controller3 = new Texture(Gdx.files.internal("keys/mouse-clicker_6524730.png"));
    public final Texture controller4 = new Texture(Gdx.files.internal("keys/space_12233469.png"));

    public final Image dasherHero = new Image(new Texture(Gdx.files.internal("sprite/T/T_Dasher_Portrait.png")));
    public final Image diamondHero = new Image(new Texture(Gdx.files.internal("sprite/T/T_Diamond_Portrait.png")));
    public final Image lilithHero = new Image(new Texture(Gdx.files.internal("sprite/T/T_Lilith_Portrait.png")));
    public final Image scarletHero = new Image(new Texture(Gdx.files.internal("sprite/T/T_Scarlett_Portrait.png")));
    public final Image shanaHero = new Image(new Texture(Gdx.files.internal("sprite/T/T_Shana_Portrait.png")));

    public final Animation<TextureRegion> menuAnimation = new Animation<>(0.2f, buildMenuAnimation(), Animation.PlayMode.LOOP);
    ;


    public static MenuGameAssetManager getMenuGameAssetManager() {
        if (menuGameAssetManager == null) {
            menuGameAssetManager = new MenuGameAssetManager();
            menuGameAssetManager.music1.setVolume(0.5f);
            menuGameAssetManager.music2.setVolume(0.5f);
            menuGameAssetManager.music3.setVolume(0.5f);
            menuGameAssetManager.music4.setVolume(0.5f);
        }
        return menuGameAssetManager;
    }

    public Skin getMenuSkin() {
        return menuSkin;
    }


    public static Array<TextureRegion> buildMenuAnimation() {
        Array<TextureRegion> frames = new Array<>();
        TextureRegion frame1 = new TextureRegion(new Texture(Gdx.files.internal("sprite/T/T_EyeBlink_0.png")));
        TextureRegion frame2 = new TextureRegion(new Texture(Gdx.files.internal("sprite/T/T_EyeBlink_1.png")));
        frames.add(frame1);
        frames.add(frame2);
        return frames;
    }

    public static void setVolume(float volume) {
        menuGameAssetManager.music1.setVolume(volume);
        menuGameAssetManager.music2.setVolume(volume);
        menuGameAssetManager.music3.setVolume(volume);
        menuGameAssetManager.music4.setVolume(volume);
    }

}
