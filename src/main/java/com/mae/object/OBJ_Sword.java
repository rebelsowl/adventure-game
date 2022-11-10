package com.mae.object;

import com.mae.config.Settings;
import com.mae.object.parent.Weapon;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Sword extends Weapon {

    public OBJ_Sword(GamePanel gp) {
        super(gp);
        setName("Normal Sword");
        setDescription("[" + getName() + "]\nAn old sword.");

        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/sword_normal.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setType(WeaponType.SWORD);
        setAttackValue(1);
        getAttackArea().width = 36;
        getAttackArea().height = 36;

    }
}
