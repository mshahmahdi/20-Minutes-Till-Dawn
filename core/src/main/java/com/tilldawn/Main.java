package com.tilldawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.controller.*;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.Service.UserSaveManager;
import com.tilldawn.model.User;
import com.tilldawn.view.*;

import java.util.ArrayList;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        UserSaveManager.loadUsersFromJson();
        App.getApp().setLoggedInUser(new User("mostafa", "paSS12#$", "salam"));
        App.getApp().addUser(App.getApp().getLoggedInUser());
        SignupMenuController.setAvatar(App.getApp().getLoggedInUser());
        //App.getApp().getLoggedInUser().setCurrentGame(new Game(1, App.getApp().getLoggedInUser()));
        for (int i = 0; i < 9; i++) {
            User user = new User(randomName(), "paSS12#$", "salam");
            App.getApp().addUser(user);
            SignupMenuController.setAvatar(user);
        }
        //Main.getMain().setScreen(new AbilityMenuView(new AbilityMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        //Main.getMain().setScreen(new FinalMenuView(new FinalMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin(), true));
        //Main.getMain().setScreen(new PauseMenuView(new PauseMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin(), "Daddkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"));
        //getMain().setScreen(new PregameMenuView(new PregameMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        //getMain().setScreen(new TalentMenuView(new TalentMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        //getMain().setScreen(new ScoreboardMenuView(new ScoreboardMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin(), getUsernames()));
        //getMain().setScreen(new ProfileMenuView(new ProfileMenuController(),MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        //getMain().setScreen(new SettingsMenuView(new SettingsMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        //getMain().setScreen(new PauseMenuView(new PauseMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin(), "sasasas"));
        //getMain().setScreen(new MainMenuView(new MainMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        //getMain().setScreen(new ForgetPasswordView(new ForgetPasswordMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        //getMain().setScreen(new LoginMenuView(new LoginMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        getMain().setScreen(new SignupMenuView(new SignupMenuController(), MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin()));
        UserSaveManager.saveUsersToJson();
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

    public static String randomName() {
        String a = "abcdefghijklmnopqrstuvwxyz";
        int len = 5 + (int) (Math.random() * 6);
        StringBuilder s = new StringBuilder();
        s.append(Character.toUpperCase(a.charAt((int) (Math.random() * 26))));
        for (int i = 1; i < len; i++)
            s.append(a.charAt((int) (Math.random() * 26)));
        return s.toString();
    }

    private ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for (User user : App.getApp().getUsers()) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }
}


