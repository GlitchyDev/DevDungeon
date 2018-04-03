package com.devotedworker.Generation.Building.Builders.StoneHallway;

import com.devotedworker.Generation.Building.Builders.AbstractRoomBuilder;
import com.devotedworker.Generation.Building.Builders.BuildingPhase;
import com.devotedworker.Generation.Building.RoomSection;
import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.Rooms.AbstractRoom;
import com.devotedworker.Generation.Rooms.HallwayRoom;
import com.devotedworker.Generation.Utility.Enums.RoomDirection;
import com.devotedworker.Generation.Utility.Enums.RoomType;
import com.devotedworker.Generation.Utility.RoomLocation;
import com.devotedworker.plugin.DevDungeon;
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
                                if(!roomData.get(room).getRoomData().get(touchingSections).isSolid()) {
                                    if (touchingSections == RoomSection.CENTER_SECTION) {
                                        pasteSchematic("Large_Engraved_Upper_Wall", editSession, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                    } else {
                                        pasteSchematic("Engraved_Upper_Wall", editSession, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                    }
                                    pasteSchematic("Lower_Wall_Engraved", editSession, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                }
                            }
                            break;
                        case ADDEDPATH:

                            pasteSchematic("Structure_Support", editSession,adjustedLocation,roomSection.getEquivalent().getRotation());
                            pasteSchematic("Floor", editSession,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Full_Ceiling", editSession,adjustedLocation,random.nextInt(4) * 90);


                            ArrayList<RoomDirection> notFacingDirections = RoomDirection.getFloorRoomDirections();
                            notFacingDirections.removeAll(roomSection.getTouchingSectionDirections());

                            for(RoomSection touchingSections: roomSection.getTouchingSections())
                            {
                                if (touchingSections == RoomSection.CENTER_SECTION) {
                                    pasteSchematic("Large_Engraved_Upper_Wall", editSession, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                } else {
                                    pasteSchematic("Engraved_Upper_Wall", editSession, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                }
                                if(roomData.get(room).getRoomData().get(touchingSections).isSolid()) {
                                    pasteSchematic("Lower_Wall_Engraved", editSession, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                }
                            }


                            for(RoomDirection nonTouchingSection: notFacingDirections)
                            {
                                if(getDirectionalRoomSection(dungeonGenerationMap,room.getRoomLocation(),nonTouchingSection,roomSection).isSolid() || !room.getRoomOrientation().getDirectionConnection(nonTouchingSection).isOpen()) {
                                    pasteSchematic("Plain_Upper_Wall", editSession, adjustedLocation, nonTouchingSection.reverse().getRotation());
                                    pasteSchematic("Lower_Wall_Plain", editSession, adjustedLocation, nonTouchingSection.reverse().getRotation());
                                }
                            }
                            break;
                        case UNUSUED:
                            pasteSchematic("Complete_Stone_Core", editSession, adjustedLocation, 0);
                            break;
                    }
                }
                break;
        }
    }

    public HallwayRoomSection getDirectionalRoomSection(DungeonGenerationMap dungeonGenerationMap, RoomLocation roomLocation, RoomDirection roomDirection, RoomSection roomSection)
    {
        if(dungeonGenerationMap.doesRoomExist(roomLocation.getDirectionLocation(roomDirection))) {
            if (!dungeonGenerationMap.isRoomNull(roomLocation.getDirectionLocation(roomDirection))) {
                if (dungeonGenerationMap.getRoom(roomLocation.getDirectionLocation(roomDirection)).getRoomType() == RoomType.HALLWAY) {
                    return roomData.get(dungeonGenerationMap.getRoom(roomLocation.getDirectionLocation(roomDirection))).getRoomData().get(roomSection.getReverse(roomDirection));
                }
            }
        }
        return HallwayRoomSection.WALL;
    }



}
