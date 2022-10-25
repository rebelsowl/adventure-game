package com.mae.handler;

import com.mae.config.Settings;
import com.mae.entity.Entity;
import com.mae.object.SuperObject;
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

    /**
     * Check if entity is in contact with any object
     * @param entity players, npcs any entity type
     * @param player is method caller a player
     * @return index of object | -1 if there is no contact
     */
    public int checkObject(Entity entity, boolean player){
        int index = -1;

        int i = 0;
        for (SuperObject obj: gp.getObjects()) {
            if (obj != null) {
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                obj.getSolidArea().x =  obj.getWorldX() + obj.getSolidArea().x;
                obj.getSolidArea().y = obj.getWorldY() + obj.getSolidArea().y;

                switch (entity.getDirection()) {
                    case UP:
                        entity.getSolidArea().y -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(obj.getSolidArea())) {
                            if (obj.isCollision())
                                entity.setCollision(true);
                            if (player)
                                index = i;
                        }
                        break;
                    case DOWN:
                        entity.getSolidArea().y += entity.getSpeed();
                        if (entity.getSolidArea().intersects(obj.getSolidArea())) {
                            if (obj.isCollision())
                                entity.setCollision(true);
                            if (player)
                                index = i;
                        }
                        break;
                    case LEFT:
                        entity.getSolidArea().x -= entity.getSpeed();
                        if (entity.getSolidArea().intersects(obj.getSolidArea())) {
                            if (obj.isCollision())
                                entity.setCollision(true);
                            if (player)
                                index = i;
                        }
                        break;
                    case RIGHT:
                        entity.getSolidArea().x += entity.getSpeed();
                        if (entity.getSolidArea().intersects(obj.getSolidArea())) {
                            if (obj.isCollision())
                                entity.setCollision(true);
                            if (player)
                                index = i;
                        }
                        break;

                }
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();

                obj.getSolidArea().x = obj.getSolidAreaDefaultX();
                obj.getSolidArea().y = obj.getSolidAreaDefaultY();
            }
            i++;
        }

        return index;
    }
}
