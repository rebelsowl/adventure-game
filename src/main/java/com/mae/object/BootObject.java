package com.mae.object;

import com.mae.config.Settings;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BootObject extends  SuperObject {

    public BootObject() {
        setName("Boots");
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/boots.png")), Settings.tileSize, Settings.tileSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
