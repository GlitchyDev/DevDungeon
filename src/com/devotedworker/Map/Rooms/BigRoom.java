package com.devotedworker.Map.Rooms;

import com.devotedworker.Map.Dungeon;
import com.devotedworker.Map.Utility.Enums.RoomConnection;
import com.devotedworker.Map.Utility.Enums.RoomType;
import com.devotedworker.Map.Utility.RoomLocation;

public class BigRoom extends AbstractRoom {



    public BigRoom(Dungeon dungeon, RoomLocation roomLocation, int roomPos) {
        super(dungeon, roomLocation);
        roomType = RoomType.BIGROOM;
        switch(roomPos)
        {
            case 0:
                roomOrientation.setConnectNorth(RoomConnection.POSSIBLE_ENTRANCE);
                roomOrientation.setConnectEast(RoomConnection.NONE);
                roomOrientation.setConnectSouth(RoomConnection.NONE);
                roomOrientation.setConnectWest(RoomConnection.POSSIBLE_ENTRANCE);
                break;
            case 1:
                roomOrientation.setConnectNorth(RoomConnection.POSSIBLE_ENTRANCE);
                roomOrientation.setConnectEast(RoomConnection.POSSIBLE_ENTRANCE);
                roomOrientation.setConnectSouth(RoomConnection.NONE);
                roomOrientation.setConnectWest(RoomConnection.NONE);
                break;
            case 2:
                roomOrientation.setConnectNorth(RoomConnection.NONE);
                roomOrientation.setConnectEast(RoomConnection.NONE);
                roomOrientation.setConnectSouth(RoomConnection.POSSIBLE_ENTRANCE);
                roomOrientation.setConnectWest(RoomConnection.POSSIBLE_ENTRANCE);
                break;
            case 3:
                roomOrientation.setConnectNorth(RoomConnection.NONE);
                roomOrientation.setConnectEast(RoomConnection.POSSIBLE_ENTRANCE);
                roomOrientation.setConnectSouth(RoomConnection.POSSIBLE_ENTRANCE);
                roomOrientation.setConnectWest(RoomConnection.NONE);
                break;
        }
    }
}
