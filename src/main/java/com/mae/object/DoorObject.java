package com.mae.object;

import com.mae.config.Settings;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class DoorObject extends SuperObject {

    public DoorObject() {
        setName("Door");
        setCollision(true);
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/door.png")), Settings.tileSize, Settings.tileSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
