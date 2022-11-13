package com.mae.object;

import com.mae.config.Settings;
import com.mae.entity.Entity;
import com.mae.object.parent.Collectable;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Coin extends Collectable {

    public OBJ_Coin(GamePanel gp) {
        super(gp);
        setName("Bronze Coin");
        setValue(1);
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/coin_bronze.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void use(Entity entity) {
        gp.playSoundEffect(1);
        gp.getUi().addMessage("Coin + " + value);
        gp.getPlayer().setCoin(gp.getPlayer().getCoin() + value);

    }


}
