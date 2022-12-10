package com.mae.entity.monster;

import com.mae.config.Settings;
import com.mae.constant.Enums;
import com.mae.entity.projectile.Rock;
import com.mae.object.OBJ_Coin;
import com.mae.object.OBJ_Heart;
import com.mae.object.OBJ_Mana_Crystal;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

import static com.mae.config.Settings.TILE_SIZE;

public class MON_GreenSlime extends Monster {

    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        setName("Green Slime");
        setDefaultSpeed(1);
        setSpeed(getDefaultSpeed());
        setMaxLife(4);
        setLife(getMaxLife());
        setType(2);

        setExp(2);

        setProjectileSkill(new Rock(gp));

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        setSolidAreaDefaultX(solidArea.x);
        setSolidAreaDefaultY(solidArea.y);

        attack = 5;
        defence = 0;

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

    public void setAction() {
        if (isOnPath()) {
            // follow the player
            int goalCol = (gp.getPlayer().getWorldX() + gp.getPlayer().getSolidArea().x) / TILE_SIZE;
            int goalRow = (gp.getPlayer().getWorldY() + gp.getPlayer().getSolidArea().y) / TILE_SIZE;
            searchPath(goalCol, goalRow);

            // can shoot when agroed
            int i = new Random().nextInt(200) + 1;
            if (i > 198 && !projectileSkill.isAlive() && shootAvailable == 30) {
                projectileSkill.set(worldX, worldY, direction, true, this);

                for (int y = 0; y < gp.getProjectiles()[GamePanel.currentMap].length; y++) {
                    if (gp.getProjectiles()[GamePanel.currentMap][y] == null) {
                        gp.getProjectiles()[GamePanel.currentMap][y] = projectileSkill;
                        break;
                    }
                }
                shootAvailable = 0;
            }
        } else {
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

    public void damageReaction() {
        actionLockCounter = 0;
//        direction = gp.getPlayer().getDirection(); // move away from the player
        setOnPath(true); // follow the player to attack
    }

    @Override
    public void update() {
        super.update();

        // aggro
        int xDistance = Math.abs(worldX - gp.getPlayer().getWorldX());
        int yDistance = Math.abs(worldY - gp.getPlayer().getWorldY());
        int distanceBetweenPlayer = (xDistance + yDistance) / TILE_SIZE;
        if (!isOnPath() && distanceBetweenPlayer < 5) {
            int i = new Random().nextInt(101);
            if (i > 50)
                setOnPath(true);
        }

        // remove aggro

        if (isOnPath() && (distanceBetweenPlayer > 25))
            setOnPath(false);


    }


    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        if (i < 50)
            dropItem(new OBJ_Coin(gp));
        else if (i < 75)
            dropItem(new OBJ_Heart(gp));
        else
            dropItem(new OBJ_Mana_Crystal(gp));

    }

}
