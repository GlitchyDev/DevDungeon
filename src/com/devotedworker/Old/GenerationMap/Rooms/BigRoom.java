package com.devotedworker.Old.GenerationMap.Rooms;

import com.devotedworker.Old.GenerationMap.Dungeon;
import com.devotedworker.Old.GenerationMap.Utility.Enums.RoomConnection;
import com.devotedworker.Old.GenerationMap.Utility.Enums.RoomType;
import com.devotedworker.Old.GenerationMap.Utility.RoomLocation;

public class BigRoom extends AbstractRoom {



    public BigRoom(Dungeon dungeon, RoomLocation roomLocation, int roomPos) {
        super(dungeon, roomLocation);
        roomType = RoomType.BIGROOM;
        switch(roomPos)
        {
            case 0:
                roomOrientation.setConnectNorth(RoomConnection.WALL);
                roomOrientation.setConnectEast(RoomConnection.NONE);
                roomOrientation.setConnectSouth(RoomConnection.NONE);
                roomOrientation.setConnectWest(RoomConnection.WALL);
                break;
            case 1:
                roomOrientation.setConnectNorth(RoomConnection.WALL);
                roomOrientation.setConnectEast(RoomConnection.WALL);
                roomOrientation.setConnectSouth(RoomConnection.NONE);
                roomOrientation.setConnectWest(RoomConnection.NONE);
                break;
            case 2:
                roomOrientation.setConnectNorth(RoomConnection.NONE);
                roomOrientation.setConnectEast(RoomConnection.NONE);
                roomOrientation.setConnectSouth(RoomConnection.WALL);
                roomOrientation.setConnectWest(RoomConnection.WALL);
                break;
            case 3:
                roomOrientation.setConnectNorth(RoomConnection.NONE);
                roomOrientation.setConnectEast(RoomConnection.WALL);
                roomOrientation.setConnectSouth(RoomConnection.WALL);
                roomOrientation.setConnectWest(RoomConnection.NONE);
                break;
        }
    }
}
