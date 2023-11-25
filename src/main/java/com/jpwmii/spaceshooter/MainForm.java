package com.jpwmii.spaceshooter;

import com.jpwmii.spaceshooter.graphics.GameComponent;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private GameComponent gameComponent;

    public MainForm(int width, int height) {
        this.setTitle("Space Shooter");
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUIComponents();
        this.setVisible(true);
    }

    private void createUIComponents() {
        gameComponent = new GameComponent();
        gameComponent.setPreferredSize(new Dimension(getWidth(), getHeight()));
        this.add(gameComponent);
    }
}
