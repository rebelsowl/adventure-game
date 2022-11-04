package com.mae.object;

import com.mae.config.Settings;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends SuperObject {

    public OBJ_Door(GamePanel gp) {
        super(gp);
        setName("Door");
        setCollision(true);
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/door.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
