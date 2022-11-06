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

import static com.mae.config.Settings.TILE_SIZE;

@Data
public class Player extends Entity {

    private final int screenX = (Settings.SCREEN_WIDTH / 2) - (TILE_SIZE / 2); // coordinates on the visible screen
    private final int screenY = (Settings.SCREEN_HEIGHT / 2) - (TILE_SIZE / 2); // subtracted the half tile size because frame starts at top left with 0,0
    KeyboardInputHandler keyHandler;


    public Player(GamePanel gp, KeyboardInputHandler keyHandler) {
        super(gp);

        this.keyHandler = keyHandler;
        // setting initial values
        setWorldX(TILE_SIZE * 23);
        setWorldY(TILE_SIZE * 21);
        setSpeed(4);
        direction = Directions.DOWN;

        setMaxLife(6);
        setLife(maxLife);

        solidArea = new Rectangle(8, 16, 27, 27);
        setSolidAreaDefaultX(solidArea.x);
        setSolidAreaDefaultY(solidArea.y);

        initPlayerImages();
    }


    public void initPlayerImages() {
        try {
            setUp1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png")), TILE_SIZE, TILE_SIZE));
            setUp2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png")), TILE_SIZE, TILE_SIZE));
            setLeft1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png")), TILE_SIZE, TILE_SIZE));
            setLeft2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png")), TILE_SIZE, TILE_SIZE));
            setRight1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png")), TILE_SIZE, TILE_SIZE));
            setRight2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png")), TILE_SIZE, TILE_SIZE));
            setDown1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png")), TILE_SIZE, TILE_SIZE));
            setDown2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png")), TILE_SIZE, TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void update() {
        if (movementKeyPressed() || interactKeyPressed()) {
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

            // Entity Collision
            int entityIndex = gp.getCollisionChecker().checkEntity(this, gp.getNpcs());
            interactWithEntity(entityIndex);

            // Monster Collision
            int monsterIndex = gp.getCollisionChecker().checkEntity(this, gp.getMonsters());
            interactWithMonster(monsterIndex);

            // Check event
            gp.getEventHandler().checkEvent();


            keyHandler.enterPressed = false;

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
        // invincible time
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    private void interactWithMonster(int index) {
        if (index > -1) {
            if (!invincible) {
                life -= 1; //TODO: detailed calculations with stats
                invincible = true;
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
                gp.setGameState(GamePanel.DIALOGUE_STATE);
                gp.getNpcs()[entityIndex].speak();
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

        if (invincible)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); // make %70 transparent

        g2.drawImage(img, screenX, screenY, null); // screenX/Y -> player will be always in the middle

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // reset transparency

        // debug
//        g2.setFont(new Font("Arial", Font.PLAIN, 20));
//        g2.setColor(Color.white);
//        g2.drawString("invincible Counter: " + invincibleCounter, 10, 400);

    }

    private boolean movementKeyPressed() {
        return keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed;
    }

    private boolean interactKeyPressed(){
        return keyHandler.enterPressed;
    }


    //TODO: add focus lost halt  other vids - Episode #07 dk 17
}
