package com.mae.handler;

import com.mae.config.Settings;
import com.mae.entity.Entity;
import com.mae.panel.GamePanel;

public class CollisionHandler {
    private GamePanel gp;

    public CollisionHandler(GamePanel gp){
        this.gp = gp;

    }



    public  void  checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX/ Settings.tileSize;
        int entityRightCol = entityRightWorldX / Settings.tileSize;
        int entityTopRow = entityTopWorldY / Settings.tileSize;
        int entityBottomRow = entityBottomWorldY / Settings.tileSize;

        int tileNum1, tileNum2; // Entity can be bordered with 2 tiles at max
        switch (entity.getDirection()) {
            case UP:
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / Settings.tileSize;
                tileNum1 = gp.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
                if (gp.getTileManager().getTileTypes()[tileNum1].isCollision() || gp.getTileManager().getTileTypes()[tileNum2].isCollision()) {
                    entity.setCollision(true);
                }
                break;
            case DOWN:
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / Settings.tileSize;
                tileNum1 = gp.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];
                if (gp.getTileManager().getTileTypes()[tileNum1].isCollision() || gp.getTileManager().getTileTypes()[tileNum2].isCollision()) {
                    entity.setCollision(true);
                }
                break;
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / Settings.tileSize;
                tileNum1 = gp.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
                if (gp.getTileManager().getTileTypes()[tileNum1].isCollision() || gp.getTileManager().getTileTypes()[tileNum2].isCollision()) {
                    entity.setCollision(true);
                }
                break;
            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / Settings.tileSize;
                tileNum1 = gp.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
                if (gp.getTileManager().getTileTypes()[tileNum1].isCollision() || gp.getTileManager().getTileTypes()[tileNum2].isCollision()) {
                    entity.setCollision(true);
                }
                break;
        }



    }

}
