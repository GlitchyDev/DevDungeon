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


    private HashMap<HallwayRoom, HashMap<RoomSection,HallwayRoomSection>> roomData;

    public StoneHallwayBuilder()
    {
        roomData = new HashMap<>();
    }

    @Override
    public void build(DungeonGenerationMap dungeonGenerationMap, AbstractRoom room, BuildingPhase phase, Location location, Random random, EditSession editSession) {


        switch(phase)
        {
            case PREBUILD:
                roomData.put((HallwayRoom) room,new HashMap<RoomSection,HallwayRoomSection>());
                roomData.get(room).put(RoomSection.CENTER_SECTION,HallwayRoomSection.CENTER);


                // Places the rooms original path
                for(RoomSection roomSection: RoomSection.getAllNonCenter())
                {
                    roomData.get(room).put(roomSection,HallwayRoomSection.WALL);
                }
                for(RoomDirection direction: RoomDirection.getFloorRoomDirections())
                {
                    if(room.getRoomOrientation().getDirectionConnection(direction).isOpen())
                    {
                        roomData.get(room).put(RoomSection.getEquivalent(direction), HallwayRoomSection.ORIGINALPATH);
                    }
                }


                for(RoomSection roomSection: RoomSection.getAllNonCenter())
                {
                    if(roomData.get(room).get(roomSection) == HallwayRoomSection.WALL)
                    {
                        if(random.nextInt(2) == 0)
                        {
                            roomData.get(room).put(roomSection,HallwayRoomSection.ADDEDPATH);
                        }
                    }
                }

                for(RoomSection roomSection: RoomSection.getAllNonCenter())
                {
                    if(roomData.get(room).get(roomSection) == HallwayRoomSection.ADDEDPATH) {
                        int connections = 0;
                        for (RoomSection touchingSection : roomSection.getTouchingSections()) {
                            if (touchingSection != RoomSection.CENTER_SECTION) {
                                if (roomData.get(room).get(touchingSection) == HallwayRoomSection.ADDEDPATH) {
                                    connections++;
                                }
                            }
                        }
                        if (connections == 0) {
                            roomData.get(room).put(roomSection, HallwayRoomSection.WALL);
                        }
                    }
                }
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

                    switch(roomData.get(room).get(roomSection))
                    {
                        // Carnal Direction, Floor, HallwaySupport, Ceiling
                        case ORIGINALPATH:
                            if(room.getRoomLocation().getY() == 0 || (dungeonGenerationMap.doesRoomExist(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN))&& dungeonGenerationMap.isRoomNull(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)))) {
                                pasteSchematic("SubFloor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            }
                            pasteSchematic("Floor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Hallway_Support",editSession,random,adjustedLocation,roomSection.getEquivalent().getRotation());
                            pasteSchematic("Ceiling",editSession,random,adjustedLocation,0);
                            break;
                        case WALL:
                            if(room.getRoomLocation().getY() == 0 || (dungeonGenerationMap.doesRoomExist(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN))&& dungeonGenerationMap.isRoomNull(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)))) {
                                pasteSchematic("SubFloor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            }
                            pasteSchematic("Floor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Stone_Filler",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Support_Beam", editSession,random,adjustedLocation,roomSection.getEquivalent().getRotation());
                            for(RoomSection touchingSection: roomSection.getTouchingSections())
                            {
                                RoomDirection facingDirection = roomSection.getFacingDirection(touchingSection);
                                if(roomData.get(room).get(touchingSection) == HallwayRoomSection.ORIGINALPATH || roomData.get(room).get(touchingSection) == HallwayRoomSection.CENTER)
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
                            if(room.getRoomLocation().getY() == 0 || (dungeonGenerationMap.doesRoomExist(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN))&& dungeonGenerationMap.isRoomNull(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)))) {
                                pasteSchematic("SubFloor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            }
                            pasteSchematic("Floor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                            pasteSchematic("Support_Beam", editSession,random,adjustedLocation,roomSection.getEquivalent().getRotation());
                            pasteSchematic("Ceiling",editSession,random,adjustedLocation,0);
                            for(RoomSection touchingSection: roomSection.getTouchingSections())
                            {
                                RoomDirection facingDirection = roomSection.getFacingDirection(touchingSection);
                                if(roomData.get(room).get(touchingSection) == HallwayRoomSection.WALL)
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


    public HallwayRoomSection getDirectionalRoomSection(DungeonGenerationMap dungeonGenerationMap, RoomLocation roomLocation, RoomDirection roomDirection, RoomSection roomSection)
    {
        if(dungeonGenerationMap.doesRoomExist(roomLocation.getDirectionLocation(roomDirection))) {
            if (!dungeonGenerationMap.isRoomNull(roomLocation.getDirectionLocation(roomDirection))) {
                if (dungeonGenerationMap.getRoom(roomLocation.getDirectionLocation(roomDirection)).getRoomType() == RoomType.HALLWAY) {
                    return roomData.get(dungeonGenerationMap.getRoom(roomLocation.getDirectionLocation(roomDirection))).get(roomSection.getReverse(roomDirection));
                }
            }
        }
        return HallwayRoomSection.WALL;
    }



}
