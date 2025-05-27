package com.tilldawn.model.Enums;

import com.badlogic.gdx.Input;

public enum KeysController {
    UP(Input.Keys.W),
    DOWN(Input.Keys.S),
    LEFT(Input.Keys.A),
    RIGHT(Input.Keys.D),
    SHOOT(Input.Buttons.LEFT),
    RELOAD(Input.Keys.R),
    AUTO_AIM(Input.Keys.SPACE),

    ;

    private int key;

    KeysController(int key) {
        this.key = key;
    }

    public  int getKey() {
        return key;
    }

    public void setKey(int newKey) {
        this.key = newKey;
    }
}


