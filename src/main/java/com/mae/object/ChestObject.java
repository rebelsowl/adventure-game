package com.mae.object;

import com.mae.config.Settings;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ChestObject extends SuperObject {

    public  ChestObject() {
        setName("Chest");
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/chest.png")), Settings.tileSize, Settings.tileSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
