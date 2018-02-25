package com.devotedworker.Generation;

import com.devotedworker.Generation.Generators.AbstractRoomGenerator;
import com.devotedworker.Generation.Generators.GeneratorType;

import java.util.HashMap;
import java.util.Random;


/**
 * DungeonGenerator creates the appropriate DungeonGenerationMap from the Template, being supplied the Dimensions
 * It keeps track of what GeneratorType are needed by passing through ActionSetup Template Action list, then runs each action
 * The Result should be a "DungeonMap" which can be supplied to a builder to build the "Minecraft Equivalent"
 */
public class DungeonGenerator {
    private GenAction[] genActions;
    private HashMap<GeneratorType,AbstractRoomGenerator> generators;


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
        generators = new HashMap<>();
        Random random = new Random(seed);
        DungeonGenerationMap generationMap = new DungeonGenerationMap(x,z,y);


        for(GenAction action: template.getActionList())
        {
            if(!generators.containsKey(action.getNativeGenerator()))
            {
                generators.put(action.getNativeGenerator(),action.getNativeGenerator().getGenerator());
            }
        }

        for(GenAction action: template.getActionList())
        {
            generators.get(action.getNativeGenerator()).geneneratorActions(action,generationMap,random);
        }
    }
}
