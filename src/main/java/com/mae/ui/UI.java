package com.mae.ui;

import com.mae.config.Settings;
import com.mae.object.KeyObject;
import com.mae.panel.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    private GamePanel gp;
    private Font arial_40, arial_80B;

    private BufferedImage keyImage;

    private boolean messageOn = false;
    private String message = "";
    private int messageDisplayTime = 0;
    private boolean gameFinished = false;
    private double playTime = 0;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        KeyObject keyObject = new KeyObject();
        keyImage = keyObject.getImage();

    }

    public void draw(Graphics2D g2) {

        if(gameFinished) {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            String text = "You found the treasure!";
            int textLength =(int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            int x = Settings.screenWidth/2 - textLength/2;
            int y = Settings.screenHeight/2 - Settings.tileSize *3;
            g2.drawString(text,x,y);

            text = "Your time is : " + decimalFormat.format(playTime) + "!";
            textLength =(int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = Settings.screenWidth/2 - textLength/2;
            y = Settings.screenHeight/2 + Settings.tileSize *2;
            g2.drawString(text,x,y);


            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulations!";
            textLength =(int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = Settings.screenWidth/2 - textLength/2;
            y = Settings.screenHeight/2 + Settings.tileSize;
            g2.drawString(text,x,y);

            gp.setGameThread(null); // TODO: use atomicBoolean maybe
        } else {

            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, Settings.tileSize / 2, Settings.tileSize / 2, Settings.tileSize, Settings.tileSize, null);

            g2.drawString("x " + gp.getPlayer().getHasKey(), 74, 65);

            playTime += (double) 1/60;
            g2.drawString("Time: " + decimalFormat.format(playTime), Settings.tileSize * 11, 65);

            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, Settings.tileSize / 2, Settings.tileSize * 5);

                if (messageDisplayTime == 0) {
                    messageOn = false;
                } else {
                    messageDisplayTime--;
                }
            }
        }
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
        messageDisplayTime = 120;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }
}
