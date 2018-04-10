package com.devotedworker.Generation.Building.Builders.BigRoom;

import com.devotedworker.Generation.Building.Builders.AbstractRoomBuilder;
import com.devotedworker.Generation.Building.Builders.BuildingPhase;
import com.devotedworker.Generation.Building.Builders.StoneHallway.HallwayRoomSection;
import com.devotedworker.Generation.Building.RoomSection;
import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.Rooms.AbstractRoom;
import com.devotedworker.Generation.Rooms.BigRoom;
import com.devotedworker.Generation.Rooms.HallwayRoom;
import com.devotedworker.Generation.Utility.Enums.RoomConnection;
import com.devotedworker.Generation.Utility.Enums.RoomDirection;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class StoneBigRoomBuilder extends AbstractRoomBuilder {
    private HashMap<BigRoom, HashMap<RoomSection, BigRoomSection>> roomData;
    private HashMap<BigRoom, ArrayList<BigRoom>> completeStructure;


    public StoneBigRoomBuilder() {
        roomData = new HashMap<>();
        completeStructure = new HashMap<>();
    }


    @Override
    public void build(DungeonGenerationMap dungeonGenerationMap, AbstractRoom room, BuildingPhase phase, Location location, Random random, EditSession editSession) {
        BigRoom primaryRoom = (BigRoom) room;
        switch (phase) {
            case PREBUILD:
                if (primaryRoom.getRoomPos() == 0) {
                    completeStructure.put(primaryRoom, new ArrayList<BigRoom>());
                    completeStructure.get(primaryRoom).add(primaryRoom);
                    completeStructure.get(primaryRoom).add((BigRoom) dungeonGenerationMap.getRoom(primaryRoom.getRoomLocation().getEast()));
                    completeStructure.get(primaryRoom).add((BigRoom) dungeonGenerationMap.getRoom(primaryRoom.getRoomLocation().getSouth()));
                    completeStructure.get(primaryRoom).add((BigRoom) dungeonGenerationMap.getRoom(primaryRoom.getRoomLocation().getEast().getSouth()));
                    completeStructure.get(primaryRoom).add((BigRoom) dungeonGenerationMap.getRoom(primaryRoom.getRoomLocation().getUp()));
                    completeStructure.get(primaryRoom).add((BigRoom) dungeonGenerationMap.getRoom(primaryRoom.getRoomLocation().getUp().getEast()));
                    completeStructure.get(primaryRoom).add((BigRoom) dungeonGenerationMap.getRoom(primaryRoom.getRoomLocation().getUp().getSouth()));
                    completeStructure.get(primaryRoom).add((BigRoom) dungeonGenerationMap.getRoom(primaryRoom.getRoomLocation().getUp().getEast().getSouth()));
                    for (BigRoom bigRoom : completeStructure.get(primaryRoom)) {
                        roomData.put(bigRoom, new HashMap<RoomSection, BigRoomSection>());
                        for (RoomDirection roomDirection : RoomDirection.getFloorRoomDirections()) {
                            switch (bigRoom.getRoomOrientation().getDirectionConnection(roomDirection)) {
                                case WALL:
                                    for (RoomSection roomSection : RoomSection.getDirectionalSections(roomDirection)) {
                                        roomData.get(bigRoom).put(roomSection, BigRoomSection.WALL);
                                    }
                                    break;
                                case ENTRANCE:
                                    RoomSection[] sections = RoomSection.getDirectionalSections(roomDirection);
                                    roomData.get(bigRoom).put(sections[0], BigRoomSection.WALL);
                                    roomData.get(bigRoom).put(sections[1], BigRoomSection.ENTRANCE);
                                    roomData.get(bigRoom).put(sections[2], BigRoomSection.WALL);
                                    break;
                            }
                        }
                        for (RoomSection roomSection : RoomSection.values()) {
                            if (!roomData.get(bigRoom).containsKey(roomSection)) {
                                if (bigRoom.isTopRoom()) {
                                    roomData.get(bigRoom).put(roomSection, BigRoomSection.EMPTY);
                                } else {
                                    roomData.get(bigRoom).put(roomSection, BigRoomSection.GROUND_FLOOR);
                                }
                            }
                        }
                    }

                    if (completeStructure.get(primaryRoom).get(4).getRoomOrientation().getDirectionConnection(RoomDirection.NORTH) == RoomConnection.ENTRANCE || completeStructure.get(primaryRoom).get(5).getRoomOrientation().getDirectionConnection(RoomDirection.NORTH) == RoomConnection.ENTRANCE) {
                        roomData.get(completeStructure.get(primaryRoom).get(4)).put(RoomSection.CENTER_SECTION, BigRoomSection.UPPER_WALKWAY);
                        roomData.get(completeStructure.get(primaryRoom).get(4)).put(RoomSection.EAST_SECTION, BigRoomSection.UPPER_WALKWAY);
                        roomData.get(completeStructure.get(primaryRoom).get(5)).put(RoomSection.CENTER_SECTION, BigRoomSection.UPPER_WALKWAY);
                        roomData.get(completeStructure.get(primaryRoom).get(5)).put(RoomSection.WEST_SECTION, BigRoomSection.UPPER_WALKWAY);
                    }

                }
                break;
            case BUILD:

                if (!((BigRoom) room).isTopRoom()) {
                    switch (((BigRoom) room).getRoomPos()) {
                        case 0:
                            pasteSchematic("BigRoom_Ceiling", editSession, random, location, 0);
                            break;
                        case 1:
                            pasteSchematic("BigRoom_Ceiling", editSession, random, location, 90);
                            break;
                        case 2:
                            pasteSchematic("BigRoom_Ceiling", editSession, random, location, 270);
                            break;
                        case 3:
                            pasteSchematic("BigRoom_Ceiling", editSession, random, location, 180);
                            break;
                    }

                }

                for (RoomSection roomSection : RoomSection.values()) {
                    Location adjustedLocation = roomSection.getLocationOffset(location);
                    switch (roomData.get(room).get(roomSection)) {
                        case ENTRANCE:
                            if (room.getRoomLocation().getY() == 0 || (dungeonGenerationMap.doesRoomExist(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)) && dungeonGenerationMap.isRoomNull(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)))) {
                                pasteSchematic("SubFloor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            }
                            pasteSchematic("Floor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            pasteSchematic("BigRoom_Support", editSession, random, adjustedLocation, roomSection.getEquivalent().getRotation());
                            break;
                        case WALL:
                            if (room.getRoomLocation().getY() == 0 || (dungeonGenerationMap.doesRoomExist(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)) && dungeonGenerationMap.isRoomNull(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)))) {
                                pasteSchematic("SubFloor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            }
                            pasteSchematic("Floor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            pasteSchematic("Stone_Filler", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            pasteSchematic("Support_Beam", editSession, random, adjustedLocation, roomSection.getEquivalent().getRotation());
                            for (RoomSection touchingSection : roomSection.getTouchingSections()) {
                                RoomDirection facingDirection = roomSection.getFacingDirection(touchingSection);
                                if (roomData.get(room).get(touchingSection) == BigRoomSection.EMPTY || roomData.get(room).get(touchingSection) == BigRoomSection.GROUND_FLOOR) {
                                    pasteSchematicWithAir("Engraved_Lower_Wall", editSession, random, adjustedLocation, facingDirection.reverse().getRotation());
                                    if (((BigRoom) room).isTopRoom()) {
                                        pasteSchematic("BigRoom_Rim", editSession, random, adjustedLocation, facingDirection.reverse().getRotation());
                                        pasteSchematicWithAir("BigRoom_Clear", editSession, random, adjustedLocation, facingDirection.reverse().getRotation());

                                    } else {
                                        pasteSchematicWithAir("BigRoom_Panel", editSession, random, adjustedLocation, facingDirection.reverse().getRotation());
                                    }
                                } else {
                                    if (roomData.get(room).get(touchingSection) == BigRoomSection.ENTRANCE) {
                                        pasteSchematicWithAir("Engraved_Lower_Wall", editSession, random, adjustedLocation, facingDirection.reverse().getRotation());
                                    }
                                }
                            }
                            break;
                        case GROUND_FLOOR:
                            if (room.getRoomLocation().getY() == 0 || (dungeonGenerationMap.doesRoomExist(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)) && dungeonGenerationMap.isRoomNull(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)))) {
                                pasteSchematic("SubFloor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            }
                            pasteSchematic("Floor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            break;
                    }

                }


                break;

        }


    }


    public int getBigRoomRotation(int bigRoomID) {
        switch (bigRoomID % 4) {
            case 0:
                return 0;
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return 270;
        }
        return 0;
    }
}


        /*
        switch(phase)
        {
            case PREBUILD:
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
                for(RoomSection roomSection: RoomSection.values())
                {
                    Location adjustedLocation = roomSection.getLocationOffset(location);
                    if(roomSection.isDiagnal())
                    {
                        pasteSchematic("Stone_Filler",editSession,random,adjustedLocation,random.nextInt(4) * 90);

                    }
                    if(roomSection.isCarnal())
                    {

                    }
                    if(roomSection == RoomSection.CENTER_SECTION)
                    {

                    }

                    Location adjustedLocation = roomSection.getLocationOffset(location);
                    if(!room.getRoomOrientation().getConnectDown().isOpen()) {
                        if(room.getRoomLocation().getY() == 0 || (dungeonGenerationMap.doesRoomExist(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN))&& dungeonGenerationMap.isRoomNull(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)))) {
                            pasteSchematic("SubFloor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                        }
                        pasteSchematic("Floor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                    }
                    else
                    {
                        pasteSchematic("Ceiling", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                    }

                }
                break;
        }
        */







