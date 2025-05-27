package com.tilldawn.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.view.MainMenuView;
import com.tilldawn.view.SettingsMenuView;
import com.tilldawn.view.SignupMenuView;

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


            } else if (view.getLogoutButton().isChecked()) {
                App app = App.getApp();
                app.setLoggedInUser(null);
                app.setBlackAndWhiteMode(false);
                app.setAutoReload(false);
                app.setCurrentGame(null);
                app.setMusic(MenuGameAssetManager.getMenuGameAssetManager().music3);
                app.getMusic().setVolume(0.5f);
                Main.getMain().setScreen(new SignupMenuView(new SignupMenuController() , skin));
            }
        }
    }
}
