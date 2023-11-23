package com.jpwmii.spaceshooter.components;

import javax.swing.*;

public class GameComponent extends JComponent {
    private int playerScore;
    private int playerLives;

    public GameComponent() {
        this.playerScore = 0;
        this.playerLives = 3;
    }
}
