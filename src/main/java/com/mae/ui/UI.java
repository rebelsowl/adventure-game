package com.mae.ui;

import com.mae.config.Settings;
import com.mae.object.OBJ_Heart;
import com.mae.object.SuperObject;
import com.mae.panel.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static com.mae.config.Settings.TILE_SIZE;

public class UI {
    public static int titleScreenCommandNumber = 0;
    private final GamePanel gp;
    BufferedImage heartFull, heartHalf, heartBlank;
    private Font purisaBoldFont;
    private boolean messageOn = false;
    private String message = "";
    private int messageDisplayTime = 0;
    private boolean gameFinished = false;
    private String currentDialogue = "";

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaBoldFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SuperObject heart = new OBJ_Heart(gp);
        heartFull = heart.getImage();
        heartHalf = heart.getImage2();
        heartBlank = heart.getImage3();

    }

    public void draw(Graphics2D g2) {
        g2.setFont(purisaBoldFont);
        g2.setColor(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (gp.getGameState() == GamePanel.TITLE_STATE) {
            drawTitleScreen(g2);
        } else if (gp.getGameState() == GamePanel.PLAY_STATE) {
            drawPlayersLife(g2);
        } else if (gp.getGameState() == GamePanel.PAUSE_STATE) {
            drawPlayersLife(g2);
            drawPauseScreen(g2);
        } else if (gp.getGameState() == GamePanel.DIALOGUE_STATE) {
            drawPlayersLife(g2);
            drawDialogueScreen(g2);
        } else if (gp.getGameState() == GamePanel.CHARACTER_STATUS_STATE) {
            drawCharacterStatusScreen(g2);

        }


    }

    private void drawPlayersLife(Graphics2D g2) {
        int x = TILE_SIZE / 2;
        int y = TILE_SIZE / 2;

        for (int i = 0; i < gp.getPlayer().getMaxLife() / 2; i++) { // max life
            g2.drawImage(heartBlank, x, y, null);
            x += TILE_SIZE;
        }

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
        if (titleScreenCommandNumber == 0) {
            g2.drawString(">", x - TILE_SIZE, y);
        }

        text = "LOAD GAME";
        x = getXForCenteredTextDisplay(text, g2);
        y += TILE_SIZE;
        g2.drawString(text, x, y);
        if (titleScreenCommandNumber == 1) {
            g2.drawString(">", x - TILE_SIZE, y);
        }

        text = "QUIT";
        x = getXForCenteredTextDisplay(text, g2);
        y += TILE_SIZE;
        g2.drawString(text, x, y);
        if (titleScreenCommandNumber == 2) {
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


    public void showMessage(String text) {
        message = text;
        messageOn = true;
        messageDisplayTime = 120;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
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
        final int frameX = TILE_SIZE;
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
        textY += lineHeight + 20;
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
        g2.drawImage(gp.getPlayer().getCurrentWeapon().getImage(), tailX - TILE_SIZE, textY - 14, null);

        textY += TILE_SIZE;
        g2.drawImage(gp.getPlayer().getCurrentShield().getImage(), tailX - TILE_SIZE, textY - 14, null);


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


    public String getCurrentDialogue() {
        return currentDialogue;
    }

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

}
