package com.mae.object;

import com.mae.config.Settings;
import com.mae.interfaces.Usable;
import com.mae.object.parent.SuperObject;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject implements Usable {

    public OBJ_Key(GamePanel gp) {
        super(gp);
        setName("Key");
        setDescription("[" + getName() + "]\nOpens a door.");
        setPrice(100);
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/key.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean use() {
        gp.setGameState(GamePanel.DIALOGUE_STATE);
        int objIndex = gp.getPlayer().getDetectedObjectIndex();
        if (objIndex > -1 && gp.getObjects()[GamePanel.currentMap][objIndex] instanceof OBJ_Door) {
            gp.getUi().setCurrentDialogue("Used the " + getName() + " and open the door!");
            gp.playSoundEffect(3);
            gp.getObjects()[GamePanel.currentMap][objIndex] = null;
            return true;
        } else {
            gp.getUi().setCurrentDialogue("You should use this key near a door!");
        }
        return false;
    }

}
