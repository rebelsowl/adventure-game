package com.mae.handler;

import com.mae.config.Settings;
import com.mae.entity.Entity;
import com.mae.entity.NPC_OldMan;
import com.mae.object.OBJ_Door;
import com.mae.panel.GamePanel;

import static com.mae.config.Settings.TILE_SIZE;

public class AssetHandler {
    GamePanel gp;

    public AssetHandler(GamePanel gp) {
        this.gp = gp;
    }


    public void placeInitialObjectsInWorld() {
        OBJ_Door door1 = new OBJ_Door(gp);
        door1.setWorldX(24 * TILE_SIZE);
        door1.setWorldY(22 * TILE_SIZE);

        door1.getSolidArea().x = 0;
        door1.getSolidArea().y = 16;
        door1.getSolidArea().width = Settings.TILE_SIZE;
        door1.getSolidArea().height = 32;

        gp.getObjects()[0] = door1;

    }

    public void createNpcs() {
        Entity[] arr = new Entity[10];

        Entity npc1 = new NPC_OldMan(gp);
        npc1.setWorldX(TILE_SIZE * 21);
        npc1.setWorldY(TILE_SIZE * 21);

        arr[0] = npc1;

        gp.setNpcs(arr);
    }

}
