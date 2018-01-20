package com.devotedworker.DungeonBuilding.Builders;

import com.devotedworker.DungeonBuilding.Enums.RoomPosition;
import com.devotedworker.GenerationMap.Dungeon;
import com.devotedworker.GenerationMap.Rooms.HallwayRoom;
import com.devotedworker.GenerationMap.Utility.Enums.RoomConnection;
import com.devotedworker.GenerationMap.Utility.Enums.RoomDirection;
import com.devotedworker.GenerationMap.Utility.RoomLocation;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Random;

public class HallwayBuilder extends AbstractBuilder {

    public static void buildRoom(EditSession buildSession, Random random, Location roomBase, Dungeon dungeon, int x, int z, int y)
    {
        Location location = roomBase.clone();
        HallwayRoom room = (HallwayRoom) dungeon.getRoom(new RoomLocation(x,z,y));

        buildHallwayCenter(room,buildSession,random,location);

        for(RoomPosition position: RoomPosition.getCardinalDirections())
        {
            buildHallwayCardinal(room,buildSession,random,roomBase,position);
        }
        for(RoomPosition position: RoomPosition.getDiagnalDirections())
        {
            buildHallwayDiagonal(room,buildSession,random,roomBase,position);
        }
    }



    public static void buildHallwayCardinal(HallwayRoom room, EditSession buildSession, Random random,  Location roomBase, RoomPosition position)
    {
        switch(room.getRoomOrientation().getDirectionConnection(position.getRoomDirection()))
        {
            case WALL:
                if(random.nextInt(5) == 0)
                {
                    pasteSchematic("HALLWAY_CORNER_SINGLE_GATE", buildSession, RoomPosition.getLocationOffset(roomBase, position), position.getRotation());
                }
                else {
                    pasteSchematic("HALLWAY_WALL_FILLED", buildSession, RoomPosition.getLocationOffset(roomBase, position), position.getRotation());
                }
                break;
            case ENTRANCE:
                pasteSchematic("HALLWAY_FLOOR",buildSession,RoomPosition.getLocationOffset(roomBase,position),0);
                pasteSchematic("HALLWAY_CEILING",buildSession,RoomPosition.getLocationOffset(roomBase,position),0);

                break;
        }

    }

