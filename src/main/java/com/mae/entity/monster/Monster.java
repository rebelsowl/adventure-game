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
            if (gp.getObjects()[i] == null) {
                gp.getObjects()[i] = droppedItem;
                gp.getObjects()[i].setWorldX(worldX); // dead monster's coordinates
                gp.getObjects()[i].setWorldY(worldY);
                break;
            }
        }

    }

}
