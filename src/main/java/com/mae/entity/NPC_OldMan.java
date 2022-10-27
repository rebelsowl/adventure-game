package com.mae.entity;

import com.mae.config.Settings;
import com.mae.constant.Enums.Directions;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

import static com.mae.config.Settings.tileSize;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        this.gp = gp;

        setDirection(Directions.DOWN);
        setSpeed(1);


        initImages();
        initDialogues();
    }

    private void initDialogues() {
        getDialogues()[0] = "Hello, adventurer.";
        getDialogues()[1] = "So, you have come to this island to \nfind the treasure.";
        getDialogues()[2] = "I used to be a great wizard but now...\nI'm a a bit too old for an adventure.";
        getDialogues()[3] = "Well, good luck to you.";
    }

    public void initImages() {
        try {
            setUp1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/oldman_up_1.png")), tileSize, tileSize));
            setUp2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/oldman_up_2.png")), tileSize, tileSize));
            setLeft1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/oldman_left_1.png")), tileSize, tileSize));
            setLeft2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/oldman_left_2.png")), tileSize, tileSize));
            setRight1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/oldman_right_1.png")), tileSize, tileSize));
            setRight2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/oldman_right_2.png")), tileSize, tileSize));
            setDown1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/oldman_down_1.png")), tileSize, tileSize));
            setDown2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/npc/oldman_down_2.png")), tileSize, tileSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter > Settings.FPS * 3) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                setDirection(Directions.UP);
            } else if (i <= 50) {
                setDirection(Directions.DOWN);
            } else if (i <= 75) {
                setDirection(Directions.LEFT);
            } else {
                setDirection(Directions.RIGHT);
            }
            actionLockCounter = 0;
        }
    }
}
