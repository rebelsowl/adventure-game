package com.mae.entity.monster;

import com.mae.config.Settings;
import com.mae.constant.Enums;
import com.mae.entity.Entity;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.util.Random;

import static com.mae.config.Settings.TILE_SIZE;

public class MON_GreenSlime extends Entity {

    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        setSpeed(1);
        setMaxLife(4);
        setLife(getMaxLife());

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        setSolidAreaDefaultX(solidArea.x);
        setSolidAreaDefaultY(solidArea.y);

        initImages();
    }

    public void initImages() {
        try {
            setUp1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/monster/slime/greenslime_down_1.png")), TILE_SIZE, TILE_SIZE));
            setUp2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/monster/slime/greenslime_down_2.png")), TILE_SIZE, TILE_SIZE));
            setDown1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/monster/slime/greenslime_down_1.png")), TILE_SIZE, TILE_SIZE));
            setDown2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/monster/slime/greenslime_down_2.png")), TILE_SIZE, TILE_SIZE));
            setLeft1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/monster/slime/greenslime_down_1.png")), TILE_SIZE, TILE_SIZE));
            setLeft2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/monster/slime/greenslime_down_2.png")), TILE_SIZE, TILE_SIZE));
            setRight1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/monster/slime/greenslime_down_1.png")), TILE_SIZE, TILE_SIZE));
            setRight2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/monster/slime/greenslime_down_2.png")), TILE_SIZE, TILE_SIZE));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction(){
        actionLockCounter++;

        if (actionLockCounter > Settings.FPS * 3) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                setDirection(Enums.Directions.UP);
            } else if (i <= 50) {
                setDirection(Enums.Directions.DOWN);
            } else if (i <= 75) {
                setDirection(Enums.Directions.LEFT);
            } else {
                setDirection(Enums.Directions.RIGHT);
            }
            actionLockCounter = 0;
        }
    }

}
