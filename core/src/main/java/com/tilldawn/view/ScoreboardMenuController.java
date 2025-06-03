package com.tilldawn.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tilldawn.Main;
import com.tilldawn.controller.MainMenuController;
import com.tilldawn.controller.ScoreboardMenuView;
import com.tilldawn.model.App;
import com.tilldawn.model.MenuGameAssetManager;
import com.tilldawn.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class ScoreboardMenuController {
    private ScoreboardMenuView view;
    private final Skin skin = MenuGameAssetManager.getMenuGameAssetManager().getMenuSkin();

    public void setView(ScoreboardMenuView view) {
        this.view = view;
    }

    public void handleScoreboardMenu() {
        view.getSortbySelectBox().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                String selected = view.getSortbySelectBox().getSelected();
                App app = App.getApp();
                ArrayList<Integer> sortedBy = new ArrayList<>();
                ArrayList<String> sortedUsernames = new ArrayList<>();
                switch (selected) {
                    case "Score":
                        sortedBy = ascendingSortUnique(getScores());
                        sortedUsernames = sortByScore(sortedBy);
                        ScoreboardMenuView view1 = new ScoreboardMenuView(new ScoreboardMenuController(), skin, sortedUsernames);
                        view1.getSortbySelectBox().setSelected("Score");
                        Main.getMain().setScreen(view1);
                        break;
                    case "Username":
                        sortedUsernames = alphabeticalSortUnique(getUsernames());
                        ScoreboardMenuView view2 = new ScoreboardMenuView(new ScoreboardMenuController(), skin, sortedUsernames);
                        view2.getSortbySelectBox().setSelected("Username");
                        Main.getMain().setScreen(view2);
                        break;
                    case "Kills":
                        sortedBy = ascendingSortUnique(getKills());
                        sortedUsernames = sortByKills(sortedBy);
                        ScoreboardMenuView view3 = new ScoreboardMenuView(new ScoreboardMenuController(), skin, sortedUsernames);
                        view3.getSortbySelectBox().setSelected("Kills");
                        Main.getMain().setScreen(view3);
                        break;
                    case "Survived Time":
                        sortedBy = ascendingSortUnique(getSurvivedTimes());
                        sortedUsernames = sortBySurvivedTime(sortedBy);
                        ScoreboardMenuView view4 = new ScoreboardMenuView(new ScoreboardMenuController(), skin, sortedUsernames);
                        view4.getSortbySelectBox().setSelected("Survived Time");
                        Main.getMain().setScreen(view4);
                        break;
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

    public static ArrayList<Integer> ascendingSortUnique(ArrayList<Integer> list) {
        TreeSet<Integer> set = new TreeSet<>(list); // خودش تکراری‌ها رو حذف و صعودی مرتب می‌کنه
        return new ArrayList<>(set);
    }

    public static ArrayList<Integer> descendingSortUnique(ArrayList<Integer> list) {
        TreeSet<Integer> set = new TreeSet<>(Collections.reverseOrder());
        set.addAll(list);
        return new ArrayList<>(set);
    }

    public static ArrayList<String> alphabeticalSortUnique(ArrayList<String> list) {
        TreeSet<String> set = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        set.addAll(list);
        return new ArrayList<>(set);
    }

    public static ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for (Player player : App.getApp().getPlayers()) {
            usernames.add(player.getUsername());
        }
        return usernames;
    }

    public static ArrayList<Integer> getScores() {
        ArrayList<Integer> scores = new ArrayList<>();
        for (Player player : App.getApp().getPlayers()) {
            scores.add(player.getScore());
        }
        return scores;
    }

    public static ArrayList<Integer> getKills() {
        ArrayList<Integer> kills = new ArrayList<>();
        for (Player player : App.getApp().getPlayers()) {
            kills.add(player.getKills());
        }
        return kills;
    }

    public static ArrayList<Integer> getSurvivedTimes() {
        ArrayList<Integer> survivedTimes = new ArrayList<>();
        for (Player player : App.getApp().getPlayers()) {
            survivedTimes.add(player.getLongestSurvivalTime());
        }
        return survivedTimes;
    }

    public static ArrayList<String> sortByScore(ArrayList<Integer> scores) {
        ArrayList<String> sorted = new ArrayList<>();
        for (Integer score : scores) {
            for (Player player : App.getApp().getPlayers()) {
                if (player.getScore() == score) {
                    sorted.add(player.getUsername());
                }
            }
        }
        return sorted;
    }

    public static ArrayList<String> sortByKills(ArrayList<Integer> kills) {
        ArrayList<String> sorted = new ArrayList<>();
        for (Integer kill : kills) {
            for (Player player : App.getApp().getPlayers()) {
                if (player.getKills() == kill) {
                    sorted.add(player.getUsername());
                }
            }
        }
        return sorted;
    }

    public static ArrayList<String> sortBySurvivedTime(ArrayList<Integer> survivedTimes) {
        ArrayList<String> sorted = new ArrayList<>();
        for (Integer survivedTime : survivedTimes) {
            for (Player player : App.getApp().getPlayers()) {
                if (player.getLongestSurvivalTime() == survivedTime) {
                    sorted.add(player.getUsername());
                }
            }
        }
        return sorted;
    }

}
