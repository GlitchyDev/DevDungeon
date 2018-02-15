package com.devotedworker.FileIO;

import com.devotedworker.GenerationMap.Dungeon;
import com.devotedworker.GenerationMap.Utility.DungeonDisplay;
import com.devotedworker.plugin.DevDungeon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Generates the "Image" used for debugging of generation
 */
public class GenerationMapImageIO {


    public static void writeGenerationMapToFile(Dungeon dungeon, boolean isBukkitExecution)
    {

        long imageWriteStart = System.currentTimeMillis();
        File file;
        if(isBukkitExecution) {
            file = new File(DevDungeon.getPlugin(DevDungeon.class).getDataFolder() + "/GenerationMap.png");
        }
        else
        {
            file = new File("GenerationMap.png");
        }
        try {
            ImageIO.write(DungeonDisplay.generateDisplay(dungeon),"PNG",file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long imageWriteEndTime = System.currentTimeMillis();
        DevDungeon.log("Dungeon: Image Write Time: " + getSeconds(imageWriteStart,imageWriteEndTime));
    }

    private static double getSeconds(long startTime, long endTime)
    {
        return (endTime - startTime)/1000.0;
    }




}
