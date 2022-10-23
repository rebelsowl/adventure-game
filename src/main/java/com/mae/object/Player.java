package com.mae.object;

import com.mae.config.Settings;
import com.mae.constants.Enums.Directions;
import com.mae.handler.KeyboardInputHandler;
import com.mae.panel.GamePanel;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Data
public class Player extends SuperObject {

    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private int spriteCounter = 0;
    private int spriteNumber = 0;
    private Directions direction;

    GamePanel gp;
    KeyboardInputHandler keyHandler;

    public Player(GamePanel gp, KeyboardInputHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        // setting initial values
        setX(100);
        setY(100);
        setSpeed(4);
        direction = Directions.DOWN;
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
                setY(getY() - getSpeed());
            }
            if (keyHandler.downPressed) {
                setDirection(Directions.DOWN);
                setY(getY() + getSpeed());
            }

            if (keyHandler.leftPressed) {
                setDirection(Directions.LEFT);
                setX(getX() - getSpeed());
            }

            if (keyHandler.rightPressed) {
                setDirection(Directions.RIGHT);
                setX(getX() + getSpeed());
            }

            spriteCounter++; // for movement
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
                if (spriteNumber == 1)
                    img = right1;
                else
                    img = right2;
                break;
        }

        g2.drawImage(img, getX(), getY(), Settings.tileSize, Settings.tileSize, null);

    }

    private boolean movementKeyPressed() {
        return keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed;
    }
}
