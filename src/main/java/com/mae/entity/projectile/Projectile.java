package com.mae.entity.projectile;

import com.mae.config.Settings;
import com.mae.constant.Enums;
import com.mae.entity.Entity;
import com.mae.panel.GamePanel;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;

@Getter
@Setter
public abstract class Projectile extends Entity {
    private Entity user;
    private int useCost;
    private int spriteNum = 1;


    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, Enums.Directions direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = maxLife;
    }

    public void update() {

        if (user.equals(gp.getPlayer())) {
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.getMonsters());
            if (monsterIndex > -1){
                gp.getPlayer().hitMonster(monsterIndex, attack);
                generateParticle(user.getProjectileSkill(), gp.getMonsters()[monsterIndex]);
                alive = false;
            }
        } else {
            boolean contactPlayer = gp.getCollisionChecker().checkPlayer(this);
            if (contactPlayer && ! gp.getPlayer().isInvincible()) {
                damagePlayer(attack);
                generateParticle(user.getProjectileSkill(), gp.getPlayer());
                alive = false;
            }
        }


        switch (direction) {
            case UP:
                worldY -= speed;
                break;
            case DOWN:
                worldY += speed;
                break;
            case RIGHT:
                worldX += speed;
                break;
            case LEFT:
                worldX -= speed;
                break;
        }

        life--;
        if (life <= 0) {
            alive = false;
        } else {
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1)
                    spriteNum = 2;
                else
                    spriteNum = 1;
            }

        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage img = null;

        int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
        int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

        if (worldX + Settings.TILE_SIZE > gp.player.getWorldX() - gp.player.getScreenX() &&
                worldX - Settings.TILE_SIZE < gp.player.getWorldX() + gp.player.getScreenX() &&
                worldY + Settings.TILE_SIZE > gp.player.getWorldY() - gp.player.getScreenY() &&
                worldY - Settings.TILE_SIZE < gp.player.getWorldY() + gp.player.getScreenY()) { // Only draw the tiles around the player

            switch (direction) {
                case UP:
                    if (spriteNum == 1) img = up1;
                    else img = up2;
                    break;
                case DOWN:
                    if (spriteNum == 1) img = down1;
                    else img = down2;
                    break;
                case LEFT:
                    if (spriteNum == 1) img = left1;
                    else img = left2;
                    break;
                case RIGHT:
                    if (spriteNum == 1) img = right1;
                    else img = right2;
                    break;
            }

            g2.drawImage(img, screenX, screenY, null);
        }

    }

    public boolean hasEnoughMana(Entity user){
        return user.getMana() >= getUseCost();
    }

    //TODO: should we do this via user's class?
    public void useMana(Entity user){
        user.setMana(user.getMana() - useCost);
    }


}
