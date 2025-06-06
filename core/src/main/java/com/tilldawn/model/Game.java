package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Game {
    private int id;
    private User user;
    private int score;
    private float time;
    private float totalTime;
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

    private float damagerTimer = 0f;

    private FinalBoss finalBoss;
    private boolean bossSpawned = false;
    private float bossWallSize = 7000f; // اندازه اولیه حفاظ
    private boolean bossWallActive = false;

    private Texture bossRingTexture;
    private Sprite bossRingSprite;

    private ArrayList<TreeMonster> treeMonsters = new ArrayList<>();
    private ArrayList<PumpkinMonster> pumpkinMonsters = new ArrayList<>();
    private List<DroppedItem> droppedItems = new ArrayList<>();
    private ArrayList<EyebatMonster> eyebatMonsters = new ArrayList<>();

    private boolean isAutoAim = false;

    public Game(int id, User user, int time, int heroNumber, int gunNumber) {
        bossRingTexture = new Texture("sprite/T/T_SelectorBubble_0.png");
        bossRingSprite = new Sprite(bossRingTexture);
        bossRingSprite.setOriginCenter();
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
        this.survivalTime = 0f;
        this.totalTime = time;
        this.minutes = (int) (time / 60);
        this.seconds = (int) (time % 60);
        this.timeText = String.format("%02d:%02d", minutes, seconds);
        ;
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

    public float getTotalTime() {
        return totalTime;
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
        if (damager) {
            damagerTimer = 10f; // مدت زمان ابیلیتی به ثانیه
        }
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

    public void setAutoAim(boolean autoAim) {
        isAutoAim = autoAim;
    }

    public boolean isAutoAim() {
        return isAutoAim;
    }

    public void update(float delta) {
        if (isRunning) {
            elapsedTime += delta;
        }
        if (damager) {
            damagerTimer -= delta;
            if (damagerTimer <= 0f) {
                damager = false;
            }
        }

        if (!bossSpawned && elapsedTime >= 300f) {
            finalBoss = new FinalBoss(App.getApp().getCurrentGameView().getController().getPlayerController().getPlayer().getPosX() + 100,
                App.getApp().getCurrentGameView().getController().getPlayerController().getPlayer().getPosY() + 100); // یه مکان مناسب انتخاب کن
            bossSpawned = true;
            bossWallActive = true;
        }

        // اگر باس اسپان شده، آپدیت کن
        if (bossSpawned && finalBoss != null && !finalBoss.isDead()) {
            finalBoss.update(delta, App.getApp().getCurrentGameView().getController().getPlayerController().getPlayer());

            // کاهش اندازه حفاظ به مرور
            bossWallSize -= delta * 10f; // سرعت کاهش اندازه
            if (playerIsOutsideWall(App.getApp().getCurrentGameView().getController().getPlayerController().getPlayer(), bossWallSize)) {
                App.getApp().getCurrentGameView().getController().getPlayerController().getPlayer().takeDamage(1); // اگر از محیط بزنه بیرون
            }
        }

        // حذف باس و حفاظ
        if (bossSpawned && finalBoss != null) {
            bossWallActive = false;
        }

        if (bossWallActive) {
            bossWallSize -= delta * 10f;

            float centerX = finalBoss.getSprite().getX() + finalBoss.getSprite().getWidth() / 2f;
            float centerY = finalBoss.getSprite().getY() + finalBoss.getSprite().getHeight() / 2f;

            bossRingSprite.setScale(bossWallSize / bossRingTexture.getWidth()); // حلقه رو با scale کوچیک کن
            bossRingSprite.setPosition(centerX - bossRingSprite.getWidth() / 2f, centerY - bossRingSprite.getHeight() / 2f);

        }
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
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

    public FinalBoss getFinalBoss() {
        return finalBoss;
    }

    public boolean isBossSpawned() {
        return bossSpawned;
    }

    public float getBossWallSize() {
        return bossWallSize;
    }

    public boolean isBossWallActive() {
        return bossWallActive;
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

    public ArrayList<EyebatMonster> getEyebatMonsters() {
        return eyebatMonsters;
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

    private boolean playerIsOutsideWall(Player player, float wallSize) {
        float px = player.getPosX();
        float py = player.getPosY();

        if (finalBoss == null) return false; // اگه باس هنوز نیومده

        float centerX = finalBoss.getSprite().getX() + finalBoss.getSprite().getWidth() / 2f;
        float centerY = finalBoss.getSprite().getY() + finalBoss.getSprite().getHeight() / 2f;

        float dx = px - centerX;
        float dy = py - centerY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance > bossWallSize / 2f;
    }

    public Sprite getBossRingSprite() {
        return bossRingSprite;
    }

    public void setFinalBoss(FinalBoss finalBoss) {
        this.finalBoss = finalBoss;
    }


}
