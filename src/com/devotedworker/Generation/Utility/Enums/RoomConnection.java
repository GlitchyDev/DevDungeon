package com.devotedworker.Generation.Utility.Enums;

public enum RoomConnection {
    WALL,
    ENTRANCE,
    NONE;


    public boolean isOpen()
    {
        switch(this)
        {
            case WALL:
                return false;
            default:
                return true;
        }
    }
}
