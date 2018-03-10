package com.devotedworker.Generation.Building;

import com.devotedworker.Generation.Building.Builder.AbstractRoomBuilder;
import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.Utility.Enums.RoomType;
import org.bukkit.Location;

import java.util.ArrayList;

public class DungeonBuilder {

    public void build(Location location, DungeonGenerationMap dungeonGenerationMap)
    {
        ArrayList<RoomType> utilizedRooms = dungeonGenerationMap.getUtilizedRooms();

        ArrayList<AbstractRoomBuilder> builders = new ArrayList<>();
        for(RoomType roomType: utilizedRooms)
        {

        }
    }

    public AbstractRoomBuilder getNativeBuilder(RoomType roomType)
    {
        switch(roomType)
        {

        }
        return null;
    }
}
