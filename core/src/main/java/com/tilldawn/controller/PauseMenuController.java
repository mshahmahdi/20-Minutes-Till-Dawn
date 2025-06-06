package com.tilldawn.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.Game;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.Player;
import com.tilldawn.view.FinalMenuView;
import com.tilldawn.view.MainMenuView;
import com.tilldawn.view.PauseMenuView;
import com.tilldawn.view.TalentMenuView;

public class PauseMenuController {
    private PauseMenuView view;
    private final Skin menusSkin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();
    private final Skin gamesSkin = MenuGameAssetManager.getMenuGameAssetManager().getGameSkin();

    public void setView(PauseMenuView view) {
        this.view = view;
    }

    public void handlePause() {
        view.getResumeButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (view.getResumeButton().isChecked()) {
                    Main.getMain().setScreen(App.getApp().getCurrentGameView());
                    App.getApp().getCurrentGameView().setIsPaused(false);
                }
            }
        });

        view.getExitButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (view.getExitButton().isChecked()) {
                    Main.getMain().setScreen(new MainMenuView(new MainMenuController(), menusSkin));
                }
            }
        });

        view.getBlackAndWhiteCheckBox().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                App.getApp().setBlackAndWhiteMode(view.getBlackAndWhiteCheckBox().isChecked());
            }
        });

        view.getGiveUpButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (view.getGiveUpButton().isChecked()) {
                    Main.getMain().setScreen(new FinalMenuView(new FinalMenuController(), menusSkin, false));
                }
            }
        });

    }
}
