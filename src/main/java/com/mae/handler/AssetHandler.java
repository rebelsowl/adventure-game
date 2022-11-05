package com.mae.handler;

import com.mae.config.Settings;
import com.mae.entity.Entity;
import com.mae.entity.NPC_OldMan;
import com.mae.entity.monster.MON_GreenSlime;
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

    public void createMonsters(){
        Entity slime1 = new MON_GreenSlime(gp);
        slime1.setWorldX(TILE_SIZE * 23);
        slime1.setWorldY(TILE_SIZE * 36);

        gp.getMonsters()[0] = slime1;

        Entity slime2 = new MON_GreenSlime(gp);
        slime2.setWorldX(TILE_SIZE * 23);
        slime2.setWorldY(TILE_SIZE * 37);

        gp.getMonsters()[1] = slime2;
    }
}
