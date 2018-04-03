package com.devotedworker.Generation.Building.Builders.StoneHallway;

public enum HallwayRoomSection {
    CENTER,
    ORIGINALPATH,
    ADDEDPATH,
    WALL,
    UNUSUED;


    public boolean isSolid()
    {
        switch(this)
        {
            case WALL:
            case UNUSUED:
                return true;
        }
        return false;
    }


}
