package com.mae.object;

import com.mae.config.Settings;
import com.mae.panel.GamePanel;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

@Data
public abstract class SuperObject {
    protected int worldX, worldY;
    protected String name;
    protected BufferedImage image;
    protected Rectangle solidArea = new Rectangle(0,0,Settings.tileSize,Settings.tileSize); // making whole object solid
    protected int solidAreaDefaultX = 0;
    protected int solidAreaDefaultY = 0;
    protected boolean collision = false;



    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
        int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

        if (worldX + Settings.tileSize > gp.player.getWorldX() - gp.player.getScreenX() &&
            worldX - Settings.tileSize < gp.player.getWorldX() + gp.player.getScreenX() &&
            worldY + Settings.tileSize > gp.player.getWorldY() - gp.player.getScreenY() &&
            worldY - Settings.tileSize < gp.player.getWorldY() + gp.player.getScreenY()) { // Only draw the tiles around the player
            g2.drawImage(image, screenX, screenY,null);
        }


    }

}
