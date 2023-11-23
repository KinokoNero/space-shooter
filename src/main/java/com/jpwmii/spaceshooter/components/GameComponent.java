package com.jpwmii.spaceshooter.components;

import com.jpwmii.spaceshooter.objects.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameComponent extends JComponent {
    private final Background background;

    public GameComponent() {
        this.background = new Background();
        Timer backgroundAnimationTimer = new Timer(background.getAnimationSpeed(), new BackgroundAnimationListener(this));
        backgroundAnimationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
    }

    //region Paint methods
    private void paintBackground(Graphics g) {
        if (background.getBackgroundFrame() != null) {
            int parentWidth = getParent().getWidth();
            int parentHeight = getParent().getHeight();
            setPreferredSize(new Dimension(parentWidth, parentHeight));
            g.drawImage(background.getBackgroundFrame(), 0, 0, parentWidth, parentHeight, this);
        }
    }
    //endregion

    //region Action listeners
    static class BackgroundAnimationListener implements ActionListener {
        private final GameComponent component;

        public BackgroundAnimationListener(GameComponent component) {
            this.component = component;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            component.getComponentBackground().prepareNextFrame();
            component.repaint();
        }
    }
    //endregion

    //region Getters & Setters
    public Background getComponentBackground() {
        return background;
    }
    //endregion
}
