package com.tilldawn.model;

import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

public class App {
    private static App app;

    private User loggedInUser;
    private User pendingUser;
    private Game currentGame;

    private int currentGameId = 101;

    private boolean blackAndWhiteMode;
    private Music music;

    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Game> Games = new ArrayList<>();

    private boolean autoReload;
    private boolean soundEffect;

    public static App getApp() {
        if (app == null) {
            app = new App();
            app.music = MenuGameAssetManager.getMenuGameAssetManager().music3;
            app.autoReload = false;
            app.blackAndWhiteMode = false;
            app.soundEffect = true;
        }
        return app;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public ArrayList<User> getUsers() { return users; }

    public ArrayList<Game> getGames() {
        return Games;
    }

    public User getPendingUPlayer() {
        return pendingUser;
    }

    public Music getMusic() { return music; }

    public int getCurrentGameId() { return currentGameId; }

    public boolean isSoundEffect() { return soundEffect; }

    public boolean isAutoReload() { return autoReload; }

    public boolean isBlackAndWhiteMode() { return blackAndWhiteMode; }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public void setPendingPlayer(User pendingUser) {
        this.pendingUser = pendingUser;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public void setAutoReload(boolean autoReload) { this.autoReload = autoReload; }

    public void setBlackAndWhiteMode(boolean blackAndWhiteMode) { this.blackAndWhiteMode = blackAndWhiteMode; }

    public void setSoundEffect(boolean soundEffect) { this.soundEffect = soundEffect; }

    public void setCurrentGameId(int currentGameId) { this.currentGameId = currentGameId; }

    // Auxiliary functions :

    public void addUser(User user) {
        users.add(user);
    }

    public void addGame(Game game) {
        Games.add(game);
    }
}
