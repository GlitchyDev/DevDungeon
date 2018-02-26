package com.devotedworker.Generation.Generators;

import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.GenAction;
import com.devotedworker.Generation.Rooms.AbstractRoom;
import com.devotedworker.Generation.Utility.RoomLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Populates a DungeonGenerationMap with Rooms of the specified Type, actions this generator can make are
 * Defined

 */
public abstract class AbstractRoomGenerator {
    protected ArrayList<AbstractRoom> createdRooms = new ArrayList<>();

    abstract public void geneneratorActions(DungeonGenerationMap generationMap, HashMap<GeneratorType,AbstractRoomGenerator> generators, GenAction genAction, Random random);

    public static boolean canGenerateStructure(int width, int length, int height, int x, int z, int y, DungeonGenerationMap dungeonGenerationMap)
    {
        for(int x1 = x; x1 < x+width; x1++)
        {
            for(int z1 = z; z1 < z+length; z1++)
            {
                for(int y1 = y; y1 < y+height; y1++)
                {
                    if(!dungeonGenerationMap.doesRoomExist(new RoomLocation(x1,z1,y1)) || dungeonGenerationMap.getRoom(new RoomLocation(x1,z1,y1)) != null)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<AbstractRoom> getCreatedRooms()
    {
        return createdRooms;
    }
}
