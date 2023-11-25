package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.graphics.RelativeBounds;
import com.jpwmii.spaceshooter.graphics.Sprite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Projectile extends Entity implements ActionListener {
    private Timer timer = new Timer(10, this);
    private Direction direction; //TODO: flip the projectile image based on direction but before it is painted
    private boolean visible;

    public Projectile(RelativeBounds relativeBounds, Direction direction) {
        super(relativeBounds, new Sprite("/projectile.png"));
        if(direction == Direction.UP || direction == Direction.DOWN)
            this.direction = direction;
        else
            throw new IllegalArgumentException("Direction for projectile can only be UP or DOWN");
        this.visible = true;
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.relativeBounds.setVerticalPosition(this.relativeBounds.getVerticalPosition() + this.direction.value * 0.01);
        if(
                this.relativeBounds.getVerticalPosition() <= - this.relativeBounds.getHeight() ||
                this.relativeBounds.getVerticalPosition() == 0
        )
            this.visible = false;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isVisible() {
        return visible;
    }
}
