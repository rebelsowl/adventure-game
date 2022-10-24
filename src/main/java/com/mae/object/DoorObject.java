package com.mae.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class DoorObject extends SuperObject {

    public DoorObject() {
        setName("Door");
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/door.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
