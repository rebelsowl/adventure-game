package com.mae.tile.interactive;

import com.mae.constant.Enums;
import com.mae.entity.Entity;
import com.mae.object.OBJ_Axe;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

import static com.mae.config.Settings.TILE_SIZE;

public class IT_DryTree extends InteractiveTile {

    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        setDirection(Enums.Directions.DOWN);
        setDestructible(true);
        setLife(new Random().nextInt(3)+1);
        try {
            setDown1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/tiles_interactive/drytree.png")), TILE_SIZE, TILE_SIZE));
            setDown2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/tiles_interactive/drytree.png")), TILE_SIZE, TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isCorrectItem(Entity entity){
        return entity.getCurrentWeapon() instanceof OBJ_Axe;
    }

    @Override
    public void playSoundEffect(){
        gp.playSoundEffect(11);
    }

    @Override
    public InteractiveTile getDestroyedForm() {
        return new IT_Trunk(gp, worldX/TILE_SIZE, worldY/TILE_SIZE);
    }



}
