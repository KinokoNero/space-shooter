package com.jpwmii.spaceshooter.graphics;

import com.jpwmii.spaceshooter.entities.Entity;
import com.jpwmii.spaceshooter.entities.Player;
import com.jpwmii.spaceshooter.entities.Projectile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

public class GameComponent extends JComponent implements ActionListener {
    private final int animationSpeed = 50;
    private final Sprite background;
    private final Player player;
    private ArrayList<Projectile> projectileList = new ArrayList<>();

    public GameComponent() {
        this.background = new Sprite("/background-sprite-sheet.png", 40);

        double playerSpaceshipSpriteSize = 0.1;
        this.player = new Player( //creates player spaceship at the bottom-middle of the window
                new RelativeBounds(
                        0.5 - playerSpaceshipSpriteSize / 2,
                        1,
                        playerSpaceshipSpriteSize,
                        playerSpaceshipSpriteSize,
                        true
                )
        );

        Timer animationTimer = new Timer(animationSpeed, this);
        animationTimer.start();
    }

    //region Paint methods
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //background
        paintBackground(g);

        //all projectiles
        Iterator<Projectile> projectileIterator = projectileList.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();
            if (projectile.isVisible()) {
                paintEntity(g, projectile);
            } else {
                projectileIterator.remove();
            }
        }

        //player spaceship
        paintEntity(g, this.player);
    }

    private void paintBackground(Graphics g) {
        BufferedImage backgroundFrame = this.background.getSpriteFrame();
        int windowWidth = getParent().getWidth();
        int windowHeight = getParent().getHeight();
        g.drawImage(backgroundFrame, 0, 0, windowWidth, windowHeight, this);
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
    @Override
    public void actionPerformed(ActionEvent e) { //Every animation timer tick
        background.prepareNextFrame();
        player.getEntitySprite().prepareNextFrame();
        repaint();
    }

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
