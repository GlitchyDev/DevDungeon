package com.devotedworker.Old.GenerationMap.Rooms;

import com.devotedworker.Old.GenerationMap.Dungeon;
import com.devotedworker.Old.GenerationMap.Utility.Enums.RoomType;
import com.devotedworker.Old.GenerationMap.Utility.RoomLocation;
import com.devotedworker.Old.GenerationMap.Utility.RoomOrientation;

/**
 * A room for the purpose of DungeonGenerationMap Generation
 */
public abstract class AbstractRoom {
    protected RoomLocation roomLocation;
    protected Dungeon dungeon;
    protected RoomType roomType;
    protected RoomOrientation roomOrientation;

    public AbstractRoom(Dungeon dungeon, RoomLocation roomLocation)
    {
        this.dungeon = dungeon;
        this.roomLocation = roomLocation;
        roomType = RoomType.DEFAULT;
        roomOrientation = new RoomOrientation();
    }

    public RoomLocation getRoomLocation() {
        return roomLocation;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public RoomOrientation getRoomOrientation() {
        return roomOrientation;
    }



}
