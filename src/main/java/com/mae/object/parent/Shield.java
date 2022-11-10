package com.mae.object.parent;

import com.mae.panel.GamePanel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Shield extends SuperObject {

    protected int defenceValue;

    public Shield(GamePanel gp) {
        super(gp);
    }
}
