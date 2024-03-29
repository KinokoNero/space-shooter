package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.graphics.RelativeBounds;
import com.jpwmii.spaceshooter.graphics.Sprite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Projectile extends Entity implements ActionListener {
    private Timer movementTimer = new Timer(2, this);
    private final double moveStep = 0.001;
    private final double moveSpeed = 3;
    private boolean outOfBounds;

    public Projectile(RelativeBounds relativeBounds) {
        super(relativeBounds, new Sprite("/images/projectile.png"));
        this.outOfBounds = false;
        movementTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.relativeBounds.setVerticalPosition(relativeBounds.getVerticalPosition() - moveStep * moveSpeed);
        if(
                this.relativeBounds.getVerticalPosition() <= - this.relativeBounds.getHeight() ||
                this.relativeBounds.getVerticalPosition() == 0
        )
            this.outOfBounds = true;
    }

    public boolean isOutOfBounds() {
        return outOfBounds;
    }
}
