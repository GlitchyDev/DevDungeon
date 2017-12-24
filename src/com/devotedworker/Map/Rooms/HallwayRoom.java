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
    private HallwayRoom connectedRoom;
    private RoomDirection connectionDirection;

    public HallwayRoom(Dungeon dungeon, RoomLocation roomlocation) {
        super(dungeon, roomlocation);
        roomType = RoomType.HALLWAY;

    }

    public HallwayRoom(Dungeon dungeon, RoomLocation roomlocation, Random random) {
        super(dungeon, roomlocation);
        roomType = RoomType.HALLWAY;

        for(RoomDirection direction: RoomDirection.getFloorRoomDirections())
        {
            switch(random.nextInt(3))
            {
                case 0:
                    roomOrientation.setDirectionConnection(direction,RoomConnection.NONE);
                    break;
                case 1:
                    roomOrientation.setDirectionConnection(direction,RoomConnection.ABSOLUTE_ENTRANCE);
                    break;
                case 2:
                    roomOrientation.setDirectionConnection(direction,RoomConnection.ABSOLUTE_ENTRANCE);
                    break;
            }
        }
    }


    public HallwayRoom getConnectedRoom() {
        return connectedRoom;
    }
    public void setConnectedRoom(HallwayRoom hallwayRoom, RoomDirection direction)
    {
        this.connectedRoom = hallwayRoom;
        this.connectionDirection = direction;
    }

    public RoomDirection getConnectionDirection() {
        return connectionDirection;
    }

    public boolean isRoomConnected(HallwayRoom hallwayRoom)
    {
        HallwayRoom currentRoom = connectedRoom;
        while(true)
        {
            if(currentRoom == null)
            {
                return false;
            }
            else
            {
                if(currentRoom == hallwayRoom)
                {
                    return true;
                }
                currentRoom = currentRoom.getConnectedRoom();
            }
        }
    }

    public HallwayRoom getRoot()
    {
        HallwayRoom currentRoom = this;
        while(true)
        {
            if(currentRoom.getConnectedRoom() == null)
            {
                return currentRoom;
            }
            else
            {
                currentRoom = currentRoom.getConnectedRoom();
            }
        }
    }
}
