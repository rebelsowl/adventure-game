package com.mae.entity;

import com.mae.constant.Enums;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

@Data
public abstract class Entity { // parent class for Player, NPCs, Monsters
    protected int worldX, worldY; // coordinates
    protected int speed;

    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected int spriteCounter = 0;
    protected int spriteNumber = 0;
    protected Enums.Directions direction;

    protected Rectangle solidArea;
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;
    protected boolean collision = false;



}
