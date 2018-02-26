package com.devotedworker;

import com.devotedworker.FileIO.GenerationMapImageIO;
import com.devotedworker.Generation.DungeonGenerator;
import com.devotedworker.Generation.DungeonTemplate;


/**
 * Should be able to run a DungeonGenerationMap for testing purposes
 */
public class Main {
    public static void main(String[] args)
    {

        DungeonGenerator dungeonGenerator = new DungeonGenerator();
        dungeonGenerator.generate(DungeonTemplate.STONE_DUNGEON,50,50,3);
        GenerationMapImageIO.writeGenerationMapToFile(dungeonGenerator.getDungeonGenerationMap(),false);


    }

}
