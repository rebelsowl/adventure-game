package com.mae.object.parent;

import com.mae.panel.GamePanel;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class Weapon extends SuperObject {

    protected Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    protected int attackValue;
    protected WeaponType type;
    public Weapon(GamePanel gp) {
        super(gp);
    }

    public enum WeaponType {
        SWORD,
        AXE
    }
}
