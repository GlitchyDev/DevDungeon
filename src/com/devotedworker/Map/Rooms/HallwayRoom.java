package com.devotedworker.Map.Rooms;

import com.devotedworker.Map.Dungeon;
import com.devotedworker.Map.Utility.Enums.RoomConnection;
import com.devotedworker.Map.Utility.Enums.RoomDirection;
import com.devotedworker.Map.Utility.Enums.RoomType;
import com.devotedworker.Map.Utility.RoomLocation;
import com.devotedworker.Map.Utility.RoomOrientation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class HallwayRoom extends AbstractRoom {
    private HashMap<RoomDirection,HallwayRoom> connectedRooms;
    private int treeID = -1;

    public HallwayRoom(Dungeon dungeon, RoomLocation roomlocation) {
        super(dungeon, roomlocation);
        roomType = RoomType.HALLWAY;
        connectedRooms = new HashMap<>();
    }

    public HashMap<RoomDirection,HallwayRoom> getPossibleConnections(Dungeon dungeon)
    {
        HashMap<RoomDirection,HallwayRoom> connectedRooms = new HashMap<>();
        for(RoomDirection direction: RoomDirection.getFloorRoomDirections())
        {
            if(dungeon.doesRoomExist(roomLocation.getDirectionLocation(direction))) {
                if (dungeon.getRoom(roomLocation.getDirectionLocation(direction)).getRoomType() == RoomType.HALLWAY) {
                    HallwayRoom room = (HallwayRoom) dungeon.getRoom(roomLocation.getDirectionLocation(direction));
                    if (room.getTreeID() == -1 || room.getTreeID() != treeID) {
                        connectedRooms.put(direction,room);
                    }
                }
            }
        }
        return connectedRooms;
    }



    public int getTreeID() {
        return treeID;
    }

    public void setTreeID(int treeID) {
        this.treeID = treeID;
    }

    public HashMap<RoomDirection, HallwayRoom> getConnectedRooms() {
        return connectedRooms;
    }
}
