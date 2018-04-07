package com.devotedworker.Generation.Rooms;


import com.devotedworker.Generation.Utility.Enums.RoomConnection;
import com.devotedworker.Generation.Utility.Enums.RoomType;
import com.devotedworker.Generation.Utility.RoomLocation;

public class BigRoom extends AbstractRoom {
    private final int roomPos;

    public BigRoom(RoomLocation roomLocation, int roomPos) {
        super(roomLocation);
        roomType = RoomType.BIGROOM;
        switch(roomPos)
        {
            case 0:
                roomOrientation.setConnectEast(RoomConnection.NONE);
                roomOrientation.setConnectSouth(RoomConnection.NONE);
                roomOrientation.setConnectUp(RoomConnection.NONE);
                break;
            case 1:
                roomOrientation.setConnectSouth(RoomConnection.NONE);
                roomOrientation.setConnectWest(RoomConnection.NONE);
                roomOrientation.setConnectUp(RoomConnection.NONE);
                break;
            case 2:
                roomOrientation.setConnectNorth(RoomConnection.NONE);
                roomOrientation.setConnectEast(RoomConnection.NONE);
                roomOrientation.setConnectUp(RoomConnection.NONE);
                break;
            case 3:
                roomOrientation.setConnectNorth(RoomConnection.NONE);
                roomOrientation.setConnectWest(RoomConnection.NONE);
                roomOrientation.setConnectUp(RoomConnection.NONE);
                break;
                // Second Floor
            case 4:
                roomOrientation.setConnectEast(RoomConnection.NONE);
                roomOrientation.setConnectSouth(RoomConnection.NONE);
                roomOrientation.setConnectDown(RoomConnection.NONE);
                break;
            case 5:
                roomOrientation.setConnectSouth(RoomConnection.NONE);
                roomOrientation.setConnectWest(RoomConnection.NONE);
                roomOrientation.setConnectDown(RoomConnection.NONE);
                break;
            case 6:
                roomOrientation.setConnectNorth(RoomConnection.NONE);
                roomOrientation.setConnectEast(RoomConnection.NONE);
                roomOrientation.setConnectDown(RoomConnection.NONE);
                break;
            case 7:
                roomOrientation.setConnectNorth(RoomConnection.NONE);
                roomOrientation.setConnectWest(RoomConnection.NONE);
                roomOrientation.setConnectDown(RoomConnection.NONE);
                break;
        }
        this.roomPos = roomPos;
    }

    public int getRoomPos() {
        return roomPos;
    }

    public boolean isTopRoom()
    {
        return roomPos > 3;
    }
}
