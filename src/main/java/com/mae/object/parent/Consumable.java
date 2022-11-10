package com.mae.object.parent;

import com.mae.entity.Entity;
import com.mae.panel.GamePanel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Consumable extends SuperObject {

    public Consumable(GamePanel gp) {
        super(gp);
    }


    public void use(Entity entity) {}

}
