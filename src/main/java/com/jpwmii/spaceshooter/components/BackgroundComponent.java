package com.jpwmii.spaceshooter.components;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BackgroundComponent extends JComponent {
    private int width;
    private int height;
    private ImageIcon backgroundImage;

    public BackgroundComponent(int width, int height) {
        this.width = width;
        this.height = height;
        loadBackground();
        setPreferredSize(new Dimension(width, height));
    }

    private void loadBackground() {
        URL imageUrl = getClass().getResource("/background.gif");

        if (imageUrl != null) {
            this.backgroundImage = new ImageIcon(imageUrl);
        } else {
            System.err.println("Failed to load the animated background image.");
        }
    }

    //TODO: rescale image icon if bigger than window

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (backgroundImage != null) {
            //backgroundImage.paintIcon(this, graphics, 0, 0);
            graphics.drawImage(backgroundImage.getImage(), 0, 0, this.width, this.height, this);
        }
    }
}
