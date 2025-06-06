package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int id;
    private User user;
    private int score;
    private float time;
    private float survivalTime;
    private int minutes;
    private int seconds;
    private String timeText;
    private final int heroNumber;
    private final int gunNumber;

    private float elapsedTime;
    private boolean isRunning = true;

    private boolean vitality = false;
    private boolean damager = false;
    private boolean procrease = false;
    private boolean amocrease = false;
    private boolean speedy = false;

    private ArrayList<TreeMonster> treeMonsters = new ArrayList<>();
    private ArrayList<PumpkinMonster> pumpkinMonsters = new ArrayList<>();
    private List<DroppedItem> droppedItems = new ArrayList<>();

    public Game(int id, User user, int time, int heroNumber, int gunNumber) {
        this.id = id;
        elapsedTime = 0f;
        this.user = user;
        this.score = 0;
        if (time == 0) {
            this.time = 20f * 60f;
        } else if (time == 1) {
            this.time = 10f * 60f;
        } else if (time == 2) {
            this.time = 5f * 60f;
        } else if (time == 3) {
            this.time = 2f * 60f;
        }
        this.minutes = (int) (time / 60);
        this.seconds = (int) (time % 60);
        this.timeText = String.format("%02d:%02d", minutes, seconds);;
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

    public float getTime() {
        return time;
    }

    public void setTime(float time, float delta) {
        this.time = time;
        this.minutes = (int) (time / 60);
        this.seconds = (int) (time % 60);
        this.timeText = String.format("%02d:%02d", minutes, seconds);
        this.survivalTime = survivalTime + delta;
    }

    public String getTimeText() {
        return timeText;
    }

    public float getSurvivalTime() {
        return survivalTime;
    }

    public boolean isVitality() {
        return vitality;
    }

    public boolean isDamager() {
        return damager;
    }

    public boolean isProcrease() {
        return procrease;
    }

    public boolean isAmocrease() {
        return amocrease;
    }

    public boolean isSpeedy() {
        return speedy;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setVitality(boolean vitality) {
        this.vitality = vitality;
    }

    public void setDamager(boolean damager) {
        this.damager = damager;
    }

    public void setProcrease(boolean procrease) {
        this.procrease = procrease;
    }

    public void setAmocrease(boolean amocrease) {
        this.amocrease = amocrease;
    }

    public void setSpeedy(boolean speedy) {
        this.speedy = speedy;
    }

    public void update(float delta) {
        if (isRunning) {
            elapsedTime += delta;
        }
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void pause() {
        isRunning = false;
    }

    public void resume() {
        isRunning = true;
    }

    public int getHeroNumber() {
        return heroNumber;
    }

    public int getGunNumber() {
        return gunNumber;
    }

    public ArrayList<TreeMonster> getTreeMonsters() {
        return treeMonsters;
    }

    public ArrayList<PumpkinMonster> getPumpkinMonsters() {
        return pumpkinMonsters;
    }

    public List<DroppedItem> getDroppedItems() {
        return droppedItems;
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

    public String getAbilities() {
        String abilities = "";
        if (isVitality()) {
            abilities += "Vitality  ";
        } else if (isDamager()) {
            abilities += "Damager  ";
        } else if (isProcrease()) {
            abilities += "Procrease ";
        } else if (isAmocrease()) {
            abilities += "Amocrease ";
        } else if (isSpeedy()) {
            abilities += "Speedy ";
        }
        return abilities;
    }
}
