package com.mae.tile.interactive;

import com.mae.entity.Entity;
import com.mae.panel.GamePanel;
import lombok.Data;

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


}
