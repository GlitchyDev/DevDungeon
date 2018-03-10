package com.devotedworker.FileIO;

import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.Utility.DungeonDisplay;
import com.devotedworker.Generation.Utility.PerformanceUtility;
import com.devotedworker.plugin.DevDungeon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Generates the "Image" used for debugging of generation
 */
public class GenerationMapImageIO {


    public static void writeGenerationMapToFile(DungeonGenerationMap generationMap, boolean isBukkitExecution)
    {
        DevDungeon.log("DungeonGenerationMap: Start Image Write");
        PerformanceUtility.startLogging("ImageWrite");

        File file;
        if(isBukkitExecution) {
            file = new File(DevDungeon.getPlugin(DevDungeon.class).getDataFolder() + "/DungeonGenerationMap.png");
        }
        else
        {
            file = new File("DungeonGenerationMap.png");
        }
        try {
            ImageIO.write(DungeonDisplay.generateDisplay(generationMap),"PNG",file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PerformanceUtility.endLogging("ImageWrite");
        DevDungeon.log("DungeonGenerationMap: Image Write Time: " + PerformanceUtility.getTimings("ImageWrite"));
    }





}
