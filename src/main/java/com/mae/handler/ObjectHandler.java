package com.mae.handler;

import com.mae.entity.Entity;
import com.mae.entity.NPC_OldMan;
import com.mae.panel.GamePanel;

import static com.mae.config.Settings.TILE_SIZE;

public class ObjectHandler {
    GamePanel gp;

    public ObjectHandler(GamePanel gp) {
        this.gp = gp;
    }


    public void placeInitialObjectsInWorld() {

    }

    public void createNpcs(){
        Entity[] arr = new Entity[10];

        Entity npc1 = new NPC_OldMan(gp);
        npc1.setWorldX(TILE_SIZE * 21);
        npc1.setWorldY(TILE_SIZE * 21);

        arr[0] = npc1;


        gp.setNpcs(arr);

    }

}
