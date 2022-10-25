package com.mae.tile;

import com.mae.config.Settings;
import com.mae.panel.GamePanel;
import com.mae.utility.UtilityTool;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.mae.config.Settings.tileSize;

@Data
public class TileManager {
    public Tile[] tileTypes;
    GamePanel gp;
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        this.tileTypes = new Tile[10];

        mapTileNum = new int[Settings.maxWorldCol][Settings.maxWorldRow];

        initializeTileTypes();
        loadMap("/maps/worldV1.txt");
    }

    private void initializeTileTypes() {
        setUpTileType(0, "grass", false);
        setUpTileType(1, "wall", true);
        setUpTileType(2, "water", true);
        setUpTileType(3, "earth", false);
        setUpTileType(4, "tree", true);
        setUpTileType(5, "sand", false);
    }

    private void setUpTileType(int index, String imageName, boolean collision) {
        try {
            tileTypes[index] = new Tile();
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tileTypes[index].setImage(UtilityTool.scaleImage(image, tileSize, tileSize));
            tileTypes[index].setCollision(collision);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        //TODO: make for loop and make mapTileNum[row][col]
        while (worldCol < Settings.maxWorldCol && worldRow < Settings.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * tileSize;
            int worldY = worldRow * tileSize;
            // TODO: use encapsulation with player
            int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
            int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

            if (worldX + tileSize > gp.player.getWorldX() - gp.player.getScreenX() &&
                    worldX - tileSize < gp.player.getWorldX() + gp.player.getScreenX() &&
                    worldY + tileSize > gp.player.getWorldY() - gp.player.getScreenY() &&
                    worldY - tileSize < gp.player.getWorldY() + gp.player.getScreenY()) { // Only draw the tiles around the player
                g2.drawImage(tileTypes[tileNum].getImage(), screenX, screenY,null);
            }

            worldCol++;
            if (worldCol == Settings.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }

    }

    public void loadMap(String mapPath) {
        try {
            InputStream is = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            // TODO: make proper for loops
            while (col < Settings.maxWorldCol && row < Settings.maxWorldRow) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                while (col < Settings.maxWorldCol) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == Settings.maxWorldCol) {
                    col = 0;
                    row++;
                }

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}