package com.devotedworker.Generation;

import com.devotedworker.Generation.Rooms.AbstractRoom;
import com.devotedworker.Generation.Utility.RoomLocation;

public class DungeonGenerationMap {

    private AbstractRoom[][][] rooms;
    private int width;
    private int length;
    private int height;
    // Y -> X -> Z

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
        if(location.getX() < width && location.getX() >= 0)
        {
            if(location.getZ() < length && location.getZ() >= 0)
            {
                if(location.getY() < height && location.getY() >= 0)
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
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }
}
