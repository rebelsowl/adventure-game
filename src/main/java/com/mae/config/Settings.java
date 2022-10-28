package com.mae.config;

public class Settings {

    // SCREEN SETTINGS
    public static final int ORIGINAL_TILE_SIZE = 16; // 16 x 16 tile size
    public static final int SCALE = 3; // for normalization
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48 x 48 tile
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 12;
    // 4:3 screen ratio
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;  // 48x16 = 768 pixels
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 48x12 = 576 pixels
    public static final int FPS = 60;

    // WORLD SETTINGS
    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;


}
