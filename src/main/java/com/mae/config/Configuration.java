package com.mae.config;

import com.mae.panel.GamePanel;

import java.io.*;

public class Configuration {
    GamePanel gp;

    public Configuration(GamePanel gp) {
        this.gp = gp;

    }


    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            //Full Screen
            if (GamePanel.fullScreenOn)
                bw.write("On");
            else
                bw.write("Off");
            bw.newLine();

            // Music Volume
            bw.write(String.valueOf(gp.getThemeMusicHandler().getVolumeScale()));
            bw.newLine();

            // Sound Effect Volume
            bw.write(String.valueOf(gp.getSoundEffectHandler().getVolumeScale()));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();
            GamePanel.fullScreenOn = s.equals("On");

            s = br.readLine();
            gp.getThemeMusicHandler().setVolumeScale(Integer.parseInt(s));

            s = br.readLine();
            gp.getSoundEffectHandler().setVolumeScale(Integer.parseInt(s));

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
