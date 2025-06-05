package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private int id;
    private User user;

    private int score;
    private int time;
    private final int heroNumber;
    private final int gunNumber;

    private ArrayList<TreeMonster> treeMonsters = new ArrayList<>();

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

    public ArrayList<TreeMonster> getTreeMonsters() {
        return treeMonsters;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayer(User user) {
        this.user = user;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Auxiliary functions :

    public void initTrees(float mapWidth, float mapHeight) {
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            float x = random.nextFloat() * (mapWidth - 100); // فاصله از لبه
            float y = random.nextFloat() * (mapHeight - 100);
            TreeMonster tree = new TreeMonster(x, y);
            treeMonsters.add(tree);
        }
    }
}
