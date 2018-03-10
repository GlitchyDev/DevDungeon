package com.devotedworker.Generation.Generators;

public enum GeneratorType {
    STONEBIGROOM,
    STONEHALLWAY,
    TREASUREROOM;


    public AbstractRoomGenerator getGenerator()
    {
        switch(this)
        {
            case STONEHALLWAY:
                return new StoneHallwayGenerator();
            case STONEBIGROOM:
                return new StoneBigRoomGenerator();
            case TREASUREROOM:
                return new TreasureRoomGenerator();
            default:
                return null;
        }
    }
}
