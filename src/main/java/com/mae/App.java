package com.mae;

import com.mae.panel.GamePanel;

import javax.swing.*;


public class App {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); //  sizes the frame so that all its contents are at or above their preferred sizes

        window.setLocationRelativeTo(null); // displayed at the center
        window.setVisible(true);

        gamePanel.startGameThread();

    }
}
