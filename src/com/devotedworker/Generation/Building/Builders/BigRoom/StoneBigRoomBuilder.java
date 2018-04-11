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
    private HashMap<BigRoom, RoomDirection> roomDirectionLadder;


    public StoneBigRoomBuilder() {
        roomData = new HashMap<>();
        completeStructure = new HashMap<>();
        roomDirectionLadder = new HashMap<>();
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


                    ArrayList<RoomDirection> upperPlatformDirections = new ArrayList<>();
                    for (RoomDirection roomDirection : RoomDirection.getFloorRoomDirections()) {
                        int[] rooms = getUpperBigRoomsInDirection(roomDirection);
                        if (completeStructure.get(primaryRoom).get(rooms[0]).getRoomOrientation().getDirectionConnection(roomDirection) == RoomConnection.ENTRANCE || completeStructure.get(primaryRoom).get(rooms[1]).getRoomOrientation().getDirectionConnection(roomDirection) == RoomConnection.ENTRANCE) {
                            if (roomDirection == RoomDirection.NORTH || roomDirection == RoomDirection.SOUTH) {
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[0])).put(RoomSection.CENTER_SECTION, BigRoomSection.UPPER_WALKWAY);
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[0])).put(RoomSection.EAST_SECTION, BigRoomSection.UPPER_WALKWAY);
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[1])).put(RoomSection.CENTER_SECTION, BigRoomSection.UPPER_WALKWAY);
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[1])).put(RoomSection.WEST_SECTION, BigRoomSection.UPPER_WALKWAY);
                            } else {
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[0])).put(RoomSection.CENTER_SECTION, BigRoomSection.UPPER_WALKWAY);
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[0])).put(RoomSection.SOUTH_SECTION, BigRoomSection.UPPER_WALKWAY);
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[1])).put(RoomSection.CENTER_SECTION, BigRoomSection.UPPER_WALKWAY);
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[1])).put(RoomSection.NORTH_SECTION, BigRoomSection.UPPER_WALKWAY);
                            }
                            upperPlatformDirections.add(roomDirection);
                        }

                    }

                    // Check that all shit connects
                    if (upperPlatformDirections.size() == 2) {
                        if (upperPlatformDirections.get(0).reverse() == upperPlatformDirections.get(1)) {
                            if (upperPlatformDirections.get(0) == RoomDirection.NORTH) {
                                int[] rooms;
                                if (random.nextBoolean()) {
                                    rooms = getUpperBigRoomsInDirection(RoomDirection.EAST);
                                    upperPlatformDirections.add(RoomDirection.EAST);
                                } else {
                                    rooms = getUpperBigRoomsInDirection(RoomDirection.WEST);
                                    upperPlatformDirections.add(RoomDirection.WEST);

                                }
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[0])).put(RoomSection.CENTER_SECTION, BigRoomSection.UPPER_WALKWAY);
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[0])).put(RoomSection.SOUTH_SECTION, BigRoomSection.UPPER_WALKWAY);
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[1])).put(RoomSection.CENTER_SECTION, BigRoomSection.UPPER_WALKWAY);
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[1])).put(RoomSection.NORTH_SECTION, BigRoomSection.UPPER_WALKWAY);
                            } else {
                                int[] rooms;
                                if (random.nextBoolean()) {
                                    rooms = getUpperBigRoomsInDirection(RoomDirection.NORTH);
                                    upperPlatformDirections.add(RoomDirection.NORTH);
                                } else {
                                    rooms = getUpperBigRoomsInDirection(RoomDirection.SOUTH);
                                    upperPlatformDirections.add(RoomDirection.SOUTH);

                                }
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[0])).put(RoomSection.CENTER_SECTION, BigRoomSection.UPPER_WALKWAY);
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[0])).put(RoomSection.EAST_SECTION, BigRoomSection.UPPER_WALKWAY);
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[1])).put(RoomSection.CENTER_SECTION, BigRoomSection.UPPER_WALKWAY);
                                roomData.get(completeStructure.get(primaryRoom).get(rooms[1])).put(RoomSection.WEST_SECTION, BigRoomSection.UPPER_WALKWAY);

                            }
                        }

                    }

                    if (upperPlatformDirections.size() != 0)
                    {

                        RoomDirection roomDirection = upperPlatformDirections.get(random.nextInt(upperPlatformDirections.size()));
                        BigRoom parentLadderRoom = completeStructure.get(primaryRoom).get(getUpperBigRoomsInDirection(roomDirection)[random.nextInt(2)]);
                        BigRoom ladderRoom = completeStructure.get(primaryRoom).get(parentLadderRoom.getRoomPos()%4);
                        roomData.get(ladderRoom).put(getLadderPlacementSection(ladderRoom.getRoomPos()),BigRoomSection.LADDER);
                        roomDirectionLadder.put(ladderRoom,roomDirection);

                    }




                }
                break;
            case BUILD:

                if (!((BigRoom) room).isTopRoom()) {

                    pasteSchematic("BigRoom_Ceiling", editSession, random, location, getBigRoomRotation(((BigRoom) room).getRoomPos()));


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
                                if (roomData.get(room).get(touchingSection) == BigRoomSection.EMPTY || roomData.get(room).get(touchingSection) == BigRoomSection.GROUND_FLOOR || roomData.get(room).get(touchingSection) == BigRoomSection.UPPER_WALKWAY) {
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
                        case UPPER_WALKWAY:
                            ArrayList<RoomDirection> roomDirections = new ArrayList<>();
                            for(RoomDirection roomDirection: RoomDirection.getFloorRoomDirections())
                            {
                                BigRoomSection bigRoomSection = roomData.get(room).get(RoomSection.getEquivalent(roomDirection));
                                if(bigRoomSection == BigRoomSection.UPPER_WALKWAY)
                                {
                                    roomDirections.add(roomDirection);
                                }
                            }


                            if(roomSection == RoomSection.CENTER_SECTION && roomDirections.size() == 2)
                            {
                                pasteSchematic("Upper_Walkway_Corner", editSession, random, adjustedLocation.clone().add(0,-10,0), getBigRoomRotation(((BigRoom) room).getRoomPos()));

                            }
                            else
                            {
                                RoomDirection designatedDirection = roomSection.getEquivalent();
                                if(roomSection == RoomSection.CENTER_SECTION)
                                {
                                    designatedDirection = roomDirections.get(0);
                                }
                                switch(designatedDirection)
                                {
                                    case NORTH:
                                        if(((BigRoom) room).getRoomPos() == 6)
                                        {
                                            pasteSchematic("Upper_Walkway", editSession, random, adjustedLocation.clone().add(0,-10,0), 270);
                                        }
                                        else
                                        {
                                            pasteSchematic("Upper_Walkway", editSession, random, adjustedLocation.clone().add(0,-10,0), 90);
                                        }
                                        // 6
                                        // 7
                                        break;
                                    case EAST:
                                        if(((BigRoom) room).getRoomPos() == 4)
                                        {
                                            pasteSchematic("Upper_Walkway", editSession, random, adjustedLocation.clone().add(0,-10,0), 0);
                                        }
                                        else
                                        {
                                            pasteSchematic("Upper_Walkway", editSession, random, adjustedLocation.clone().add(0,-10,0), 180);
                                        }
                                        // 4
                                        // 6
                                        break;
                                    case SOUTH:
                                        if(((BigRoom) room).getRoomPos() == 4)
                                        {
                                            pasteSchematic("Upper_Walkway", editSession, random, adjustedLocation.clone().add(0,-10,0), 270);
                                        }
                                        else
                                        {
                                            pasteSchematic("Upper_Walkway", editSession, random, adjustedLocation.clone().add(0,-10,0), 90);
                                        }
                                        // 4 5
                                        break;
                                    case WEST:
                                        if(((BigRoom) room).getRoomPos() == 5)
                                        {
                                            pasteSchematic("Upper_Walkway", editSession, random, adjustedLocation.clone().add(0,-10,0), 0);
                                        }
                                        else
                                        {
                                            pasteSchematic("Upper_Walkway", editSession, random, adjustedLocation.clone().add(0,-10,0), 180);
                                        }
                                        // 5 7
                                        break;
                                }
                            }





                            break;
                        case LADDER:
                            if (room.getRoomLocation().getY() == 0 || (dungeonGenerationMap.doesRoomExist(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)) && dungeonGenerationMap.isRoomNull(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)))) {
                                pasteSchematic("SubFloor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            }
                            pasteSchematic("Floor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            pasteSchematic("BigRoom_Ladder", editSession, random, adjustedLocation, roomDirectionLadder.get(room).getRotation());
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
                return 270;
            case 3:
                return 180;
        }
        return 0;
    }

    public RoomSection getLadderPlacementSection(int bigRoomID)
    {
        switch (bigRoomID % 4) {
            case 0:
                return RoomSection.SOUTHEAST_SECTION;
            case 1:
                return RoomSection.SOUTHWEST_SECTION;
            case 2:
                return RoomSection.NORTHEAST_SECTION;
            case 3:
                return RoomSection.NORTHWEST_SECTION;
        }
        return RoomSection.CENTER_SECTION;
    }

    public int[] getUpperBigRoomsInDirection(RoomDirection roomDirection)
    {
        switch(roomDirection)
        {
            case NORTH:
                return new int[]{4,5};
            case EAST:
                return new int[]{5,7};
            case SOUTH:
                return new int[]{6,7};
            case WEST:
                return new int[]{4,6};
        }
        return null;
    }
}


