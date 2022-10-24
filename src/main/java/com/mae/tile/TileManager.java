package com.mae.tile;

import com.mae.config.Settings;
import com.mae.panel.GamePanel;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Data
public class TileManager {
    GamePanel gp;
    public Tile[] tileTypes;

    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        this.tileTypes = new Tile[10];

        mapTileNum = new int[Settings.maxWorldCol][Settings.maxWorldRow];

        initializeTileTypes();
        loadMap("/maps/worldV1.txt");
    }

    private void initializeTileTypes() {
        try {
            tileTypes[0] = new Tile();
            tileTypes[0].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")));
            tileTypes[1] = new Tile();
            tileTypes[1].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png")));
            tileTypes[1].setCollision(true);
            tileTypes[2] = new Tile();
            tileTypes[2].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/water.png")));
            tileTypes[2].setCollision(true);
            tileTypes[3] = new Tile();
            tileTypes[3].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png")));
            tileTypes[4] = new Tile();
            tileTypes[4].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png")));
            tileTypes[4].setCollision(true);
            tileTypes[5] = new Tile();
            tileTypes[5].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        //TODO: make for loop and make mapTileNum[row][col]
        while (worldCol < Settings.maxWorldCol && worldRow < Settings.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * Settings.tileSize;
            int worldY = worldRow * Settings.tileSize;
            // TODO: use encapsulation with player
            int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
            int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

            if (worldX + Settings.tileSize > gp.player.getWorldX() - gp.player.getScreenX() &&
                worldX - Settings.tileSize < gp.player.getWorldX() + gp.player.getScreenX() &&
                worldY + Settings.tileSize > gp.player.getWorldY() - gp.player.getScreenY() &&
                worldY - Settings.tileSize < gp.player.getWorldY() + gp.player.getScreenY()) { // Only draw the tiles around the player
                g2.drawImage(tileTypes[tileNum].getImage(), screenX, screenY, Settings.tileSize, Settings.tileSize, null);
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
