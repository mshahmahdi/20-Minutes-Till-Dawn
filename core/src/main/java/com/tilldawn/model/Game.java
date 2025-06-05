package com.tilldawn.model;

public class Game {
    private int id;
    private User user;

    private int score;
    private int time;
    private final int heroNumber;
    private final int gunNumber;

    public Game(int id, User user, int time, int heroNumber, int gunNumber) {
        this.id = id;
        this.user = user;
        this.score = 0;
        if (time == 0) {
            this.time = 20;
        } else if (time == 1) {
            this.time = 10;
        } else if (time == 2) {
            this.time = 5;
        } else if (time == 3) {
            this.time = 2;
        }
        this.heroNumber = heroNumber;
        this.gunNumber = gunNumber;
    }

    public int getId() {
        return id;
    }

    public User getPlayer() {
        return user;
    }

    public int getScore() {
        return score;
    }

    public int getTime() { return time; }

    public int getHeroNumber() { return heroNumber; }

    public int getGunNumber() { return gunNumber; }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayer(User user) {
        this.user = user;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
