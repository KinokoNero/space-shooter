package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.graphics.RelativeBounds;
import com.jpwmii.spaceshooter.graphics.Sprite;

import javax.swing.*;
import java.net.URL;

public class Player extends Entity {
    private int lives;
    private int score;
    private ImageIcon lifeIcon;

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

    public void moveSpaceship(Direction direction) { //direction value is either (-1) for LEFT or (1) for RIGHT
        double newPosition = this.relativeBounds.getHorizontalPosition() + movementStep * direction.value;
        if(newPosition < 0)
            newPosition = 0;
        if(newPosition > 1)
            newPosition = 1;
        this.relativeBounds.setHorizontalPosition(newPosition);
    }
}
