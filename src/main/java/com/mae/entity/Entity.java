package com.mae.entity;

import com.mae.config.Settings;
import com.mae.constant.Enums;
import com.mae.panel.GamePanel;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.mae.config.Settings.tileSize;

@Data
public abstract class Entity { // parent class for Player, NPCs, Monsters
    protected GamePanel gp;

    protected int worldX, worldY; // coordinates
    protected int speed;

    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected int spriteCounter = 0;
    protected int spriteNumber = 0;
    protected Enums.Directions direction;

    protected Rectangle solidArea = new Rectangle(0, 0, tileSize, tileSize);
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;
    protected boolean collision = false;

    protected int actionLockCounter = 0; // for npc ai's movement to be smooth


    public void draw(Graphics2D g2) {
        BufferedImage img = null;

        int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
        int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

        if (worldX + Settings.tileSize > gp.player.getWorldX() - gp.player.getScreenX() &&
                worldX - Settings.tileSize < gp.player.getWorldX() + gp.player.getScreenX() &&
                worldY + Settings.tileSize > gp.player.getWorldY() - gp.player.getScreenY() &&
                worldY - Settings.tileSize < gp.player.getWorldY() + gp.player.getScreenY()) { // Only draw the tiles around the player

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
            g2.drawImage(img, screenX, screenY, null);
        }

    }

    public void setAction(){}

    public void update(){
        setAction();

        setCollision(false);
        gp.getCollisionChecker().checkTile(this);
        gp.getCollisionChecker().checkObject(this, false);
        gp.getCollisionChecker().checkPlayer(this);

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
