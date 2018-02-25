package com.devotedworker.Generation.Rooms;


import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.Utility.Enums.RoomConnection;
import com.devotedworker.Generation.Utility.Enums.RoomType;
import com.devotedworker.Generation.Utility.RoomLocation;

public class BigRoom extends AbstractRoom {



    public BigRoom(DungeonGenerationMap dungeonGenerationMap, RoomLocation roomLocation, int roomPos) {
        super(roomLocation);
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
