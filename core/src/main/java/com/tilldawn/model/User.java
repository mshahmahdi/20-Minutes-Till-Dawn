package com.tilldawn.model;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class User {
    private String username;
    private String password;
    private String securityQuestion;
    private Image avatar;

    private Game currentGame;

    private int kills;
    private int score;
    private int longestSurvivalTime;

    public User(String username, String password, String securityQuestion) {
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.currentGame = null;
        this.score = 0;
        this.longestSurvivalTime = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public Image getAvatar() {
        return avatar;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public int getScore() {
        return score;
    }

    public int getKills() { return kills; }

    public int getLongestSurvivalTime() { return longestSurvivalTime; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setKills(int kills) { this.kills = kills; }

    public void setLongestSurvivalTime(int longestSurvivalTime) { this.longestSurvivalTime = longestSurvivalTime; }
}
