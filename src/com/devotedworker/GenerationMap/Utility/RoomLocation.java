package com.devotedworker.GenerationMap.Utility;

import com.devotedworker.GenerationMap.Utility.Enums.RoomDirection;

public class RoomLocation {
    private int x;
    private int z;
    private int y;

    public RoomLocation(int x, int z, int y)
    {
        this.x = x;
        this.z = z;
        this.y = y;
    }

    public RoomLocation getDirectionLocation(RoomDirection direction)
    {
        switch(direction)
        {
            case NORTH:
                return getNorth();
            case EAST:
                return getEast();
            case SOUTH:
                return getSouth();
            case WEST:
                return getWest();
            case UP:
                return getUp();
            case DOWN:
                return getDown();
            default:
                return this;

        }
    }
    public RoomLocation getUp()
    {
        return new RoomLocation(x,z,y+1);
    }
    public RoomLocation getDown()
    {
        return new RoomLocation(x,z,y-1);
    }
    public RoomLocation getNorth()
    {
        return new RoomLocation(x,z-1,y);
    }
    public RoomLocation getSouth() { return new RoomLocation(x,z+1,y); }
    public RoomLocation getEast()
    {
        return new RoomLocation(x+1,z,y);
    }
    public RoomLocation getWest()
    {
        return new RoomLocation(x-1,z,y);
    }

    public RoomLocation getRelative(int relativeX, int relativeZ, int relativeY)
    {
        return new RoomLocation(x + relativeX, z + relativeZ, y + relativeY);
    }

    public double getDistance(RoomLocation location)
    {
        return Math.sqrt(Math.pow(this.x - location.getX(),2) + Math.pow(this.z - location.getZ(),2) + Math.pow(this.y - location.getY(),2));
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getY() {
        return y;
    }
}
