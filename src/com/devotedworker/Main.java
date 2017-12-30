package com.devotedworker;

import com.devotedworker.GenerationMap.Dungeon;
import com.devotedworker.GenerationMap.Utility.DungeonDisplay;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("Dungeon: Starting Generation");
        long generationStartTime = System.currentTimeMillis();
        File file = new File("Test.png");
        Dungeon dungeon = new Dungeon(50,50,5);
        long generationEndTime = System.currentTimeMillis();
        System.out.println("Dungeon: Total Generation Time: " + getSeconds(generationStartTime,generationEndTime));
        System.out.println("Dungeon: Start Image Write");
        try {
            ImageIO.write(DungeonDisplay.generateDisplay(dungeon),"PNG",file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long imageWriteEndTime = System.currentTimeMillis();
        System.out.println("Dungeon: Image Write Time: " + getSeconds(generationEndTime,imageWriteEndTime));

    }

    public static double getSeconds(long startTime, long endTime)
    {
        return (endTime - startTime)/1000.0;
    }
}
