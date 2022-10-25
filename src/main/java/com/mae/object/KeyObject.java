package com.mae.object;

import com.mae.config.Settings;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class KeyObject extends SuperObject {

    public KeyObject() {
        setName("Key");
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/key.png")), Settings.tileSize, Settings.tileSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
