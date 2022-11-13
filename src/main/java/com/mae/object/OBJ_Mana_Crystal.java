package com.mae.object;

import com.mae.config.Settings;
import com.mae.entity.Entity;
import com.mae.object.parent.Collectable;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Getter
@Setter
public class OBJ_Mana_Crystal extends Collectable {
    private BufferedImage image2, image3;

    public OBJ_Mana_Crystal(GamePanel gp) {
        super(gp);
        setName("Mana Crystal");
        setValue(1);
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/manacrystal_full.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
            setImage2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/manacrystal_blank.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use(Entity entity) {
        gp.playSoundEffect(2);
        gp.getUi().addMessage("Mana +" + getValue());

        if (entity.getMana() + getValue() > entity.getMaxMana())
            entity.setMana(entity.getMaxMana());
        else
            entity.setMana(entity.getMana() + getValue());

    }

}
