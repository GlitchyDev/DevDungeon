package com.devotedworker.Generation.Building;

import com.devotedworker.Generation.Utility.Enums.RoomDirection;
import com.devotedworker.plugin.DevDungeon;
import org.bukkit.Location;

import java.util.ArrayList;

public enum RoomSection {
    CENTER_SECTION,
    NORTH_SECTION,
    EAST_SECTION,
    SOUTH_SECTION,
    WEST_SECTION,
    NORTHEAST_SECTION,
    NORTHWEST_SECTION,
    SOUTHEAST_SECTION,
    SOUTHWEST_SECTION;


    public Location getLocationOffset(Location location)
    {
        Location clone = location.clone();
        switch(this)
        {
            case CENTER_SECTION:
                return clone.add(0,0,0);
            case NORTH_SECTION:
                return clone.add(0,0,-5);
            case EAST_SECTION:
                return clone.add(5,0,0);
            case SOUTH_SECTION:
                return clone.add(0,0,5);
            case WEST_SECTION:
                return clone.add(-5,0,0);
            case NORTHEAST_SECTION:
                return clone.add(5,0,-5);
            case NORTHWEST_SECTION:
                return clone.add(-5,0,-5);
            case SOUTHEAST_SECTION:
                return clone.add(5,0,5);
            case SOUTHWEST_SECTION:
                return clone.add(-5,0,5);
            default:
                return clone;
        }
    }

    public static RoomSection getEquivalent(RoomDirection roomDirection)
    {
        switch(roomDirection)
        {
            case NORTH:
                return NORTH_SECTION;
            case EAST:
                return EAST_SECTION;
            case SOUTH:
                return SOUTH_SECTION;
            case WEST:
                return WEST_SECTION;
            default:
                DevDungeon.log(roomDirection + " caused this!");
                return null;
        }
    }

    public RoomDirection getEquivalent()
    {
        switch(this)
        {
            case NORTH_SECTION:
                return RoomDirection.NORTH;
            case EAST_SECTION:
                return RoomDirection.EAST;
            case SOUTH_SECTION:
                return RoomDirection.SOUTH;
            case WEST_SECTION:
                return RoomDirection.WEST;
            default:
                return RoomDirection.NORTH;
        }
    }

    /**
     * Used to find the facing rotation between 2 touching room sections
     *
     */
    public RoomDirection getFacingDirection(RoomSection facingSection)
    {

        switch(this) {
            case NORTH_SECTION:
                switch(facingSection) {
                    case NORTHEAST_SECTION:
                        return RoomDirection.EAST;
                    case CENTER_SECTION:
                        return RoomDirection.SOUTH;
                    case NORTHWEST_SECTION:
                        return RoomDirection.WEST;
                }
            case NORTHEAST_SECTION:
                switch(facingSection) {
                    case EAST_SECTION:
                        return RoomDirection.SOUTH;
                    case NORTH_SECTION:
                        return RoomDirection.WEST;
                }
                break;
            case EAST_SECTION:
                switch(facingSection) {
                    case SOUTHEAST_SECTION:
                        return RoomDirection.SOUTH;
                    case CENTER_SECTION:
                        return RoomDirection.WEST;
                    case NORTHEAST_SECTION:
                        return RoomDirection.NORTH;
                }
                break;
            case SOUTHEAST_SECTION:
                switch(facingSection) {
                    case SOUTH_SECTION:
                        return RoomDirection.WEST;
                    case EAST_SECTION:
                        return RoomDirection.NORTH;
                }
                break;
            case SOUTH_SECTION:
                switch(facingSection) {
                    case SOUTHWEST_SECTION:
                        return RoomDirection.WEST;
                    case CENTER_SECTION:
                        return RoomDirection.NORTH;
                    case SOUTHEAST_SECTION:
                        return RoomDirection.EAST;
                }
                break;
            case SOUTHWEST_SECTION:
                switch(facingSection) {
                    case WEST_SECTION:
                        return RoomDirection.NORTH;
                    case SOUTH_SECTION:
                        return RoomDirection.EAST;
                }
                break;
            case WEST_SECTION:
                switch(facingSection) {
                    case NORTHWEST_SECTION:
                        return RoomDirection.NORTH;
                    case CENTER_SECTION:
                        return RoomDirection.EAST;
                    case SOUTHWEST_SECTION:
                        return RoomDirection.SOUTH;
                }
                break;
             case NORTHWEST_SECTION:
                switch(facingSection) {
                    case NORTH_SECTION:
                        return RoomDirection.EAST;
                    case WEST_SECTION:
                        return RoomDirection.SOUTH;
                }
                break;
            case CENTER_SECTION:
                switch(facingSection) {
                    case NORTH_SECTION:
                        return RoomDirection.NORTH;
                    case EAST_SECTION:
                        return RoomDirection.EAST;
                    case SOUTH_SECTION:
                        return RoomDirection.SOUTH;
                    case WEST_SECTION:
                        return RoomDirection.WEST;
                }
                break;
        }




        DevDungeon.log("ERROR FACING ROTATION");
        return null;
    }


