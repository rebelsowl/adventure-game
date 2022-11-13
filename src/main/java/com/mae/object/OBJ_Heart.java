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
public class OBJ_Heart extends Collectable {
    private BufferedImage image2, image3;

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        setName("Heart");
        setValue(2);
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
            setImage2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
            setImage3(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void use(Entity entity) {
        gp.playSoundEffect(2);
        gp.getUi().addMessage("Life +" + getValue());

        if (entity.getLife() + getValue() > entity.getMaxLife())
            entity.setLife(entity.getMaxLife());
        else
            entity.setLife(entity.getLife() + getValue());

    }
}
