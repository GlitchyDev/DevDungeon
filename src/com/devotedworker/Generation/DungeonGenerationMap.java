package com.devotedworker.Generation;

import com.devotedworker.Generation.Rooms.AbstractRoom;
import com.devotedworker.Generation.Utility.RoomLocation;

public class DungeonGenerationMap {

    private AbstractRoom[][][] rooms;
    // X Z Y

    public DungeonGenerationMap(int width, int length, int height) {
        rooms = new AbstractRoom[width][length][height];
    }

    public AbstractRoom getRoom(RoomLocation location)
    {
        return rooms[location.getX()][location.getZ()][location.getY()];
    }

    public void setRooms(RoomLocation location, AbstractRoom room)
    {
        rooms[location.getX()][location.getZ()][location.getY()] = room;
    }

    public boolean doesRoomExist(RoomLocation location)
    {
        if(location.getX() < getWidth() && location.getX() >= 0)
        {
            if(location.getZ() < getLength() && location.getZ() >= 0)
            {
                if(location.getY() < getHeight() && location.getY() >= 0)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public AbstractRoom[][][] getRooms() {
        return rooms;
    }

    public int getWidth() {
        return rooms.length;
    }

    public int getLength() {
        return rooms[0].length;
    }

    public int getHeight() {
        return rooms[0][0].length;
    }
}
