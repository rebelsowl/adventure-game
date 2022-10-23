package com.mae.panel;

import com.mae.config.Settings;
import com.mae.handler.KeyboardInputHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    Thread gameThread;
    KeyboardInputHandler keyHandler = new KeyboardInputHandler();


    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(Settings.screenWidth, Settings.screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // double buffering increases the rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // calls the run method

    }


    public void run() { // Game loop, core of the game
        double timePerFrame = 1000000000.0 / Settings.FPS;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            if ((currentTime - lastTime) > timePerFrame) {
                // update
                update();

                // draw
                repaint(); // calls paintComponent() method

                lastTime = currentTime;
            }
        }
    }

/* delta/accumulator method with fps counter
    public void run() { // Game loop, core of the game
        // delta/accumulator method
        double drawInterval = 1000000000 / Settings.FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0; // for fps display
        int drawCount = 0; // for fps display

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime); // for fps display
            lastTime = currentTime;

            if (delta >= 1) {
                // update
                update();

                // draw
                repaint(); // calls paintComponent() method

                delta --;
                drawCount += 1; // for fps display
            }

            if (timer >= 1000000000) { // for fps display
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }
*/

    public void update() {
        if (KeyboardInputHandler.upPressed) {
            playerY -= playerSpeed;
        }

        if (KeyboardInputHandler.downPressed) {
            playerY += playerSpeed;
        }
        if (KeyboardInputHandler.leftPressed) {
            playerX -= playerSpeed;
        }
        if (KeyboardInputHandler.rightPressed) {
            playerX += playerSpeed;
        }


    }

    public void paintComponent(Graphics g) { // built-in method for drawing
        super.paintComponent(g);


        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.CYAN);
        g2.fillRect(playerX, playerY, Settings.tileSize, Settings.tileSize);
        g2.dispose();


    }


}
