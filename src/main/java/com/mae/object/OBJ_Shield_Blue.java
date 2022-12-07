package com.mae.object;

import com.mae.config.Settings;
import com.mae.object.parent.Shield;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Shield_Blue extends Shield {

    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);
        setName("Shield Blue");
        setDescription("[" + getName() + "]\nA shiny blue shield.");
        setPrice(250);
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/shield_blue.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefenceValue(2);

    }
}
