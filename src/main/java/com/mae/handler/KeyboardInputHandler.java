package com.mae.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;


    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            upPressed = true;
        } else if(e.getKeyCode() == KeyEvent.VK_A) {
            leftPressed = true;
        } else if(e.getKeyCode() == KeyEvent.VK_D) {
            rightPressed = true;
        } else if(e.getKeyCode() == KeyEvent.VK_S) {
            downPressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            downPressed = false;
        }
    }

}
