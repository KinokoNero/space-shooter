package com.jpwmii.spaceshooter.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Sprite {
    private BufferedImage spriteSheet;
    private BufferedImage frameImage;
    private int frameIndex = 0;
    private final int frameCount;
    private final int frameWidth;
    private final int frameHeight;
    //private final int animationSpeed;

    public Sprite(String spriteSheetURL, int frameCount, int frameWidth, int frameHeight/*, int animationSpeed*/) {
        loadSpriteSheet(spriteSheetURL);
        this.frameCount = frameCount;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        //this.animationSpeed = animationSpeed;
    }

    private void loadSpriteSheet(String spriteSheetURL) {
        URL imageUrl = getClass().getResource(spriteSheetURL);
        if (imageUrl != null) {
            try {
                this.spriteSheet = ImageIO.read(imageUrl);
            }
            catch(IOException exception) {
                System.err.println("Error while reading sprite sheet image file:\n" + exception.getLocalizedMessage());
            }
        } else {
            System.err.println("Failed to load the sprite sheet image.");
        }
    }

    public void prepareNextFrame() {
        if(frameIndex < frameCount - 1)
            frameIndex++;
        else
            frameIndex = 0;

        frameImage = spriteSheet.getSubimage(frameIndex * frameWidth, 0, frameWidth, frameHeight);
    }

    public BufferedImage getFrameImage() {
        return this.frameImage;
    }

    /*public int getAnimationSpeed() {
        return animationSpeed;
    }*/
}
