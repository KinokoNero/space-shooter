package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.graphics.RelativeBounds;
import com.jpwmii.spaceshooter.graphics.Sprite;

public class Explosion extends Entity {
    private RelativeBounds relativeBounds;
    private boolean animationDone;
    public Explosion(RelativeBounds relativeBounds) {
        super(relativeBounds, new Sprite("/images/explosion-sprite-sheet.png", 17));
    }

    public boolean isAnimationDone() {
        return this.getEntitySprite().getFrameIndex() == this.getEntitySprite().getFrameCount() - 1;
    }
}
