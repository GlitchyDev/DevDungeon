package com.devotedworker.Generation.Utility;

import com.devotedworker.Generation.Utility.Enums.RoomConnection;
import com.devotedworker.Generation.Utility.Enums.RoomDirection;


import java.util.ArrayList;

/**
 * Used to determine how a room connects to other rooms for the purpose of generation
 */
public class RoomOrientation {
    private RoomConnection connectNorth;
    private RoomConnection connectEast;
    private RoomConnection connectSouth;
    private RoomConnection connectWest;
    private RoomConnection connectUp;
    private RoomConnection connectDown;
    private ArrayList<RoomLocation> customConnections;

    public RoomOrientation()
    {
        this.connectNorth = RoomConnection.WALL;
        this.connectEast = RoomConnection.WALL;
        this.connectSouth = RoomConnection.WALL;
        this.connectWest = RoomConnection.WALL;
        this.connectUp = RoomConnection.WALL;
        this.connectDown = RoomConnection.WALL;
        customConnections = new ArrayList<>();
    }






    public RoomConnection getDirectionConnection(RoomDirection direction)
    {
        switch(direction)
        {
            case NORTH:
                return getConnectNorth();
            case EAST:
                return getConnectEast();
            case SOUTH:
                return getConnectSouth();
            case WEST:
                return getConnectWest();
            case UP:
                return getConnectUp();
            case DOWN:
                return getConnectDown();
            default:
                return getConnectNorth();

        }
    }

    public void setDirectionConnection(RoomDirection direction, RoomConnection connection)
    {
        switch(direction)
        {
            case NORTH:
                setConnectNorth(connection);
                break;
            case EAST:
                setConnectEast(connection);
                break;
            case SOUTH:
                setConnectSouth(connection);
                break;
            case WEST:
                setConnectWest(connection);
                break;
            case UP:
                setConnectUp(connection);
                break;
            case DOWN:
                setConnectSouth(connection);
                break;
        }
    }



    // Basic Getter and Setter Methods
    public RoomConnection getConnectNorth() {
        return connectNorth;
    }

    public void setConnectNorth(RoomConnection connectNorth) {
        this.connectNorth = connectNorth;
    }

    public RoomConnection getConnectEast() {
        return connectEast;
    }

    public void setConnectEast(RoomConnection connectEast) {
        this.connectEast = connectEast;
    }

    public RoomConnection getConnectSouth() {
        return connectSouth;
    }

    public void setConnectSouth(RoomConnection connectSouth) {
        this.connectSouth = connectSouth;
    }

    public RoomConnection getConnectWest() {
        return connectWest;
    }

    public void setConnectWest(RoomConnection connectWest) {
        this.connectWest = connectWest;
    }

    public RoomConnection getConnectUp() {
        return connectUp;
    }

    public void setConnectUp(RoomConnection connectUp) {
        this.connectUp = connectUp;
    }

    public RoomConnection getConnectDown() {
        return connectDown;
    }

    public void setConnectDown(RoomConnection connectDown) {
        this.connectDown = connectDown;
    }

    public ArrayList<RoomLocation> getCustomConnections() {
        return customConnections;
    }
}
