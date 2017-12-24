package com.devotedworker;

import com.devotedworker.Map.Dungeon;
import com.devotedworker.Map.Utility.DungeonDisplay;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("Dungeon Generation");
        long generationStartTime = System.currentTimeMillis();
        File file = new File("Test.png");
        Dungeon dungeon = new Dungeon(10,10
                ,1);
        long generationEndTime = System.currentTimeMillis();
        try {
            ImageIO.write(DungeonDisplay.generateDisplay(dungeon),"PNG",file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long imageWriteEndTime = System.currentTimeMillis();
        System.out.println("Generation Time: " + getSeconds(generationStartTime,generationEndTime));
        System.out.println("Write Time: " + getSeconds(generationEndTime,imageWriteEndTime));

    }

    public static double getSeconds(long startTime, long endTime)
    {
        return (endTime - startTime)/1000.0;
    }
}
