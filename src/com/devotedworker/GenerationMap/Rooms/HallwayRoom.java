package com.devotedworker.GenerationMap.Rooms;

import com.devotedworker.GenerationMap.Dungeon;
import com.devotedworker.GenerationMap.Utility.Enums.RoomConnection;
import com.devotedworker.GenerationMap.Utility.Enums.RoomDirection;
import com.devotedworker.GenerationMap.Utility.Enums.RoomType;
import com.devotedworker.GenerationMap.Utility.RoomLocation;

import java.util.HashMap;

public class HallwayRoom extends AbstractRoom {
    private int roomID;

    public HallwayRoom(Dungeon dungeon, RoomLocation roomLocation, int roomID) {
        super(dungeon, roomLocation);
        roomType = RoomType.HALLWAY;
        this.roomID = roomID;

    }


    public HashMap<RoomDirection,HallwayRoom> findPotentialConnections(Dungeon dungeon)
    {
        HashMap<RoomDirection,HallwayRoom> rooms = new HashMap<>();
        for(RoomDirection direction: RoomDirection.getFloorRoomDirections())
        {
            if(roomOrientation.getDirectionConnection(direction) == RoomConnection.WALL)
            {
                if(dungeon.doesRoomExist(roomLocation.getDirectionLocation(direction)))
                {
                    if (dungeon.getRoom(roomLocation.getDirectionLocation(direction)).getRoomType() == RoomType.HALLWAY) {
                        HallwayRoom potentialRoom = (HallwayRoom) dungeon.getRoom(roomLocation.getDirectionLocation(direction));
                        if(potentialRoom.getRoomID() != roomID) {
                            rooms.put(direction, ((HallwayRoom) dungeon.getRoom(roomLocation.getDirectionLocation(direction))));
                        }
                    }
                }
            }
        }
        return rooms;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
}
