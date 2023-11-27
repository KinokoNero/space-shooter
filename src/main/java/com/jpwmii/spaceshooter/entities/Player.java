package com.jpwmii.spaceshooter.entities;

import com.jpwmii.spaceshooter.audio.AudioPlayer;
import com.jpwmii.spaceshooter.graphics.RelativeBounds;
import com.jpwmii.spaceshooter.graphics.Sprite;

import javax.swing.*;
import java.net.URL;

public class Player extends Entity {
    private final double moveStep = 0.01;
    private int lives;
    private boolean alive;
    private int score;
    private ImageIcon lifeIcon;
    private Timer shotCooldownTimer = new Timer(500, e -> ((Timer) e.getSource()).stop());

    public Player(RelativeBounds relativeBounds) {
        super(
                relativeBounds,
                new Sprite("/images/player-spaceship-sprite-sheet.png", 101)
        );
        this.lives = 3;
        this.alive = true;
        this.score = 0;
        loadResources();
    }

    private void loadResources() {
        URL imageUrl = getClass().getResource("/images/heart.png");
        if (imageUrl != null) {
            this.lifeIcon = new ImageIcon(imageUrl);
        } else {
            System.err.println("Failed to load the life icon sprite sheet image.");
        }
    }

    public void move(Direction direction) { //direction value is either (-1) for LEFT or (1) for RIGHT
        double newPosition = this.relativeBounds.getHorizontalPosition() + moveStep * direction.value;
        if(newPosition < 0)
            newPosition = 0;
        if(newPosition > 1)
            newPosition = 1;
        this.relativeBounds.setHorizontalPosition(newPosition);
    }

    public Projectile shoot() {
        if(!shotCooldownTimer.isRunning()) {
            AudioPlayer.playLaserSound();
            shotCooldownTimer.start();
            return new Projectile(
                    new RelativeBounds(
                            this.relativeBounds.getHorizontalPosition() + this.relativeBounds.getWidth() / 4, //center of the player spaceship
                            this.relativeBounds.getVerticalPosition(),
                            0.05,
                            0.1
                    ),
                    Direction.UP
            );
        }
        return null;
    }

    public void receiveDamage() {
        this.lives--;
        if(this.lives <= 0)
            this.alive = false;
    }

    public int getLives() {
        return lives;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ImageIcon getLifeIcon() {
        return lifeIcon;
    }
}
