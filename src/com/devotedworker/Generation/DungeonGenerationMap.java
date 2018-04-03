package com.devotedworker.Generation;

import com.devotedworker.Generation.Rooms.AbstractRoom;
import com.devotedworker.Generation.Utility.Enums.RoomType;
import com.devotedworker.Generation.Utility.RoomLocation;

import java.util.ArrayList;

public class DungeonGenerationMap {

    private AbstractRoom[][][] rooms;
    private ArrayList<RoomType> utilizedRooms;
    // X Z Y

    public DungeonGenerationMap(int width, int length, int height) {
        rooms = new AbstractRoom[width][length][height];
        utilizedRooms = new ArrayList<>();
    }

    public AbstractRoom getRoom(RoomLocation location)
    {
        return rooms[location.getX()][location.getZ()][location.getY()];
    }

    public void setRooms(RoomLocation location, AbstractRoom room)
    {
        rooms[location.getX()][location.getZ()][location.getY()] = room;
        if(room != null && !utilizedRooms.contains(room.getRoomType()))
        {
            utilizedRooms.add(room.getRoomType());
        }
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

    public boolean isRoomNull(RoomLocation location)
    {
        return (rooms[location.getX()][location.getZ()][location.getY()] == null);
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

    public int getRoomCapacity()
    {
        return getWidth() * getLength() * getHeight();
    }

    public ArrayList<RoomType> getUtilizedRooms() {
        return utilizedRooms;
    }
}
