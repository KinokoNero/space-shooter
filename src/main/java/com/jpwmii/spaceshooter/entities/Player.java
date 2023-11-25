package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.graphics.RelativeBounds;
import com.jpwmii.spaceshooter.graphics.Sprite;

import javax.swing.*;
import java.net.URL;

public class Player extends Entity {
    private int lives;
    private int score;
    private ImageIcon lifeIcon;
    private Timer shotCooldownTimer = new Timer(500, e -> ((Timer) e.getSource()).stop());

    public Player(RelativeBounds relativeBounds) {
        super(
                relativeBounds,
                new Sprite("/player-spaceship-sprite-sheet.png", 101)
        );
        this.lives = 3;
        this.score = 0;
        loadResources();
    }

    private void loadResources() {
        URL imageUrl = getClass().getResource("/heart.png");
        if (imageUrl != null) {
            this.lifeIcon = new ImageIcon(imageUrl);
        } else {
            System.err.println("Failed to load the life icon sprite sheet image.");
        }
    }

    public void move(Direction direction) { //direction value is either (-1) for LEFT or (1) for RIGHT
        double newPosition = this.relativeBounds.getHorizontalPosition() + movementStep * direction.value;
        if(newPosition < 0)
            newPosition = 0;
        if(newPosition > 1)
            newPosition = 1;
        this.relativeBounds.setHorizontalPosition(newPosition);
    }

    public Projectile createProjectile() {
        if(!shotCooldownTimer.isRunning()) {
            shotCooldownTimer.start();
            return new Projectile(
                    new RelativeBounds(
                            this.relativeBounds.getHorizontalPosition() + this.relativeBounds.getWidth() / 4, //center of the player spaceship
                            this.relativeBounds.getVerticalPosition(),
                            0.05,
                            0.1,
                            false
                    ),
                    Direction.UP
            );
        }
        return null;
    }
}
