package com.devotedworker.Generation.Rooms;

import com.devotedworker.Generation.Utility.Enums.RoomType;
import com.devotedworker.Generation.Utility.RoomLocation;

public class DebugRoom extends AbstractRoom {

    public DebugRoom(RoomLocation roomLocation) {
        super(roomLocation);
        roomType = RoomType.DEBUGROOM;
    }
}
