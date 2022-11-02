package com.mae.handler;

import com.mae.config.Settings;
import com.mae.constant.Enums;
import com.mae.panel.GamePanel;

import java.awt.*;


public class EventHandler {
    private final GamePanel gp;
    private final Rectangle eventRectangle;
    private final int eventRectangleDefaultX;
    private final int getEventRectangleDefaultY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRectangle = new Rectangle();
        eventRectangle.x = 23;
        eventRectangle.y = 23;
        eventRectangle.width = 2;
        eventRectangle.height = 2;

        eventRectangleDefaultX = eventRectangle.x;
        getEventRectangleDefaultY = eventRectangle.y;
    }


    public void checkEvent() {
        if (hit(27, 16, Enums.Directions.RIGHT))
            pitFallEvent(GamePanel.DIALOGUE_STATE);

        if (hit(23, 12, Enums.Directions.UP))
            poolHealingEvent(GamePanel.DIALOGUE_STATE);



    }

    private void pitFallEvent(int gameState) {

        gp.setGameState(gameState);
        gp.getUi().setCurrentDialogue("You fall into a pit!");
        gp.getPlayer().setLife(gp.getPlayer().getLife() - 1);


    }

    public boolean hit(int eventCol, int eventRow, Enums.Directions requiredDirection) {
        gp.getPlayer().getSolidArea().x = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidArea().x;
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidArea().y;

        eventRectangle.x = eventCol * Settings.TILE_SIZE + eventRectangle.x;
        eventRectangle.y = eventRow * Settings.TILE_SIZE + eventRectangle.y;

        if (gp.getPlayer().getSolidArea().intersects(eventRectangle)) {
            if (gp.getPlayer().getDirection().equals(requiredDirection) || requiredDirection.equals(Enums.Directions.ANY))
                return true;
        }

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();

        eventRectangle.x = eventRectangleDefaultX;
        eventRectangle.y = getEventRectangleDefaultY;

        return false;
    }

    public void poolHealingEvent(int gameState){
        if (gp.getKeyHandler().enterPressed){
            gp.setGameState(gameState);
            gp.getUi().setCurrentDialogue("You drink the water. \nYour life has been recovered.");
            gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
        }

    }

}
