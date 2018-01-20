package com.devotedworker.DungeonBuilding.Utility;

import com.devotedworker.DungeonBuilding.Enums.HallwayRoomMicroTypes;

public class SchematicSetup {
    private HallwayRoomMicroTypes hallwaySchem;
    private int rotation;

    public SchematicSetup(HallwayRoomMicroTypes hallwaySchem, int rotation)
    {
        this.hallwaySchem = hallwaySchem;
        this.rotation = rotation;
    }


    public HallwayRoomMicroTypes getHallwaySchem() {
        return hallwaySchem;
    }

    public int getRotation() {
        return rotation;
    }
}
