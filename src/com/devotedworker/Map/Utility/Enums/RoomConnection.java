package com.devotedworker.Map.Utility.Enums;

public enum RoomConnection {
    ABSOLUTE_WALL,
    POSSIBLE_ENTRANCE,
    ABSOLUTE_ENTRANCE,
    NONE;


    public boolean isConnectable()
    {
        return (this == POSSIBLE_ENTRANCE || this == ABSOLUTE_ENTRANCE || this == NONE);
    }
}
