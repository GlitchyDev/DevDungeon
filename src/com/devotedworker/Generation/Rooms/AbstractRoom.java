package com.devotedworker.Generation.Rooms;


import com.devotedworker.Generation.Utility.Enums.RoomType;
import com.devotedworker.Generation.Utility.RoomLocation;
import com.devotedworker.Generation.Utility.RoomOrientation;

/**
 * A room for the purpose of DungeonGenerationMap Generation
 */
public abstract class AbstractRoom {
    protected RoomLocation roomLocation;
    protected RoomType roomType;
    protected RoomOrientation roomOrientation;

    public AbstractRoom(RoomLocation roomLocation)
    {
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
