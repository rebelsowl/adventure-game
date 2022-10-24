package com.mae.entity;

import com.mae.config.Settings;
import com.mae.constant.Enums.Directions;
import com.mae.handler.KeyboardInputHandler;
import com.mae.panel.GamePanel;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Data
public class Player extends Entity {

    private final int screenX = (Settings.screenWidth / 2) - (Settings.tileSize / 2); // coordinates on the visible screen
    private final int screenY = (Settings.screenHeight / 2) - (Settings.tileSize / 2); // subtracted the half tile size because frame starts at top left with 0,0
    GamePanel gp;
    KeyboardInputHandler keyHandler;

    public Player(GamePanel gp, KeyboardInputHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        // setting initial values
        setWorldX(Settings.tileSize * 23);
        setWorldY(Settings.tileSize * 21);
        setSpeed(4);
        direction = Directions.DOWN;

        solidArea = new Rectangle(8, 16, 27, 27);

        initPlayerImages();
    }


    public void initPlayerImages() {
        try {
            setUp1(ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png")));
            setUp2(ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png")));
            setLeft1(ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png")));
            setLeft2(ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png")));
            setRight1(ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png")));
            setRight2(ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png")));
            setDown1(ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png")));
            setDown2(ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void update() {
        if (movementKeyPressed()) {


            if (keyHandler.upPressed) {
                setDirection(Directions.UP);
            }
            if (keyHandler.downPressed) {
                setDirection(Directions.DOWN);

            }
            if (keyHandler.leftPressed) {
                setDirection(Directions.LEFT);

            }
            if (keyHandler.rightPressed) {
                setDirection(Directions.RIGHT);

            }


            setCollision(false);
            gp.collisionChecker.checkTile(this);

            if (!isCollision()) {
                switch (direction) {
                    case UP:
                        setWorldY(getWorldY() - getSpeed());
                        break;
                    case DOWN:
                        setWorldY(getWorldY() + getSpeed());
                        break;
                    case LEFT:
                        setWorldX(getWorldX() - getSpeed());
                        break;
                    case RIGHT:
                        setWorldX(getWorldX() + getSpeed());
                        break;
                }
            }


            spriteCounter++; // for movement animation
            if (spriteCounter > 12) {
                spriteNumber = (spriteNumber + 1) % 2;
                spriteCounter = 0;
            }

        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage img = null;

        switch (direction) {
            case UP:
                if (spriteNumber == 1) img = up1;
                else img = up2;
                break;
            case DOWN:
                if (spriteNumber == 1) img = down1;
                else img = down2;
                break;
            case LEFT:
                if (spriteNumber == 1) img = left1;
                else img = left2;
                break;
            case RIGHT:
                if (spriteNumber == 1) img = right1;
                else img = right2;
                break;
        }

        g2.drawImage(img, screenX, screenY, Settings.tileSize, Settings.tileSize, null); // screenX/Y -> player will be always in the middle

    }

    private boolean movementKeyPressed() {
        return keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed;
    }

    //TODO: add focus lost halt  other vids - Episode #07 dk 17
}
