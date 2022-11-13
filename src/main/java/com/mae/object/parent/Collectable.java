package com.mae.object.parent;

import com.mae.entity.Entity;
import com.mae.panel.GamePanel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Collectable extends SuperObject {

    protected int value;

    public Collectable(GamePanel gp) {
        super(gp);
    }

    public void use(Entity entity) {}


}
