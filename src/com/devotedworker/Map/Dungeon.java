package com.devotedworker.Map;

import com.devotedworker.Map.Generators.BigRoomGenerator;
import com.devotedworker.Map.Generators.HallwayRoomGenerator;
import com.devotedworker.Map.Rooms.AbstractRoom;
import com.devotedworker.Map.Rooms.HallwayRoom;
import com.devotedworker.Map.Utility.RoomLocation;

import java.util.Random;

public class Dungeon {

    private int width;
    private int length;
    private int height;
    private AbstractRoom[][][] rooms;
    // Y -> X -> Z

    public Dungeon(int width, int length, int height) {
        this.width = width;
        this.length = length;
        this.height = height;
        rooms = new AbstractRoom[width][length][height];
        Random random = new Random();
        BigRoomGenerator.generateRooms(this, random);
        HallwayRoomGenerator.generateRooms(this, random);
    }
    public Dungeon(int width, int length, int height, long seed) {
        this.width = width;
        this.length = length;
        this.height = height;
        rooms = new AbstractRoom[width][length][height];
        Random random = new Random(seed);
        BigRoomGenerator.generateRooms(this, random);
        HallwayRoomGenerator.generateRooms(this, random);
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
