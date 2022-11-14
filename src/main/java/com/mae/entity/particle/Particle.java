package com.mae.entity.particle;

import com.mae.entity.Entity;
import com.mae.panel.GamePanel;

import java.awt.*;

import static com.mae.config.Settings.TILE_SIZE;

public class Particle extends Entity {

    Entity generator;
    Color color;
    int size;
    int xd;  // particle movement direction : 1 | -1
    int yd;  // particle movement direction : 1 | -1


    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gp);
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        int offset = (TILE_SIZE / 2) - (size / 2); // to center the tile
        worldX = generator.getWorldX() + offset;
        worldY = generator.getWorldY() + offset;

    }

    @Override
    public void update() {
        life--;

        if (life <= 0)
            alive = false;
        else if (life < maxLife / 3) // gravity
            yd++;


        worldX += (xd * speed);
        worldY += yd * speed;

    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);

    }


}
