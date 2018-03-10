package com.devotedworker.Generation.Generators;

import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.GenAction;
import com.devotedworker.Generation.Rooms.BigRoom;
import com.devotedworker.Generation.Rooms.TreasureRoom;
import com.devotedworker.Generation.Utility.Enums.RoomConnection;
import com.devotedworker.Generation.Utility.Enums.RoomDirection;
import com.devotedworker.Generation.Utility.RoomLocation;
import com.devotedworker.plugin.DevDungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TreasureRoomGenerator extends AbstractRoomGenerator {



    @Override
    public void geneneratorActions(DungeonGenerationMap generationMap, HashMap<GeneratorType, AbstractRoomGenerator> generators, GenAction genAction, Random random) {
        final double conversionPercentage = 0.08;

        ArrayList<BigRoom> bigRoomList = (ArrayList<BigRoom>) generators.get(GeneratorType.STONEBIGROOM).createdRooms.clone();

     //   DevDungeon.log("TreasureRoomGen: Starting Attaching");
     //   PerformanceUtility.startLogging("TreasureRoomAttach");
        for(int i = 0; i < bigRoomList.size() * conversionPercentage; i++)
        {
            int index = random.nextInt(bigRoomList.size());
            BigRoom selectedRoom = bigRoomList.get(index);

            ArrayList<RoomDirection> openDirections = new ArrayList<>();
            for(RoomDirection roomDirection: RoomDirection.getFloorRoomDirections())
            {
                // Find valid spawning walls
                if(selectedRoom.getRoomOrientation().getDirectionConnection(roomDirection) == RoomConnection.WALL)
                {
                    openDirections.add(roomDirection);
                }
            }

            if(openDirections.size() != 0) {
                RoomDirection selectedDirection = openDirections.get(random.nextInt(openDirections.size()));
                RoomLocation selectedSpawningArea = selectedRoom.getRoomLocation().getDirectionLocation(selectedDirection);

                if (generationMap.doesRoomExist(selectedSpawningArea) && generationMap.isRoomNull(selectedSpawningArea)) {
                    createRoom(generationMap, selectedSpawningArea, new TreasureRoom(selectedSpawningArea, selectedDirection.reverse()));
                    selectedRoom.getRoomOrientation().setDirectionConnection(selectedDirection, RoomConnection.ENTRANCE);
                }
            }
        }

     //   PerformanceUtility.endLogging("TreasureRoomAttach");
      //  DevDungeon.log("TreasureRoomGen: Attaching Time: " + PerformanceUtility.getTimings("TreasureRoomAttach"));

    }
}
