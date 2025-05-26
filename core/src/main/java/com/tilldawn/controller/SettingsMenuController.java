package com.tilldawn.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.Enums.KeysController;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.view.MainMenuView;
import com.tilldawn.view.SettingsMenuView;

public class SettingsMenuController {
    private SettingsMenuView view;
    private final Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();

    public void setView(SettingsMenuView view) {
        this.view = view;
    }

    public void handleSettingsMenu() {
        if (view != null) {
            view.getVolumeSlider().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    float volume = view.getVolumeSlider().getValue();
                    view.getMusic().pause();
                    MenuGameAssetManager.setVolume(volume/10f);
                    App.getApp().getMusic().setVolume(volume/10);
                    view.getMusic().setVolume(volume/10);
                    view.getMusic().play();
                }
            });

            view.getMusicSelectBox().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    String selected = view.getMusicSelectBox().getSelected();
                    App app = App.getApp();
                    switch (selected) {
                        case "Music 1":
                            view.getMusic().stop();
                            app.setMusic(MenuGameAssetManager.getMenuGameAssetManager().music1);
                            view.setMusic(MenuGameAssetManager.getMenuGameAssetManager().music1);
                            view.getMusic().play();
                            break;
                        case "Music 2":
                            view.getMusic().stop();
                            app.setMusic(MenuGameAssetManager.getMenuGameAssetManager().music2);
                            view.setMusic(MenuGameAssetManager.getMenuGameAssetManager().music2);
                            view.getMusic().play();
                            break;
                        case "Music 3":
                            view.getMusic().stop();
                            app.setMusic(MenuGameAssetManager.getMenuGameAssetManager().music3);
                            view.setMusic(MenuGameAssetManager.getMenuGameAssetManager().music3);
                            view.getMusic().play();
                            break;
                        case "Music 4":
                            view.getMusic().stop();
                            app.setMusic(MenuGameAssetManager.getMenuGameAssetManager().music4);
                            view.setMusic(MenuGameAssetManager.getMenuGameAssetManager().music4);
                            view.getMusic().play();
                            break;
                    }
                }
            });

            view.getControllerSelectBox().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    int selected = view.getControllerSelectBox().getSelectedIndex();
                    switch (selected) {
                        case 0:
                            KeysController.UP.setKey(Input.Keys.W);
                            KeysController.DOWN.setKey(Input.Keys.S);
                            KeysController.LEFT.setKey(Input.Keys.A);
                            KeysController.RIGHT.setKey(Input.Keys.D);
                        case 1:
                            KeysController.UP.setKey(Input.Keys.UP);
                            KeysController.DOWN.setKey(Input.Keys.DOWN);
                            KeysController.LEFT.setKey(Input.Keys.LEFT);
                            KeysController.RIGHT.setKey(Input.Keys.RIGHT);
                    }
                }
            });

            view.getControllerSelectBox2().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    int selected = view.getControllerSelectBox2().getSelectedIndex();
                    switch (selected) {
                        case 0:
                            KeysController.SHOOT.setKey(Input.Buttons.LEFT);
                            break;
                        case 1:
                            KeysController.SHOOT.setKey(Input.Keys.SPACE);
                    }
                }
            });

            view.getAutoReloadCheckbox().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    if (view.getAutoReloadCheckbox().isChecked()) {
                        App.getApp().setAutoReload(true);
                    } else {
                        App.getApp().setAutoReload(false);
                    }
                }
            });

            view.getBlackAndWhiteCheckbox().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    if (view.getBlackAndWhiteCheckbox().isChecked()) {
                        App.getApp().setBlackAndWhiteMode(true);
                    } else {
                        App.getApp().setBlackAndWhiteMode(false);
                    }
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
        }
    }


}
