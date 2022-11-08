package com.mae.entity;

import com.mae.config.Settings;
import com.mae.constant.Enums.Directions;
import com.mae.handler.KeyboardInputHandler;
import com.mae.object.OBJ_Shield;
import com.mae.object.OBJ_Sword;
import com.mae.panel.GamePanel;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.mae.config.Settings.TILE_SIZE;

@Data
public class Player extends Entity {


    private final int screenX = (Settings.SCREEN_WIDTH / 2) - (TILE_SIZE / 2); // coordinates on the visible screen
    private final int screenY = (Settings.SCREEN_HEIGHT / 2) - (TILE_SIZE / 2); // subtracted the half tile size because frame starts at top left with 0,0
    KeyboardInputHandler keyHandler;
    private BufferedImage attackUp1, attackUp2, attackRight1, attackRight2, attackLeft1, attackLeft2, attackDown1, attackDown2;


    private int level;
    private int strength;
    private int dexterity;
    private int attack;
    private int defence;
    private int exp;
    private int nextLvlExp;
    private int coin;

    private OBJ_Sword currentWeapon;
    private OBJ_Shield currentShield;

    private boolean attacking = false;
    private boolean hitSoundEffectPlaying = false;

    public Player(GamePanel gp, KeyboardInputHandler keyHandler) {
        super(gp);

        this.keyHandler = keyHandler;
        // setting initial values
        setWorldX(TILE_SIZE * 23);
        setWorldY(TILE_SIZE * 21);
        setSpeed(4);
        direction = Directions.DOWN;

        solidArea = new Rectangle(8, 16, 27, 27);
        setSolidAreaDefaultX(solidArea.x);
        setSolidAreaDefaultY(solidArea.y);

        attackArea.width = 36;
        attackArea.height = 36;

        // player status
        level = 1;
        setMaxLife(6);
        setLife(maxLife);
        strength = 1; // increases attack
        dexterity = 1; // increases defence
        exp = 0;
        nextLvlExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword(gp);
        currentShield = new OBJ_Shield(gp);
        attack = getFinalAttackValue();
        defence = getFinalDefenceValue();


        initPlayerImages();
    }

    private int getFinalDefenceValue() {
        return defence = dexterity * currentShield.getDefenceValue();
    }

    private int getFinalAttackValue() {
        return attack = strength * currentWeapon.getAttackValue();
    }


    public void initPlayerImages() {
        setUp1(setImage("/player/boy_up_1", TILE_SIZE, TILE_SIZE));
        setUp2(setImage("/player/boy_up_2", TILE_SIZE, TILE_SIZE));
        setLeft1(setImage("/player/boy_left_1", TILE_SIZE, TILE_SIZE));
        setLeft2(setImage("/player/boy_left_2", TILE_SIZE, TILE_SIZE));
        setRight1(setImage("/player/boy_right_1", TILE_SIZE, TILE_SIZE));
        setRight2(setImage("/player/boy_right_2", TILE_SIZE, TILE_SIZE));
        setDown1(setImage("/player/boy_down_1", TILE_SIZE, TILE_SIZE));
        setDown2(setImage("/player/boy_down_2", TILE_SIZE, TILE_SIZE));

        setAttackUp1(setImage("/player/boy_attack_up_1", TILE_SIZE, TILE_SIZE * 2));
        setAttackUp2(setImage("/player/boy_attack_up_2", TILE_SIZE, TILE_SIZE * 2));
        setAttackLeft1(setImage("/player/boy_attack_left_1", TILE_SIZE * 2, TILE_SIZE));
        setAttackLeft2(setImage("/player/boy_attack_left_2", TILE_SIZE * 2, TILE_SIZE));
        setAttackRight1(setImage("/player/boy_attack_right_1", TILE_SIZE * 2, TILE_SIZE));
        setAttackRight2(setImage("/player/boy_attack_right_2", TILE_SIZE * 2, TILE_SIZE));
        setAttackDown1(setImage("/player/boy_attack_down_1", TILE_SIZE, TILE_SIZE * 2));
        setAttackDown2(setImage("/player/boy_attack_down_2", TILE_SIZE, TILE_SIZE * 2));
    }


