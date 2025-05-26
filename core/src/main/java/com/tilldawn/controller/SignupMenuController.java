package com.tilldawn.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.Enums.SignupMenuCommands;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.Player;
import com.tilldawn.view.LoginMenuView;
import com.tilldawn.view.MainMenuView;
import com.tilldawn.view.SignupMenuView;

import java.util.Random;
import java.util.regex.Matcher;

public class SignupMenuController {
    private SignupMenuView view;
    private final Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();

    public void setView(SignupMenuView view) {
        this.view = view;
    }

    public void handleSignupMenu() {
        if (view != null) {
            String username = view.getUsernameField().getText();
            String password = view.getPasswordField().getText();
            String answer = view.getSecurityField().getText();
            if (view.getSignupButton().isChecked()) {
                Matcher matcher;
                if (username.isEmpty() || username.equals("enter username")) {
                    clearMessages();
                    view.getUsernameLabelMessage().setText("Username cannot be empty.");
                    view.getSignupButton().setChecked(false);
                } else if (password.isEmpty() || password.equals("enter password")) {
                    clearMessages();
                    view.getPasswordLabelMessage().setText("Password cannot be empty.");
                    view.getSignupButton().setChecked(false);
                } else if (answer.isEmpty() || answer.equals("enter answer")) {
                    clearMessages();
                    view.getSecurityLabelMessage().setText("Security answer cannot be empty.");
                    view.getSignupButton().setChecked(false);
                } else if (isUsernameExist(username)) {
                    clearMessages();
                    view.getUsernameLabelMessage().setText("Username already taken. Please choose another.");
                    view.getSignupButton().setChecked(false);
                } else if ((matcher = SignupMenuCommands.Password.getMatcher(password)) == null) {
                    clearMessages();
                    view.getPasswordLabelMessage().setText("Password is too weak. Please use a stronger one.");
                    view.getSignupButton().setChecked(false);
                } else {
                    App app = App.getApp();
                    Player newPlayer = new Player(username,
                        password,
                        answer);
                    setAvatar(newPlayer);
                    app.addUser(newPlayer);
                    Main.getMain().getScreen().dispose();
                    view.getSignupButton().setChecked(true);
                    Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), skin));
                }
            }
        } if (view.getLoginButton().isChecked()) {
            view.getLoginButton().setChecked(false);
            Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(),skin));
        } if (view.getGuestButton().isChecked()) {
            App.getApp().setLoggedInUser(new Player("Guest", "Guest", "Guest"));
            setAvatar(App.getApp().getLoggedInUser());
            Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            view.getGuestButton().setChecked(false);
        }
    }

// Auxiliary functions :

    private boolean isUsernameExist(String username) {
        App app = App.getApp();
        for (Player player : app.getUsers()) {
            if (player.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private void clearMessages() {
        view.getPasswordLabelMessage().setText("");
        view.getUsernameLabelMessage().setText("");
        view.getSecurityLabelMessage().setText("");
    }

    private void clearFields() {
        view.getUsernameField().setText("enter username");
        view.getPasswordField().setText("enter password");
        view.getSecurityField().setText("enter answer");
    }

    public static void setAvatar(Player player) {
        int num = rollDice();
        if (num == 1) {
            player.setAvatar(MenuGameAssetManager.getMenuGameAssetManager().avatar1);
        } else if (num == 2) {
            player.setAvatar(MenuGameAssetManager.getMenuGameAssetManager().avatar2);
        } else if (num == 3) {
            player.setAvatar(MenuGameAssetManager.getMenuGameAssetManager().avatar3);
        } else if (num == 4) {
            player.setAvatar(MenuGameAssetManager.getMenuGameAssetManager().avatar4);
        } else if (num == 5) {
            player.setAvatar(MenuGameAssetManager.getMenuGameAssetManager().avatar5);
        } else if (num == 6) {
            player.setAvatar(MenuGameAssetManager.getMenuGameAssetManager().avatar6);
        }
    }

    public static int rollDice() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}
