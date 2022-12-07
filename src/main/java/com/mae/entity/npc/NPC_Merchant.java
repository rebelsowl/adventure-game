package com.mae.entity.npc;

import com.mae.constant.Enums;
import com.mae.entity.Entity;
import com.mae.object.OBJ_Key;
import com.mae.object.OBJ_Potion;
import com.mae.object.OBJ_Shield_Blue;
import com.mae.object.OBJ_Sword;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

import static com.mae.config.Settings.TILE_SIZE;

public class NPC_Merchant extends Entity {

    public NPC_Merchant(GamePanel gp) {
        super(gp);

        setDirection(Enums.Directions.DOWN);
        setSpeed(0);

        initImages();
        initDialogues();
        initInventory();

    }


    private void initDialogues() {
        getDialogues()[0] = "He he, so you found me.\nI have some good stuff.\nDo you want to trade?";

    }

    public void initImages() {
        try {
            setUp1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/merchant_down_1.png")), TILE_SIZE, TILE_SIZE));
            setUp2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/merchant_down_2.png")), TILE_SIZE, TILE_SIZE));
            setDown1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/merchant_down_1.png")), TILE_SIZE, TILE_SIZE));
            setDown2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/merchant_down_2.png")), TILE_SIZE, TILE_SIZE));
            setRight1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/merchant_down_1.png")), TILE_SIZE, TILE_SIZE));
            setRight2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/merchant_down_2.png")), TILE_SIZE, TILE_SIZE));
            setLeft1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/merchant_down_1.png")), TILE_SIZE, TILE_SIZE));
            setLeft2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/merchant_down_2.png")), TILE_SIZE, TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void speak() {
        super.speak();
        gp.setGameState(GamePanel.TRADE_STATE);
        gp.getUi().setTraderNpc(this);
    }

    private void initInventory() {
        getInventory().add(new OBJ_Potion(gp));
        getInventory().add(new OBJ_Key(gp));
        getInventory().add(new OBJ_Shield_Blue(gp));
        getInventory().add(new OBJ_Sword(gp));

    }
}
