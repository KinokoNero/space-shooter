package com.jpwmii.spaceshooter.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class BackgroundComponent extends JComponent implements ActionListener {
    private BufferedImage backgroundSpriteSheet;
    private BufferedImage backgroundFrame;
    private int frameIndex = 0;
    private final int frameCount = 40;
    private final int frameWidth = 560;
    private final int frameHeight = 498;
    private final int animationSpeed = 50;

    public BackgroundComponent() {
        loadBackgroundImage();
        Timer timer = new Timer(animationSpeed, this);
        timer.start();
    }

    private void loadBackgroundImage() {
        URL imageUrl = getClass().getResource("/background-sprite-sheet.png");

        if (imageUrl != null) {
            try {
                this.backgroundSpriteSheet = ImageIO.read(imageUrl);
            }
            catch(IOException exception) {
                System.err.println("Error while reading from background file:\n" + exception.getLocalizedMessage());
            }
        } else {
            System.err.println("Failed to load the animated background image.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundFrame != null) {
            int parentWidth = getParent().getWidth();
            int parentHeight = getParent().getHeight();
            setPreferredSize(new Dimension(parentWidth, parentHeight));
            g.drawImage(backgroundFrame, 0, 0, parentWidth, parentHeight, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Executes every timer tick
        if(frameIndex < frameCount - 1)
            frameIndex++;
        else
            frameIndex = 0;

        backgroundFrame = backgroundSpriteSheet.getSubimage(frameIndex * frameWidth, 0, frameWidth, frameHeight);

        repaint();
    }
}
