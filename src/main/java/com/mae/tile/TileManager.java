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

import static com.mae.config.Settings.TILE_SIZE;

@Data
public class TileManager {
    public Tile[] tileTypes;
    GamePanel gp;
    int[][][] mapTileNum; // 3D array, first holds map number, 2nd and 3rd are for column and rows

    public TileManager(GamePanel gp) {
        this.gp = gp;
        this.tileTypes = new Tile[50];

        mapTileNum = new int[GamePanel.MAX_MAP][Settings.MAX_WORLD_COL][Settings.MAX_WORLD_ROW];

        initializeTileTypes();
        loadMap("/maps/worldV3.txt", 0);
        loadMap("/maps/interior01.txt", 1);
    }

    private void initializeTileTypes() {
        setUpTileType(0, "grass00", false);
        setUpTileType(1, "grass00", false);
        setUpTileType(2, "grass00", false);
        setUpTileType(3, "grass00", false);
        setUpTileType(4, "grass00", false);
        setUpTileType(5, "grass00", false);
        setUpTileType(6, "grass00", false);
        setUpTileType(7, "grass00", false);
        setUpTileType(8, "grass00", false);
        setUpTileType(9, "grass00", false); // first ten won't be used | world map txt will become readable

        setUpTileType(10, "grass00", false);
        setUpTileType(11, "grass01", false);
        setUpTileType(12, "water00", true);
        setUpTileType(13, "water01", true);
        setUpTileType(14, "water02", true);
        setUpTileType(15, "water03", true);
        setUpTileType(16, "water04", true);
        setUpTileType(17, "water05", true);
        setUpTileType(18, "water06", true);
        setUpTileType(19, "water07", true);
        setUpTileType(20, "water08", true);
        setUpTileType(21, "water09", true);
        setUpTileType(22, "water10", true);
        setUpTileType(23, "water11", true);
        setUpTileType(24, "water12", true);
        setUpTileType(25, "water13", true);
        setUpTileType(26, "road00", false);
        setUpTileType(27, "road01", false);
        setUpTileType(28, "road02", false);
        setUpTileType(29, "road03", false);
        setUpTileType(30, "road04", false);
        setUpTileType(31, "road05", false);
        setUpTileType(32, "road06", false);
        setUpTileType(33, "road07", false);
        setUpTileType(34, "road08", false);
        setUpTileType(35, "road09", false);
        setUpTileType(36, "road10", false);
        setUpTileType(37, "road11", false);
        setUpTileType(38, "road12", false);
        setUpTileType(39, "earth", false);
        setUpTileType(40, "wall", true);
        setUpTileType(41, "tree", true);

        setUpTileType(42, "hut", false);
        setUpTileType(43, "floor01", false);
        setUpTileType(44, "table01", true);


    }

    private void setUpTileType(int index, String imageName, boolean collision) {
        try {
            tileTypes[index] = new Tile();
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tileTypes[index].setImage(UtilityTool.scaleImage(image, TILE_SIZE, TILE_SIZE));
            tileTypes[index].setCollision(collision);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        //TODO: make for loop and make mapTileNum[row][col]
        while (worldCol < Settings.MAX_WORLD_COL && worldRow < Settings.MAX_WORLD_ROW) {
            int tileNum = mapTileNum[GamePanel.currentMap][worldCol][worldRow];

            int worldX = worldCol * TILE_SIZE;
            int worldY = worldRow * TILE_SIZE;
            // TODO: use encapsulation with player
            int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
            int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

            if (worldX + TILE_SIZE > gp.player.getWorldX() - gp.player.getScreenX() &&
                    worldX - TILE_SIZE < gp.player.getWorldX() + gp.player.getScreenX() &&
                    worldY + TILE_SIZE > gp.player.getWorldY() - gp.player.getScreenY() &&
                    worldY - TILE_SIZE < gp.player.getWorldY() + gp.player.getScreenY()) { // Only draw the tiles around the player
                g2.drawImage(tileTypes[tileNum].getImage(), screenX, screenY,null);
            }

            worldCol++;
            if (worldCol == Settings.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }

        }

    }

    public void loadMap(String mapPath, int mapNumber) {
        try {
            InputStream is = getClass().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            // TODO: make proper for loops
            while (col < Settings.MAX_WORLD_COL && row < Settings.MAX_WORLD_ROW) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                while (col < Settings.MAX_WORLD_COL) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[mapNumber][col][row] = num;
                    col++;
                }
                if (col == Settings.MAX_WORLD_COL) {
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