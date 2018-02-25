package com.devotedworker.Generation.Generators;

public enum GeneratorType {
    STONEBIGROOM,
    STONEHALLWAY;


    public AbstractRoomGenerator getGenerator()
    {
        switch(this)
        {
            case STONEHALLWAY:
                return new StoneHallwayGenerator();
            case STONEBIGROOM:
                return new StoneBigRoomGenerator();
            default:
                return null;
        }
    }
}
