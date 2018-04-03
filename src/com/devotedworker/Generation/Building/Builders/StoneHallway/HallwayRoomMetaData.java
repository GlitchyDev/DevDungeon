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
        for(RoomDirection direction: RoomDirection.getFloorRoomDirections())
        {
            if(room.getRoomOrientation().getDirectionConnection(direction).isOpen())
            {
                roomData.put(RoomSection.getEquivalent(direction), HallwayRoomSection.ORIGINALPATH);
            }
            else
            {
                roomData.put(RoomSection.getEquivalent(direction), HallwayRoomSection.WALL);
            }
        }
      for(RoomSection roomPosition: RoomSection.getDiagonal())
      {
          roomData.put(roomPosition, HallwayRoomSection.WALL);
      }

      for(RoomSection roomSection: RoomSection.getAllNonCenter())
      {
          int connections = 0;
          for(RoomSection connectedSection: roomSection.getTouchingSections())
          {
              if(roomData.get(connectedSection) != HallwayRoomSection.WALL)
              {
                  connections++;
              }
          }
          if(connections == 0)
          {
              roomData.put(roomSection,HallwayRoomSection.UNUSUED);
          }
      }
    }

    public HashMap<RoomSection,HallwayRoomSection> getRoomData()
    {
        return roomData;
    }



}
