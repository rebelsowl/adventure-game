package com.mae.entity;

import com.mae.config.Settings;
import com.mae.constant.Enums;
import com.mae.constant.Enums.Directions;
import com.mae.entity.particle.Particle;
import com.mae.entity.projectile.Projectile;
import com.mae.interfaces.Drawable;
import com.mae.object.parent.SuperObject;
import com.mae.object.parent.Weapon;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mae.config.Settings.TILE_SIZE;

@NoArgsConstructor
@Data
public abstract class Entity implements Drawable { // parent class for Player, NPCs, Monsters


    protected GamePanel gp;
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    protected Rectangle solidArea = new Rectangle(0, 0, TILE_SIZE, TILE_SIZE);
    protected Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;
    protected boolean collision = false;
    protected String[] dialogues = new String[20];

    // STATE
    protected int worldX, worldY; // coordinates
    protected Enums.Directions direction = Directions.DOWN;
    protected int spriteNumber = 0;
    protected int dialogueIndex = 0;
    protected boolean invincible = false;

    protected boolean dying = false;
    protected boolean alive = true;
    protected boolean hpBarOn = false;
    protected boolean onPath = false; // entity is following a path
    protected boolean knockBack = false;

    // COUNTER
    protected int spriteCounter = 0;
    protected int actionLockCounter = 0; // for npc AI's movement to be smooth
    protected int invincibleCounter = 0;
    protected int dyingCounter = 0;
    protected int hpBarCounter = 0;

    protected int knockBackCounter = 0;

    // CHARACTER ATTRIBUTES
    protected String name;
    protected int defaultSpeed;
    protected int speed;
    protected int maxLife;
    protected int life;
    protected int maxMana;
    protected int mana;
    protected int attack;
    protected int defence;
    protected int exp;
    protected int shootAvailable = 1;
    protected Weapon currentWeapon;
    protected Projectile projectileSkill;
    private List<SuperObject> inventory = new ArrayList<>();
    private final int maxInventorySize = 20;
    protected int type; // 0 -> player 1 -> npc 2 -> monster


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

    public void setAction() {
    }

    public void damageReaction() {
    }


    public void update() {
        setAction();
        checkCollision();
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
        if (shootAvailable < 30) {
            shootAvailable++;
        }

    }

    private void checkCollision() {
        setCollision(false);
        gp.getCollisionChecker().checkTile(this);
        gp.getCollisionChecker().checkObject(this, false);
        gp.getCollisionChecker().checkEntity(this, gp.getNpcs()[GamePanel.currentMap]);
        gp.getCollisionChecker().checkEntity(this, gp.getMonsters()[GamePanel.currentMap]);
        gp.getCollisionChecker().checkEntity(this, gp.getITiles()[GamePanel.currentMap]);
        boolean playerContact = gp.getCollisionChecker().checkPlayer(this);
        if (this.type == 2 && playerContact) {
            damagePlayer(attack);
        }
    }

    public void damagePlayer(int attack) {
        if (!gp.getPlayer().isInvincible()) {
            gp.playSoundEffect(6);

            int damage = attack - gp.getPlayer().getDefence();
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
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -1, -1);
        gp.getParticles().add(p1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 1, -1);
        gp.getParticles().add(p2);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -1, 1);
        gp.getParticles().add(p3);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 1, 1);
        gp.getParticles().add(p4);


    }

    public Color getParticleColor() {
        return new Color(0, 0, 0);
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

    /**
     * finds optimal path to destination
     *
     * @param goalCol
     * @param goalRow
     */
    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / TILE_SIZE;
        int startRow = (worldY + solidArea.y) / TILE_SIZE;

        gp.getPathFinder().setNodes(startCol, startRow, goalCol, goalRow);
        if(gp.getPathFinder().search()) { //
            int nextX = gp.getPathFinder().getPathList().get(0).getCol() * TILE_SIZE;
            int nextY = gp.getPathFinder().getPathList().get(0).getRow() * TILE_SIZE;

            // entity's solid area position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < (nextX + TILE_SIZE))
                direction = Directions.UP;
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < (nextX + TILE_SIZE))
                direction = Directions.DOWN;
            else if (enTopY >= nextY && enBottomY < (nextY + TILE_SIZE)) {
                if (enLeftX > nextX)
                    direction = Directions.LEFT;
                else if (enLeftX < nextX)
                    direction = Directions.RIGHT;
            } else if (enTopY > nextY && enLeftX > nextX) { // stuck on top right
                direction = Directions.UP;
                checkCollision();
                if (collision)
                    direction = Directions.LEFT;
            } else if (enTopY > nextY && enLeftX < nextX) { // stuck on top left
                direction = Directions.UP;
                checkCollision();
                if (collision)
                    direction = Directions.RIGHT;
            } else if (enTopY < nextY && enLeftX > nextX) { // stuck on bottom right
                direction = Directions.DOWN;
                checkCollision();
                if (collision)
                    direction = Directions.LEFT;
            } else if (enTopY < nextY && enRightX < nextX) { // stuck on bottom left
                direction = Directions.DOWN;
                checkCollision();
                if (collision)
                    direction = Directions.RIGHT;
            }
            // stop when reaching the goal
            /* TODO: Acabilirsin hedefe simdilik playeri takip ettigi icin kapali
            int nextCol = gp.getPathFinder().getPathList().get(0).getCol();
            int nextRow=  gp.getPathFinder().getPathList().get(0).getRow();
            if (nextCol == goalCol && nextRow == goalRow){
                onPath = false;
            }
             */
        }
    }

}
