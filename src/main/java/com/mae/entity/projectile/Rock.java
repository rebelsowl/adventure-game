package com.mae.entity.projectile;

import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static com.mae.config.Settings.TILE_SIZE;

public class Rock extends Projectile {

    public Rock(GamePanel gp) {
        super(gp);

        setName("Rock");
        setSpeed(7);
        setMaxLife(80);
        setLife(getMaxLife());
        setAttack(2);

        setUseCost(1);
        setAlive(false);

        initImages();
    }


    public void initImages() {
        try {
            setUp1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/projectiles/rock_down_1.png")), TILE_SIZE, TILE_SIZE));
            setUp2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/projectiles/rock_down_1.png")), TILE_SIZE, TILE_SIZE));
            setLeft1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/projectiles/rock_down_1.png")), TILE_SIZE, TILE_SIZE));
            setLeft2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/projectiles/rock_down_1.png")), TILE_SIZE, TILE_SIZE));
            setRight1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/projectiles/rock_down_1.png")), TILE_SIZE, TILE_SIZE));
            setRight2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/projectiles/rock_down_1.png")), TILE_SIZE, TILE_SIZE));
            setDown1(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/projectiles/rock_down_1.png")), TILE_SIZE, TILE_SIZE));
            setDown2(UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream("/projectiles/rock_down_1.png")), TILE_SIZE, TILE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Color getParticleColor() {
        return new Color(65, 50, 30);
    }

    @Override
    public int getParticleSize() {
        return 6; //pixels
    }

    @Override
    public int getParticleSpeed() {
        return 1;
    }

    @Override
    public int getParticleMaxLife() {
        return 20; // frames
    }


}
