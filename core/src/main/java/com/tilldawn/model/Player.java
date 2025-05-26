package com.tilldawn.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player {
    private String username;
    private String password;
    private String securityQuestion;
    private Image avatar;
    private game currentGame;
    private int score;

    public Player(String username, String password, String securityQuestion) {
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.currentGame = null;
        this.score = 0;
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

    public game getCurrentGame() {
        return currentGame;
    }

    public int getScore() {
        return score;
    }

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

    public void setCurrentGame(game currentGame) {
        this.currentGame = currentGame;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
