package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.graphics.Sprite;

import javax.swing.*;
import java.net.URL;

public class Player extends Entity {
    //private double horizontalPosition; //expected values between 0 and 1; specifies position from the left side of the screen as %
    //private final double horizontalPositionMovementStep = 0.01;
    private int lives;
    private int score;
    private ImageIcon lifeIcon;
    //private Animation playerStarship;

    public Player() {
        this.horizontalPosition = 0.5;
        this.lives = 3;
        this.score = 0;
        loadResources();
    }

    private void loadResources() {
        this.entitySprite = new Sprite("/player-spaceship.png", 0, 0, 0); //TODO: modify this line accordingly

        URL imageUrl = getClass().getResource("/heart.png");
        if (imageUrl != null) {
            this.lifeIcon = new ImageIcon(imageUrl);
        } else {
            System.err.println("Failed to load the life icon sprite sheet image.");
        }
    }

    public void moveSpaceship(Direction direction) { //direction value is either (-1) for LEFT or (1) for RIGHT
        double newPosition = this.horizontalPosition + movementStep * direction.value;
        if(newPosition < 0)
            newPosition = 0;
        if(newPosition > 1)
            newPosition = 1; //TODO: make sure the spaceship fits the screen from the right side
        this.horizontalPosition = newPosition;
    }

    public Sprite getPlayerStarship() {
        return this.entitySprite;
    }
}
