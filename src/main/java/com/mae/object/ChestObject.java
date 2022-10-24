package com.mae.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ChestObject extends SuperObject {

    public  ChestObject() {
        setName("Chest");
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/chest.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