    public RoomSection[] getTouchingSections()
    {
        switch(this)
        {
            case NORTH_SECTION:
                return new RoomSection[]{NORTHEAST_SECTION,CENTER_SECTION,NORTHWEST_SECTION};
            case NORTHEAST_SECTION:
                return new RoomSection[]{NORTH_SECTION,EAST_SECTION};
            case EAST_SECTION:
                return new RoomSection[]{NORTHEAST_SECTION,CENTER_SECTION,SOUTHEAST_SECTION};
            case SOUTHEAST_SECTION:
                return new RoomSection[]{EAST_SECTION,SOUTH_SECTION};
            case SOUTH_SECTION:
                return new RoomSection[]{SOUTHWEST_SECTION,CENTER_SECTION,SOUTHEAST_SECTION};
            case SOUTHWEST_SECTION:
                return new RoomSection[]{SOUTH_SECTION,WEST_SECTION};
            case WEST_SECTION:
                return new RoomSection[]{SOUTHWEST_SECTION,CENTER_SECTION,NORTHWEST_SECTION};
            case NORTHWEST_SECTION:
                return new RoomSection[]{WEST_SECTION,NORTH_SECTION};
        }
        return new RoomSection[0];
    }

    public ArrayList<RoomDirection> getTouchingSectionDirections()
    {
        ArrayList<RoomDirection> directions = new ArrayList<>();
        for(RoomSection roomSection: this.getTouchingSections())
        {
            directions.add(this.getFacingDirection(roomSection));
        }
        return directions;
    }


    public static RoomSection[] getAllNonCenter()
    {
        return new RoomSection[]{NORTH_SECTION,NORTHEAST_SECTION,EAST_SECTION,SOUTHEAST_SECTION,SOUTH_SECTION,SOUTHWEST_SECTION,WEST_SECTION,NORTHWEST_SECTION};
    }

    public static RoomSection[] getCarnal()
    {
        return new RoomSection[]{NORTH_SECTION,EAST_SECTION,SOUTH_SECTION,WEST_SECTION};

    }

    public static RoomSection[] getDiagonal()
    {
        return new RoomSection[]{NORTHEAST_SECTION,SOUTHEAST_SECTION,SOUTHWEST_SECTION,NORTHWEST_SECTION};

    }


    public boolean isCarnal()
    {
        switch(this)
        {
            case NORTH_SECTION:
            case EAST_SECTION:
            case SOUTH_SECTION:
            case WEST_SECTION:
                return true;
            default:
                return false;
        }
    }

    public boolean isDiagnal()
    {
        switch(this)
        {
            case NORTHEAST_SECTION:
            case NORTHWEST_SECTION:
            case SOUTHEAST_SECTION:
            case SOUTHWEST_SECTION:
                return true;
            default:
                return false;
        }
    }
}
