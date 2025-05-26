package com.tilldawn.model;

public class game {
    private int id;
    private Player player;
    private int score;

    public game(int id, Player player) {
        this.id = id;
        this.player = player;
        this.score = 0;
    }

    public int getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
