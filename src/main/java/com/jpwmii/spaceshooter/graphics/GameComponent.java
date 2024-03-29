package com.jpwmii.spaceshooter.graphics;

import com.jpwmii.spaceshooter.audio.AudioPlayer;
import com.jpwmii.spaceshooter.entities.*;
import com.jpwmii.spaceshooter.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class GameComponent extends JComponent {
    public enum ProgramState {MENU, IN_GAME, GAME_OVER}
    private ProgramState currentProgramState;
    private Game game;
    private final int animationSpeed = 50;
    private final Sprite background;

    public GameComponent() {
        this.currentProgramState = ProgramState.MENU;
        this.background = new Sprite("/images/background-sprite-sheet.png", 40);

        ActionListener gameListener = e -> {
            background.prepareNextFrame();
            repaint();
        };
        Timer gameTimer = new Timer(animationSpeed, gameListener);
        gameTimer.start();

        AudioPlayer.playBackgroundMusic();
    }

    //region Paint methods
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //background
        paintBackground(g);

        switch(currentProgramState) {
            case MENU -> {
                Graphics2D g2d = (Graphics2D) g;
                int windowWidth = getParent().getWidth();
                int windowHeight = getParent().getHeight();
                int fontSize = (int) (0.06 * windowWidth);
                Font font = new Font("Arial", Font.BOLD, fontSize);
                g2d.setFont(font);
                g2d.setColor(Color.WHITE);

                String startText = "Press ENTER to start";

                FontMetrics fontMetrics = g2d.getFontMetrics(font);
                int startTextWidth = fontMetrics.stringWidth(startText);
                int textHeight = fontMetrics.getHeight();

                int startX = (windowWidth - startTextWidth) / 2;
                int y = (windowHeight - textHeight) / 2;

                g2d.drawString(startText, startX, y);
            }

            case IN_GAME -> {
                //all asteroids
                Iterator<Asteroid> asteroidIterator = game.getAsteroidList().iterator();
                while (asteroidIterator.hasNext()) {
                    Asteroid asteroid = asteroidIterator.next();
                    if (asteroid.isOutOfBounds()) {
                        asteroidIterator.remove();
                    } else {
                        paintEntity(g, asteroid);
                    }
                }

                //all projectiles
                Iterator<Projectile> projectileIterator = game.getProjectileList().iterator();
                while (projectileIterator.hasNext()) {
                    Projectile projectile = projectileIterator.next();
                    if (projectile.isOutOfBounds()) {
                        projectileIterator.remove();
                    } else {
                        paintEntity(g, projectile);
                    }
                }

                //all explosions
                Iterator<Explosion> explosionIterator = game.getExplosionList().iterator();
                while (explosionIterator.hasNext()) {
                    Explosion explosion = explosionIterator.next();
                    if (explosion.isAnimationDone())
                        explosionIterator.remove();
                    else
                        paintEntity(g, explosion);
                }

                //player spaceship
                paintEntity(g, game.getPlayer());

                //HUD
                paintHUD(g);

                game.handleCollisions();

                if(!game.getPlayer().isAlive()) {
                    this.currentProgramState = ProgramState.GAME_OVER;
                    return;
                }
            }

            case GAME_OVER -> {
                Graphics2D g2d = (Graphics2D) g;
                int windowWidth = getParent().getWidth();
                int windowHeight = getParent().getHeight();
                int fontSize = (int) (0.06 * windowWidth);
                Font font = new Font("Arial", Font.BOLD, fontSize);
                g2d.setFont(font);
                g2d.setColor(Color.WHITE);

                String gameOverText = "GAME OVER";
                String playerScoreText = "Final Score: " + this.game.getPlayer().getScore();
                String restartText = "Press ENTER to restart";

                FontMetrics fontMetrics = g2d.getFontMetrics(font);
                int gameOverTextWidth = fontMetrics.stringWidth(gameOverText);
                int restartTextWidth = fontMetrics.stringWidth(restartText);
                int playerScoreTextWidth = fontMetrics.stringWidth(playerScoreText);
                int textHeight = fontMetrics.getHeight();

                int gameOverTextX = (windowWidth - gameOverTextWidth) / 2;
                int restartTextX = (windowWidth - restartTextWidth) / 2;
                int playerScoreTextX = (windowWidth - playerScoreTextWidth) / 2;
                int y = (windowHeight - textHeight) / 2;

                g2d.drawString(gameOverText, gameOverTextX, y);
                g2d.drawString(playerScoreText, playerScoreTextX, y + textHeight);
                g2d.drawString(restartText, restartTextX, y + 2 * textHeight);
            }
        }
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
        g2d.drawString("Score: " + game.getPlayer().getScore(), (int) (0.02 * windowWidth), (int) (0.03 * windowHeight + fontSize / 2));

        //lives
        Image lifeIconImage = game.getPlayer().getLifeIcon().getImage();
        double lifeIconImageWidth = 0.07;
        double lifeIconImageHeight = 0.07;
        double bufferBetweenIcons = 0.02;
        for(int i = 0; i < game.getPlayer().getLives(); i++) {
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ProgramState getCurrentProgramState() {
        return currentProgramState;
    }

    public void setCurrentProgramState(ProgramState currentProgramState) {
        this.currentProgramState = currentProgramState;
    }
}
