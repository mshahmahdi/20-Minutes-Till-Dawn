package com.tilldawn.model;

import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

public class App {
    private static App app;

    private Player loggedInPlayer;
    private Player pendingPlayer;
    private game currentGame;

    private boolean blackAndWhiteMode;
    private Music music;

    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<game> games = new ArrayList<>();

    private boolean autoReload;
    private boolean soundEffect;

    public static App getApp() {
        if (app == null) {
            app = new App();
            app.music = MenuGameAssetManager.getMenuGameAssetManager().music3;
            app.autoReload = false;
            app.blackAndWhiteMode = false;
        }
        return app;
    }

    public Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public game getCurrentGame() {
        return currentGame;
    }

    public ArrayList<Player> getPlayers() { return players; }

    public ArrayList<game> getGames() {
        return games;
    }

    public Player getPendingUPlayer() {
        return pendingPlayer;
    }

    public Music getMusic() { return music; }

    public boolean isSoundEffect() { return soundEffect; }

    public boolean isAutoReload() { return autoReload; }

    public boolean isBlackAndWhiteMode() { return blackAndWhiteMode; }

    public void setLoggedInPlayer(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    public void setCurrentGame(game currentGame) {
        this.currentGame = currentGame;
    }

    public void setPendingPlayer(Player pendingPlayer) {
        this.pendingPlayer = pendingPlayer;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public void setAutoReload(boolean autoReload) { this.autoReload = autoReload; }

    public void setBlackAndWhiteMode(boolean blackAndWhiteMode) { this.blackAndWhiteMode = blackAndWhiteMode; }

    public void setSoundEffect(boolean soundEffect) { this.soundEffect = soundEffect; }

    // Auxiliary functions :

    public void addUser(Player player) {
        players.add(player);
    }

    public void addGame(game game) {
        games.add(game);
    }
}
