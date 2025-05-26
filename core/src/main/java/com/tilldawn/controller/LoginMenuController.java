package com.tilldawn.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.Player;
import com.tilldawn.view.ForgetPasswordView;
import com.tilldawn.view.LoginMenuView;
import com.tilldawn.view.MainMenuView;
import com.tilldawn.view.SignupMenuView;

public class LoginMenuController {
    private LoginMenuView view;
    private final Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();

    public void setView(LoginMenuView view) {
        this.view = view;
    }

    public void handleLoginMenu() {
        if (view != null) {
            if (view.getLoginButton().isChecked()) {
                String username = view.getUsernameField().getText();
                String password = view.getPasswordField().getText();
                if (username.isEmpty() || username.equals("enter your username")) {
                    cleanMessage();
                    view.getUsernameMessage().setText("Username cannot be empty.");
                    view.getLoginButton().setChecked(false);
                } else if (!isUsernameExist(username)) {
                    cleanMessage();
                    view.getUsernameMessage().setText("Username not found. Please check and try again.");
                    view.getLoginButton().setChecked(false);
                } else if (password.isEmpty() || password.equals("enter your password")) {
                    cleanMessage();
                    view.getPasswordMessage().setText("Password cannot be empty.");
                    view.getLoginButton().setChecked(false);
                } else if (!getUserByUsername(username).getPassword().equals(password)) {
                    cleanMessage();
                    view.getPasswordMessage().setText("Incorrect password. Please try again");
                    view.getLoginButton().setChecked(false);
                } else {
                    App app = App.getApp();
                    app.setLoggedInUser(getUserByUsername(username));
                    Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
                }
            } else if (view.getForgetPasswordButton().isChecked()) {
                Main.getMain().setScreen(new ForgetPasswordView(new ForgetPasswordMenuController(), skin));
            } else if (view.getBackButton().isChecked()) {
                Main.getMain().setScreen(new SignupMenuView(new SignupMenuController(), skin));
            }
        }
    }


// Auxiliary functions :

    private void cleanMessage() {
        view.getUsernameMessage().setText("");
        view.getPasswordMessage().setText("");
    }

    private boolean isUsernameExist(String username) {
        App app = App.getApp();
        for (Player player : app.getUsers()) {
            if (player.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private Player getUserByUsername(String username) {
        App app = App.getApp();
        for (Player player : app.getUsers()) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }
}
