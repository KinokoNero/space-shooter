package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.graphics.RelativeBounds;
import com.jpwmii.spaceshooter.graphics.Sprite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Asteroid extends Entity implements ActionListener {
    private final double moveSpeed;
    private final double moveStep = 0.01;
    private boolean outOfBounds;
    private Timer movementTimer;

    public Asteroid(RelativeBounds relativeBounds, double moveSpeed) {
        super(
                relativeBounds,
                new Sprite("/asteroid-sprite-sheet.png", 20)
        );
        this.moveSpeed = moveSpeed;
        this.outOfBounds = false;
        movementTimer = new Timer(50, this);
        movementTimer.start();
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
