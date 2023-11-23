package com.jpwmii.spaceshooter;

import com.jpwmii.spaceshooter.components.BackgroundComponent;
import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private BackgroundComponent backgroundComponent;

    public MainForm(int width, int height) {
        this.setTitle("Space Shooter");
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUIComponents();
        this.setVisible(true);
    }

    private void createUIComponents() {
        //Main panel
        this.mainPanel = new JPanel();
        this.add(mainPanel);

        //Background component
        backgroundComponent = new BackgroundComponent();
        backgroundComponent.setPreferredSize(new Dimension(getWidth(), getHeight()));
        mainPanel.add(backgroundComponent);
    }
}
