package com.mae.handler;

import com.mae.panel.GamePanel;
import com.mae.ui.UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputHandler implements KeyListener {

    private final GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, spacePressed, shotKeyPressed;

    public KeyboardInputHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {

        if (gp.getGameState() == GamePanel.TITLE_STATE)// TITLE_STATE
            handleTitleState(e);
        else if (gp.getGameState() == GamePanel.PLAY_STATE) // PLAY_STATE
            handlePlayState(e);
        else if (gp.getGameState() == GamePanel.PAUSE_STATE) // PAUSE_STATE
            handlePauseState(e);
        else if (gp.getGameState() == GamePanel.DIALOGUE_STATE) // DIALOGUE_STATE
            handleDialogueState(e);
        else if (gp.getGameState() == GamePanel.CHARACTER_STATUS_STATE) // CHARACTER_STATUS_STATE
            handleCharacterStatusState(e);

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
        if (e.getKeyCode() == KeyEvent.VK_F)
            shotKeyPressed = false;
    }

    private void handleTitleState(KeyEvent e) {
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
//                    gp.playThemeSong(0); // TODO: enable when project finished :)
                    break;
                case 1:
                    break;
                case 2:
                    System.exit(0);

            }
        }
    }

    private void handlePlayState(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W)
            upPressed = true;
        else if (e.getKeyCode() == KeyEvent.VK_A)
            leftPressed = true;
        else if (e.getKeyCode() == KeyEvent.VK_D)
            rightPressed = true;
        else if (e.getKeyCode() == KeyEvent.VK_S)
            downPressed = true;

        else if (e.getKeyCode() == KeyEvent.VK_SPACE)
            spacePressed = true;
        else if (e.getKeyCode() == KeyEvent.VK_F)
            shotKeyPressed = true;
        else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_E)
            enterPressed = true;
        else if (e.getKeyCode() == KeyEvent.VK_C)
            gp.setGameState(GamePanel.CHARACTER_STATUS_STATE);
        else if (e.getKeyCode() == KeyEvent.VK_P)
            gp.setGameState(GamePanel.PAUSE_STATE);
    }

    private void handlePauseState(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE)
            gp.setGameState(GamePanel.PLAY_STATE);
    }

    private void handleDialogueState(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_E)
            gp.setGameState(GamePanel.PLAY_STATE);
    }

    private void handleCharacterStatusState(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C || e.getKeyCode() == KeyEvent.VK_ESCAPE)
            gp.setGameState(GamePanel.PLAY_STATE);
        else if (e.getKeyCode() == KeyEvent.VK_W) {
            if (gp.getUi().getInventorySlotRow() != 0 ) {
                gp.getUi().setInventorySlotRow(gp.getUi().getInventorySlotRow() - 1);
                gp.playSoundEffect(9);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            if (gp.getUi().getInventorySlotCol() != 0) {
                gp.getUi().setInventorySlotCol(gp.getUi().getInventorySlotCol() - 1);
                gp.playSoundEffect(9);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            if (gp.getUi().getInventorySlotRow() != 3) {
                gp.getUi().setInventorySlotRow(gp.getUi().getInventorySlotRow() + 1);
                gp.playSoundEffect(9);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            if (gp.getUi().getInventorySlotCol() != 4) {
                gp.getUi().setInventorySlotCol(gp.getUi().getInventorySlotCol() + 1);
                gp.playSoundEffect(9);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gp.getPlayer().selectItem();
            
        }


    }

}
