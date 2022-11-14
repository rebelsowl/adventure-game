package com.mae.tile.interactive;

import com.mae.config.Settings;
import com.mae.entity.Entity;
import com.mae.panel.GamePanel;
import lombok.Data;

import java.awt.*;

import static com.mae.config.Settings.TILE_SIZE;

@Data
public abstract class InteractiveTile extends Entity {

    protected boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row){
        super(gp);
        this.worldX = col * TILE_SIZE;
        this.worldY = row * TILE_SIZE;
    }

    /**
     * does entity trying to destroy the tile with correct weapon
     * @param entity user
     */
    public boolean isCorrectItem(Entity entity){
        return false;
    }

    public void playSoundEffect(){}

    public InteractiveTile getDestroyedForm() {
        return null;
    }

    @Override
    public void update(){
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
        int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

        if (worldX + Settings.TILE_SIZE > gp.player.getWorldX() - gp.player.getScreenX() &&
                worldX - Settings.TILE_SIZE < gp.player.getWorldX() + gp.player.getScreenX() &&
                worldY + Settings.TILE_SIZE > gp.player.getWorldY() - gp.player.getScreenY() &&
                worldY - Settings.TILE_SIZE < gp.player.getWorldY() + gp.player.getScreenY()) { // Only draw the tiles around the player

            g2.drawImage(down1, screenX, screenY, null);

        }

    }


}
