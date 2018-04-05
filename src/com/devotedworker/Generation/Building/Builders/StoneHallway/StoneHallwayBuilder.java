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
                pasteSchematic("SubFloor",editSession,random,location,random.nextInt(4) * 90);
                pasteSchematic("Floor",editSession,random,location,random.nextInt(4) * 90);
                pasteSchematic("Ceiling_Engraving",editSession,random,location,random.nextInt(4) * 90);
                pasteSchematic("Ceiling",editSession,random,location,random.nextInt(4) * 90);


                // Do everything else
                for(RoomSection roomSection: RoomSection.getAllNonCenter())
                {
                    Location adjustedLocation = roomSection.getLocationOffset(location);

                    switch(roomData.get(room).getRoomData().get(roomSection))
                    {
                        // Carnal Direction, Floor, HallwaySupport, Ceiling
                        case ORIGINALPATH:
                            pasteSchematic("SubFloor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Floor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Hallway_Support",editSession,random,adjustedLocation,roomSection.getEquivalent().getRotation());
                            pasteSchematic("Ceiling",editSession,random,adjustedLocation,0);
                            break;
                        case WALL:
                            pasteSchematic("Stone_Filler",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Support_Beam", editSession,random,adjustedLocation,roomSection.getEquivalent().getRotation());
                            pasteSchematic("SubFloor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Floor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            for(RoomSection touchingSection: roomSection.getTouchingSections())
                            {
                                RoomDirection facingDirection = roomSection.getFacingDirection(touchingSection);
                                if(roomData.get(room).getRoomData().get(touchingSection) == HallwayRoomSection.ORIGINALPATH || roomData.get(room).getRoomData().get(touchingSection) == HallwayRoomSection.CENTER)
                                {
                                    pasteSchematicWithAir("Engraved_Lower_Wall", editSession, random, adjustedLocation, facingDirection.reverse().getRotation());
                                }
                                if(touchingSection == RoomSection.CENTER_SECTION)
                                {
                                    pasteSchematic("Large_Engraved_Upper_Wall", editSession, random, adjustedLocation, facingDirection.reverse().getRotation());
                                }
                                else
                                {
                                    pasteSchematic("Engraved_Upper_Wall", editSession, random, adjustedLocation, facingDirection.reverse().getRotation());
                                }
                            }
                            break;
                        case ADDEDPATH:
                            pasteSchematic("Support_Beam", editSession,random,adjustedLocation,roomSection.getEquivalent().getRotation());
                            pasteSchematic("SubFloor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Floor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Ceiling",editSession,random,adjustedLocation,0);
                            for(RoomSection touchingSection: roomSection.getTouchingSections())
                            {
                                RoomDirection facingDirection = roomSection.getFacingDirection(touchingSection);
                                if(roomData.get(room).getRoomData().get(touchingSection) == HallwayRoomSection.WALL)
                                {
                                    pasteSchematic("Plain_Lower_Wall", editSession, random, adjustedLocation, facingDirection.reverse().getRotation());
                                }
                                if(touchingSection == RoomSection.CENTER_SECTION)
                                {
                                    pasteSchematic("Large_Engraved_Upper_Wall", editSession, random, adjustedLocation, facingDirection.reverse().getRotation());
                                }
                                else
                                {
                                    pasteSchematic("Engraved_Upper_Wall", editSession, random, adjustedLocation, facingDirection.reverse().getRotation());
                                }
                            }
                            ArrayList<RoomDirection> notFacingDirections = RoomDirection.getFloorRoomDirections();
                            notFacingDirections.removeAll(roomSection.getTouchingSectionDirections());
                            for(RoomDirection roomDirection: notFacingDirections)
                            {
                                    if(getDirectionalRoomSection(dungeonGenerationMap,room.getRoomLocation(),roomDirection,roomSection) == HallwayRoomSection.WALL) {
                                        pasteSchematic("Plain_Upper_Wall", editSession, random, adjustedLocation, roomDirection.reverse().getRotation());
                                        pasteSchematic("Plain_Lower_Wall", editSession, random, adjustedLocation, roomDirection.reverse().getRotation());
                                    }
                            }
                            break;

                    }
                }
                break;
            case POSTBUILD:
                break;
        }
    }

    /*
    switch(phase)
        {
            case PREBUILD:
                roomData.put((HallwayRoom) room,new HallwayRoomMetaData((HallwayRoom) room,random));
                break;
            case BUILD:

                // Paste the center since its the same in every iteration
                pasteSchematic("SubFloor",editSession,random,location,random.nextInt(4) * 90);
                pasteSchematic("Floor",editSession,random,location,random.nextInt(4) * 90);
                pasteSchematic("Ceiling_Engraving",editSession,random,location,random.nextInt(4) * 90);
                pasteSchematic("Ceiling",editSession,random,location,random.nextInt(4) * 90);


                // Do everything else
                for(RoomSection roomSection: RoomSection.getAllNonCenter())
                {
                    Location adjustedLocation = roomSection.getLocationOffset(location);

                    switch(roomData.get(room).getRoomData().get(roomSection))
                    {
                        // Carnal Direction, Floor, HallwaySupport, Ceiling
                        case ORIGINALPATH:
                            pasteSchematic("SubFloor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Floor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Hallway_Support",editSession,random,adjustedLocation,roomSection.getEquivalent().getRotation());
                            pasteSchematic("Ceiling",editSession,random,adjustedLocation,0);
                            break;
                        case WALL:
                            pasteSchematic("Stone_Filler",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Support_Beam",editSession,random,adjustedLocation,roomSection.getEquivalent().getRotation());

                           // ArrayList<RoomDirection> directions = RoomDirection.getFloorRoomDirections().remove(roomSection.getTouchingSectionDirections())
                            for(RoomSection touchingSections: roomSection.getTouchingSections())
                            {
                                if(!roomData.get(room).getRoomData().get(touchingSections).isSolid() && roomData.get(room).getRoomData().get(touchingSections) != HallwayRoomSection.ADDEDPATH ) {
                                    if (touchingSections == RoomSection.CENTER_SECTION) {
                                        pasteSchematic("Large_Engraved_Upper_Wall", editSession,random, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                    } else {
                                        pasteSchematic("Engraved_Upper_Wall", editSession,random, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                    }
                                    pasteSchematic("Engraved_Lower_Wall", editSession,random, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                }
                            }
                            break;
                        case ADDEDPATH:

                            pasteSchematic("Support_Beam", editSession,random,adjustedLocation,roomSection.getEquivalent().getRotation());
                            pasteSchematic("SubFloor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Floor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Ceiling", editSession,random,adjustedLocation,random.nextInt(4) * 90);


                            ArrayList<RoomDirection> notFacingDirections = RoomDirection.getFloorRoomDirections();
                            notFacingDirections.removeAll(roomSection.getTouchingSectionDirections());

                            for(RoomSection touchingSections: roomSection.getTouchingSections())
                            {
                                if (touchingSections == RoomSection.CENTER_SECTION) {
                                    pasteSchematic("Large_Engraved_Upper_Wall", editSession,random, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                } else {
                                    pasteSchematic("Engraved_Upper_Wall", editSession,random, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                }
                                if(roomData.get(room).getRoomData().get(touchingSections).isSolid()) {
                                    pasteSchematic("Engraved_Lower_Wall", editSession,random, adjustedLocation, roomSection.getFacingDirection(touchingSections).reverse().getRotation());
                                }
                            }


                            for(RoomDirection nonTouchingSection: notFacingDirections)
                            {
                                if(getDirectionalRoomSection(dungeonGenerationMap,room.getRoomLocation(),nonTouchingSection,roomSection).isSolid() || !room.getRoomOrientation().getDirectionConnection(nonTouchingSection).isOpen()) {
                                    pasteSchematic("Plain_Upper_Wall", editSession,random, adjustedLocation, nonTouchingSection.reverse().getRotation());
                                    pasteSchematic("Plain_Lower_Wall", editSession,random, adjustedLocation, nonTouchingSection.reverse().getRotation());
                                }
                            }
                            break;
                        case UNUSUED:
                            pasteSchematic("Stone_Filler", editSession,random, adjustedLocation, 0);
                            break;
                    }
                }
                break;
        }
     */

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
