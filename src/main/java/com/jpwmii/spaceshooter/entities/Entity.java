package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.graphics.Sprite;

public abstract class Entity {
    //TODO: consider if the position has to be window size relative - it might be automatically adjusted during window resizing
    protected double horizontalPosition; //expected values between 0 and 1; specifies position from the left side of the screen as % toward the right side
    protected double verticalPosition; //expected values between 0 and 1; specifies position from the top side of the screen as % toward the bottom side
    protected final double movementStep = 0.01;
    protected Sprite entitySprite;
    protected enum Direction {
        LEFT(-1), RIGHT(1), UP(-1), DOWN(1);

        final int value;

        Direction(int value) {
            this.value = value;
        }
    }


}
