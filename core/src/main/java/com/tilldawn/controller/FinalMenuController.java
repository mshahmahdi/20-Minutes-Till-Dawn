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
import com.tilldawn.view.PregameMenuView;

public class FinalMenuController {
    private FinalMenuView view;
    private final Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();

    public void setView(FinalMenuView view) {
        this.view = view;
    }

    public void handleFinalMenu() {
        view.getMainMenuButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                App app = App.getApp();
                Player player = App.getApp().getCurrentGameView().getController().getPlayerController().getPlayer();
                Game game = App.getApp().getCurrentGameView().game;
                if (view.getMainMenuButton().isChecked()) {
                    app.getLoggedInUser().setKills(app.getLoggedInUser().getKills() + player.getKills());
                    app.getLoggedInUser().setLongestSurvivalTime((int) (game.getSurvivalTime() / 60));
                    app.getLoggedInUser().setScore(app.getLoggedInUser().getScore() + 100 * player.getLevel());
                    app.setCurrentGameView(null);
                    app.setCurrentGame(null);
                    Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
                }
            }
        });

        view.getPlayAgainButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                App app = App.getApp();
                Player player = App.getApp().getCurrentGameView().getController().getPlayerController().getPlayer();
                Game game = App.getApp().getCurrentGameView().game;
                if (view.getPlayAgainButton().isChecked()) {
                    app.getLoggedInUser().setKills(app.getLoggedInUser().getKills() + player.getKills());
                    app.getLoggedInUser().setLongestSurvivalTime((int) (game.getSurvivalTime() / 60));
                    app.getLoggedInUser().setScore(app.getLoggedInUser().getScore() + 100 * player.getLevel());
                    app.setCurrentGameView(null);
                    app.setCurrentGame(null);
                    Main.getMain().setScreen(new PregameMenuView(new PregameMenuController(), skin));
                }
            }
        });
    }
}
