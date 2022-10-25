package com.mae.object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BootObject extends  SuperObject {

    public BootObject() {
        setName("Boots");
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/objects/boots.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
