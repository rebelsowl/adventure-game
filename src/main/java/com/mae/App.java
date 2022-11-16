package com.mae;

import com.mae.panel.GamePanel;

import javax.swing.*;


public class App {
    public static JFrame window;

    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.getConfig().loadConfig();
        if (GamePanel.fullScreenOn)
            App.window.setUndecorated(true); // removes the top bar

        window.pack(); //  sizes the frame so that all its contents are at or above their preferred sizes

        window.setLocationRelativeTo(null); // displayed at the center
        window.setVisible(true);

        gamePanel.setupGame();

        gamePanel.startGameThread();

    }
}
