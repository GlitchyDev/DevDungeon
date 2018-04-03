package com.devotedworker.Generation.Building.Builders.StoneHallway;

import com.devotedworker.Generation.Building.Builders.AbstractRoomBuilder;
import com.devotedworker.Generation.Building.Builders.BuildingPhase;
import com.devotedworker.Generation.Building.RoomSection;
import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.Rooms.AbstractRoom;
import com.devotedworker.Generation.Rooms.HallwayRoom;
import com.devotedworker.Generation.Utility.Enums.RoomDirection;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class StoneHallwayBuilder extends AbstractRoomBuilder {
    private HashMap<HallwayRoom,HallwayRoomMetaData> roomData;

    public StoneHallwayBuilder()
    {
        roomData = new HashMap<>();
    }

    @Override
    public void build(DungeonGenerationMap dungeonGenerationMap, AbstractRoom room, BuildingPhase phase, Location location, Random random, EditSession editSession) {


        switch(phase)
        {
            case PREBUILD:
                roomData.put((HallwayRoom) room,new HallwayRoomMetaData((HallwayRoom) room,random));
                break;
            case BUILD:

                // Paste the center since its the same in every iteration
                pasteSchematic("Floor",editSession,location,random.nextInt(4) * 90);
                pasteSchematic("Upper_Engraving",editSession,location,random.nextInt(4) * 90);
                pasteSchematic("Full_Ceiling",editSession,location,random.nextInt(4) * 90);


                // Do everything else
                for(RoomSection roomSection: RoomSection.getAllNonCenter())
                {
                    Location adjustedLocation = roomSection.getLocationOffset(location);

                    switch(roomData.get(room).getRoomData().get(roomSection))
                    {
                        // Carnal Direction, Floor, HallwaySupport, Ceiling
                        case ORIGINALPATH:
                            pasteSchematic("Floor",editSession,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Hallway_Support_Connector",editSession,adjustedLocation,roomSection.getEquivalent().getRotation());
                            pasteSchematic("Full_Ceiling",editSession,adjustedLocation,0);
                            break;
                        case WALL:
                            pasteSchematic("Complete_Stone_Core",editSession,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Structure_Support",editSession,adjustedLocation,roomSection.getEquivalent().getRotation());

                           // ArrayList<RoomDirection> directions = RoomDirection.getFloorRoomDirections().remove(roomSection.getTouchingSectionDirections())
                            for(RoomSection touchingSections: roomSection.getTouchingSections())
                            {
                                if(roomData.get(room).getRoomData().get(touchingSections) != HallwayRoomSection.WALL) {
                                    if (touchingSections == RoomSection.CENTER_SECTION) {
                                        pasteSchematic("Large_Engraved_Upper_Wall", editSession, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                    } else {
                                        pasteSchematic("Engraved_Upper_Wall", editSession, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                    }
                                    pasteSchematic("Lower_Wall_Engraved", editSession, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                }
                            }
                            break;
                        case UNUSUED:
                            pasteSchematic("Complete_Stone_Core", editSession, adjustedLocation, 0);

                            break;
                    }


                }
                /*
                pasteSchematic("Floor",editSession,location,random.nextInt(4) * 90);
                pasteSchematic("Upper_Engraving",editSession,location,random.nextInt(4) * 90);
                pasteSchematic("Full_Ceiling",editSession,location,random.nextInt(4) * 90);

                for(RoomSection roomSection: RoomSection.getAllNonCenter())
                {
                    Location adjustedLocation = roomSection.getLocationOffset(location);

                    switch(roomData.get(room).getRoomData().get(roomSection))
                    {
                        case WALL:
                            pasteSchematic("Complete_Stone_Core",editSession,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Structure_Support",editSession,adjustedLocation,roomSection.getEquivalent().getRotation());

                            if(roomSection.isCarnal())
                            {
                                pasteSchematic("Large_Engraved_Upper_Wall", editSession, adjustedLocation, roomSection.getEquivalent().getRotation());
                                pasteSchematic("Lower_Wall_Engraved", editSession,adjustedLocation, roomSection.getEquivalent().getRotation());

                            }



                            if(roomData.get(room).getRoomData().get(roomSection.getTouchingSections()[0]) == HallwayRoomSection.ORIGINALPATH)
                            {
                                pasteSchematic("Engraved_Upper_Wall", editSession, adjustedLocation, roomSection.getEquivalent().getRotation());
                                pasteSchematic("Lower_Wall_Engraved", editSession,adjustedLocation, roomSection.getEquivalent().getRotation());
                            }
                            if(roomData.get(room).getRoomData().get(roomSection.getTouchingSections()[1]) == HallwayRoomSection.ORIGINALPATH)
                            {
                                pasteSchematic("Engraved_Upper_Wall", editSession, adjustedLocation, roomSection.getEquivalent().reverse().getRotation());
                                pasteSchematic("Lower_Wall_Engraved", editSession,adjustedLocation, roomSection.getEquivalent().reverse().getRotation());
                            }


                            break;
                        case ORIGINALPATH:
                            pasteSchematic("Floor",editSession,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Hallway_Support_Connector",editSession,adjustedLocation,roomSection.getEquivalent().getRotation());
                            pasteSchematic("Full_Ceiling",editSession,adjustedLocation,0);

                            break;
                        case ADDEDPATH:
                            break;
                    }
                }

               */


                break;
        }

        // 9 quadrants,
    }
}
