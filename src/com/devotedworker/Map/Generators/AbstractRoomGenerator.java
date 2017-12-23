package com.devotedworker.Map.Generators;

import com.devotedworker.Map.Dungeon;
import com.devotedworker.Map.Utility.RoomLocation;

public abstract class AbstractRoomGenerator {


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



}
