package com.devotedworker.Old.DungeonBuilding.Enums;

import com.devotedworker.Old.GenerationMap.Rooms.HallwayRoom;
import com.devotedworker.Old.GenerationMap.Utility.Enums.RoomDirection;
import org.bukkit.Location;

import java.util.ArrayList;

public enum RoomPosition {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST,
    CENTER;

    public boolean isDiagnal()
    {
        return this == NORTH_EAST || this == NORTH_WEST || this == SOUTH_EAST || this == SOUTH_WEST;
    }

    public static ArrayList<RoomPosition> getCardinalDirections()
    {
        ArrayList<RoomPosition> directions = new ArrayList<>();
        directions.add(NORTH);
        directions.add(EAST);
        directions.add(SOUTH);
        directions.add(WEST);
        return directions;
    }

    public static ArrayList<RoomPosition> getDiagnalDirections()
    {
        ArrayList<RoomPosition> directions = new ArrayList<>();
        directions.add(NORTH_EAST);
        directions.add(NORTH_WEST);
        directions.add(SOUTH_EAST);
        directions.add(SOUTH_WEST);
        return directions;
    }

    public static Location getLocationOffset(Location location, RoomPosition roomPostion)
    {
        Location returnLocation = location.clone();
        switch(roomPostion)
        {
            case NORTH:
                return returnLocation.add(0,0,-5);
            case NORTH_EAST:
                return returnLocation.add(5,0,-5);
            case EAST:
                return returnLocation.add(5,0,0);
            case SOUTH_EAST:
                return returnLocation.add(5,0,+5);
            case SOUTH:
                return returnLocation.add(0,0,5);
            case SOUTH_WEST:
                return returnLocation.add(-5,0,+5);
            case WEST:
                return returnLocation.add(-5,0,0);
            case NORTH_WEST:
                return returnLocation.add(-5,0,-5);
            case CENTER:
                return returnLocation;
            default:
                return returnLocation;
        }
    }

    public int getRotation()
    {
        switch(this)
        {
            case NORTH:
                return 0;
            case NORTH_EAST:
                return 0;
            case EAST:
                return 90;
            case SOUTH_EAST:
                return 90;
            case SOUTH:
                return 180;
            case SOUTH_WEST:
                return 180;
            case WEST:
                return 270;
            case NORTH_WEST:
                return 270;
            case CENTER:
                return 0;
            default:
                return 0;
        }
    }

    public int getReversedRotation()
    {
        switch(this)
        {
            case NORTH:
                return 180;
            case NORTH_EAST:
                return 180;
            case EAST:
                return 270;
            case SOUTH_EAST:
                return 270;
            case SOUTH:
                return 0;
            case SOUTH_WEST:
                return 0;
            case WEST:
                return 90;
            case NORTH_WEST:
                return 90;
            case CENTER:
                return 0;
            default:
                return 0;
        }
    }

    public RoomDirection getRoomDirection()
    {
        switch(this)
        {
            case NORTH:
                return RoomDirection.NORTH;
            case EAST:
                return RoomDirection.EAST;
            case SOUTH:
                return RoomDirection.SOUTH;
            case WEST:
                return RoomDirection.WEST;
            default:
                return RoomDirection.NORTH;
        }
    }

    public boolean isCornerVisible(HallwayRoom room)
    {
        switch(this)
        {
            case NORTH_EAST:
                return room.getRoomOrientation().getDirectionConnection(RoomDirection.NORTH).isOpen() && room.getRoomOrientation().getDirectionConnection(RoomDirection.EAST).isOpen();
            case NORTH_WEST:
                return room.getRoomOrientation().getDirectionConnection(RoomDirection.NORTH).isOpen() && room.getRoomOrientation().getDirectionConnection(RoomDirection.WEST).isOpen();
            case SOUTH_EAST:
                return room.getRoomOrientation().getDirectionConnection(RoomDirection.SOUTH).isOpen() && room.getRoomOrientation().getDirectionConnection(RoomDirection.EAST).isOpen();
            case SOUTH_WEST:
                return room.getRoomOrientation().getDirectionConnection(RoomDirection.SOUTH).isOpen() && room.getRoomOrientation().getDirectionConnection(RoomDirection.WEST).isOpen();
        }
        return false;
    }


}
