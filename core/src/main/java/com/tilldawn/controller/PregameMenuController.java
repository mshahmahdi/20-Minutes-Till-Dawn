package com.tilldawn.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.Game;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.view.GameView;
import com.tilldawn.view.MainMenuView;
import com.tilldawn.view.PregameMenuView;

public class PregameMenuController {
    private PregameMenuView view;
    private final Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();

    public void setView(PregameMenuView view) {
        this.view = view;
    }

    public void handlePregameMenu() {

        view.getHeroesSelect().addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {

            }
        });

        view.getTimeSelection().addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {

            }
        });

        view.getGunesSelect().addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {

            }
        });

        App app = App.getApp();
        view.getStartGameButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (view.getStartGameButton().isChecked()) {
                    Game newGame = new Game(app.getCurrentGameId(),
                        app.getLoggedInUser(),
                        view.getTimeSelection().getSelectedIndex(),
                        view.getHeroesSelect().getSelectedIndex(),
                        view.getGunesSelect().getSelectedIndex());
                    newGame.initTrees(MenuGameAssetManager.getMenuGameAssetManager().mapImage.getWidth(),
                        MenuGameAssetManager.getMenuGameAssetManager().mapImage.getHeight());
                    app.setCurrentGameId(app.getCurrentGameId() + 1);
                    app.setCurrentGame(newGame);
                    app.getLoggedInUser().setCurrentGame(newGame);
                    Main.getMain().setScreen(new GameView(new GameController(newGame), skin, newGame));
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
