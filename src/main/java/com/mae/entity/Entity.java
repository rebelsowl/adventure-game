package com.mae.entity;

import com.mae.config.Settings;
import com.mae.constant.Enums;
import com.mae.constant.Enums.Directions;
import com.mae.entity.particle.Particle;
import com.mae.entity.projectile.Projectile;
import com.mae.interfaces.Drawable;
import com.mae.object.parent.Weapon;
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

    protected String name;
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

    protected Weapon currentWeapon;
    protected int actionLockCounter = 0; // for npc AI's movement to be smooth
    protected boolean invincible = false;
    protected int invincibleCounter = 0;
    protected String[] dialogues = new String[20];
    protected int dialogueIndex = 0;
    protected int type; // 0 -> player 1 -> npc 2 -> monster

    protected boolean alive = true;
    protected boolean dying = false;
    protected int dyingCounter = 0;

    // CHARACTER STATUS
    protected int attack;

    protected int defence;
    protected int maxLife;
    protected int life;
    protected int exp;

    protected int maxMana;
    protected int mana;
    protected Projectile projectileSkill;
    protected int shotAvailableCounter = 1;



    protected boolean hpBarOn = false;
    protected int hpBarCounter = 0;

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

            // Monster HP Bar
            if (type == 2 && hpBarOn) {
                double oneScale = (double) TILE_SIZE / maxLife;
                double hpBarValue = oneScale * life;
                hpBarValue = hpBarValue < 0 ? 0 : hpBarValue;


                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, TILE_SIZE + 2, 12);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f); // make %40 transparent

            }

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
            alive = false;
        }

    }

    private void changeAlpha(Graphics2D g2, float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public void setAction() {}
    public void damageReaction(){}


    public void update() {
        setAction();

        setCollision(false);
        gp.getCollisionChecker().checkTile(this);
        gp.getCollisionChecker().checkObject(this, false);
        gp.getCollisionChecker().checkEntity(this, gp.getNpcs());
        gp.getCollisionChecker().checkEntity(this, gp.getMonsters());
        gp.getCollisionChecker().checkEntity(this, gp.getITiles());
        boolean playerContact = gp.getCollisionChecker().checkPlayer(this);
        if (this.type == 2 && playerContact) {
            damagePlayer(attack);
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

        // projectile skill cooldown
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

    }

    public void damagePlayer(int attack){
        if (!gp.getPlayer().isInvincible()) {
            gp.playSoundEffect(6);

            int damage = attack  - gp.getPlayer().getDefence();
            damage = Math.max(damage, 0);

            gp.getPlayer().life -= damage;
            gp.getPlayer().setInvincible(true);
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


    // PARTICLE
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size =generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed,maxLife, -1,-1);
        gp.getParticles().add(p1);
        Particle p2 = new Particle(gp, target, color, size, speed,maxLife, 1,-1);
        gp.getParticles().add(p2);
        Particle p3 = new Particle(gp, target, color, size, speed,maxLife, -1,1);
        gp.getParticles().add(p3);
        Particle p4 = new Particle(gp, target, color, size, speed,maxLife, 1,1);
        gp.getParticles().add(p4);


    }

    public Color getParticleColor() {
        return new Color(0,0,0);
    }

    public int getParticleSize() {
        return 0; //pixels
    }

    public int getParticleSpeed() {
        return 0;
    }

    public int getParticleMaxLife() {
        return 20; // frames
    }



}
