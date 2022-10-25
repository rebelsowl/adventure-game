package com.mae.utility;

import java.awt.*;
import java.awt.image.BufferedImage;


public class UtilityTool {

    /**
     * scales given image, for improving rendering in runtime
     *
     * @param original unscaled image
     * @param width    output width
     * @param height   output height
     * @return scaled image
     */
    public static BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType()); // empty canvas
        Graphics2D g2 = scaledImage.createGraphics(); // g2 drawing will be saved to scaledImage
        g2.drawImage(original, 0, 0, width, height, null); // we are scaling the image now only once, so we don't have to scale while rendering
        g2.dispose();
        return scaledImage;
    }

}