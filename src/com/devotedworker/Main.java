package com.devotedworker;

import com.devotedworker.FileIO.GenerationMapImageIO;
import com.devotedworker.GenerationMap.Dungeon;
import com.devotedworker.plugin.DevDungeon;

/**
 * Should be able to run a GenerationMap for testing purposes
 */
public class Main {
    public static void main(String[] args)
    {
        DevDungeon.log("Dungeon: Starting Generation");
        long generationStartTime = System.currentTimeMillis();
        Dungeon dungeon = new Dungeon(50,50,5);
        long generationEndTime = System.currentTimeMillis();
        DevDungeon.log("Dungeon: Total Generation Time: " + getSeconds(generationStartTime,generationEndTime));
        DevDungeon.log("Dungeon: Start Image Write");
        GenerationMapImageIO.writeGenerationMapToFile(dungeon,false);
    }

    public static double getSeconds(long startTime, long endTime)
    {
        return (endTime - startTime)/1000.0;
    }
}
