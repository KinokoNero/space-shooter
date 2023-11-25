package com.jpwmii.spaceshooter.graphics;

import com.jpwmii.spaceshooter.entities.Entity;
import com.jpwmii.spaceshooter.entities.Player;
import com.jpwmii.spaceshooter.entities.Projectile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameComponent extends JComponent implements ActionListener {
    private final int animationSpeed = 50;
    private final Sprite background;
    private final Player player;
    private ArrayList<Projectile> projectileList = new ArrayList<>();

    public GameComponent() {
        this.background = new Sprite("/background-sprite-sheet.png", 40/*, 560, 498*/);
        this.player = new Player(new Dimension(100, 100));

        Timer animationTimer = new Timer(animationSpeed, this);
        animationTimer.start();
    }

    //region Paint methods
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        paintEntity(g, this.player);
    }

    private void paintBackground(Graphics g) {
        BufferedImage backgroundFrame = this.background.getImage();
        int windowWidth = getParent().getWidth();
        int windowHeight = getParent().getHeight();
        g.drawImage(backgroundFrame, 0, 0, windowWidth, windowHeight, this);
    }

    private void paintEntity(Graphics g, Entity entity) {
        BufferedImage entitySpriteFrame = entity.getEntitySprite().getImage();
        int windowWidth = getParent().getWidth();
        int windowHeight = getParent().getHeight();
        RelativeBounds entityRelativeBounds = entity.getRelativeBounds();

        g.drawImage(
                entitySpriteFrame,
                (int) (windowWidth * entityRelativeBounds.getHorizontalPosition() - entityRelativeBounds.getWidth() / 2),
                (int) (windowHeight * entityRelativeBounds.getVerticalPosition() - entityRelativeBounds.getHeight() / 2),
                (int) entityRelativeBounds.getWidth(),
                (int) entityRelativeBounds.getHeight(),
                this
        );
    }
    //endregion

    @Override
    public void actionPerformed(ActionEvent e) { //Every animation timer tick
        this.background.prepareNextFrame();
        this.player.getPlayerStarship().prepareNextFrame();
        this.repaint();
    }

    //region Action listeners
    /*static class BackgroundAnimationListener implements ActionListener {
        private final GameComponent component;

        public BackgroundAnimationListener(GameComponent component) {
            this.component = component;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //component.getComponentBackground().prepareNextFrame();
            component.background.prepareNextFrame();//component.background.getNextBackgroundFrame();
            component.repaint();
        }
    }*/
    //endregion
}
