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
        else if (gp.getGameState() == GamePanel.PLAY_STATE)
            handlePlayState(e);
        else if (gp.getGameState() == GamePanel.PAUSE_STATE)
            handlePauseState(e);
        else if (gp.getGameState() == GamePanel.DIALOGUE_STATE)
            handleDialogueState(e);
        else if (gp.getGameState() == GamePanel.CHARACTER_STATUS_STATE)
            handleCharacterStatusState(e);
        else if (gp.getGameState() == GamePanel.OPTIONS_STATE)
            handleOptionsState(e);
        else if (gp.getGameState() == GamePanel.GAME_OVER_STATE)
            handleGameOverState(e);
        else if (gp.getGameState() == GamePanel.TRADE_STATE)
            handleTradeState(e);

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
            UI.stateCommandNumber--;
            if (UI.stateCommandNumber < 0)
                UI.stateCommandNumber = 2;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            UI.stateCommandNumber++;
            if (UI.stateCommandNumber > 2) {
                UI.stateCommandNumber = 0;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (UI.stateCommandNumber) {
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
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            gp.setGameState(GamePanel.OPTIONS_STATE);


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

        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            gp.getPlayer().selectItem();

        playerInventory(e.getKeyCode());

    }



    private void handleOptionsState(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            gp.setGameState(GamePanel.PLAY_STATE);

        int maxCommandNumber = 0;
        switch (UI.subState) {
            case 0:
                maxCommandNumber = 5;
                break;
            case 3:
                maxCommandNumber = 1;
                break;
        }

        if (e.getKeyCode() == KeyEvent.VK_W) {
            UI.stateCommandNumber--;
            gp.playSoundEffect(9);
            if (UI.stateCommandNumber < 0)
                UI.stateCommandNumber = maxCommandNumber;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            UI.stateCommandNumber++;
            gp.playSoundEffect(9);
            if (UI.stateCommandNumber > maxCommandNumber)
                UI.stateCommandNumber = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER)
            enterPressed = true;
        else if (e.getKeyCode() == KeyEvent.VK_A) {
            if (UI.subState == 0) {
                if (UI.stateCommandNumber == 1 && gp.getThemeMusicHandler().getVolumeScale() > 0) {
                    gp.getThemeMusicHandler().setVolumeScale(gp.getThemeMusicHandler().getVolumeScale() - 1);
                    gp.getThemeMusicHandler().checkVolume();
                    gp.playSoundEffect(9);
                } else if (UI.stateCommandNumber == 2 && gp.getSoundEffectHandler().getVolumeScale() > 0) {
                    gp.getSoundEffectHandler().setVolumeScale(gp.getSoundEffectHandler().getVolumeScale() - 1);
                    gp.playSoundEffect(9);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            if (UI.subState == 0) {
                if (UI.stateCommandNumber == 1 && gp.getThemeMusicHandler().getVolumeScale() < 5) {
                    gp.getThemeMusicHandler().setVolumeScale(gp.getThemeMusicHandler().getVolumeScale() + 1);
                    gp.getThemeMusicHandler().checkVolume();
                    gp.playSoundEffect(9);
                } else if (UI.stateCommandNumber == 2 && gp.getSoundEffectHandler().getVolumeScale() < 5) {
                    gp.getSoundEffectHandler().setVolumeScale(gp.getSoundEffectHandler().getVolumeScale() + 1);
                    gp.playSoundEffect(9);
                }
            }
        }
    }

    private void handleGameOverState(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            UI.stateCommandNumber = 0;
            gp.playSoundEffect(9);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            UI.stateCommandNumber = 1;
            gp.playSoundEffect(9);
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (UI.stateCommandNumber == 0) {
                gp.retry();
                gp.playThemeSong(0);
                gp.setGameState(GamePanel.PLAY_STATE);
            } else if (UI.stateCommandNumber == 1) {
                gp.restart();
                gp.setGameState(GamePanel.TITLE_STATE);
            }
        }
    }

    private void handleTradeState(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            enterPressed = true;

        if(UI.subState == 0){
            if (e.getKeyCode() == KeyEvent.VK_W) {
                UI.stateCommandNumber--;
                gp.playSoundEffect(9);
                if (UI.stateCommandNumber < 0)
                    UI.stateCommandNumber = 2;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                UI.stateCommandNumber++;
                gp.playSoundEffect(9);
                if (UI.stateCommandNumber > 2)
                    UI.stateCommandNumber = 0;

            }
        } else if (UI.subState == 1) {
            npcInventory(e.getKeyCode());
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                UI.subState = 0;
        } else if (UI.subState == 2) {
            playerInventory(e.getKeyCode());
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                UI.subState = 0;
        }
    }


    /***************************************************************************************************************/

    private void playerInventory(int keyCode){
        if (keyCode == KeyEvent.VK_W) {
            if (gp.getUi().getPlayerInventorySlotRow() != 0) {
                gp.getUi().setPlayerInventorySlotRow(gp.getUi().getPlayerInventorySlotRow() - 1);
                gp.playSoundEffect(9);
            }
        } else if (keyCode == KeyEvent.VK_A) {
            if (gp.getUi().getPlayerInventorySlotCol() != 0) {
                gp.getUi().setPlayerInventorySlotCol(gp.getUi().getPlayerInventorySlotCol() - 1);
                gp.playSoundEffect(9);
            }
        } else if (keyCode == KeyEvent.VK_S) {
            if (gp.getUi().getPlayerInventorySlotRow() != 3) {
                gp.getUi().setPlayerInventorySlotRow(gp.getUi().getPlayerInventorySlotRow() + 1);
                gp.playSoundEffect(9);
            }
        } else if (keyCode == KeyEvent.VK_D) {
            if (gp.getUi().getPlayerInventorySlotCol() != 4) {
                gp.getUi().setPlayerInventorySlotCol(gp.getUi().getPlayerInventorySlotCol() + 1);
                gp.playSoundEffect(9);
            }
        }
    }

    private void npcInventory(int keyCode){
        if (keyCode == KeyEvent.VK_W) {
            if (gp.getUi().getNpcInventorySlotRow() != 0) {
                gp.getUi().setNpcInventorySlotRow(gp.getUi().getNpcInventorySlotRow() - 1);
                gp.playSoundEffect(9);
            }
        } else if (keyCode == KeyEvent.VK_A) {
            if (gp.getUi().getNpcInventorySlotCol() != 0) {
                gp.getUi().setNpcInventorySlotCol(gp.getUi().getNpcInventorySlotCol() - 1);
                gp.playSoundEffect(9);
            }
        } else if (keyCode == KeyEvent.VK_S) {
            if (gp.getUi().getNpcInventorySlotRow() != 3) {
                gp.getUi().setNpcInventorySlotRow(gp.getUi().getNpcInventorySlotRow() + 1);
                gp.playSoundEffect(9);
            }
        } else if (keyCode == KeyEvent.VK_D) {
            if (gp.getUi().getNpcInventorySlotCol() != 4) {
                gp.getUi().setNpcInventorySlotCol(gp.getUi().getNpcInventorySlotCol() + 1);
                gp.playSoundEffect(9);
            }
        }
    }


}
