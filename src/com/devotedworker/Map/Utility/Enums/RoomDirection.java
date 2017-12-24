package com.devotedworker.Map.Utility.Enums;

import java.util.ArrayList;
import java.util.Arrays;

public enum RoomDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST,
    UP,
    DOWN;

    public boolean isVertical()
    {
        return (this != UP || this != DOWN);
    }

    public RoomDirection reverse()
    {
        switch(this)
        {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            default:
                return this;
        }
    }

    public static ArrayList<RoomDirection> getFloorRoomDirections()
    {
        ArrayList<RoomDirection> directions = new ArrayList<>();
        directions.add(NORTH);
        directions.add(EAST);
        directions.add(SOUTH);
        directions.add(WEST);
        return directions;
    }

}
