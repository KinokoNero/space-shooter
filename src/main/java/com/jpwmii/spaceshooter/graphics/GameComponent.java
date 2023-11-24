package com.jpwmii.spaceshooter.graphics;

import com.jpwmii.spaceshooter.entities.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class GameComponent extends JComponent implements ActionListener {
    private final Sprite background;
    private final Player player;
    private final int animationSpeed = 50;

    public GameComponent() {
        this.background = new Sprite("/background-sprite-sheet.png", 40, 560, 498);
        //Timer backgroundAnimationTimer = new Timer(background.getAnimationSpeed(), new BackgroundAnimationListener(this));
        //backgroundAnimationTimer.start();
        this.player = new Player();

        Timer animationTimer = new Timer(animationSpeed, this);
        animationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
    }

    //region Paint methods
    private void paintBackground(Graphics g) {
        BufferedImage backgroundFrame = this.background.getFrameImage();
        int parentWidth = getParent().getWidth();
        int parentHeight = getParent().getHeight();
        setPreferredSize(new Dimension(parentWidth, parentHeight));
        g.drawImage(backgroundFrame, 0, 0, parentWidth, parentHeight, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.background.prepareNextFrame();
        this.player.getPlayerStarship().prepareNextFrame();
        this.repaint();
    }
    //endregion

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
