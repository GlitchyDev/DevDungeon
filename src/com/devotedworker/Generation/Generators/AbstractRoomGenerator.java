package com.devotedworker.Generation.Generators;

import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.GenAction;

import java.util.Random;

/**
 * Populates a DungeonGenerationMap with Rooms of the specified Type, actions this generator can make are
 * Defined

 */
public abstract class AbstractRoomGenerator {


    abstract public void geneneratorActions(GenAction action, DungeonGenerationMap generationMap, Random random);
}
