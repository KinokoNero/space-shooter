package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.graphics.RelativeBounds;
import com.jpwmii.spaceshooter.graphics.Sprite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Asteroid extends Entity implements ActionListener {
    private final double moveSpeed;
    private final double moveStep = 0.001;
    private boolean outOfBounds;
    private Timer movementTimer;

    public Asteroid(RelativeBounds relativeBounds, double moveSpeed) {
        super(
                relativeBounds,
                new Sprite("/images/asteroid-sprite-sheet.png", 20)
        );
        this.moveSpeed = moveSpeed;
        this.outOfBounds = false;
        movementTimer = new Timer(5, this);
        movementTimer.start();
    }

    /*public void move() {
        this.relativeBounds.setVerticalPosition(relativeBounds.getVerticalPosition() + moveStep * moveSpeed);
        if(this.relativeBounds.getVerticalPosition() > 1)
            this.outOfBounds = true;
    }*/

    public Explosion explode() {
        return new Explosion(this.relativeBounds);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.relativeBounds.setVerticalPosition(relativeBounds.getVerticalPosition() + moveStep * moveSpeed);
        if(this.relativeBounds.getVerticalPosition() > 1)
            this.outOfBounds = true;
    }

    public boolean isOutOfBounds() {
        return outOfBounds;
    }
}
