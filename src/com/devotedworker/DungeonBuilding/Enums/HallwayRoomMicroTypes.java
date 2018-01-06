package com.devotedworker.DungeonBuilding.Enums;

public enum HallwayRoomMicroTypes {

    // Used to generate the cobble walls
    // Use individual schematics
    HALLWAY_SUPPORTBEAM_4WAY,
    HALLWAY_SUPPORTBEAM_3WAY,
    HALLWAY_SUPPORT_L,
    HALLWAY_SUPPORT_STRAIGHT,

    // Used to propagate the corners
    // Probably use base schematics
    HALLYWAY_CORNER_FILLED,
    HALLWAY_CORNER_FILLED_CRACKED,
    HALLWAY_CORNER_FILLED_HOLE,
    HALLWAY_CORNER_SINGLE_GATE,
    HALLWAY_CORNER_SINGLE_GATE_DAMAGED,
    HALLWAY_CORNER_SINGLE_GATE_HOLE,  // Possible resource/exit generation
    HALLWAY_CORNER_DOUBLE_GATE,
    HALLWAY_CORNER_DOUBLE_GATE_CRACKED,
    HALLWAY_CORNER_DOUBLE_GATE_HOLE, // Possible resource/exit generation

    // Used to propagate walls
    HALLWAY_WALL_FILLED,
    HALLWAY_WALL_FILLED_CRACKED,
    HALLWAY_WALL_FILLED_HOLE


}
