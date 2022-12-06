package com.mae.handler;

import com.mae.config.Settings;
import com.mae.constant.Enums;
import com.mae.panel.GamePanel;
import com.mae.utility.EventRectangle;

import static com.mae.config.Settings.TILE_SIZE;


public class EventHandler {
    private final GamePanel gp;
    private final EventRectangle[][][] eventRectangle;
    boolean canTriggerEvent = true;
    private int prevEventX, prevEventY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRectangle = new EventRectangle[GamePanel.MAX_MAP][Settings.MAX_WORLD_COL][Settings.MAX_WORLD_ROW];

        int col = 0;
        int row = 0;
        int mapNum = 0;

        while (mapNum < GamePanel.MAX_MAP && col < Settings.MAX_WORLD_COL && row < Settings.MAX_WORLD_ROW) {
            EventRectangle rect = new EventRectangle(23, 23, 2, 2);
            rect.setDefaultX(rect.x);
            rect.setGetDefaultY(rect.y);
            eventRectangle[mapNum][col][row] = rect;

            col++;
            if (col == Settings.MAX_WORLD_COL) {
                col = 0;
                row++;
                if (row == Settings.MAX_WORLD_ROW){
                    row = 0;
                    mapNum++;
                }
            }
        }

    }


    public void checkEvent() {
        int xDistance = Math.abs(gp.getPlayer().getWorldX() - prevEventX);
        int yDistance = Math.abs(gp.getPlayer().getWorldY() - prevEventY);
        if (Math.max(xDistance, yDistance) > TILE_SIZE) {
            canTriggerEvent = true;
        }

        if (canTriggerEvent) {
            if (hit(0, 27, 16, Enums.Directions.RIGHT))
                pitFallEvent(GamePanel.DIALOGUE_STATE);
            else if (hit(0, 23, 12, Enums.Directions.UP))
                poolHealingEvent(GamePanel.DIALOGUE_STATE);
            else if (hit(0, 10, 39, Enums.Directions.ANY))
                teleport(1, 12, 13);
            else if (hit(1, 12, 13, Enums.Directions.ANY))
                teleport(0, 10, 39);
        }


    }




    public boolean hit(int map, int col, int row, Enums.Directions requiredDirection) {
        if (map == GamePanel.currentMap) {
            gp.getPlayer().getSolidArea().x = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidArea().x;
            gp.getPlayer().getSolidArea().y = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidArea().y;

            eventRectangle[map][col][row].x = col * TILE_SIZE + eventRectangle[map][col][row].x;
            eventRectangle[map][col][row].y = row * TILE_SIZE + eventRectangle[map][col][row].y;

            if (gp.getPlayer().getSolidArea().intersects(eventRectangle[map][col][row])) {
                if (gp.getPlayer().getDirection().equals(requiredDirection) || requiredDirection.equals(Enums.Directions.ANY)) {
                    prevEventX = gp.getPlayer().getWorldX();
                    prevEventY = gp.getPlayer().getWorldY();
                    return true;
                }
            }

            gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
            gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();

            eventRectangle[map][col][row].x = eventRectangle[map][col][row].getDefaultX();
            eventRectangle[map][col][row].y = eventRectangle[map][col][row].getGetDefaultY();
        }
        return false;
    }

    private void pitFallEvent(int gameState) {
        gp.setGameState(gameState);
        gp.getUi().setCurrentDialogue("You fall into a pit!");
        gp.getPlayer().setLife(gp.getPlayer().getLife() - 1);
        gp.playSoundEffect(6);
        canTriggerEvent = false;
    }
    public void poolHealingEvent(int gameState) {
        if (gp.getKeyHandler().enterPressed) {
            gp.setGameState(gameState);
            gp.playSoundEffect(2);
            gp.getUi().setCurrentDialogue("You drink the water. \nYour life and mana have been recovered.");
            gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
            gp.getPlayer().setMana(gp.getPlayer().getMaxMana());
            gp.getAssetSetter().createMonsters(); // resets monsters

        }

    }

    private void teleport(int map, int col, int row) {
        GamePanel.currentMap = map;
        gp.getPlayer().setWorldX(TILE_SIZE * col);
        gp.getPlayer().setWorldY(TILE_SIZE * row);
        gp.playSoundEffect(13);

        prevEventX = gp.getPlayer().getWorldX();
        prevEventY = gp.getPlayer().getWorldY();
        canTriggerEvent = false;

    }

}
