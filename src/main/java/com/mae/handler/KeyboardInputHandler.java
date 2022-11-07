package com.mae.handler;

import com.mae.panel.GamePanel;
import com.mae.ui.UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputHandler implements KeyListener {

    private final GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, spacePressed;

    public KeyboardInputHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {

        if (gp.getGameState() == GamePanel.TITLE_STATE) {// TITLE_STATE
            if (e.getKeyCode() == KeyEvent.VK_W) {
                UI.titleScreenCommandNumber--;
                if (UI.titleScreenCommandNumber < 0)
                    UI.titleScreenCommandNumber = 2;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                UI.titleScreenCommandNumber++;
                if (UI.titleScreenCommandNumber > 2) {
                    UI.titleScreenCommandNumber = 0;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                switch (UI.titleScreenCommandNumber) {
                    case 0:
                        gp.setGameState(GamePanel.PLAY_STATE);
                        gp.playThemeSong(0);
                        break;
                    case 1:
                        break;
                    case 2:
                        System.exit(0);

                }
            }


        } else if (gp.getGameState() == GamePanel.PLAY_STATE) { // PLAY_STATE
            if (e.getKeyCode() == KeyEvent.VK_W)
                upPressed = true;
            else if (e.getKeyCode() == KeyEvent.VK_A)
                leftPressed = true;
            else if (e.getKeyCode() == KeyEvent.VK_D)
                rightPressed = true;
            else if (e.getKeyCode() == KeyEvent.VK_S)
                downPressed = true;

            if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_E)
                enterPressed = true;

            if (e.getKeyCode() == KeyEvent.VK_SPACE)
                spacePressed = true;

            if (e.getKeyCode() == KeyEvent.VK_P)
                gp.setGameState(GamePanel.PAUSE_STATE);

        } else if (gp.getGameState() == GamePanel.PAUSE_STATE) { // PAUSE_STATE
            if (e.getKeyCode() == KeyEvent.VK_P)
                gp.setGameState(GamePanel.PLAY_STATE);

        } else if (gp.getGameState() == GamePanel.DIALOGUE_STATE) { // DIALOGUE_STATE
            if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE)
                gp.setGameState(GamePanel.PLAY_STATE);

        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W)
            upPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_A)
            leftPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_D)
            rightPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_S)
            downPressed = false;
    }

}
