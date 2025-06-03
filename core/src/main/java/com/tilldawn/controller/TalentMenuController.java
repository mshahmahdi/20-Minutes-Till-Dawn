package com.tilldawn.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tilldawn.Main;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.view.MainMenuView;
import com.tilldawn.view.TalentMenuView;

public class TalentMenuController {
    private TalentMenuView view;
    private final Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();

    public void setView(TalentMenuView view) {
        this.view = view;
    }

    public void handleTalentMenu() {
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
