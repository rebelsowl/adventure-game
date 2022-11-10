package com.mae.object.parent;

import com.mae.panel.GamePanel;
import lombok.Data;

import java.awt.*;

@Data
public class Weapon extends  SuperObject{

    public enum WeaponType {
        SWORD,
        AXE
    }


    protected Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    protected int attackValue;
    protected WeaponType type;

    public Weapon(GamePanel gp) {
        super(gp);
    }
}
