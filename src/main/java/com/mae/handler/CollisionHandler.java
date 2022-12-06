package com.mae.handler;

import com.mae.config.Settings;
import com.mae.entity.Entity;
import com.mae.panel.GamePanel;

public class CollisionHandler {
    private final GamePanel gp;

    public CollisionHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX / Settings.TILE_SIZE;
        int entityRightCol = entityRightWorldX / Settings.TILE_SIZE;
        int entityTopRow = entityTopWorldY / Settings.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / Settings.TILE_SIZE;

        int tileNum1, tileNum2; // Entity can be bordered with 2 tiles at max
        switch (entity.getDirection()) {
            case UP:
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / Settings.TILE_SIZE;
                tileNum1 = gp.getTileManager().getMapTileNum()[GamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[GamePanel.currentMap][entityRightCol][entityTopRow];
                if (gp.getTileManager().getTileTypes()[tileNum1].isCollision() || gp.getTileManager().getTileTypes()[tileNum2].isCollision()) {
                    entity.setCollision(true);
                }
                break;
            case DOWN:
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / Settings.TILE_SIZE;
                tileNum1 = gp.getTileManager().getMapTileNum()[GamePanel.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[GamePanel.currentMap][entityRightCol][entityBottomRow];
                if (gp.getTileManager().getTileTypes()[tileNum1].isCollision() || gp.getTileManager().getTileTypes()[tileNum2].isCollision()) {
                    entity.setCollision(true);
                }
                break;
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / Settings.TILE_SIZE;
                tileNum1 = gp.getTileManager().getMapTileNum()[GamePanel.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[GamePanel.currentMap][entityLeftCol][entityTopRow];
                if (gp.getTileManager().getTileTypes()[tileNum1].isCollision() || gp.getTileManager().getTileTypes()[tileNum2].isCollision()) {
                    entity.setCollision(true);
                }
                break;
            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / Settings.TILE_SIZE;
                tileNum1 = gp.getTileManager().getMapTileNum()[GamePanel.currentMap][entityRightCol][entityBottomRow];
                tileNum2 = gp.getTileManager().getMapTileNum()[GamePanel.currentMap][entityRightCol][entityTopRow];
                if (gp.getTileManager().getTileTypes()[tileNum1].isCollision() || gp.getTileManager().getTileTypes()[tileNum2].isCollision()) {
                    entity.setCollision(true);
                }
                break;
        }


    }

    /**
     * Check if entity is in contact with any object
     *
     * @param entity players, npcs any entity type
     * @param player is method caller a player
     * @return index of object | -1 if there is no contact
     */
    public int checkObject(Entity entity, boolean player) {
        int index = -1;

        for (int i = 0; i < gp.getObjects()[GamePanel.currentMap].length; i++) {
            if (gp.getObjects()[GamePanel.currentMap][i] != null) {
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                gp.getObjects()[GamePanel.currentMap][i].getSolidArea().x = gp.getObjects()[GamePanel.currentMap][i].getWorldX() + gp.getObjects()[GamePanel.currentMap][i].getSolidArea().x;
                gp.getObjects()[GamePanel.currentMap][i].getSolidArea().y = gp.getObjects()[GamePanel.currentMap][i].getWorldY() + gp.getObjects()[GamePanel.currentMap][i].getSolidArea().y;

                switch (entity.getDirection()) {
                    case UP:
                        entity.getSolidArea().y -= entity.getSpeed();
                        break;
                    case DOWN:
                        entity.getSolidArea().y += entity.getSpeed();
                        break;
                    case LEFT:
                        entity.getSolidArea().x -= entity.getSpeed();
                        break;
                    case RIGHT:
                        entity.getSolidArea().x += entity.getSpeed();
                        break;
                }
                if (entity.getSolidArea().intersects(gp.getObjects()[GamePanel.currentMap][i].getSolidArea())) {
                    if (gp.getObjects()[GamePanel.currentMap][i].isCollision())
                        entity.setCollision(true);
                    if (player)
                        index = i;
                }
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();

                gp.getObjects()[GamePanel.currentMap][i].getSolidArea().x = gp.getObjects()[GamePanel.currentMap][i].getSolidAreaDefaultX();
                gp.getObjects()[GamePanel.currentMap][i].getSolidArea().y = gp.getObjects()[GamePanel.currentMap][i].getSolidAreaDefaultY();
            }

        }

        return index;
    }

    /**
     * checks npc or monster collision
     *
     * @param entity player
     * @param target other entities in the game
     * @return index of the colliding entity
     */
    public int checkEntity(Entity entity, Entity[] target) {
        int index = -1;

        int i = 0;
        for (Entity targetEnt : target) {
            if (targetEnt != null) {
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                targetEnt.getSolidArea().x = targetEnt.getWorldX() + targetEnt.getSolidArea().x;
                targetEnt.getSolidArea().y = targetEnt.getWorldY() + targetEnt.getSolidArea().y;

                switch (entity.getDirection()) {
                    case UP:
                        entity.getSolidArea().y -= entity.getSpeed();
                        break;
                    case DOWN:
                        entity.getSolidArea().y += entity.getSpeed();
                        break;
                    case LEFT:
                        entity.getSolidArea().x -= entity.getSpeed();
                        break;
                    case RIGHT:
                        entity.getSolidArea().x += entity.getSpeed();
                        break;
                }

                if (entity.getSolidArea().intersects(targetEnt.getSolidArea())) {
                    if (targetEnt != entity) { // not to block itself
                        entity.setCollision(true);
                        index = i;
                    }
                }

                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();

                targetEnt.getSolidArea().x = targetEnt.getSolidAreaDefaultX();
                targetEnt.getSolidArea().y = targetEnt.getSolidAreaDefaultY();
            }
            i++;
        }

        return index;
    }

    /**
     * checks if entity is colliding with player
     *
     * @param entity moving entity
     */
    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;
        entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
        entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidArea().x;
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidArea().y;

        switch (entity.getDirection()) {
            case UP:
                entity.getSolidArea().y -= entity.getSpeed();
                break;
            case DOWN:
                entity.getSolidArea().y += entity.getSpeed();
                break;
            case LEFT:
                entity.getSolidArea().x -= entity.getSpeed();
                break;
            case RIGHT:
                entity.getSolidArea().x += entity.getSpeed();
                break;
        }
        if (entity.getSolidArea().intersects(gp.getPlayer().getSolidArea())) {
            entity.setCollision(true);
            contactPlayer = true;
        }
        entity.getSolidArea().x = entity.getSolidAreaDefaultX();
        entity.getSolidArea().y = entity.getSolidAreaDefaultY();

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();

        return contactPlayer;
    }

}
