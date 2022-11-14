package com.mae.tile.interactive;

import com.mae.constant.Enums;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import java.io.IOException;

import static com.mae.config.Settings.TILE_SIZE;
import static javax.imageio.ImageIO.read;

public class IT_Trunk extends InteractiveTile {
    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        setDirection(Enums.Directions.DOWN);

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;

        try {
            setDown1(UtilityTool.scaleImage(read(getClass().getResourceAsStream("/tiles_interactive/trunk.png")), TILE_SIZE, TILE_SIZE));
            setDown2(UtilityTool.scaleImage(read(getClass().getResourceAsStream("/tiles_interactive/trunk.png")), TILE_SIZE, TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}