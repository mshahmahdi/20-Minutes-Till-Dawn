package com.tilldawn.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.Player;
import com.tilldawn.view.AbilityMenuView;

public class AbilityMenuController {
    private AbilityMenuView view;
    private final Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();

    public void setView(AbilityMenuView view) {
        this.view = view;
    }

    public void handleAbilityMenu() {
        view.getBackToGameButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (view.getBackToGameButton().isChecked()) {
                    switch (view.getAbilitySelectBox().getSelected()) {
                        case "DAMAGER" :
                            App.getApp().getCurrentGameView().game.setDamager(true);
                            Main.getMain().setScreen(App.getApp().getCurrentGameView());
                            App.getApp().getCurrentGameView().setIsPaused(false);
                            App.getApp().getCurrentGame().setDamager(true);
                            break;
                        case "VITALITY" :
                            App.getApp().getCurrentGameView().getController().getPlayerController().getPlayer().addHP();
                            Main.getMain().setScreen(App.getApp().getCurrentGameView());
                            App.getApp().getCurrentGameView().setIsPaused(false);
                            App.getApp().getCurrentGame().setVitality(true);
                            break;
                        case "AMOCREASE" :
                            App.getApp().getCurrentGameView().getController().getWeaponController().getWeapon().add5();
                            Main.getMain().setScreen(App.getApp().getCurrentGameView());
                            App.getApp().getCurrentGameView().setIsPaused(false);
                            App.getApp().getCurrentGame().setAmocrease(true);
                            break;
                        case "PROCREASE" :
                            App.getApp().getCurrentGameView().getController().getWeaponController().getWeapon().addPellet();
                            Main.getMain().setScreen(App.getApp().getCurrentGameView());
                            App.getApp().getCurrentGameView().setIsPaused(false);
                            App.getApp().getCurrentGame().setProcrease(true);
                            break;
                        case "SPEEDY" :
                            App.getApp().getCurrentGameView().getController().getPlayerController().getPlayer().activateSpeedBoost();
                            Main.getMain().setScreen(App.getApp().getCurrentGameView());
                            App.getApp().getCurrentGameView().setIsPaused(false);
                            App.getApp().getCurrentGame().setSpeedy(true);
                            break;
                    }
                }
            }
        });
    }
}
