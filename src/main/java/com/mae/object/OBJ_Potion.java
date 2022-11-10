package com.mae.object;

import com.mae.config.Settings;
import com.mae.entity.Entity;
import com.mae.object.parent.Consumable;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Potion extends Consumable {
    int healingValue = 5;

    public OBJ_Potion(GamePanel gp) {
        super(gp);
        setName("Red Potion");
        setDescription("[" + getName() + "]\nHeals your life by: " + healingValue);
        setDescription("");
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/potion_red.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void use(Entity entity) {
        gp.setGameState(GamePanel.DIALOGUE_STATE);
        gp.getUi().setCurrentDialogue("You drink the " + getName() + "!\n" +
                "Your life has been recovered by: " + healingValue + ".");
        entity.setLife(entity.getLife() + healingValue);
        if (entity.getLife() > entity.getMaxLife())
            entity.setLife(entity.getMaxLife());

        gp.playSoundEffect(2);
    }
}
