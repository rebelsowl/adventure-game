package com.mae.object;

import com.mae.config.Settings;
import com.mae.object.parent.Weapon;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Axe extends Weapon {

    public OBJ_Axe(GamePanel gp) {
        super(gp);
        setName("Woodcutter's Axe");
        setDescription("[" + getName() + "]\nA bit rusty but still \ncan cut some trees.");
        setPrice(75);
        setKnockBackPower(2);
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/axe.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setType(WeaponType.AXE);
        setAttackValue(2);
        getAttackArea().width = 30;
        getAttackArea().height = 30;
    }

}
