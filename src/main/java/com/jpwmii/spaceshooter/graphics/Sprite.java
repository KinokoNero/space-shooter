package com.jpwmii.spaceshooter.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Sprite {
    private BufferedImage spriteSheet;
    private BufferedImage spriteFrame;
    private int frameIndex = 0;
    private int frameCount = 0;
    private final int frameWidth;
    private final int frameHeight;

    public Sprite(String spriteFileURL) { //static sprite
        loadSpriteImage(spriteFileURL);
        this.frameWidth = this.spriteSheet.getWidth();
        this.frameHeight = this.spriteSheet.getHeight();
        prepareNextFrame();
    }

    public Sprite(String spriteSheetURL, int frameCount) { //animated sprite
        loadSpriteImage(spriteSheetURL);
        this.frameCount = frameCount;
        this.frameWidth = this.spriteSheet.getWidth() / frameCount;
        this.frameHeight = this.spriteSheet.getHeight();
        prepareNextFrame();
    }

    private void loadSpriteImage(String spriteFileURL) {
        URL imageUrl = getClass().getResource(spriteFileURL);
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

        spriteFrame = spriteSheet.getSubimage(frameIndex * frameWidth, 0, frameWidth, frameHeight);
    }

    public BufferedImage getSpriteFrame() {
        return this.spriteFrame;
    }
}
