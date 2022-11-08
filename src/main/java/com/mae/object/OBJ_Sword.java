package com.mae.object;

import com.mae.config.Settings;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Sword extends SuperObject {

    public OBJ_Sword(GamePanel gp) {
        super(gp);
        setName("Normal Sword");
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/sword_normal.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAttackValue(1);
    }
}
