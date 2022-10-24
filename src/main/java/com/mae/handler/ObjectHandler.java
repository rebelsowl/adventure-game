package com.mae.handler;

import com.mae.config.Settings;
import com.mae.object.ChestObject;
import com.mae.object.DoorObject;
import com.mae.object.KeyObject;
import com.mae.panel.GamePanel;

public class ObjectHandler {
   GamePanel gp;

   public ObjectHandler(GamePanel gp){
       this.gp = gp;
   }


   public void placeInitialObjectsInWorld(){
       gp.getObjects()[0] = new KeyObject();
       gp.getObjects()[0].setWorldX(23 * Settings.tileSize);
       gp.getObjects()[0].setWorldY(7 * Settings.tileSize);

       gp.getObjects()[1] = new KeyObject();
       gp.getObjects()[1].setWorldX(23 * Settings.tileSize);
       gp.getObjects()[1].setWorldY(40 * Settings.tileSize);

       gp.getObjects()[2] = new KeyObject();
       gp.getObjects()[2].setWorldX(38 * Settings.tileSize);
       gp.getObjects()[2].setWorldY(8 * Settings.tileSize);

       gp.getObjects()[3] = new DoorObject();
       gp.getObjects()[3].setWorldX(10 * Settings.tileSize);
       gp.getObjects()[3].setWorldY(11 * Settings.tileSize);

       gp.getObjects()[4] = new DoorObject();
       gp.getObjects()[4].setWorldX(8 * Settings.tileSize);
       gp.getObjects()[4].setWorldY(28 * Settings.tileSize);

       gp.getObjects()[5] = new DoorObject();
       gp.getObjects()[5].setWorldX(12 * Settings.tileSize);
       gp.getObjects()[5].setWorldY(22 * Settings.tileSize);

       gp.getObjects()[6] = new ChestObject();
       gp.getObjects()[6].setWorldX(10 * Settings.tileSize);
       gp.getObjects()[6].setWorldY(8 * Settings.tileSize);


   }

}
