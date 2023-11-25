package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.graphics.RelativeBounds;
import com.jpwmii.spaceshooter.graphics.Sprite;

public abstract class Entity {
    protected RelativeBounds relativeBounds;
    protected final double movementStep = 0.01;
    protected Sprite entitySprite;
    public enum Direction {
        LEFT(-1), RIGHT(1), UP(-1), DOWN(1);

        final int value;

        Direction(int value) {
            this.value = value;
        }
    }

    public Entity(RelativeBounds relativeBounds, Sprite entitySprite) {
        this.relativeBounds = relativeBounds;
        this.entitySprite = entitySprite;
    }

    public RelativeBounds getRelativeBounds() {
        return relativeBounds;
    }

    public Sprite getEntitySprite() {
        return entitySprite;
    }
}
