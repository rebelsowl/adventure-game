package com.mae.ui;

import com.mae.config.Settings;
import com.mae.panel.GamePanel;

import java.awt.*;
import java.text.DecimalFormat;

public class UI {
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    private final GamePanel gp;
    private final Font arial_40;
    private final Font arial_80B;
    private boolean messageOn = false;
    private String message = "";
    private int messageDisplayTime = 0;
    private boolean gameFinished = false;
    private final double playTime = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

    }

    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gp.getGameState() == GamePanel.playState) {

        } else if (gp.getGameState() == GamePanel.pauseState){
            drawPauseScreen(g2);
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

    private void drawPauseScreen(Graphics2D g2){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
        String text = "PAUSED";
        int x = getXForCenteredTextDisplay(text, g2);
        int y = Settings.screenHeight/2;

        g2.drawString(text,x,y);

    }


    private int getXForCenteredTextDisplay(String text, Graphics2D g2){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return Settings.screenWidth/2 - length/2;

    }


}
