package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.graphics.RelativeBounds;
import com.jpwmii.spaceshooter.graphics.Sprite;

public class Projectile extends Entity {
    public Projectile(RelativeBounds relativeBounds) {
        super(relativeBounds, new Sprite("/projectile.png", 1));
    }
}
