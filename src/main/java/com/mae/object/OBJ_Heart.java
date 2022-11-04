package com.mae.object;

import com.mae.config.Settings;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Heart extends SuperObject{
    private BufferedImage image2, image3;

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        setName("Heart");
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
            setImage2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
            setImage3(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
