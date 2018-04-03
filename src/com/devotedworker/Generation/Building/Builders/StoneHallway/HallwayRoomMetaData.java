package com.devotedworker.Generation.Building.Builders.StoneHallway;

import com.devotedworker.Generation.Building.RoomSection;
import com.devotedworker.Generation.Rooms.HallwayRoom;
import com.devotedworker.Generation.Utility.Enums.RoomDirection;

import java.util.HashMap;
import java.util.Random;

public class HallwayRoomMetaData {
    private HashMap<RoomSection,HallwayRoomSection> roomData;

    public HallwayRoomMetaData(HallwayRoom room, Random random)
    {
        roomData = new HashMap<>();
        // Go ahead and seed the room with the default stuff
        roomData.put(RoomSection.CENTER_SECTION, HallwayRoomSection.CENTER);
        // Places the rooms original path
        for(RoomSection roomSection: RoomSection.getAllNonCenter())
        {
            roomData.put(roomSection, HallwayRoomSection.WALL);
        }
        for(RoomDirection direction: RoomDirection.getFloorRoomDirections())
        {
            if(room.getRoomOrientation().getDirectionConnection(direction).isOpen())
            {
                roomData.put(RoomSection.getEquivalent(direction), HallwayRoomSection.ORIGINALPATH);
            }
        }


        for(RoomSection roomSection: RoomSection.getAllNonCenter())
        {
            if(roomData.get(roomSection) == HallwayRoomSection.WALL)
            {
                if(random.nextInt(2) == 0)
                {
                   roomData.put(roomSection,HallwayRoomSection.ADDEDPATH);
                }
            }
        }

    }

    public HashMap<RoomSection,HallwayRoomSection> getRoomData()
    {
        return roomData;
    }



}
