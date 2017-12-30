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

    public HashMap<RoomDirection,HallwayRoom> findConnectedHallways(Dungeon dungeon)
    {
        HashMap<RoomDirection,HallwayRoom> rooms = new HashMap<>();
        for(RoomDirection direction: RoomDirection.getFloorRoomDirections())
        {
            if(roomOrientation.getDirectionConnection(direction) == RoomConnection.ENTRANCE)
            {
                if(dungeon.doesRoomExist(roomLocation.getDirectionLocation(direction)))
                {
                    if (dungeon.getRoom(roomLocation.getDirectionLocation(direction)).getRoomType() == RoomType.HALLWAY) {
                        rooms.put(direction, ((HallwayRoom) dungeon.getRoom(roomLocation.getDirectionLocation(direction))));
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
    /*
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
*/