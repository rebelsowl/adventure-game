package com.mae.entity;

import com.mae.config.Settings;
import com.mae.constant.Enums;
import com.mae.constant.Enums.Directions;
import com.mae.interfaces.Drawable;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.mae.config.Settings.TILE_SIZE;

@NoArgsConstructor
@Data
public abstract class Entity implements Drawable { // parent class for Player, NPCs, Monsters
    protected GamePanel gp;

    protected int worldX, worldY; // coordinates
    protected int speed;
    protected Enums.Directions direction = Directions.DOWN;

    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected int spriteCounter = 0;
    protected int spriteNumber = 0;


    protected Rectangle solidArea = new Rectangle(0, 0, TILE_SIZE, TILE_SIZE);
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;
    protected boolean collision = false;

    protected Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    protected int actionLockCounter = 0; // for npc AI's movement to be smooth
    protected boolean invincible = false;
    protected int invincibleCounter = 0;
    protected String[] dialogues = new String[20];
    protected int dialogueIndex = 0;
    protected int type; // 0 -> player 1 -> npc 2 -> monster

    // CHARACTER STATUS
    protected int maxLife;
    protected int life;

    protected boolean alive = true;
    protected boolean dying = false;
    protected int dyingCounter = 0;


    public Entity(GamePanel gp) {
        this.gp = gp;
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
                    if (spriteNumber == 1) img = up1;
                    else img = up2;
                    break;
                case DOWN:
                    if (spriteNumber == 1) img = down1;
                    else img = down2;
                    break;
                case LEFT:
                    if (spriteNumber == 1) img = left1;
                    else img = left2;
                    break;
                case RIGHT:
                    if (spriteNumber == 1) img = right1;
                    else img = right2;
                    break;
            }

            if (invincible)
                changeAlpha(g2, 0.4f); // make %40 transparent

            if (dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(img, screenX, screenY, null);

            changeAlpha(g2, 1f); // reset transparency
        }

    }

    private void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int interval = 5; //frames
        if (dyingCounter <= interval)
            changeAlpha(g2, 0f); // make %100 transparent
        else if (dyingCounter <= interval * 2)
            changeAlpha(g2, 1f);
        else if (dyingCounter <= interval * 3)
            changeAlpha(g2, 0f);
        else if (dyingCounter <= interval * 4)
            changeAlpha(g2, 1f);
        else if (dyingCounter <= interval * 5)
            changeAlpha(g2, 0f);
        else if (dyingCounter <= interval * 6)
            changeAlpha(g2, 1f);
        else if (dyingCounter <= interval * 7)
            changeAlpha(g2, 0f);
        else {
            dying = false;
            alive = false;
        }

    }

    private void changeAlpha(Graphics2D g2, float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public void setAction() {
    }

    public void update() {
        setAction();

        setCollision(false);
        gp.getCollisionChecker().checkTile(this);
        gp.getCollisionChecker().checkObject(this, false);
        gp.getCollisionChecker().checkEntity(this, gp.getNpcs());
        gp.getCollisionChecker().checkEntity(this, gp.getMonsters());
        boolean playerContact = gp.getCollisionChecker().checkPlayer(this);
        if (this.type == 2 && playerContact) {
            if (!gp.getPlayer().isInvincible()) {
                gp.playSoundEffect(6);
                gp.getPlayer().life -= 1;
                gp.getPlayer().setInvincible(true);
            }
        }

        if (!isCollision()) {
            switch (direction) {
                case UP:
                    setWorldY(getWorldY() - getSpeed());
                    break;
                case DOWN:
                    setWorldY(getWorldY() + getSpeed());
                    break;
                case LEFT:
                    setWorldX(getWorldX() - getSpeed());
                    break;
                case RIGHT:
                    setWorldX(getWorldX() + getSpeed());
                    break;
            }
        }

        spriteCounter++; // for movement animation
        if (spriteCounter > 12) {
            spriteNumber = (spriteNumber + 1) % 2;
            spriteCounter = 0;
        }

        // invincible time
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public void speak() {
        if (dialogues[dialogueIndex] == null)
            dialogueIndex = 0;
        gp.getUi().setCurrentDialogue(dialogues[dialogueIndex]);
        dialogueIndex++;

        switch (gp.getPlayer().getDirection()) {
            case UP:
                setDirection(Directions.DOWN);
                break;
            case RIGHT:
                setDirection(Directions.LEFT);
                break;
            case LEFT:
                setDirection(Directions.RIGHT);
                break;
            case DOWN:
                setDirection(Directions.UP);
                break;
        }
    }

    protected BufferedImage setImage(String imagePath, int width, int height) {
        BufferedImage image = null;
        try {
            image = UtilityTool.scaleImage(ImageIO.read(getClass().getResourceAsStream(imagePath + ".png")), width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