    public void update() {

        if (keyHandler.spacePressed) {
            setAttacking(true);
            attack();


        } else if (movementKeyPressed() || interactKeyPressed()) {
            if (keyHandler.upPressed) {
                setDirection(Directions.UP);
            } else if (keyHandler.downPressed) {
                setDirection(Directions.DOWN);
            } else if (keyHandler.leftPressed) {
                setDirection(Directions.LEFT);
            } else if (keyHandler.rightPressed) {
                setDirection(Directions.RIGHT);
            }

            // tile collision
            setCollision(false);
            gp.getCollisionChecker().checkTile(this);

            // object collision
            int objIndex = gp.getCollisionChecker().checkObject(this, true);
            interactWithObject(objIndex);

            // Entity Collision
            int entityIndex = gp.getCollisionChecker().checkEntity(this, gp.getNpcs());
            interactWithEntity(entityIndex);

            // Monster Collision
            int monsterIndex = gp.getCollisionChecker().checkEntity(this, gp.getMonsters());
            interactWithMonster(monsterIndex);

            // Check event
            gp.getEventHandler().checkEvent();

            if (!isCollision() && !interactKeyPressed()) {
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

            keyHandler.enterPressed = false;


            spriteCounter++; // for movement animation
            if (spriteCounter > 12) {
                spriteNumber = (spriteNumber + 1) % 2;
                spriteCounter = 0;
            }

        }
        // invincible time
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    private void attack() {

        if (!hitSoundEffectPlaying) {
            gp.playSoundEffect(7);
            hitSoundEffectPlaying = true;
        }

        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNumber = 1;
        } else if (spriteCounter < 25) {
            spriteNumber = 2;

            // save current positions
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // adjust for the attack
            switch (direction) {
                case UP:
                    worldY -= attackArea.height;
                    break;
                case LEFT:
                    worldX -= attackArea.width;
                    break;
                case RIGHT:
                    worldX += attackArea.width;
                    break;
                case DOWN:
                    worldY += attackArea.height;
                    break;
            }

            // attack area becomes solid area for collision checker to check
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.getCollisionChecker().checkEntity(this, gp.getMonsters());
            hitMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        } else {
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
            keyHandler.spacePressed = false;
            hitSoundEffectPlaying = false;
        }

    }

    private void hitMonster(int index) {
        if (index > -1) {
            if (!gp.getMonsters()[index].isInvincible()) {
                gp.playSoundEffect(6);
                gp.getMonsters()[index].life -= 1;
                gp.getMonsters()[index].setInvincible(true);
                gp.getMonsters()[index].damageReaction();
                if (gp.getMonsters()[index].getLife() <= 0)
                    gp.getMonsters()[index].setDying(true);
            }
        }

    }

    private void interactWithMonster(int index) {
        if (index > -1) {
            if (!invincible) {
                gp.playSoundEffect(5);
                life -= 1; //TODO: detailed calculations with stats
                invincible = true;
            }
        }

    }


    public void interactWithObject(int index) {
        if (index > -1) {

        }
    }

    private void interactWithEntity(int entityIndex) {
        if (entityIndex > -1) {
            if (keyHandler.enterPressed) {
                gp.setGameState(GamePanel.DIALOGUE_STATE);
                gp.getNpcs()[entityIndex].speak();
            }
        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage img = null;

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case UP:
                if (attacking) {
                    tempScreenY = screenY - TILE_SIZE;
                    if (spriteNumber == 1)
                        img = attackUp1;
                    else img = attackUp2;
                } else {
                    if (spriteNumber == 1) img = up1;
                    else img = up2;
                }
                break;
            case DOWN:
                if (attacking) {
                    if (spriteNumber == 1)
                        img = attackDown1;
                    else
                        img = attackDown2;
                } else {
                    if (spriteNumber == 1) img = down1;
                    else img = down2;
                }
                break;
            case LEFT:
                if (attacking) {
                    tempScreenX = screenX - TILE_SIZE;
                    if (spriteNumber == 1)
                        img = attackLeft1;
                    else
                        img = attackLeft2;
                } else {
                    if (spriteNumber == 1) img = left1;
                    else img = left2;
                }
                break;
            case RIGHT:
                if (attacking) {
                    if (spriteNumber == 1)
                        img = attackRight1;
                    else
                        img = attackRight2;
                } else {
                    if (spriteNumber == 1) img = right1;
                    else img = right2;
                }
                break;
        }

        if (invincible)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); // make %70 transparent

        g2.drawImage(img, tempScreenX, tempScreenY, null); // screenX/Y -> player will be always in the middle

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // reset transparency

        // debug
//        g2.setFont(new Font("Arial", Font.PLAIN, 20));
//        g2.setColor(Color.white);
//        g2.drawString("invincible Counter: " + invincibleCounter, 10, 400);

    }

    private boolean movementKeyPressed() {
        return keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed;
    }

    private boolean interactKeyPressed() {
        return keyHandler.enterPressed;
    }


    //TODO: add focus lost halt  other vids - Episode #07 dk 17
}
