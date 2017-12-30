package com.devotedworker.GenerationMap.Rooms;

import com.devotedworker.GenerationMap.Dungeon;
import com.devotedworker.GenerationMap.Utility.Enums.RoomType;
import com.devotedworker.GenerationMap.Utility.RoomLocation;
import com.devotedworker.GenerationMap.Utility.RoomOrientation;

/**
 * A room for the purpose of Dungeon Generation
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
