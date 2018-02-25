package com.devotedworker.Generation.Utility.Enums;

import java.util.ArrayList;
import java.util.Random;

public enum RoomDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST,
    UP,
    DOWN;

    public boolean isVertical() {
        return (this != UP || this != DOWN);
    }

    public RoomDirection reverse() {
        switch (this) {
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

    public static ArrayList<RoomDirection> getFloorRoomDirections() {
        ArrayList<RoomDirection> directions = new ArrayList<>();
        directions.add(NORTH);
        directions.add(EAST);
        directions.add(SOUTH);
        directions.add(WEST);
        return directions;
    }

    public static RoomDirection randomDirection(Random random) {
        switch (random.nextInt(4)) {
            case 0:
                return NORTH;
            case 1:
                return EAST;
            case 2:
                return SOUTH;
            case 3:
                return WEST;
        }
        return NORTH;
    }

    public int getRotation() {
        switch (this) {
            case NORTH:
                return 0;
            case EAST:
                return 90;
            case SOUTH:
                return 180;
            case WEST:
                return 270;
        }
        return 0;
    }


}
