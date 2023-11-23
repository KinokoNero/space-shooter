package com.jpwmii.spaceshooter;

import com.jpwmii.spaceshooter.components.BackgroundComponent;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainForm extends JFrame {
    private int width;
    private int height;
    private JPanel mainPanel;
    private BackgroundComponent backgroundComponent;

    public MainForm(int width, int height) {
        this.width = width;
        this.height = height;
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
        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });

        //Background component
        this.backgroundComponent = new BackgroundComponent(this.width, this.height);
        mainPanel.add(backgroundComponent);
    }
}
