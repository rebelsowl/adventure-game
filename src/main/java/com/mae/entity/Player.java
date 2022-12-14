package com.mae.entity;

import com.mae.config.Settings;
import com.mae.constant.Enums.Directions;
import com.mae.entity.projectile.Fireball;
import com.mae.handler.KeyboardInputHandler;
import com.mae.interfaces.Interactable;
import com.mae.interfaces.Usable;
import com.mae.object.OBJ_Shield;
import com.mae.object.OBJ_Sword;
import com.mae.object.parent.*;
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
    private int nextLvlExp;
    private int coin;

    private Shield currentShield;
    private boolean attacking = false;
    private boolean hitSoundEffectPlaying = false;


    public Player(GamePanel gp, KeyboardInputHandler keyHandler) {
        super(gp);

        this.keyHandler = keyHandler;
        // setting initial values
        setDefaultPositionAndDirection();


        setDefaultValues();

        initPlayerImages();
        setPlayerAttackImages();
        setItems();
    }

    public void setDefaultValues() {
        solidArea = new Rectangle(8, 16, 27, 27);
        setSolidAreaDefaultX(solidArea.x);
        setSolidAreaDefaultY(solidArea.y);

        attackArea.width = 36;
        attackArea.height = 36;

        // player status
        level = 1;
        setMaxLife(6);
        setLife(maxLife);
        setDefaultSpeed(4);
        setSpeed(getDefaultSpeed());
        strength = 1; // increases attack
        dexterity = 1; // increases defence
        exp = 0;
        nextLvlExp = 5;
        coin = 500;
        currentWeapon = new OBJ_Sword(gp);
        currentShield = new OBJ_Shield(gp);
        attack = getFinalAttackValue();
        defence = getFinalDefenceValue();

        projectileSkill = new Fireball(gp);
        setMaxMana(4);
        setMana(getMaxMana());
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
    }

    public void setPlayerAttackImages() {
        if (currentWeapon.getType().equals(Weapon.WeaponType.SWORD)) {
            setAttackUp1(setImage("/player/boy_attack_up_1", TILE_SIZE, TILE_SIZE * 2));
            setAttackUp2(setImage("/player/boy_attack_up_2", TILE_SIZE, TILE_SIZE * 2));
            setAttackLeft1(setImage("/player/boy_attack_left_1", TILE_SIZE * 2, TILE_SIZE));
            setAttackLeft2(setImage("/player/boy_attack_left_2", TILE_SIZE * 2, TILE_SIZE));
            setAttackRight1(setImage("/player/boy_attack_right_1", TILE_SIZE * 2, TILE_SIZE));
            setAttackRight2(setImage("/player/boy_attack_right_2", TILE_SIZE * 2, TILE_SIZE));
            setAttackDown1(setImage("/player/boy_attack_down_1", TILE_SIZE, TILE_SIZE * 2));
            setAttackDown2(setImage("/player/boy_attack_down_2", TILE_SIZE, TILE_SIZE * 2));
        } else if (currentWeapon.getType().equals(Weapon.WeaponType.AXE)) {
            setAttackUp1(setImage("/player/boy_axe_up_1", TILE_SIZE, TILE_SIZE * 2));
            setAttackUp2(setImage("/player/boy_axe_up_2", TILE_SIZE, TILE_SIZE * 2));
            setAttackLeft1(setImage("/player/boy_axe_left_1", TILE_SIZE * 2, TILE_SIZE));
            setAttackLeft2(setImage("/player/boy_axe_left_2", TILE_SIZE * 2, TILE_SIZE));
            setAttackRight1(setImage("/player/boy_axe_right_1", TILE_SIZE * 2, TILE_SIZE));
            setAttackRight2(setImage("/player/boy_axe_right_2", TILE_SIZE * 2, TILE_SIZE));
            setAttackDown1(setImage("/player/boy_axe_down_1", TILE_SIZE, TILE_SIZE * 2));
            setAttackDown2(setImage("/player/boy_axe_down_2", TILE_SIZE, TILE_SIZE * 2));
        }
    }


    public void setDefaultPositionAndDirection() {
        setWorldX(TILE_SIZE * 23);
        setWorldY(TILE_SIZE * 21);
        direction = Directions.DOWN;

        // new world debug
//        setWorldX(TILE_SIZE * 13);
//        setWorldY(TILE_SIZE * 11);
//        GamePanel.currentMap = 1;

    }

    public void restoreLifeAndMana() {
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }


    public void update() {

        if (keyHandler.spacePressed) {
            setAttacking(true);
            attack();
        } else if (keyHandler.shotKeyPressed && !projectileSkill.isAlive() && shootAvailable == 30 && projectileSkill.hasEnoughMana(this)) {
            projectileSkill.set(worldX, worldY, direction, true, this);

            for (int i = 0; i < gp.getProjectiles()[GamePanel.currentMap].length; i++) {
                if (gp.getProjectiles()[GamePanel.currentMap][i] == null) {
                    gp.getProjectiles()[GamePanel.currentMap][i] = projectileSkill;
                    break;
                }
            }

            projectileSkill.useMana(this);
            shootAvailable = 0;
            gp.playSoundEffect(10);
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

            // Interactive tile collision
            gp.getCollisionChecker().checkEntity(this, gp.getITiles()[GamePanel.currentMap]);

            // object collision
            int objIndex = gp.getCollisionChecker().checkObject(this, true);
            interactWithObject(objIndex);

            // Entity Collision
            int entityIndex = gp.getCollisionChecker().checkEntity(this, gp.getNpcs()[GamePanel.currentMap]);
            interactWithEntity(entityIndex);

            // Monster Collision
            int monsterIndex = gp.getCollisionChecker().checkEntity(this, gp.getMonsters()[GamePanel.currentMap]);
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

        // projectile skill cooldown
        if (shootAvailable < 30) {
            shootAvailable++;
        }

        if (life <= 0) {
            gp.setGameState(GamePanel.GAME_OVER_STATE);
            gp.stopThemeSong();
            gp.playSoundEffect(12);
        }

    }

    public void selectItem() {
        int itemIndex = gp.getUi().getInventoryItemIndexFromColAndRow(gp.getUi().getPlayerInventorySlotCol(), gp.getUi().getPlayerInventorySlotRow());

        if (itemIndex < getInventory().size()) {
            SuperObject selectedItem = getInventory().get(itemIndex);
            if (selectedItem instanceof Weapon) {
                setCurrentWeapon((Weapon) selectedItem);
                attack = getFinalAttackValue();
                setPlayerAttackImages();
            } else if (selectedItem instanceof Shield) {
                setCurrentShield((Shield) selectedItem);
                defence = getFinalDefenceValue();
            } else if (selectedItem instanceof Consumable) {
                Consumable item = (Consumable) selectedItem;
                item.use(this);
                getInventory().remove(itemIndex);
            } else if (selectedItem instanceof Usable) {
                if (((Usable) selectedItem).use()) // if item is used
                    getInventory().remove(itemIndex);
            }
        }
    }

    public void setItems() {
        getInventory().clear();
        getInventory().add(currentWeapon);
        getInventory().add(currentShield);
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

            int monsterIndex = gp.getCollisionChecker().checkEntity(this, gp.getMonsters()[GamePanel.currentMap]);
            hitMonster(monsterIndex, attack, currentWeapon.getKnockBackPower());

            int iTileIndex = gp.getCollisionChecker().checkEntity(this, gp.getITiles()[GamePanel.currentMap]);
            hitInteractiveTile(iTileIndex);

            int projectileCollisionIndex = gp.getCollisionChecker().checkEntity(this, gp.getProjectiles()[GamePanel.currentMap]);
            hitProjectile(projectileCollisionIndex);


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

    public void hitMonster(int index, int attack, int knockBackPower) {
        if (index > -1) {
            if (!gp.getMonsters()[GamePanel.currentMap][index].isInvincible()) {
                gp.playSoundEffect(6);

                knockBack(gp.getMonsters()[GamePanel.currentMap][index], knockBackPower);

                int damage = attack - gp.getMonsters()[GamePanel.currentMap][index].getDefence();
                damage = Math.max(damage, 0);
                gp.getUi().addMessage(damage + " damage!");
                gp.getMonsters()[GamePanel.currentMap][index].life -= damage;
                gp.getMonsters()[GamePanel.currentMap][index].setInvincible(true);
                gp.getMonsters()[GamePanel.currentMap][index].damageReaction();
                if (gp.getMonsters()[GamePanel.currentMap][index].getLife() <= 0) {
                    gp.getMonsters()[GamePanel.currentMap][index].setDying(true);
                    gp.getUi().addMessage("Killed the " + gp.getMonsters()[GamePanel.currentMap][index].getName() + "!");
                    gp.getUi().addMessage("Exp + " + gp.getMonsters()[GamePanel.currentMap][index].getExp());

                    exp += gp.getMonsters()[GamePanel.currentMap][index].getExp(); // TODO: status abstraction --> exp handler
                    checkLevelUp();
                }
            }
        }
    }

    private void hitInteractiveTile(int index) {
        if (index > -1 && gp.getITiles()[GamePanel.currentMap][index].isDestructible() && gp.getITiles()[GamePanel.currentMap][index].isCorrectItem(this) && !gp.getITiles()[GamePanel.currentMap][index].isInvincible()) {
            gp.getITiles()[GamePanel.currentMap][index].life--;
            gp.getITiles()[GamePanel.currentMap][index].setInvincible(true);
            generateParticle(gp.getITiles()[GamePanel.currentMap][index], gp.getITiles()[GamePanel.currentMap][index]);
            if (gp.getITiles()[GamePanel.currentMap][index].life <= 0) {
                gp.getITiles()[GamePanel.currentMap][index].playSoundEffect();
                gp.getITiles()[GamePanel.currentMap][index] = gp.getITiles()[GamePanel.currentMap][index].getDestroyedForm();
            }
        }

    }

    private void hitProjectile(int index) {
        if (index > -1) {
            Entity projectile = gp.getProjectiles()[GamePanel.currentMap][index];
            projectile.setAlive(false);
            generateParticle(projectile, projectile);
        }
    }

    private void interactWithMonster(int index) {
        if (index > -1) {
            if (!invincible && !gp.getMonsters()[GamePanel.currentMap][index].isDying()) {
                gp.playSoundEffect(5);

                int damage = gp.getMonsters()[GamePanel.currentMap][index].getAttack() - getDefence();
                damage = Math.max(damage, 0);
                life -= damage;
                invincible = true;
            }
        }
    }

    public void interactWithObject(int index) {
        if (index > -1) {

            if (gp.getObjects()[GamePanel.currentMap][index] instanceof Collectable) { // collectable
                ((Collectable) gp.getObjects()[GamePanel.currentMap][index]).use(this);
                gp.getObjects()[GamePanel.currentMap][index] = null;
            } else if (gp.getObjects()[GamePanel.currentMap][index] instanceof Interactable) {
                if (keyHandler.enterPressed){
                    ((Interactable) gp.getObjects()[GamePanel.currentMap][index]).interact();
                }
            } else {
                String text;
                if (getInventory().size() < getMaxInventorySize()) {
                    getInventory().add(gp.getObjects()[GamePanel.currentMap][index]);
                    gp.playSoundEffect(1);
                    text = "Obtained a " + gp.getObjects()[GamePanel.currentMap][index].getName() + "!";
                    gp.getObjects()[GamePanel.currentMap][index] = null;
                } else {
                    text = "You cannot carry anymore!";
                }
                gp.getUi().addMessage(text);
            }

        }
    }

    public void knockBack(Entity entity, int knockBackPower) {
        if (knockBackPower > 0) {
            entity.direction = direction;
            entity.setSpeed(entity.getSpeed() + knockBackPower);
            entity.setKnockBack(true);
        }
    }

    private void interactWithEntity(int entityIndex) {
        if (entityIndex > -1) {
            if (keyHandler.enterPressed) {
                gp.setGameState(GamePanel.DIALOGUE_STATE);
                gp.getNpcs()[GamePanel.currentMap][entityIndex].speak();
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

    private void checkLevelUp() {
        if (exp >= nextLvlExp) {
            level += 1;
            nextLvlExp *= 2;

            maxLife += 2;
            setLife(maxLife);
            setMana(maxMana);
            strength++;
            dexterity++;

            attack = getFinalAttackValue();
            defence = getFinalDefenceValue();

            gp.playSoundEffect(8);
            gp.setGameState(GamePanel.DIALOGUE_STATE);
            gp.getUi().setCurrentDialogue("You are level " + level + " now!\n" + "You feel stronger!");

        }

    }

    private int getFinalDefenceValue() {
        return defence = dexterity * currentShield.getDefenceValue();
    }

    private int getFinalAttackValue() {
        attackArea = currentWeapon.getAttackArea();
        return attack = strength * currentWeapon.getAttackValue();
    }

    private boolean movementKeyPressed() {
        return keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed;
    }

    private boolean interactKeyPressed() {
        return keyHandler.enterPressed;
    }


    //TODO: add focus lost halt  other vids - Episode #07 dk 17
}
