package com.tilldawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.controller.*;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.Player;
import com.tilldawn.model.game;
import com.tilldawn.view.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        App.getApp().setLoggedInPlayer(new Player("mostafa", "paSS12#$", "salam"));
        App.getApp().addUser(App.getApp().getLoggedInPlayer());
        App.getApp().getLoggedInPlayer().setCurrentGame(new game(1, App.getApp().getLoggedInPlayer()));
        SignupMenuController.setAvatar(App.getApp().getLoggedInPlayer());
        getMain().setScreen(new ProfileMenuView(new ProfileMenuController(),MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        //getMain().setScreen(new SettingsMenuView(new SettingsMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        //getMain().setScreen(new MainMenuView(new MainMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        //getMain().setScreen(new ForgetPasswordView(new ForgetPasswordMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        //getMain().setScreen(new LoginMenuView(new LoginMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        //getMain().setScreen(new SignupMenuView(new SignupMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }
}
