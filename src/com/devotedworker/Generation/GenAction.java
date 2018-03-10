package com.devotedworker.Generation;

import com.devotedworker.Generation.Generators.GeneratorType;

/**
 * A representation of the Actions any given Generator can make. The Actions are marked with their Native GeneratorType by Name
 */
public enum GenAction {
    GStoneHallway(GeneratorType.STONEHALLWAY), // Generate
    CStoneHallway(GeneratorType.STONEHALLWAY), // Clear ( dead ends )

    GStoneBigRoom(GeneratorType.STONEBIGROOM), // Generate
    FStoneBigRoom(GeneratorType.STONEBIGROOM), // Fix
    GTreasureRoomRoom(GeneratorType.TREASUREROOM); // Generate


    private final GeneratorType nativeGenerator;

    GenAction(GeneratorType nativeGenerator) {
        this.nativeGenerator = nativeGenerator;
    }


    public GeneratorType getNativeGenerator() {
        return nativeGenerator;
    }
}
