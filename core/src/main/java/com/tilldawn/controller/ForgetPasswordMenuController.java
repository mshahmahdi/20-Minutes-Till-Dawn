package com.tilldawn.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.Enums.SignupMenuCommands;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.Player;
import com.tilldawn.view.ForgetPasswordView;
import com.tilldawn.view.LoginMenuView;

import java.util.regex.Matcher;

public class ForgetPasswordMenuController {
    private ForgetPasswordView view;
    private final Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();

    public void setView(ForgetPasswordView view) {
        this.view = view;
    }

    public void handleForgetPasswordMenu() {
        Matcher matcher;
        if (view != null) {
            if (view.getLoginButton().isChecked()) {
                String username = view.getUsernameField().getText();
                String password = view.getPasswordField().getText();
                String answer = view.getSecurityQuestionField().getText();
                if (username.isEmpty() || username.equals("enter your username")) {
                    cleanMessageLabel();
                    view.getUsernameMessageLabel().setText("Username cannot be empty.");
                    view.getLoginButton().setChecked(false);
                } else if (!isUsernameExist(username)) {
                    cleanMessageLabel();
                    view.getUsernameMessageLabel().setText("Username not found. Please check and try again.");
                    view.getLoginButton().setChecked(false);
                } else if (answer.isEmpty() || answer.equals("enter your answer")) {
                    cleanMessageLabel();
                    view.getSecurityMessageLabel().setText("Answer cannot be empty.");
                    view.getLoginButton().setChecked(false);
                } else if (!getUserByUsername(username).getSecurityQuestion().equals(answer)) {
                    cleanMessageLabel();
                    view.getSecurityMessageLabel().setText("Incorrect answer to the security question.");
                    view.getLoginButton().setChecked(false);
                } else if (password.isEmpty() || password.equals("enter your new password")) {
                    cleanMessageLabel();
                    view.getForgetPasswordMessageLabel().setText("Password cannot be empty.");
                    view.getLoginButton().setChecked(false);
                } else if ((matcher = SignupMenuCommands.Password.getMatcher(password)) == null) {
                    cleanMessageLabel();
                    view.getForgetPasswordMessageLabel().setText("Password is too weak. Please use a stronger one.");
                    view.getLoginButton().setChecked(false);
                } else {
                    getUserByUsername(username).setPassword(password);
                    Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), skin));
                }
            } else if (view.getBackButton().isChecked()) {
                Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), skin));
            }
        }
    }


    // Auxiliary functions :

    private void cleanMessageLabel() {
        view.getUsernameMessageLabel().setText("");
        view.getSecurityMessageLabel().setText("");
        view.getForgetPasswordMessageLabel().setText("");
    }

    private boolean isUsernameExist(String username) {
        App app = App.getApp();
        for (Player player : app.getPlayers()) {
            if (player.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private Player getUserByUsername(String username) {
        App app = App.getApp();
        for (Player player : app.getPlayers()) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }
}


