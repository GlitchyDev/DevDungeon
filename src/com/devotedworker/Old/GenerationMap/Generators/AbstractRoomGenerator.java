package com.devotedworker.Old.GenerationMap.Generators;

import com.devotedworker.Old.GenerationMap.Dungeon;
import com.devotedworker.Old.GenerationMap.Utility.RoomLocation;

/**
 * This generates rooms of the specified type onto a Generation Map
 * In the future, a Event hookin should allow this to be rebased as a non static object, with a building priority queuesews
 */
public abstract class AbstractRoomGenerator {


    /**
     * Verifies that a room can be built in that Location.
     * @param width
     * @param length
     * @param height
     * @param x
     * @param z
     * @param y
     * @param dungeon
     * @return
     */
    public static boolean canGenerateStructure(int width, int length, int height, int x, int z, int y, Dungeon dungeon)
    {
        for(int x1 = x; x1 < x+width; x1++)
        {
            for(int z1 = z; z1 < z+length; z1++)
            {
                for(int y1 = y; y1 < y+height; y1++)
                {
                    if(!dungeon.doesRoomExist(new RoomLocation(x1,z1,y1)) || dungeon.getRoom(new RoomLocation(x1,z1,y1)) != null)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static double getSeconds(long start, long end)
    {
        return (end-start)/1000.0;
    }



}
