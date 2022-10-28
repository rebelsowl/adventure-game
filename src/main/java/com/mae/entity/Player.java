package com.mae.entity;

import com.mae.config.Settings;
import com.mae.constant.Enums.Directions;
import com.mae.handler.KeyboardInputHandler;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.mae.config.Settings.tileSize;

@Data
public class Player extends Entity {

    private final int screenX = (Settings.screenWidth / 2) - (tileSize / 2); // coordinates on the visible screen
    private final int screenY = (Settings.screenHeight / 2) - (tileSize / 2); // subtracted the half tile size because frame starts at top left with 0,0
    KeyboardInputHandler keyHandler;


    public Player(GamePanel gp, KeyboardInputHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        // setting initial values
        setWorldX(tileSize * 23);
        setWorldY(tileSize * 21);
        setSpeed(4);
        direction = Directions.DOWN;

        solidArea = new Rectangle(8, 16, 27, 27);
        setSolidAreaDefaultX(solidArea.x);
        setSolidAreaDefaultY(solidArea.y);

        initPlayerImages();
    }


    public void initPlayerImages() {
        try {
            setUp1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png")), tileSize, tileSize));
            setUp2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png")), tileSize, tileSize));
            setLeft1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png")), tileSize, tileSize));
            setLeft2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png")), tileSize, tileSize));
            setRight1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png")), tileSize, tileSize));
            setRight2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png")), tileSize, tileSize));
            setDown1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png")), tileSize, tileSize));
            setDown2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png")), tileSize, tileSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void update() {
        if (movementKeyPressed()) {

            if (keyHandler.upPressed) {
                setDirection(Directions.UP);
            } else if (keyHandler.downPressed) {
                setDirection(Directions.DOWN);
            } else if (keyHandler.leftPressed) {
                setDirection(Directions.LEFT);
            } else if (keyHandler.rightPressed) {
                setDirection(Directions.RIGHT);
            }

            // tile collision
            setCollision(false);
            gp.getCollisionChecker().checkTile(this);

            // object collision
            int objIndex = gp.getCollisionChecker().checkObject(this, true);
            interactWithObject(objIndex);

            // EntityCollision
            int entityIndex = gp.getCollisionChecker().checkEntity(this, gp.getNpcs());
            interactWithEntity(entityIndex);


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


    public void interactWithObject(int index) {
        if (index > -1) {

        }
    }

    private void interactWithEntity(int entityIndex) {
        if (entityIndex > -1) {
            if (keyHandler.enterPressed) {
                gp.setGameState(GamePanel.dialogueState);
                gp.getNpcs()[entityIndex].speak();
            }
        }
        keyHandler.enterPressed = false;
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

        g2.drawImage(img, screenX, screenY, null); // screenX/Y -> player will be always in the middle

    }

    private boolean movementKeyPressed() {
        return keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed;
    }

    //TODO: add focus lost halt  other vids - Episode #07 dk 17
}
