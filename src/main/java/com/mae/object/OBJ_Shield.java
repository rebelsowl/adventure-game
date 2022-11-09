package com.mae.object;

import com.mae.config.Settings;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Shield extends  SuperObject{

    public OBJ_Shield(GamePanel gp) {
        super(gp);
        setName("Shield Wood");
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/shield_wood.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefenceValue(1);

    }
}
