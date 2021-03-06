package com.devotedworker.Generation;

import com.devotedworker.Generation.Generators.AbstractRoomGenerator;
import com.devotedworker.Generation.Generators.GeneratorType;
import com.devotedworker.Generation.Utility.PerformanceUtility;
import com.devotedworker.plugin.DevDungeon;

import java.util.HashMap;
import java.util.Random;


/**
 * DungeonGenerator creates the appropriate DungeonGenerationMap from the Template, being supplied the Dimensions
 * It keeps track of what GeneratorType are needed by passing through ActionSetup Template Action list, then runs each action
 * The Result should be a "DungeonMap" which can be supplied to a builder to build the "Minecraft Equivalent"
 */
public class DungeonGenerator {
    private HashMap<GeneratorType,AbstractRoomGenerator> generators;
    DungeonGenerationMap generationMap;


    /**
     * Create a Dungeon GenerationMap, no seed specifid
     * @param template
     * @param x
     * @param z
     * @param y
     */
    public void generate(DungeonTemplate template, int x, int z, int y)
    {
        generate(template,x,z,y,new Random().nextLong());

    }

    public void generate(DungeonTemplate template, int x, int z, int y, long seed)
    {
        DevDungeon.log("DungeonGenerator: Starting Dungeon Generation using Template " + template.toString());
        PerformanceUtility.startLogging("DungeonGeneration");

        Random random = new Random(seed);
        generators = new HashMap<>();
        generationMap = new DungeonGenerationMap(x,z,y);


        // Setting up only the Generators needed
        for(GenAction action: template.getActionList())
        {
            if(!generators.containsKey(action.getNativeGenerator()))
            {
                generators.put(action.getNativeGenerator(),action.getNativeGenerator().getGenerator());
            }
        }

        // Completing Generation Actions, logging gross time
        DevDungeon.log("DungeonGenerator: *********************");

        for(GenAction action: template.getActionList())
        {
            DevDungeon.log("DungeonGenerator: " + action.toString() + " Starting Generation");

            PerformanceUtility.startLogging(action.toString());
            generators.get(action.getNativeGenerator()).geneneratorActions(generationMap,generators,action,random);
            PerformanceUtility.endLogging(action.toString());

            DevDungeon.log("DungeonGenerator: " + action.toString() + " Total Time: " + PerformanceUtility.getTimings(action.toString()));
            DevDungeon.log("DungeonGenerator: *********************");

        }

        PerformanceUtility.endLogging("DungeonGeneration");
        DevDungeon.log("DungeonGenerator: Total Generation Time: " + PerformanceUtility.getTimings("DungeonGeneration"));
    }

    public DungeonGenerationMap getDungeonGenerationMap()
    {
        return generationMap;
    }
}
