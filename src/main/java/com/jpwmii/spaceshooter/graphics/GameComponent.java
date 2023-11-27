package com.jpwmii.spaceshooter.graphics;

import com.jpwmii.spaceshooter.entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

public class GameComponent extends JComponent {
    private final int animationSpeed = 50;
    private final Sprite background;
    private final Player player;
    private final AsteroidSpawner asteroidSpawner;
    private final int asteroidSpawnFrequency = 1000;
    private final ArrayList<Projectile> projectileList = new ArrayList<>();
    private final ArrayList<Asteroid> asteroidList = new ArrayList<>();
    private final ArrayList<Explosion> explosionList = new ArrayList<>();

    public GameComponent() {
        this.background = new Sprite("/images/background-sprite-sheet.png", 40);

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
            background.prepareNextFrame();
            player.getEntitySprite().prepareNextFrame();
            for(Asteroid asteroid : asteroidList) {
                asteroid.getEntitySprite().prepareNextFrame();
            }
            for(Explosion explosion : explosionList) {
                explosion.getEntitySprite().prepareNextFrame();
            }
            repaint();
        };
        Timer animationTimer = new Timer(animationSpeed, animationListener);
        animationTimer.start();

        //collisions
        ActionListener collisionListener = e -> handleCollisions();
        Timer collisionDetectionTimer = new Timer(1, collisionListener);
        collisionDetectionTimer.start();

        //asteroids
        this.asteroidSpawner = new AsteroidSpawner();
        ActionListener asteroidSpawnerListener = e -> {
            Asteroid asteroid = asteroidSpawner.spawnAsteroid();
            asteroidList.add(asteroid);
        };
        Timer asteroidSpawnerTimer = new Timer(asteroidSpawnFrequency, asteroidSpawnerListener);
        asteroidSpawnerTimer.start();
    }

    private void handleCollisions() {
        //projectile-asteroid collision
        Iterator<Projectile> projectileIterator = projectileList.iterator();
        while(projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            Iterator<Asteroid> asteroidIterator = asteroidList.iterator();
            while(asteroidIterator.hasNext()) {
                Asteroid asteroid = asteroidIterator.next();
                if (projectile.collidesWith(asteroid)) {
                    projectileIterator.remove();
                    asteroidIterator.remove();
                    Explosion explosion = asteroid.explode();
                    explosionList.add(explosion);
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
                this.player.receiveDamage();
                asteroidIterator.remove();
                Explosion explosion = asteroid.explode();
                explosionList.add(explosion);
            }
        }
    }

    //region Paint methods
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //background
        paintBackground(g);

        //all asteroids
        Iterator<Asteroid> asteroidIterator = asteroidList.iterator();
        while(asteroidIterator.hasNext()) {
            Asteroid asteroid = asteroidIterator.next();
            if (asteroid.isOutOfBounds()) {
                asteroidIterator.remove();
            }
            else {
                paintEntity(g, asteroid);
            }
        }

        //all projectiles
        Iterator<Projectile> projectileIterator = projectileList.iterator();
        while(projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            if (projectile.isOutOfBounds()) {
                projectileIterator.remove();
            }
            else {
                paintEntity(g, projectile);
            }
        }

        //all explosions
        Iterator<Explosion> explosionIterator = explosionList.iterator();
        while(explosionIterator.hasNext()) {
            Explosion explosion = explosionIterator.next();
            if(explosion.isAnimationDone())
                explosionIterator.remove();
            else
                paintEntity(g, explosion);
        }

        //player spaceship
        paintEntity(g, this.player);

        //HUD
        paintHUD(g);
    }

    private void paintBackground(Graphics g) {
        BufferedImage backgroundFrame = this.background.getSpriteFrame();
        int windowWidth = getParent().getWidth();
        int windowHeight = getParent().getHeight();
        g.drawImage(backgroundFrame, 0, 0, windowWidth, windowHeight, this);
    }

    private void paintHUD(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int windowWidth = getParent().getWidth();
        int windowHeight = getParent().getHeight();

        //score
        int fontSize = (int) (0.05 * windowWidth);
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + this.player.getScore(), (int) (0.02 * windowWidth), (int) (0.03 * windowHeight + fontSize / 2));

        //lives
        Image lifeIconImage = this.player.getLifeIcon().getImage();
        double lifeIconImageWidth = 0.07;
        double lifeIconImageHeight = 0.07;
        double bufferBetweenIcons = 0.02;
        for(int i = 0; i < this.player.getLives(); i++) {
            g2d.drawImage(
                    lifeIconImage,
                    (int) ((1 - (bufferBetweenIcons + lifeIconImageWidth) - i * (bufferBetweenIcons + lifeIconImageWidth)) * windowWidth),
                    (int) (0.02 * windowHeight),
                    (int) (lifeIconImageWidth * windowWidth),
                    (int) (lifeIconImageHeight * windowHeight),
                    this
            );
        }
    }

    private void paintEntity(Graphics g, Entity entity) {
        BufferedImage entitySprite = entity.getEntitySprite().getSpriteFrame();
        int windowWidth = this.getParent().getWidth();
        int windowHeight = this.getParent().getHeight();
        RelativeBounds entityRelativeBounds = entity.getRelativeBounds();

        int posX = (int) Math.round(entityRelativeBounds.getHorizontalPosition() * windowWidth);
        int posY = (int) Math.round(entityRelativeBounds.getVerticalPosition() * windowHeight);
        int width = (int) Math.round(entityRelativeBounds.getWidth() * windowWidth);
        int height = (int) Math.round(entityRelativeBounds.getHeight() * windowHeight);

        g.drawImage(entitySprite, posX, posY, width, height, this);
    }
    //endregion

    //region Listeners
    public static class GameKeyListener implements KeyListener {
        private GameComponent component;
        private Set<Integer> pressedKeys = new HashSet<>();

        public GameKeyListener(GameComponent component) {
            this.component = component;
        }

        private void handleKeys() { //TODO: stops shooting after releasing a movement key
            if(pressedKeys.contains(KeyEvent.VK_SPACE)) {
                Projectile projectile = component.player.createProjectile();
                if(projectile != null) {
                    component.projectileList.add(projectile);
                    component.repaint();
                }
            }

            if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
                component.player.move(Entity.Direction.LEFT);
                component.repaint();
            }

            if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
                component.player.move(Entity.Direction.RIGHT);
                component.repaint();
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
            pressedKeys.remove(keyCode);
            handleKeys();
        }

        @Override
        public void keyTyped(KeyEvent e) {}
    }
    //endregion
}
