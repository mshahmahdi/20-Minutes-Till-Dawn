package com.tilldawn.model.Enums;

import com.badlogic.gdx.Input;

public enum ArrowMovement {
    UP(Input.Keys.UP),
    DOWN(Input.Keys.DOWN),
    LEFT(Input.Keys.LEFT),
    RIGHT(Input.Keys.RIGHT);


    ;

    private final int key;

    ArrowMovement(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
