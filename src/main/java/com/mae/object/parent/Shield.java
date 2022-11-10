package com.mae.object.parent;

import com.mae.panel.GamePanel;
import lombok.Data;

@Data
public class Shield extends  SuperObject{

    protected int defenceValue;

    public Shield(GamePanel gp) {
        super(gp);
    }
}
