package com.mae.panel;

import com.mae.config.Settings;
import com.mae.entity.Player;
import com.mae.handler.CollisionHandler;
import com.mae.handler.KeyboardInputHandler;
import com.mae.tile.TileManager;
import lombok.Data;

import javax.swing.*;
import java.awt.*;

@Data
public class GamePanel extends JPanel implements Runnable {
    public CollisionHandler collisionChecker = new CollisionHandler(this);
    Thread gameThread;
    KeyboardInputHandler keyHandler = new KeyboardInputHandler();
    public Player player = new Player(this, keyHandler);
    TileManager tileManager = new TileManager(this);

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

    public void run() { // Game loop, core of the game // delta/accumulator method
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
                delta--; // making -- doesn't reset the missing process ex-> if it worked at %105 it hands %05 to next iter
                drawCount += 1; // for fps display
            }

            if (timer >= 1000000000) { // for fps display
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }


    public void update() {

        player.update();

    }

    public void paintComponent(Graphics g) { // built-in method for drawing
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        player.draw(g2);


        g2.dispose();

    }


}
