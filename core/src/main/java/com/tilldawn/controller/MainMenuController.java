package com.tilldawn.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.Player;
import com.tilldawn.view.*;

import java.util.ArrayList;

public class MainMenuController {
    private MainMenuView view;
    private final Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void handleMainMenu() {
        if (view != null) {
            if (view.getSettingsButton().isChecked()) {
                Main.getMain().setScreen(new SettingsMenuView(new SettingsMenuController() , skin));
            } else if (view.getProfileButton().isChecked()) {
                Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController() , skin));
            } else if (view.getScoreBoardButton().isChecked()) {
                Main.getMain().setScreen(new ScoreboardMenuView(new ScoreboardMenuController(), skin, getUsernames()));
            } else if (view.getHintsButton().isChecked()) {
                Main.getMain().setScreen(new TalentMenuView(new TalentMenuController(), skin));
            } else if (view.getLogoutButton().isChecked()) {
                App app = App.getApp();
                app.setLoggedInPlayer(null);
                app.setBlackAndWhiteMode(false);
                app.setAutoReload(false);
                app.setCurrentGame(null);
                view.getMusic().stop();
                app.setMusic(MenuGameAssetManager.getMenuGameAssetManager().music3);
                app.getMusic().setVolume(0.5f);
                Main.getMain().setScreen(new SignupMenuView(new SignupMenuController() , skin));
            }
        }
    }

    private ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for (Player player : App.getApp().getPlayers()) {
            usernames.add(player.getUsername());
        }
        return usernames;
    }
}
