package com.devotedworker.Map.Rooms;

import com.devotedworker.Map.Dungeon;
import com.devotedworker.Map.Utility.Enums.RoomDirection;
import com.devotedworker.Map.Utility.Enums.RoomType;
import com.devotedworker.Map.Utility.RoomLocation;
import com.devotedworker.Map.Utility.RoomOrientation;

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
