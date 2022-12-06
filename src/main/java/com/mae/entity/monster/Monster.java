package com.mae.entity.monster;

import com.mae.entity.Entity;
import com.mae.object.parent.SuperObject;
import com.mae.panel.GamePanel;

public abstract class Monster extends Entity {




    public Monster(GamePanel gp){
        super(gp);
    }



    public abstract void checkDrop();
    public void dropItem(SuperObject droppedItem){
        for (int i = 0; i < gp.getObjects().length; i++) { // add item to empty slot
            if (gp.getObjects()[GamePanel.currentMap][i] == null) {
                gp.getObjects()[GamePanel.currentMap][i] = droppedItem;
                gp.getObjects()[GamePanel.currentMap][i].setWorldX(worldX); // dead monster's coordinates
                gp.getObjects()[GamePanel.currentMap][i].setWorldY(worldY);
                break;
            }
        }

    }

}
