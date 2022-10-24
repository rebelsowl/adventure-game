package com.mae.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class KeyObject extends SuperObject {

    public KeyObject() {
        setName("Key");
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/key.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
