package com.mae.ui;

import com.mae.config.Settings;
import com.mae.entity.Entity;
import com.mae.entity.npc.NPC_Merchant;
import com.mae.object.OBJ_Coin;
import com.mae.object.OBJ_Heart;
import com.mae.object.OBJ_Mana_Crystal;
import com.mae.object.parent.SuperObject;
import com.mae.panel.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

import static com.mae.config.Settings.*;

public class UI {

    public static int stateCommandNumber = 0; // used for cursor number
    public static int subState = 0; // holds sub state
    private final GamePanel gp;
    private final ArrayList<String> messages = new ArrayList<>();
    private final ArrayList<Integer> messageCounter = new ArrayList<>();
    BufferedImage heartFull, heartHalf, heartBlank, crystalFull, crystalBlank, coin;
    private Font purisaBoldFont;
    private String currentDialogue = "";
    // inventory
    private int playerInventorySlotCol = 0;
    private int playerInventorySlotRow = 0;

    private int npcInventorySlotCol = 0;
    private int npcInventorySlotRow = 0;


    private int counter = 0;

    private NPC_Merchant traderNpc;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaBoldFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OBJ_Heart heart = new OBJ_Heart(gp);
        heartFull = heart.getImage();
        heartHalf = heart.getImage2();
        heartBlank = heart.getImage3();

        OBJ_Mana_Crystal mana = new OBJ_Mana_Crystal(gp);
        crystalFull = mana.getImage();
        crystalBlank = mana.getImage2();

