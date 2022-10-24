package com.mae.object;

import com.mae.config.Settings;
import com.mae.panel.GamePanel;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

@Data
public abstract class SuperObject {
    protected BufferedImage image;
    protected String name;
    protected boolean collision = false;
    protected int worldX, worldY;


    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
        int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

        if (worldX + Settings.tileSize > gp.player.getWorldX() - gp.player.getScreenX() &&
            worldX - Settings.tileSize < gp.player.getWorldX() + gp.player.getScreenX() &&
            worldY + Settings.tileSize > gp.player.getWorldY() - gp.player.getScreenY() &&
            worldY - Settings.tileSize < gp.player.getWorldY() + gp.player.getScreenY()) { // Only draw the tiles around the player
            g2.drawImage(image, screenX, screenY, Settings.tileSize, Settings.tileSize, null);
        }


    }

}
