package com.mae.object;

import com.mae.config.Settings;
import com.mae.interfaces.Interactable;
import com.mae.object.parent.SuperObject;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Chest extends SuperObject implements Interactable {
    private BufferedImage openedImage;
    private boolean opened = false;
    private SuperObject loot;
    public OBJ_Chest(GamePanel gp, SuperObject loot) {
        super(gp);
        setName("Chest");
        setCollision(true);
        this.loot = loot;
        try {
            setImage(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/chest.png")), Settings.TILE_SIZE, Settings.TILE_SIZE));
            openedImage = UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/objects/chest_opened.png")), Settings.TILE_SIZE, Settings.TILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

    @Override
    public void interact() {
        gp.setGameState(GamePanel.DIALOGUE_STATE);

        if(!opened){
            gp.playSoundEffect(3);
            StringBuilder sb = new StringBuilder();
            sb.append("You opened the chest and find a " + loot.getName() + " !");

            if (gp.getPlayer().getInventory().size() == gp.getPlayer().getMaxInventorySize())
                sb.append("\n...But you cannot carry anymore!");
            else {
                sb.append("\nObtained the " + loot.getName() + "!");
                gp.getPlayer().getInventory().add(loot);
                setImage(openedImage);
                opened = true;
            }
            gp.getUi().setCurrentDialogue(sb.toString());
        } else {
            gp.getUi().setCurrentDialogue("It is empty!");
        }

    }
}
