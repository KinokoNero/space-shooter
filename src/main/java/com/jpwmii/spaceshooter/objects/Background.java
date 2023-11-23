package com.jpwmii.spaceshooter.objects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Background {
    private BufferedImage backgroundSpriteSheet;
    private BufferedImage backgroundFrame;
    private int frameIndex = 0;
    private final int frameCount = 40;
    private final int frameWidth = 560;
    private final int frameHeight = 498;
    private final int animationSpeed = 50;

    public Background() {
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

    public void prepareNextFrame() {
        if(frameIndex < frameCount - 1)
            frameIndex++;
        else
            frameIndex = 0;

        backgroundFrame = backgroundSpriteSheet.getSubimage(frameIndex * frameWidth, 0, frameWidth, frameHeight);
    }

    public BufferedImage getBackgroundFrame() {
        return backgroundFrame;
    }

    public int getAnimationSpeed() {
        return animationSpeed;
    }
}