    public static void buildHallwayDiagonal(HallwayRoom room, EditSession buildSession, Random random,  Location roomBase, RoomPosition position)
    {
        if(position.isCornerVisible(room))
        {
            // Add code to distinguish cracked walls and holes
            if(random.nextInt(2) == 0)
            {
                if(random.nextInt(2) == 0)
                {
                    pasteSchematic("HALLWAY_CORNER_DOUBLE_GATE", buildSession, RoomPosition.getLocationOffset(roomBase, position), position.getRotation());
                }
                else
                {
                    int mod = random.nextInt(2) * 90;
                    pasteSchematic("HALLWAY_CORNER_SINGLE_GATE", buildSession, RoomPosition.getLocationOffset(roomBase, position), position.getRotation() + mod);

                }
                switch(position)
                {
                    case NORTH_EAST:
                        pasteSchematic("HALLWAY_SUPPORTBEAM_I", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.NORTH), RoomPosition.NORTH.getRotation());
                        pasteSchematic("HALLWAY_SUPPORTBEAM_I", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.EAST), RoomPosition.EAST.getRotation());
                        break;
                    case NORTH_WEST:
                        pasteSchematic("HALLWAY_SUPPORTBEAM_I", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.NORTH), RoomPosition.NORTH.getRotation());
                        pasteSchematic("HALLWAY_SUPPORTBEAM_I", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.WEST), RoomPosition.WEST.getRotation());
                        break;
                    case SOUTH_EAST:
                        pasteSchematic("HALLWAY_SUPPORTBEAM_I", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.SOUTH), RoomPosition.SOUTH.getRotation());
                        pasteSchematic("HALLWAY_SUPPORTBEAM_I", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.EAST), RoomPosition.EAST.getRotation());
                        break;
                    case SOUTH_WEST:
                        pasteSchematic("HALLWAY_SUPPORTBEAM_I", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.SOUTH), RoomPosition.SOUTH.getRotation());
                        pasteSchematic("HALLWAY_SUPPORTBEAM_I", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.WEST), RoomPosition.WEST.getRotation());
                        break;
                }
            }
            else {
                pasteSchematic("HALLWAY_CORNER_FILLED", buildSession, RoomPosition.getLocationOffset(roomBase, position), position.getRotation());
            }
        }
        else
        {
            pasteSchematic("HALLWAY_CORNER_FILLED",buildSession,RoomPosition.getLocationOffset(roomBase,position),0);
        }

    }


    public static void buildHallwayCenter(HallwayRoom room, EditSession buildSession, Random random,  Location roomBase)
    {
        ArrayList<RoomDirection> openDirections = new ArrayList<>();

        for(RoomDirection direction: RoomDirection.getFloorRoomDirections())
        {
            if(room.getRoomOrientation().getDirectionConnection(direction) == RoomConnection.ENTRANCE)
            {
                openDirections.add(direction);
            }
        }

        RoomDirection firstDirection = openDirections.get(0);
        switch(openDirections.size())
        {
            case 1:
                pasteSchematic("HALLWAY_SUPPORTBEAM_END",buildSession,RoomPosition.getLocationOffset(roomBase,RoomPosition.CENTER),firstDirection.getRotation());
                break;
            case 2:
                RoomDirection secondaryDirection = openDirections.get(0);
                if(firstDirection.reverse() == secondaryDirection) {
                    if(firstDirection == RoomDirection.NORTH)
                    {
                        pasteSchematic("HALLWAY_SUPPORTBEAM_I", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.CENTER), 0);
                    }
                    else {
                        pasteSchematic("HALLWAY_SUPPORTBEAM_I", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.CENTER), 90);
                    }
                }
                else {
                    switch (firstDirection) {
                        case NORTH:
                            if (openDirections.get(1) == RoomDirection.EAST) {
                                pasteSchematic("HALLWAY_SUPPORTBEAM_L", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.CENTER), 0);
                            } else {
                                pasteSchematic("HALLWAY_SUPPORTBEAM_L", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.CENTER), 270);
                            }
                            break;
                        case EAST:
                            pasteSchematic("HALLWAY_SUPPORTBEAM_L", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.CENTER), 90);
                            break;
                        case SOUTH:
                            pasteSchematic("HALLWAY_SUPPORTBEAM_L", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.CENTER), 180);
                            break;
                    }
                }
                break;
            case 3:
                if(openDirections.get(1) == RoomDirection.EAST)
                {
                    if(openDirections.get(2) == RoomDirection.WEST)
                    {
                        pasteSchematic("HALLWAY_SUPPORTBEAM_T", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.CENTER), 0);
                    }
                    else {
                        pasteSchematic("HALLWAY_SUPPORTBEAM_T", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.CENTER), 90);
                    }
                }
                else {
                    if(openDirections.get(0) == RoomDirection.EAST)
                    {
                        pasteSchematic("HALLWAY_SUPPORTBEAM_T", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.CENTER), 180);
                    }
                    else {
                        pasteSchematic("HALLWAY_SUPPORTBEAM_T", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.CENTER), 270);
                    }

                }
                break;
            case 4:
                pasteSchematic("HALLWAY_SUPPORTBEAM_X", buildSession, RoomPosition.getLocationOffset(roomBase, RoomPosition.CENTER), 0);
                break;
        }

        pasteSchematic("HALLWAY_FLOOR",buildSession,RoomPosition.getLocationOffset(roomBase,RoomPosition.CENTER),0);
        pasteSchematic("HALLWAY_CEILING",buildSession,RoomPosition.getLocationOffset(roomBase,RoomPosition.CENTER),0);


    }



}


/**
 Location location = roomBase.clone();

 HallwayRoom room = (HallwayRoom) dungeon.getRoom(new RoomLocation(x,z,y));


 for(RoomPosition position: RoomPosition.values())
 {
 if(position.isDiagnal())
 {
 pasteSchematic("Hallway_Corner_Normal",buildSession,RoomPosition.getLocationOffset(location,position),position.getRotation());
 pasteSchematic("Hallway_Corner_Normal",buildSession,RoomPosition.getLocationOffset(location,position),position.getReversedRotation());
 pasteSchematic("Hallway_Floor_Normal",buildSession,RoomPosition.getLocationOffset(location,position),0);
 }
 else
 {
 if(position != RoomPosition.CENTER) {
 switch (room.getRoomOrientation().getDirectionConnection(position.getRoomDirection()))
 {
 case ENTRANCE:
 pasteSchematic("Hallway_Door_Normal",buildSession,RoomPosition.getLocationOffset(location,position),position.getRotation());
 break;
 case WALL:
 pasteSchematic("Hallway_Wall_Normal",buildSession,RoomPosition.getLocationOffset(location,position),position.getReversedRotation());
 break;
 }
 }
 pasteSchematic("Hallway_Floor_Normal",buildSession,RoomPosition.getLocationOffset(location,position),0);

 }
 }
 **/