package com.mae.handler;

import com.mae.config.Settings;
import com.mae.constant.Enums;
import com.mae.panel.GamePanel;
import com.mae.utility.EventRectangle;


public class EventHandler {
    private final GamePanel gp;
    private final EventRectangle[][] eventRectangle;
    boolean canTriggerEvent = true;
    private int prevEventX, prevEventY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRectangle = new EventRectangle[Settings.MAX_WORLD_COL][Settings.MAX_WORLD_ROW];

        int col = 0;
        int row = 0;

        while (col < Settings.MAX_WORLD_COL && row < Settings.MAX_WORLD_ROW) {
            EventRectangle rect = new EventRectangle(23, 23, 2, 2);
            rect.setDefaultX(rect.x);
            rect.setGetDefaultY(rect.y);
            eventRectangle[col][row] = rect;

            col++;
            if (col == Settings.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }

    }


    public void checkEvent() {
        int xDistance = Math.abs(gp.getPlayer().getWorldX() - prevEventX);
        int yDistance = Math.abs(gp.getPlayer().getWorldY() - prevEventY);
        if (Math.max(xDistance, yDistance) > Settings.TILE_SIZE) {
            canTriggerEvent = true;
        }

        if (canTriggerEvent) {
            if (hit(27, 16, Enums.Directions.RIGHT))
                pitFallEvent(GamePanel.DIALOGUE_STATE, 27, 16);
        }

        if (hit(23, 12, Enums.Directions.UP))
            poolHealingEvent(GamePanel.DIALOGUE_STATE);


    }

    private void pitFallEvent(int gameState, int col, int row) {

        if (!eventRectangle[col][row].isEventDone()) {
            gp.setGameState(gameState);
            gp.getUi().setCurrentDialogue("You fall into a pit!");
            gp.getPlayer().setLife(gp.getPlayer().getLife() - 1);
            gp.playSoundEffect(6);
//            eventRectangle[col][row].setEventDone(true); // one time event
            canTriggerEvent = false;
        }
    }

    public boolean hit(int col, int row, Enums.Directions requiredDirection) {
        gp.getPlayer().getSolidArea().x = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidArea().x;
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidArea().y;

        eventRectangle[col][row].x = col * Settings.TILE_SIZE + eventRectangle[col][row].x;
        eventRectangle[col][row].y = row * Settings.TILE_SIZE + eventRectangle[col][row].y;

        if (gp.getPlayer().getSolidArea().intersects(eventRectangle[col][row])) {
            if (gp.getPlayer().getDirection().equals(requiredDirection) || requiredDirection.equals(Enums.Directions.ANY)) {
                prevEventX = gp.getPlayer().getWorldX();
                prevEventY = gp.getPlayer().getWorldY();
                return true;
            }
        }

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();

        eventRectangle[col][row].x = eventRectangle[col][row].getDefaultX();
        eventRectangle[col][row].y = eventRectangle[col][row].getGetDefaultY();

        return false;
    }

    public void poolHealingEvent(int gameState) {
        if (gp.getKeyHandler().enterPressed) {
            gp.setGameState(gameState);
            gp.playSoundEffect(2);
            gp.getUi().setCurrentDialogue("You drink the water. \nYour life has been recovered.");
            gp.getPlayer().setLife(gp.getPlayer().getMaxLife());

            gp.getAssetSetter().createMonsters(); // resets monsters

        }

    }

}
