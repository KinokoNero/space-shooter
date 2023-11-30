package com.jpwmii.spaceshooter;

import com.jpwmii.spaceshooter.audio.AudioPlayer;
import com.jpwmii.spaceshooter.entities.*;
import com.jpwmii.spaceshooter.graphics.GameComponent;
import com.jpwmii.spaceshooter.graphics.RelativeBounds;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Game {
    private final int animationSpeed = 50;
    //private final Sprite background;
    private final Player player;
    private final AsteroidSpawner asteroidSpawner;
    private final int asteroidSpawnFrequency = 1000;
    private final ArrayList<Projectile> projectileList = new ArrayList<>();
    private final ArrayList<Asteroid> asteroidList = new ArrayList<>();
    private final ArrayList<Explosion> explosionList = new ArrayList<>();

    public Game() {
        double playerSpaceshipSpriteSize = 0.1;
        this.player = new Player( //creates player spaceship at the bottom-middle of the window
                new RelativeBounds(
                        0.5 - playerSpaceshipSpriteSize / 2,
                        1 - playerSpaceshipSpriteSize,
                        playerSpaceshipSpriteSize,
                        playerSpaceshipSpriteSize
                )
        );

        //animations
        ActionListener animationListener = e -> {
            player.getEntitySprite().prepareNextFrame();
            for(Asteroid asteroid : asteroidList) {
                asteroid.getEntitySprite().prepareNextFrame();
            }
            for(Explosion explosion : explosionList) {
                explosion.getEntitySprite().prepareNextFrame();
            }
        };
        Timer animationTimer = new Timer(animationSpeed, animationListener);
        animationTimer.start();

        //asteroids spawning
        this.asteroidSpawner = new AsteroidSpawner();
        ActionListener asteroidSpawnerListener = e -> {
            Asteroid asteroid = asteroidSpawner.spawnAsteroid();
            asteroidList.add(asteroid);
        };
        Timer asteroidSpawnerTimer = new Timer(asteroidSpawnFrequency, asteroidSpawnerListener);
        asteroidSpawnerTimer.start();

        //AudioPlayer.playBackgroundMusic();
    }

    public void handleCollisions() {
        //projectile-asteroid collision
        Iterator<Projectile> projectileIterator = projectileList.iterator();
        while(projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            Iterator<Asteroid> asteroidIterator = asteroidList.iterator();
            while(asteroidIterator.hasNext()) {
                Asteroid asteroid = asteroidIterator.next();
                if (projectile.collidesWith(asteroid)) {
                    projectileIterator.remove();
                    Explosion explosion = asteroid.explode();
                    explosionList.add(explosion);
                    asteroidIterator.remove();
                    this.player.setScore(this.player.getScore() + 1);
                    break; //Exit the inner loop after handling the collision with the current projectile
                }
            }
        }

        //asteroid-player collision
        Iterator<Asteroid> asteroidIterator = asteroidList.iterator();
        while (asteroidIterator.hasNext()) {
            Asteroid asteroid = asteroidIterator.next();
            if (player.collidesWith(asteroid)) {
                Explosion explosion = asteroid.explode();
                explosionList.add(explosion);
                asteroidIterator.remove();
                this.player.receiveDamage();
            }
        }
    }

    //region Key listener
    public static class GameKeyListener extends KeyAdapter {
        private GameComponent component;
        private Set<Integer> pressedKeys = new HashSet<>();

        public GameKeyListener(GameComponent component) {
            this.component = component;
        }

        private void handleKeys() {
            if (component.getCurrentProgramState() != GameComponent.ProgramState.IN_GAME)
                return;

            if (pressedKeys.contains(KeyEvent.VK_SPACE)) {
                Projectile projectile = component.getGame().getPlayer().shoot();
                if (projectile != null) {
                    component.getGame().getProjectileList().add(projectile);
                    //AudioPlayer.playLaserSound();
                }
            }

            if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
                component.getGame().getPlayer().move(Entity.Direction.LEFT);
            }

            if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
                component.getGame().getPlayer().move(Entity.Direction.RIGHT);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            pressedKeys.add(keyCode);
            handleKeys();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if(keyCode == KeyEvent.VK_ENTER && component.getCurrentProgramState() != GameComponent.ProgramState.IN_GAME) {
                component.setGame(new Game());
                component.setCurrentProgramState(GameComponent.ProgramState.IN_GAME);
                component.repaint();
            }

            pressedKeys.remove(keyCode);
            handleKeys();
        }
    }
    //endregion

    //region Getters & Setters
    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public Player getPlayer() {
        return player;
    }

    public AsteroidSpawner getAsteroidSpawner() {
        return asteroidSpawner;
    }

    public int getAsteroidSpawnFrequency() {
        return asteroidSpawnFrequency;
    }

    public ArrayList<Projectile> getProjectileList() {
        return projectileList;
    }

    public ArrayList<Asteroid> getAsteroidList() {
        return asteroidList;
    }

    public ArrayList<Explosion> getExplosionList() {
        return explosionList;
    }
    //endregion
}
