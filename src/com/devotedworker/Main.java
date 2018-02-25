package com.devotedworker;

import com.devotedworker.Generation.plugin.DevDungeon;

/**
 * Should be able to run a DungeonGenerationMap for testing purposes
 */
public class Main {
    public static void main(String[] args)
    {
        DevDungeon.log("DungeonGenerationMap: Starting Generators");
        long generationStartTime = System.currentTimeMillis();
        //Dungeon dungeon = new Dungeon(50,50,5);
        long generationEndTime = System.currentTimeMillis();
        DevDungeon.log("DungeonGenerationMap: Total Generators Time: " + getSeconds(generationStartTime,generationEndTime));
        DevDungeon.log("DungeonGenerationMap: Start Image Write");
        //GenerationMapImageIO.writeGenerationMapToFile(dungeon,false);
    }

    public static double getSeconds(long startTime, long endTime)
    {
        return (endTime - startTime)/1000.0;
    }
}
