package com.tilldawn.controller;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.Enums.SignupMenuCommands;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.Player;
import com.tilldawn.model.Service.ProfileModel;
import com.tilldawn.view.MainMenuView;
import com.tilldawn.view.ProfileMenuView;
import com.tilldawn.view.SignupMenuView;

import java.util.regex.Matcher;

public class ProfileMenuController {
    private ProfileModel model = new ProfileModel();
    private ProfileMenuView view;
    private final Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();

    public void setView(ProfileMenuView view) {
        this.view = view;
    }

    public void handleProfileMenu() {
        if (view != null) {
            view.getChangeUsernameButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    if (view.getChangeUsernameButton().isChecked()) {
                        if (view.getChangeUsernameField().getText().isEmpty() || view.getChangeUsernameField().getText().equals("enter your new username")) {
                            cleanMessage();
                            view.getChangeUsernameMessage().setText("username can not be empty");
                            view.getChangeUsernameButton().setChecked(false);
                            //view.getChangeUsernameField().getText()Field.setText("enter your new username");
                        } else if (view.getChangeUsernameField().getText().equals(App.getApp().getLoggedInPlayer().getUsername())) {
                            cleanMessage();
                            view.getChangeUsernameMessage().setText("This is your current username.");
                            view.getChangeUsernameButton().setChecked(false);
                            //usernameField.setText("enter your new username");
                        } else if (isUsernameExist(view.getChangeUsernameField().getText())) {
                            cleanMessage();
                            view.getChangeUsernameMessage().setText("Username already exists. Please choose another one.");
                            view.getChangeUsernameButton().setChecked(false);
                            // usernameField.setText("enter your new username");
                        } else {
                            cleanMessage();
                            getPlayerByUsername(App.getApp().getLoggedInPlayer().getUsername()).setUsername(view.getChangeUsernameField().getText());
                            view.getCurrentUsername().setText("Current username: " + App.getApp().getLoggedInPlayer().getUsername());
                            view.getChangeUsernameButton().setChecked(false);
                            //usernameField.setText("enter your new username");
                        }
                    }
                }
            });

            view.getChangePasswordButton().addListener(new ChangeListener() {
                Matcher matcher;

                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    if (view.getChangePasswordButton().isChecked()) {
                        if (view.getChangePasswordField().getText().isEmpty() || view.getChangePasswordField().getText().equals("enter your new password")) {
                            cleanMessage();
                            view.getChangePasswordMessage().setText("password can not be empty");
                            view.getChangePasswordButton().setChecked(false);
                        } else if (view.getChangePasswordField().getText().equals(App.getApp().getLoggedInPlayer().getPassword())) {
                            cleanMessage();
                            view.getChangePasswordMessage().setText("This is your current password.");
                            view.getChangePasswordButton().setChecked(false);
                        } else if ((matcher = SignupMenuCommands.Password.getMatcher(view.getChangePasswordField().getText())) == null) {
                            cleanMessage();
                            view.getChangePasswordMessage().setText("Password is too weak. Please use a stronger one.");
                            view.getChangePasswordButton().setChecked(false);
                        } else {
                            App app = App.getApp();
                            cleanMessage();
                            getPlayerByUsername(app.getLoggedInPlayer().getUsername()).setPassword(view.getChangePasswordField().getText());
                            view.getChangePasswordMessage().setText("Current password: " + App.getApp().getLoggedInPlayer().getPassword());
                            view.getChangePasswordButton().setChecked(false);
                        }
                    }
                }
            });

            view.getAvatarsSelectBox().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    String selectedAvatar = view.getAvatarsSelectBox().getSelected();
                    switch (selectedAvatar) {
                        case "Avatar1":
                            App.getApp().getLoggedInPlayer().setAvatar(MenuGameAssetManager.getMenuGameAssetManager().avatar1);
                            view.getCurrentAvatarImage().setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("sprite/T/T_Dasher_Portrait.png"))));
                            ;
                            break;
                        case "Avatar2":
                            App.getApp().getLoggedInPlayer().setAvatar(MenuGameAssetManager.getMenuGameAssetManager().avatar2);
                            view.getCurrentAvatarImage().setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("sprite/T/T_Diamond_Portrait.png"))));
                            break;
                        case "Avatar3":
                            App.getApp().getLoggedInPlayer().setAvatar(MenuGameAssetManager.getMenuGameAssetManager().avatar3);
                            view.getCurrentAvatarImage().setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("sprite/T/T_Lilith_Portrait.png"))));
                            break;
                        case "Avatar4":
                            App.getApp().getLoggedInPlayer().setAvatar(MenuGameAssetManager.getMenuGameAssetManager().avatar4);
                            view.getCurrentAvatarImage().setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("sprite/T/T_Scarlett_Portrait.png"))));
                            break;
                        case "Avatar5":
                            App.getApp().getLoggedInPlayer().setAvatar(MenuGameAssetManager.getMenuGameAssetManager().avatar5);
                            view.getCurrentAvatarImage().setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("avatars/Avatar5.png"))));
                            break;
                        case "Avatar6":
                            App.getApp().getLoggedInPlayer().setAvatar(MenuGameAssetManager.getMenuGameAssetManager().avatar6);
                            view.getCurrentAvatarImage().setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("assets/avatars/Avatar6.png"))));
                            break;
                    }
                }
            });

            view.getBackButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {

                }
            });

            view.getBackButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    if (view.getBackButton().isChecked()) {
                        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
                    }
                }
            });

            view.getDeleteAccountButton().addListener(new ChangeListener() {
                App app = App.getApp();

                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    if (view.getDeleteAccountButton().isChecked()) {
                        app.getPlayers().remove(getPlayerByUsername(app.getLoggedInPlayer().getUsername()));
                        app.setLoggedInPlayer(null);
                        app.setBlackAndWhiteMode(false);
                        app.setAutoReload(false);
                        app.setCurrentGame(null);
                        view.getMusic().stop();
                        app.setMusic(MenuGameAssetManager.getMenuGameAssetManager().music3);
                        app.getMusic().setVolume(0.5f);
                        Main.getMain().setScreen(new SignupMenuView(new SignupMenuController(), skin));
                    }
                }
            });

        }
    }

    private void cleanMessage() {
        view.getChangeUsernameMessage().setText("");
        view.getChangePasswordMessage().setText("");
    }

    private boolean isUsernameExist(String username) {
        for (Player player : App.getApp().getPlayers()) {
            if (player.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private Player getPlayerByUsername(String username) {
        for (Player player : App.getApp().getPlayers()) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    private int getPlayerIndex(String username) {
        for (int i = 0; i < App.getApp().getPlayers().size(); i++) {
            if (App.getApp().getPlayers().get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    public void onAvatarImageSelected(FileHandle fileHandle) {
        model.setAvatarFile(fileHandle);
        view.updateAvatar(fileHandle); // تصویر جدید رو بذاره توی UI
    }
}
