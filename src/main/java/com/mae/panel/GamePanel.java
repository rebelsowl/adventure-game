package com.mae.panel;

import com.mae.config.Settings;
import com.mae.entity.Player;
import com.mae.handler.CollisionHandler;
import com.mae.handler.KeyboardInputHandler;
import com.mae.handler.ObjectHandler;
import com.mae.handler.SoundHandler;
import com.mae.object.SuperObject;
import com.mae.tile.TileManager;
import com.mae.ui.UI;
import lombok.Data;

import javax.swing.*;
import java.awt.*;

@Data
public class GamePanel extends JPanel implements Runnable {
    // SYSTEM
    TileManager tileManager = new TileManager(this);
    KeyboardInputHandler keyHandler = new KeyboardInputHandler();
    SoundHandler soundEffectHandler = new SoundHandler();
    SoundHandler themeMusicHandler = new SoundHandler();
    public CollisionHandler collisionChecker = new CollisionHandler(this);
    ObjectHandler assetSetter = new ObjectHandler(this);
    UI ui = new UI(this);
    Thread gameThread;
    // ENTITY AND OBJECT
    public Player player = new Player(this, keyHandler);
    SuperObject[]  objects= new SuperObject[10];



    public GamePanel() {
        this.setPreferredSize(new Dimension(Settings.screenWidth, Settings.screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // double buffering increases the rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

    }


    public void setupGame(){
        assetSetter.placeInitialObjectsInWorld();
        playThemeSong(0);
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
        for (SuperObject obj : objects) {
            if (obj != null) {
                obj.draw(g2, this);
            }
        }
        player.draw(g2);
        ui.draw(g2);
        g2.dispose();
    }

    public void playThemeSong(int i){
        themeMusicHandler.setFile(i);
        themeMusicHandler.play();
        themeMusicHandler.loop();
    }

    public void stopThemeSong(){
        themeMusicHandler.stop();
    }

    public void playSoundEffect(int i){
        soundEffectHandler.setFile(i);
        soundEffectHandler.play();
    }

}