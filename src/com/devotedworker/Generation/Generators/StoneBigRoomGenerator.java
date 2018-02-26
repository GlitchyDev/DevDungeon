package com.devotedworker.Generation.Generators;

import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.GenAction;
import com.devotedworker.Generation.Rooms.BigRoom;
import com.devotedworker.Generation.Utility.RoomLocation;

import java.util.HashMap;
import java.util.Random;

public class StoneBigRoomGenerator extends AbstractRoomGenerator {


    @Override
    public void geneneratorActions(DungeonGenerationMap generationMap, HashMap<GeneratorType, AbstractRoomGenerator> generators, GenAction genAction, Random random) {

        switch(genAction)
        {
            case GStoneBigRoom:
                for(int i = 0; i < 1000; i++)
                {
                    int x = random.nextInt(generationMap.getWidth());
                    int z = random.nextInt(generationMap.getLength());
                    int y = random.nextInt(generationMap.getHeight());

                    generationMap.setRooms(new RoomLocation(x,z,y),new BigRoom(generationMap,new RoomLocation(x,z,y),random.nextInt(4)));
                }
                break;
            case FStoneBigRoom:
                break;
        }

    }
}
