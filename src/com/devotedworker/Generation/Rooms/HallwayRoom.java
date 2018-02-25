package com.devotedworker.Generation.Rooms;

import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.Utility.Enums.RoomConnection;
import com.devotedworker.Generation.Utility.Enums.RoomDirection;
import com.devotedworker.Generation.Utility.Enums.RoomType;
import com.devotedworker.Generation.Utility.RoomLocation;


import java.util.HashMap;

public class HallwayRoom extends AbstractRoom {
    private int roomID;

    public HallwayRoom(RoomLocation roomLocation, int roomID) {
        super(roomLocation);
        roomType = RoomType.HALLWAY;
        this.roomID = roomID;

    }


    public HashMap<RoomDirection,HallwayRoom> findPotentialConnections(DungeonGenerationMap dungeonGenerationMap)
    {
        HashMap<RoomDirection,HallwayRoom> rooms = new HashMap<>();
        for(RoomDirection direction: RoomDirection.getFloorRoomDirections())
        {
            if(roomOrientation.getDirectionConnection(direction) == RoomConnection.WALL)
            {
                if(dungeonGenerationMap.doesRoomExist(roomLocation.getDirectionLocation(direction)))
                {
                    if (dungeonGenerationMap.getRoom(roomLocation.getDirectionLocation(direction)).getRoomType() == RoomType.HALLWAY) {
                        HallwayRoom potentialRoom = (HallwayRoom) dungeonGenerationMap.getRoom(roomLocation.getDirectionLocation(direction));
                        if(potentialRoom.getRoomID() != roomID) {
                            rooms.put(direction, ((HallwayRoom) dungeonGenerationMap.getRoom(roomLocation.getDirectionLocation(direction))));
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
