package com.devotedworker.Old.DungeonBuilding.Enums;

public enum HallwayRoomMicroTypes {

    // Used to generate the cobble walls
    // Use individual schematics
    HALLWAY_SUPPORTBEAM_X, // No rotation required
    HALLWAY_SUPPORTBEAM_T, // Middle sticks north
    HALLWAY_SUPPORTBEAM_L, // Down lace left
    HALLWAY_SUPPORTBEAM_I, //  Up down
    HALLWAY_SUPPORTBEAM_End,

    // Used to propagate the corners
    // Probably use base schematics
    HALLWAY_CORNER_FILLER,
    HALLWAY_CORNER_FILLED,
    HALLWAY_CORNER_FILLED_CRACKED,
    HALLWAY_CORNER_FILLED_HOLE, // Make sure it faces the right way, since its in a corner ( Direction facing api? )
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
