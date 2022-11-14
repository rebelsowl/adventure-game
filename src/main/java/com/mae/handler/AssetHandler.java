package com.mae.handler;

import com.mae.entity.Entity;
import com.mae.entity.NPC_OldMan;
import com.mae.entity.monster.MON_GreenSlime;
import com.mae.entity.monster.Monster;
import com.mae.object.*;
import com.mae.panel.GamePanel;
import com.mae.tile.interactive.IT_DryTree;

import static com.mae.config.Settings.TILE_SIZE;

public class AssetHandler {
    GamePanel gp;

    public AssetHandler(GamePanel gp) {
        this.gp = gp;
    }


    public void placeInitialObjectsInWorld() {
        int i = 0;
        OBJ_Key key1 = new OBJ_Key(gp);
        key1.setWorldX(25 * TILE_SIZE);
        key1.setWorldY(23 * TILE_SIZE);
        gp.getObjects()[i] = key1;

        i++;
        OBJ_Coin key2 = new OBJ_Coin(gp);
        key2.setWorldX(21 * TILE_SIZE);
        key2.setWorldY(19 * TILE_SIZE);
        gp.getObjects()[i] = key2;

        i++;
        OBJ_Coin key3 = new OBJ_Coin(gp);
        key3.setWorldX(25 * TILE_SIZE);
        key3.setWorldY(19 * TILE_SIZE);
        gp.getObjects()[i] = key3;

        i++;
        OBJ_Axe axe = new OBJ_Axe(gp);
        axe.setWorldX(33 * TILE_SIZE);
        axe.setWorldY(21 * TILE_SIZE);
        gp.getObjects()[i] = axe;

        i++;
        OBJ_Shield_Blue blueShield = new OBJ_Shield_Blue(gp);
        blueShield.setWorldX(35 * TILE_SIZE);
        blueShield.setWorldY(21 * TILE_SIZE);
        gp.getObjects()[i] = blueShield;

        i++;
        OBJ_Potion redPotion = new OBJ_Potion(gp);
        redPotion.setWorldX(22 * TILE_SIZE);
        redPotion.setWorldY(27 * TILE_SIZE);
        gp.getObjects()[i] = redPotion;

        i++;
        OBJ_Heart heart1 = new OBJ_Heart(gp);
        heart1.setWorldX(22 * TILE_SIZE);
        heart1.setWorldY(29 * TILE_SIZE);
        gp.getObjects()[i] = heart1;

        i++;
        OBJ_Mana_Crystal manaCrystal1 = new OBJ_Mana_Crystal(gp);
        manaCrystal1.setWorldX(22 * TILE_SIZE);
        manaCrystal1.setWorldY(31 * TILE_SIZE);
        gp.getObjects()[i] = manaCrystal1;


    }

    public void createNpcs() {
        Entity[] arr = new Entity[10];

        Entity npc1 = new NPC_OldMan(gp);
        npc1.setWorldX(TILE_SIZE * 21);
        npc1.setWorldY(TILE_SIZE * 21);

        arr[0] = npc1;

        gp.setNpcs(arr);
    }

    public void createMonsters() {
        Monster slime0 = new MON_GreenSlime(gp);
        slime0.setWorldX(TILE_SIZE * 23);
        slime0.setWorldY(TILE_SIZE * 36);

        gp.getMonsters()[0] = slime0;

        Monster slime1 = new MON_GreenSlime(gp);
        slime1.setWorldX(TILE_SIZE * 23);
        slime1.setWorldY(TILE_SIZE * 37);

        gp.getMonsters()[1] = slime1;

        Monster slime2 = new MON_GreenSlime(gp);
        slime2.setWorldX(TILE_SIZE * 24);
        slime2.setWorldY(TILE_SIZE * 37);

        gp.getMonsters()[2] = slime2;

        Monster slime3 = new MON_GreenSlime(gp);
        slime3.setWorldX(TILE_SIZE * 34);
        slime3.setWorldY(TILE_SIZE * 42);

        gp.getMonsters()[3] = slime3;

        Monster slime4 = new MON_GreenSlime(gp);
        slime4.setWorldX(TILE_SIZE * 38);
        slime4.setWorldY(TILE_SIZE * 42);

        gp.getMonsters()[4] = slime4;

    }

    public void createInteractiveTiles() {
        int i = 0;

        gp.getITiles()[i] = new IT_DryTree(gp, 27, 12);

        i++;
        gp.getITiles()[i] = new IT_DryTree(gp, 28 ,12);

        i++;
        gp.getITiles()[i] = new IT_DryTree(gp, 29, 12);

        i++;
        gp.getITiles()[i] = new IT_DryTree(gp, 30, 12);

        i++;
        gp.getITiles()[i] = new IT_DryTree(gp, 31, 12);

        i++;
        gp.getITiles()[i] = new IT_DryTree(gp, 32, 12);

        i++;
        gp.getITiles()[i] = new IT_DryTree(gp, 33, 12);

    }

}
