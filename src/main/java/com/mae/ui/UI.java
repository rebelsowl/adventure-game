package com.mae.ui;

import com.mae.config.Settings;
import com.mae.panel.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    public static int titleScreenCommandNumber = 0;
    private final GamePanel gp;
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

    }

    public void draw(Graphics2D g2) {
        g2.setFont(purisaBoldFont);
        g2.setColor(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (gp.getGameState() == GamePanel.TITLE_STATE) {
            drawTitleScreen(g2);
        } else if (gp.getGameState() == GamePanel.PLAY_STATE) {

        } else if (gp.getGameState() == GamePanel.PAUSE_STATE) {
            drawPauseScreen(g2);
        } else if (gp.getGameState() == GamePanel.DIALOGUE_STATE) {
            drawDialogueScreen(g2);

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
        int y = Settings.TILE_SIZE * 3;

        // Title shadow
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // blue boy image
        x = Settings.SCREEN_WIDTH / 2 - (Settings.TILE_SIZE * 2) / 2;
        y += Settings.TILE_SIZE * 1.5;
        g2.drawImage(gp.getPlayer().getDown1(), x, y, Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2, null);

        // menu
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        text = "NEW GAME";
        x = getXForCenteredTextDisplay(text, g2);
        y += Settings.TILE_SIZE * 3.5;
        g2.drawString(text, x, y);
        if (titleScreenCommandNumber == 0) {
            g2.drawString(">", x - Settings.TILE_SIZE, y);
        }

        text = "LOAD GAME";
        x = getXForCenteredTextDisplay(text, g2);
        y += Settings.TILE_SIZE;
        g2.drawString(text, x, y);
        if (titleScreenCommandNumber == 1) {
            g2.drawString(">", x - Settings.TILE_SIZE, y);
        }

        text = "QUIT";
        x = getXForCenteredTextDisplay(text, g2);
        y += Settings.TILE_SIZE;
        g2.drawString(text, x, y);
        if (titleScreenCommandNumber == 2) {
            g2.drawString(">", x - Settings.TILE_SIZE, y);
        }

    }

    private void drawDialogueScreen(Graphics2D g2) {

        // window
        int x = Settings.TILE_SIZE;
        int y = Settings.TILE_SIZE / 2;
        int width = Settings.SCREEN_WIDTH - (Settings.TILE_SIZE * 2);
        int height = Settings.TILE_SIZE * 4;
        drawSubWindow(x, y, width, height, g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        x += Settings.TILE_SIZE / 2;
        y += Settings.TILE_SIZE;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += Settings.TILE_SIZE;
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


    private int getXForCenteredTextDisplay(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return Settings.SCREEN_WIDTH / 2 - length / 2;

    }


    public String getCurrentDialogue() {
        return currentDialogue;
    }

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

}