        coin = new OBJ_Coin(gp).getImage();

    }

    public void draw(Graphics2D g2) {
        g2.setFont(purisaBoldFont);
        g2.setColor(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (gp.getGameState() == GamePanel.TITLE_STATE) {
            drawTitleScreen(g2);
        } else if (gp.getGameState() == GamePanel.PLAY_STATE) {
            drawPlayersStats(g2);
            drawMessages(g2);
        } else if (gp.getGameState() == GamePanel.PAUSE_STATE) {
            drawPlayersStats(g2);
            drawPauseScreen(g2);
        } else if (gp.getGameState() == GamePanel.DIALOGUE_STATE) {
            drawDialogueScreen(g2);
        } else if (gp.getGameState() == GamePanel.CHARACTER_STATUS_STATE) {
            drawCharacterStatusScreen(g2);
            drawInventory(g2, gp.getPlayer(), true);
        } else if (gp.getGameState() == GamePanel.OPTIONS_STATE) {
            drawOptionsScreen(g2);
        } else if (gp.getGameState() == GamePanel.GAME_OVER_STATE) {
            drawGameOverScreen(g2);
        } else if (gp.getGameState() == GamePanel.LOADING_STATE) {
            drawLoadingScreen(g2);
        } else if (gp.getGameState() == GamePanel.TRADE_STATE) {
            drawTradeScreen(g2);
        }
    }


    /******************************************************* Draw Methods  ********************************************************/
    /**
     * Players stats: life, mana
     *
     * @param g2
     */
    private void drawPlayersStats(Graphics2D g2) {
        int x = TILE_SIZE / 2;
        int y = TILE_SIZE / 2;
        // draw max life
        for (int i = 0; i < gp.getPlayer().getMaxLife() / 2; i++) { // max life
            g2.drawImage(heartBlank, x, y, null);
            x += TILE_SIZE;
        }
        // draw life
        x = TILE_SIZE / 2;
        int i = 0;
        while (i < gp.getPlayer().getLife()) {
            g2.drawImage(heartHalf, x, y, null);
            i++;
            if (i < gp.getPlayer().getLife()) {
                g2.drawImage(heartFull, x, y, null);
            }
            i++;
            x += TILE_SIZE;
        }

        // draw max mana
        x = (TILE_SIZE / 2) - 5;
        y = (int) (TILE_SIZE * 1.5);
        i = 0;
        while (i < gp.getPlayer().getMaxMana()) {
            g2.drawImage(crystalBlank, x, y, null);
            i++;
            x += 35;
        }

        //draw mana
        x = (TILE_SIZE / 2) - 5;
        y = (int) (TILE_SIZE * 1.5);
        i = 0;
        while (i < gp.getPlayer().getMana()) {
            g2.drawImage(crystalFull, x, y, null);
            i++;
            x += 35;
        }

    }

    private void drawMessages(Graphics2D g2) {
        int messageX = TILE_SIZE / 2;
        int messageY = TILE_SIZE * 5;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));

        for (int i = 0; i < messages.size(); i++) {

            if (messages.get(i) != null) {

                g2.setColor(Color.black);
                g2.drawString(messages.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.white);
                g2.drawString(messages.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    messages.remove(i);
                    messageCounter.remove(i);
                }

            }
        }
    }

    private void drawTitleScreen(Graphics2D g2) {
        // Background color
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);

        // Title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 55F));
        String text = "Blue Boy Adventure";
        int x = getXForCenteredTextDisplay(text, g2);
        int y = TILE_SIZE * 3;

        // Title shadow
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // blue boy image
        x = Settings.SCREEN_WIDTH / 2 - (TILE_SIZE * 2) / 2;
        y += TILE_SIZE * 1.5;
        g2.drawImage(gp.getPlayer().getDown1(), x, y, TILE_SIZE * 2, TILE_SIZE * 2, null);

        // menu
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        text = "NEW GAME";
        x = getXForCenteredTextDisplay(text, g2);
        y += TILE_SIZE * 3.5;
        g2.drawString(text, x, y);
        if (stateCommandNumber == 0) {
            g2.drawString(">", x - TILE_SIZE, y);
        }

        text = "LOAD GAME";
        x = getXForCenteredTextDisplay(text, g2);
        y += TILE_SIZE;
        g2.drawString(text, x, y);
        if (stateCommandNumber == 1) {
            g2.drawString(">", x - TILE_SIZE, y);
        }

        text = "QUIT";
        x = getXForCenteredTextDisplay(text, g2);
        y += TILE_SIZE;
        g2.drawString(text, x, y);
        if (stateCommandNumber == 2) {
            g2.drawString(">", x - TILE_SIZE, y);
        }

    }

    private void drawDialogueScreen(Graphics2D g2) {

        // window
        int x = TILE_SIZE;
        int y = TILE_SIZE / 2;
        int width = Settings.SCREEN_WIDTH - (TILE_SIZE * 2);
        int height = TILE_SIZE * 4;
        drawSubWindow(x, y, width, height, g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        x += TILE_SIZE / 2;
        y += TILE_SIZE;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += TILE_SIZE;
        }


    }

    private void drawPauseScreen(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
        String text = "PAUSED";
        int x = getXForCenteredTextDisplay(text, g2);
        int y = Settings.SCREEN_HEIGHT / 2;

        g2.drawString(text, x, y);

    }

    private void drawCharacterStatusScreen(Graphics2D g2) {
        // create a frame
        final int frameX = TILE_SIZE * 2;
        final int frameY = TILE_SIZE;
        final int frameWidth = TILE_SIZE * 5;
        final int frameHeight = TILE_SIZE * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);

        // text
        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.PLAIN, 25));

        int textX = frameX + 20;
        int textY = frameY + TILE_SIZE;
        final int lineHeight = 35;

        // names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defence", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);

        // values
        int tailX = frameX + frameWidth - 30;
        textY = frameY + TILE_SIZE;
        String value;

        value = String.valueOf(gp.getPlayer().getLevel());
        textX = getXForAlignToRightTextDisplay(value, tailX, g2);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = gp.getPlayer().getLife() + "/" + gp.getPlayer().getMaxLife();
        textX = getXForAlignToRightTextDisplay(value, tailX, g2);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = gp.getPlayer().getMana() + "/" + gp.getPlayer().getMaxMana();
        textX = getXForAlignToRightTextDisplay(value, tailX, g2);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getStrength());
        textX = getXForAlignToRightTextDisplay(value, tailX, g2);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getDexterity());
        textX = getXForAlignToRightTextDisplay(value, tailX, g2);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getAttack());
        textX = getXForAlignToRightTextDisplay(value, tailX, g2);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getDefence());
        textX = getXForAlignToRightTextDisplay(value, tailX, g2);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getExp());
        textX = getXForAlignToRightTextDisplay(value, tailX, g2);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getNextLvlExp());
        textX = getXForAlignToRightTextDisplay(value, tailX, g2);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.getPlayer().getCoin());
        textX = getXForAlignToRightTextDisplay(value, tailX, g2);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        g2.drawImage(gp.getPlayer().getCurrentWeapon().getImage(), tailX - TILE_SIZE, textY - 24, null);

        textY += TILE_SIZE;
        g2.drawImage(gp.getPlayer().getCurrentShield().getImage(), tailX - TILE_SIZE, textY - 24, null);


    }

    private void drawInventory(Graphics2D g2, Entity entity, boolean cursor) {
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;

        int slotCol = 0;
        int slotRow = 0;

        if (entity.equals(gp.getPlayer())) {
            frameX = TILE_SIZE * 12;
            frameY = TILE_SIZE;
            frameWidth = TILE_SIZE * 6;
            frameHeight = TILE_SIZE * 5;
            slotCol = playerInventorySlotCol;
            slotRow = playerInventorySlotRow;
        } else {
            frameX = TILE_SIZE * 2;
            frameY = TILE_SIZE;
            frameWidth = TILE_SIZE * 6;
            frameHeight = TILE_SIZE * 5;
            slotCol = npcInventorySlotCol;
            slotRow = npcInventorySlotRow;
        }


        drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);

        // slot
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        final int slotSize = TILE_SIZE + 3;

        int slotX = slotXStart;
        int slotY = slotYStart;

        // draw items
        int index = 0;
        for (SuperObject item : entity.getInventory()) {

            if (entity.equals(gp.getPlayer())) { // highlight equipped items
                if (item.equals(gp.getPlayer().getCurrentShield()) || item.equals(gp.getPlayer().getCurrentWeapon())) {
                    g2.setColor(new Color(240, 190, 90));
                    g2.fillRoundRect(slotX, slotY, TILE_SIZE, TILE_SIZE, 10, 10);
                }
            }

            g2.drawImage(item.getImage(), slotX, slotY, null);

            slotX += slotSize;
            if (index == 4 || index == 9 || index == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
            index++;
        }

        if (cursor) { // Cursor

            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);
            int cursorWidth = TILE_SIZE;
            int cursorHeight = TILE_SIZE;

            // draw cursor
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3)); // thickness of the line
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // description
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = TILE_SIZE * 3;

            int textX = dFrameX + 20;
            int textY = dFrameY + TILE_SIZE;
            g2.setFont(new Font("Arial", Font.PLAIN, 25));

            int itemIndex = getInventoryItemIndexFromColAndRow(slotCol, slotRow);

            if (itemIndex < entity.getInventory().size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight, g2);
                for (String line : entity.getInventory().get(itemIndex).getDescription().split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }

    }

    private void drawOptionsScreen(Graphics2D g2) {
        g2.setColor(Color.white);

        // sub window
        int frameX = TILE_SIZE * 6;
        int frameY = TILE_SIZE;
        int frameWidth = TILE_SIZE * 8;
        int frameHeight = TILE_SIZE * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);

        switch (subState) {
            case 0:
                optionsTop(frameX, frameY, g2);
                break;
            case 1:
                optionsFullScreen(frameX, frameY, g2);
                break;
            case 2:
                optionsControl(frameX, frameY, g2);
                break;
            case 3:
                optionsEndGameConfirmation(frameX, frameY, g2);
                break;
            case 4:
                break;
        }
        gp.getKeyHandler().enterPressed = false;

    }

    private void drawGameOverScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, SCREEN_WIDTH2, SCREEN_HEIGHT2);

        int x;
        int y = TILE_SIZE * 4;
        String text = "GAME OVER";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70f));


        g2.setColor(Color.black); // shadow
        x = getXForCenteredTextDisplay(text, g2);
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // retry
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
        text = "Retry";
        x = getXForCenteredTextDisplay(text, g2);
        y += TILE_SIZE * 4;
        g2.drawString(text, x, y);
        if (stateCommandNumber == 0) {
            g2.drawString(">", x - 40, y);
        }
        // Back
        text = "Quit";
        x = getXForCenteredTextDisplay(text, g2);
        y += 55;
        g2.drawString(text, x, y);
        if (stateCommandNumber == 1) {
            g2.drawString(">", x - 40, y);
        }

    }

    private void drawLoadingScreen(Graphics2D g2) {
        counter++;
        g2.setColor(new Color(0, 0, 0, 255 / counter)); // gets lighter
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        if (counter == 20) {
            counter = 0;
            gp.setGameState(GamePanel.PLAY_STATE);
        }

    }

    private void drawTradeScreen(Graphics2D g2) {
        switch (subState) {
            case 0:
                tradeSelectOption(g2);
                break;
            case 1:
                tradeBuyOption(g2);
                break;
            case 2:
                tradeSellOption(g2);
                break;
        }
        gp.getKeyHandler().enterPressed = false;
    }


    /******************************************************* Helper Methods  ********************************************************/
    private void optionsTop(int frameX, int frameY, Graphics2D g2) {
        int textX;
        int textY;

        g2.setFont(g2.getFont().deriveFont(32F));
        // TITLE
        String text = "Options";
        textX = getXForCenteredTextDisplay(text, g2);
        textY = frameY + TILE_SIZE;
        g2.drawString(text, textX, textY);

        g2.setFont(g2.getFont().deriveFont(25F));
        // FULL SCREEN
        textX = frameX + 35;
        textY += TILE_SIZE * 2;
        g2.drawString("Full Screen", textX, textY);
        if (stateCommandNumber == 0) {
            g2.drawString(">", textX - 20, textY);
            if (gp.getKeyHandler().enterPressed) {
                GamePanel.fullScreenOn = !GamePanel.fullScreenOn;
                subState = 1;
            }
        }

        // Music
        textY += TILE_SIZE;
        g2.drawString("Music", textX, textY);
        if (stateCommandNumber == 1) g2.drawString(">", textX - 20, textY);

        // Sound Effects
        textY += TILE_SIZE;
        g2.drawString("Sound Eff.", textX, textY);
        if (stateCommandNumber == 2) g2.drawString(">", textX - 20, textY);

        // Control
        textY += TILE_SIZE;
        g2.drawString("Control", textX, textY);
        if (stateCommandNumber == 3) {
            g2.drawString(">", textX - 20, textY);
            if (gp.getKeyHandler().enterPressed) {
                subState = 2;
                stateCommandNumber = 0;
            }
        }
        // End Game
        textY += TILE_SIZE;
        g2.drawString("End Game", textX, textY);
        if (stateCommandNumber == 4) {
            g2.drawString(">", textX - 20, textY);
            if (gp.getKeyHandler().enterPressed) {
                subState = 3;
                stateCommandNumber = 0;
            }
        }

        // Back
        textY += TILE_SIZE * 2;
        g2.drawString("Back", textX, textY);
        if (stateCommandNumber == 5) {
            g2.drawString(">", textX - 20, textY);
            if (gp.getKeyHandler().enterPressed) {
                gp.setGameState(GamePanel.PLAY_STATE);
                stateCommandNumber = 0;
            }
        }


        // Full Screen Checkbox
        textX = frameX + (int) (TILE_SIZE * 4.8);
        textY = frameY + TILE_SIZE * 2 + TILE_SIZE / 2;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, TILE_SIZE / 2, TILE_SIZE / 2);
        if (GamePanel.fullScreenOn) g2.fillRect(textX, textY, TILE_SIZE / 2, TILE_SIZE / 2);


        // Music Volume
        textY += TILE_SIZE;
        g2.drawRect(textX, textY, 120, TILE_SIZE / 2);
        int volumeWidth = 24 * gp.getThemeMusicHandler().getVolumeScale();
        g2.fillRect(textX, textY, volumeWidth, TILE_SIZE / 2);

        // Sound Effect Volume
        textY += TILE_SIZE;
        g2.drawRect(textX, textY, 120, TILE_SIZE / 2);
        volumeWidth = 24 * gp.getSoundEffectHandler().getVolumeScale();
        g2.fillRect(textX, textY, volumeWidth, TILE_SIZE / 2);

        gp.getConfig().saveConfig();
    }

    private void optionsFullScreen(int frameX, int frameY, Graphics2D g2) {
        int textX = frameX + 25;
        int textY = frameY + TILE_SIZE;

        g2.setFont(g2.getFont().deriveFont(32F));
        g2.setColor(Color.white);

        String dialogue = "The change will \ntake effect \nafter restarting \nthe game.";
        for (String line : dialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += TILE_SIZE;
        }

        textY = TILE_SIZE * 9 + frameY;
        g2.drawString("Back", textX, textY);
        if (stateCommandNumber == 0) {
            g2.drawString(">", textX - 20, textY);
            if (gp.getKeyHandler().enterPressed) subState = 0;
        }

    }

    private void optionsControl(int frameX, int frameY, Graphics2D g2) {
        int textX;
        int textY = frameY + TILE_SIZE;
        g2.setFont(g2.getFont().deriveFont(20F));
        g2.setColor(Color.white);

        String text = "Control";
        textX = getXForCenteredTextDisplay(text, g2);
        g2.drawString(text, textX, textY);
        textX = frameX + 25;
        textY += TILE_SIZE;
        g2.drawString("Move", textX, textY);
        textY += TILE_SIZE;
        g2.drawString("Interact", textX, textY);
        textY += TILE_SIZE;
        g2.drawString("Attack", textX, textY);
        textY += TILE_SIZE;
        g2.drawString("Shoot/Cast", textX, textY);
        textY += TILE_SIZE;
        g2.drawString("Character Screen", textX, textY);
        textY += TILE_SIZE;
        g2.drawString("Pause", textX, textY);
        textY += TILE_SIZE;
        g2.drawString("Options", textX, textY);

        textX = frameX + 5 * TILE_SIZE + 20;
        textY = frameY + 2 * TILE_SIZE;
        g2.drawString("WASD", textX, textY);
        textY += TILE_SIZE;
        g2.drawString("E/ENTER", textX, textY);
        textY += TILE_SIZE;
        g2.drawString("SPACE", textX, textY);
        textY += TILE_SIZE;
        g2.drawString("F", textX, textY);
        textY += TILE_SIZE;
        g2.drawString("C", textX, textY);
        textY += TILE_SIZE;
        g2.drawString("P", textX, textY);
        textY += TILE_SIZE;
        g2.drawString("ESC", textX, textY);

        textX = frameX + TILE_SIZE;
        textY = frameY + TILE_SIZE * 9;
        g2.drawString("Back", textX, textY);
        if (stateCommandNumber == 0) {
            g2.drawString(">", textX - 20, textY);
            if (gp.getKeyHandler().enterPressed) {
                subState = 0;
                stateCommandNumber = 3;
            }
        }
    }

    private void optionsEndGameConfirmation(int frameX, int frameY, Graphics2D g2) {
        int textX = frameX + 25;
        int textY = frameY + TILE_SIZE;

        g2.setFont(g2.getFont().deriveFont(32F));
        g2.setColor(Color.white);

        String text = "Quit the game \nand return to \nthe title screen?";
        for (String line : text.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += TILE_SIZE;
        }

        text = "YES";
        textX = getXForCenteredTextDisplay(text, g2);
        textY = TILE_SIZE * 6;
        g2.drawString(text, textX, textY);
        if (stateCommandNumber == 0) {
            g2.drawString(">", textX - 20, textY);
            if (gp.getKeyHandler().enterPressed) {
                subState = 0;
                gp.setGameState(GamePanel.TITLE_STATE);
                gp.getThemeMusicHandler().stop();
            }
        }

        text = "NO";
        textX = getXForCenteredTextDisplay(text, g2);
        textY += TILE_SIZE;
        g2.drawString(text, textX, textY);
        if (stateCommandNumber == 1) {
            g2.drawString(">", textX - 20, textY);
            if (gp.getKeyHandler().enterPressed) {
                subState = 0;
                stateCommandNumber = 4;
            }
        }


    }

    private void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {
        Color color = new Color(0, 0, 0, 200);
        g2.setColor(color);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(5)); // white frame around sub window
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }

    private int getXForCenteredTextDisplay(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return Settings.SCREEN_WIDTH / 2 - length / 2;
    }

    private int getXForAlignToRightTextDisplay(String text, int tailX, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

    public void addMessage(String message) {
        messages.add(message);
        messageCounter.add(0);
    }

    public int getPlayerInventorySlotCol() {
        return playerInventorySlotCol;
    }

    public void setPlayerInventorySlotCol(int playerInventorySlotCol) {
        this.playerInventorySlotCol = playerInventorySlotCol;
    }

    public int getPlayerInventorySlotRow() {
        return playerInventorySlotRow;
    }

    public void setPlayerInventorySlotRow(int playerInventorySlotRow) {
        this.playerInventorySlotRow = playerInventorySlotRow;
    }

    public int getInventoryItemIndexFromColAndRow(int slotCol, int slotRow) {
        return slotCol + (slotRow * 5);
    }


    private void tradeSelectOption(Graphics2D g2) {
        drawDialogueScreen(g2);

        int x = TILE_SIZE * 15; // draw options
        int y = (int) (TILE_SIZE * 4.5);
        int width = TILE_SIZE * 3;
        int height = TILE_SIZE * 4;
        drawSubWindow(x, y, width, height, g2);

        x += TILE_SIZE / 2;
        y += TILE_SIZE;
        g2.drawString("Buy", x, y);
        if (stateCommandNumber == 0) {
            g2.drawString(">", x - 15, y);
            if (gp.getKeyHandler().enterPressed) {
                subState = 1;
            }
        }

        y += TILE_SIZE;
        g2.drawString("Sell", x, y);
        if (stateCommandNumber == 1) {
            g2.drawString(">", x - 15, y);
            if (gp.getKeyHandler().enterPressed) {
                subState = 2;
            }
        }

        y += TILE_SIZE;
        g2.drawString("Leave", x, y);
        if (stateCommandNumber == 2) {
            g2.drawString(">", x - 15, y);
            if (gp.getKeyHandler().enterPressed) {
                stateCommandNumber = 0;
                gp.setGameState(GamePanel.DIALOGUE_STATE);
                currentDialogue = "Come again, hehe!";
            }
        }

    }

    private void tradeBuyOption(Graphics2D g2) {
        drawInventory(g2, gp.getPlayer(), false);
        drawInventory(g2, traderNpc, true);

        // hint window
        int x = TILE_SIZE * 2;
        int y = TILE_SIZE * 9;
        int width = TILE_SIZE * 6;
        int height = TILE_SIZE * 2;
        drawSubWindow(x, y, width, height, g2);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        // coin window
        x = TILE_SIZE * 12;
        y = TILE_SIZE * 9;
        width = TILE_SIZE * 6;
        height = TILE_SIZE * 2;
        drawSubWindow(x, y, width, height, g2);
        g2.drawString("Coins: " + gp.getPlayer().getCoin(), x + 24, y + 60);

        // price
        int itemIndex = getInventoryItemIndexFromColAndRow(npcInventorySlotCol, npcInventorySlotRow);
        if (itemIndex < traderNpc.getInventory().size()) {
            SuperObject item = traderNpc.getInventory().get(itemIndex);
            x = (int) (TILE_SIZE * 5.5);
            y = (int) (TILE_SIZE * 5.5);
            width = (int) (TILE_SIZE * 2.5);
            height = TILE_SIZE;
            drawSubWindow(x, y, width, height, g2);
            g2.drawImage(coin, x + 15, y + 7, 32, 32, null);
            int price = item.getPrice();
            String text = String.valueOf(price);
            x = getXForAlignToRightTextDisplay(text, TILE_SIZE * 8, g2);
            g2.drawString(text, x - 20, y + 32);

            // buy item
            if (gp.getKeyHandler().enterPressed) {
                if(item.getPrice() > gp.getPlayer().getCoin()) { // money check
                    subState = 0;
                    gp.setGameState(GamePanel.DIALOGUE_STATE);
                    currentDialogue = "You need more coins to buy!";
                    drawDialogueScreen(g2);
                }else if (gp.getPlayer().getInventory().size() == gp.getPlayer().getMaxInventorySize()) { // inventory check
                    subState = 0;
                    gp.setGameState(GamePanel.DIALOGUE_STATE);
                    currentDialogue = "You cannot carry any more!";
                    drawDialogueScreen(g2);
                } else {
                    gp.getPlayer().setCoin(gp.getPlayer().getCoin() - item.getPrice());
                    gp.getPlayer().getInventory().add(item);
                }
            }
        }
    }

    private void tradeSellOption(Graphics2D g2) {
        drawInventory(g2, gp.getPlayer(), true);

        // hint window
        int x = TILE_SIZE * 2;
        int y = TILE_SIZE * 9;
        int width = TILE_SIZE * 6;
        int height = TILE_SIZE * 2;
        drawSubWindow(x, y, width, height, g2);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        // coin window
        x = TILE_SIZE * 12;
        y = TILE_SIZE * 9;
        width = TILE_SIZE * 6;
        height = TILE_SIZE * 2;
        drawSubWindow(x, y, width, height, g2);
        g2.drawString("Coins: " + gp.getPlayer().getCoin(), x + 24, y + 60);
        // price
        int itemIndex = getInventoryItemIndexFromColAndRow(playerInventorySlotCol,playerInventorySlotRow);
        if (itemIndex < gp.getPlayer().getInventory().size()) {
            SuperObject item = gp.getPlayer().getInventory().get(itemIndex);
            x = (int) (TILE_SIZE * 15.5);
            y = (int) (TILE_SIZE * 5.5);
            width = (int) (TILE_SIZE * 2.5);
            height = TILE_SIZE;
            drawSubWindow(x, y, width, height, g2);
            g2.drawImage(coin, x + 15, y + 7, 32, 32, null);
            int price = item.getPrice() / 2; // npc buys half the price
            String text = String.valueOf(price);
            x = getXForAlignToRightTextDisplay(text, TILE_SIZE * 18, g2);
            g2.drawString(text, x - 20, y + 32);

            // sell item
            if (gp.getKeyHandler().enterPressed) {
                if (gp.getPlayer().getInventory().get(itemIndex).equals(gp.getPlayer().getCurrentWeapon()) || gp.getPlayer().getInventory().get(itemIndex).equals(gp.getPlayer().getCurrentShield())){
                    subState = 0;
                    gp.setGameState(GamePanel.DIALOGUE_STATE);
                    currentDialogue = "Can't sell an equipped item!";
                    drawDialogueScreen(g2);
                } else {
                    gp.getPlayer().getInventory().remove(itemIndex);
                    gp.getPlayer().setCoin(gp.getPlayer().getCoin() + price);
                }
            }
        }
    }


    /******************************************************* Getters & Setters  ********************************************************/
    public void setTraderNpc(NPC_Merchant traderNpc) {
        this.traderNpc = traderNpc;
    }

    public int getNpcInventorySlotCol() {
        return npcInventorySlotCol;
    }

    public void setNpcInventorySlotCol(int npcInventorySlotCol) {
        this.npcInventorySlotCol = npcInventorySlotCol;
    }

    public int getNpcInventorySlotRow() {
        return npcInventorySlotRow;
    }

    public void setNpcInventorySlotRow(int npcInventorySlotRow) {
        this.npcInventorySlotRow = npcInventorySlotRow;
    }
}
