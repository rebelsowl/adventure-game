package com.mae.tile;

import lombok.Data;

import java.awt.image.BufferedImage;

@Data
public class Tile {
    private BufferedImage image;
    private boolean collision = false;

}
