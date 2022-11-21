package com.mae.panel;

import com.mae.App;
import com.mae.config.Configuration;
import com.mae.config.Settings;
import com.mae.entity.Entity;
import com.mae.entity.Player;
import com.mae.entity.monster.Monster;
import com.mae.entity.particle.Particle;
import com.mae.entity.projectile.Projectile;
import com.mae.handler.*;
import com.mae.interfaces.Drawable;
import com.mae.object.parent.SuperObject;
import com.mae.tile.TileManager;
import com.mae.tile.interactive.InteractiveTile;
import com.mae.ui.UI;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import static com.mae.config.Settings.*;

@Data
public class GamePanel extends JPanel implements Runnable {

    // GAME STATE
    private int gameState;
    public static final int TITLE_STATE = 0;
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int DIALOGUE_STATE = 3;
    public static final int CHARACTER_STATUS_STATE = 4;
    public static final int OPTIONS_STATE = 5;
    public static final int GAME_OVER_STATE = 6;

    // Settings
    public static boolean fullScreenOn = false;
    public CollisionHandler collisionChecker = new CollisionHandler(this);
    Configuration config = new Configuration(this);
    // SYSTEM
    TileManager tileManager = new TileManager(this);
    KeyboardInputHandler keyHandler = new KeyboardInputHandler(this);
    // ENTITY AND OBJECT
    public Player player = new Player(this, keyHandler);
    SoundHandler soundEffectHandler = new SoundHandler();
    SoundHandler themeMusicHandler = new SoundHandler();
    AssetHandler assetSetter = new AssetHandler(this);
    EventHandler eventHandler = new EventHandler(this);
    UI ui = new UI(this);
    Thread gameThread;
    SuperObject[] objects = new SuperObject[20];
    Entity[] npcs = new Entity[10];
    Monster[] monsters = new Monster[20];
    InteractiveTile[] iTiles = new InteractiveTile[50];
    ArrayList<Drawable> drawables = new ArrayList<>();
    ArrayList<Projectile> projectiles = new ArrayList<>();
    ArrayList<Particle> particles = new ArrayList<>();
    // FULL SCREEN
    BufferedImage tempScreen;
    Graphics2D g2;



    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, Settings.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // double buffering increases the rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        tempScreen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

    }


    public void setupGame() {
        assetSetter.placeInitialObjectsInWorld();
        assetSetter.createInteractiveTiles();
        assetSetter.createNpcs();
        assetSetter.createMonsters();
        gameState = TITLE_STATE;

        if (fullScreenOn)
            setFullScreen();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // calls the run method

    }

    public void run() { // Game loop, core of the game // delta/accumulator method
        double drawInterval = 1000000000 / (double) Settings.FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // long timer = 0; // for fps display
        // int drawCount = 0; // for fps display

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            // timer += (currentTime - lastTime); // for fps display
            lastTime = currentTime;
            if (delta >= 1) {
                // update
                update();
                // draw
                drawToTempScreen(); // we will draw everything to tempScreen
                drawToScreen(); // then we will scale it to jPanel(with specified resolution)
                delta--; // making -- doesn't reset the missing process ex-> if it worked at %105 it hands %05 to next iter
                // drawCount += 1; // for fps display
            }

            /*
            if (timer >= 1000000000) { // for fps display
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
            */
        }
    }


    public void update() {
        if (gameState == PLAY_STATE) {
            player.update();

            for (Entity npc : getNpcs()) {
                if (npc != null)
                    npc.update();
            }

            for (int i = 0; i < monsters.length; i++) {
                if (monsters[i] != null) {
                    if (monsters[i].isAlive() && !monsters[i].isDying())
                        monsters[i].update();
                    if (!monsters[i].isAlive()) {
                        monsters[i].checkDrop();
                        monsters[i] = null;

                    }
                }
            }

            Iterator<Projectile> projectileIterator = projectiles.iterator();
            while (projectileIterator.hasNext()) {
                Projectile currentProjectile = projectileIterator.next();
                if (currentProjectile.isAlive())
                    currentProjectile.update();
                else
                    projectileIterator.remove();
            }

            Iterator<Particle> particleIterator = particles.iterator();
            while (particleIterator.hasNext()) {
                Particle currentParticle = particleIterator.next();
                if (currentParticle.isAlive())
                    currentParticle.update();
                else
                    particleIterator.remove();
            }

            for (InteractiveTile iTile : iTiles) {
                if (iTile != null) {
                    iTile.update();
                }
            }

        } else if (gameState == PAUSE_STATE) {
            // nothing for now
        }
    }

    public void drawToTempScreen() {
        if (gameState == TITLE_STATE) {
            ui.draw(g2);
        } else {
            tileManager.draw(g2);

            for (InteractiveTile iTile : iTiles) {
                if (iTile != null)
                    iTile.draw(g2);
            }

            drawables.add(player);
            for (SuperObject obj : objects) {
                if (obj != null)
                    drawables.add(obj);
            }
            for (Entity entity : npcs) {
                if (entity != null)
                    drawables.add(entity);
            }

            for (Entity monster : monsters) {
                if (monster != null)
                    drawables.add(monster);
            }

            drawables.addAll(projectiles);
            drawables.addAll(particles);
            drawables.sort(Comparator.comparingInt(Drawable::getWorldY));
            for (Drawable drawable : drawables) {
                drawable.draw(g2);
            }
            drawables.clear();
            ui.draw(g2);

        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, SCREEN_WIDTH2, SCREEN_HEIGHT2, null);
        g.dispose();
    }

    public void retry(){
        player.setDefaultPositionAndDirection();
        player.restoreLifeAndMana();
        assetSetter.createNpcs();
        assetSetter.createMonsters();
    }

    public void restart(){
        player.setDefaultValues();
        player.setDefaultPositionAndDirection();
        player.restoreLifeAndMana();
        player.setItems();

        assetSetter.placeInitialObjectsInWorld();
        assetSetter.createNpcs();
        assetSetter.createMonsters();
        assetSetter.createInteractiveTiles();

    }

    public void setFullScreen() {
        // get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(App.window);

        // get full screen width and height
        SCREEN_WIDTH2 = App.window.getWidth();
        SCREEN_HEIGHT2 = App.window.getHeight();
    }

    public void playThemeSong(int i) {
        themeMusicHandler.setFile(i);
        themeMusicHandler.play();
        themeMusicHandler.loop();
    }

    public void stopThemeSong() {
        themeMusicHandler.stop();
    }

    public void playSoundEffect(int i) {
        soundEffectHandler.setFile(i);
        soundEffectHandler.play();
    }

}