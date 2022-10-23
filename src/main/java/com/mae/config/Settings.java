package com.mae.config;

public class Settings {

    // SCREEN SETTINGS
    public static final int originalTileSize = 16; // 16 x 16 tile size
    public static final int scale = 3; // for normalization

    public static final int tileSize = originalTileSize * scale; // 48 x 48 tile
    public static final int maxScreenCol = 16;
    public static final int maxScreenRow = 12;
    // 4:3 screen ratio

    public static final int screenWidth = tileSize * maxScreenCol;  // 48x16 = 768 pixels
    public static final int screenHeight = tileSize * maxScreenRow; // 48x12 = 576 pixels

    public static final int FPS = 60;


}
