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
    public final Texture controllerB = new Texture(Gdx.files.internal("keys/letter-b_12233550.png"));
    public final Texture controllerC = new Texture(Gdx.files.internal("keys/letter-c_12233553.png"));
    public final Texture controllerE = new Texture(Gdx.files.internal("keys/letter-e_12233559.png"));
    public final Texture controllerF = new Texture(Gdx.files.internal("keys/letter-f_12233562.png"));
    public final Texture controllerH = new Texture(Gdx.files.internal("keys/letter-h_12233568.png"));
    public final Texture controllerL = new Texture(Gdx.files.internal("keys/letter-l_12233580.png"));
    public final Texture controllerQ = new Texture(Gdx.files.internal("keys/letter-q_12233591.png"));
    public final Texture controllerR = new Texture(Gdx.files.internal("keys/letter-r_12233593.png"));
    public final Texture controllerT = new Texture(Gdx.files.internal("keys/letter-t_12233597.png"));

    public final Image dasherHero = new Image(new Texture(Gdx.files.internal("sprite/T/T_Dasher_Portrait.png")));
    public final Image diamondHero = new Image(new Texture(Gdx.files.internal("sprite/T/T_Diamond_Portrait.png")));
    public final Image lilithHero = new Image(new Texture(Gdx.files.internal("sprite/T/T_Lilith_Portrait.png")));
    public final Image scarletHero = new Image(new Texture(Gdx.files.internal("sprite/T/T_Scarlett_Portrait.png")));
    public final Image shanaHero = new Image(new Texture(Gdx.files.internal("sprite/T/T_Shana_Portrait.png")));

    public final Image mapImage = new Image(new Texture(Gdx.files.internal("map/map (4).png")));

    public final Animation<Texture> hero1_idl = new Animation<>(0.1f, new Texture("Heros/Dasher/idle/Idle_0 #8325.png"),
        new Texture("Heros/Dasher/idle/Idle_1 #8355.png"),
        new Texture("Heros/Dasher/idle/Idle_2 #8814.png"),
        new Texture("Heros/Dasher/idle/Idle_3 #8452.png"),
        new Texture("Heros/Dasher/idle/Idle_4 #8313.png"),
        new Texture("Heros/Dasher/idle/Idle_5 #8302.png"));
    public final Animation<Texture> hero2_idl = new Animation<>(0.1f, new Texture("Heros/Diamond/idle/Idle_0 #8328.png"),
        new Texture("Heros/Diamond/idle/Idle_1 #8358.png"),
        new Texture("Heros/Diamond/idle/Idle_2 #8817.png"),
        new Texture("Heros/Diamond/idle/Idle_3 #8455.png"),
        new Texture("Heros/Diamond/idle/Idle_4 #8316.png"),
        new Texture("Heros/Diamond/idle/Idle_5 #8305.png"));
    public final Animation<Texture> hero3_idl = new Animation<>(0.1f, new Texture("Heros/Lilith/idle/Idle_0 #8333.png"),
        new Texture("Heros/Lilith/idle/Idle_1 #8363.png"),
        new Texture("Heros/Lilith/idle/Idle_2 #8822.png"),
        new Texture("Heros/Lilith/idle/Idle_3 #8460.png"),
        new Texture("Heros/Lilith/idle/Idle_4 #8321.png"),
        new Texture("Heros/Lilith/idle/Idle_5 #8310.png"));
    public final Animation<Texture> hero4_idl = new Animation<>(0.1f, new Texture("Heros/Scarlet/idle/Idle_0 #8327.png"),
        new Texture("Heros/Scarlet/idle/Idle_1 #8357.png"),
        new Texture("Heros/Scarlet/idle/Idle_2 #8816.png"),
        new Texture("Heros/Scarlet/idle/Idle_3 #8454.png"),
        new Texture("Heros/Scarlet/idle/Idle_4 #8315.png"),
        new Texture("Heros/Scarlet/idle/Idle_5 #8304.png"));
    public final Animation<Texture> hero5_idl = new Animation<>(0.1f, new Texture("Heros/Shana/idle/Idle_0 #8330.png"),
        new Texture("Heros/Shana/idle/Idle_1 #8360.png"),
        new Texture("Heros/Shana/idle/Idle_2 #8819.png"),
        new Texture("Heros/Shana/idle/Idle_3 #8457.png"),
        new Texture("Heros/Shana/idle/Idle_4 #8318.png"),
        new Texture("Heros/Shana/idle/Idle_5 #8307.png"));

    public final Animation<Texture> TreeMonster = new Animation<>(0.5f, new Texture("tree_monster/T_TreeMonster_0.png"),
        new Texture("tree_monster/T_TreeMonster_1.png"),
        new Texture("tree_monster/T_TreeMonster_2.png"));

    public final Animation<TextureRegion> pumpkinMonster = new Animation<>(0.5f, new TextureRegion(new Texture("T_PumpkinMonster/T_PumpkinMonster_0.png")),
        new TextureRegion(new Texture("T_PumpkinMonster/T_PumpkinMonster_1.png")),
        new TextureRegion(new Texture("T_PumpkinMonster/T_PumpkinMonster_2.png")),
        new TextureRegion(new Texture("T_PumpkinMonster/T_PumpkinMonster_3.png")),
        new TextureRegion(new Texture("T_PumpkinMonster/T_PumpkinMonster_4.png")),
        new TextureRegion(new Texture("T_PumpkinMonster/T_PumpkinMonster_5.png")));

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
