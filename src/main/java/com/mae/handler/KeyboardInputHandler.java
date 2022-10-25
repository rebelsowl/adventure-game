package com.mae.handler;

import com.mae.panel.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputHandler implements KeyListener {

    private GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyboardInputHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W)
            upPressed = true;
        else if(e.getKeyCode() == KeyEvent.VK_A)
            leftPressed = true;
        else if(e.getKeyCode() == KeyEvent.VK_D)
            rightPressed = true;
        else if(e.getKeyCode() == KeyEvent.VK_S)
            downPressed = true;


        if(e.getKeyCode() == KeyEvent.VK_P) {
            if (gp.getGameState() == GamePanel.playState) {
                gp.setGameState(GamePanel.pauseState);
            } else if (gp.getGameState() == GamePanel.pauseState){
                gp.setGameState(GamePanel.playState);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W)
            upPressed = false;
        if(e.getKeyCode() == KeyEvent.VK_A)
            leftPressed = false;
        if(e.getKeyCode() == KeyEvent.VK_D)
            rightPressed = false;
        if(e.getKeyCode() == KeyEvent.VK_S)
            downPressed = false;
    }

}
