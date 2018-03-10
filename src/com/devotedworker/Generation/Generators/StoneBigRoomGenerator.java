package com.devotedworker.Generation.Generators;

import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.GenAction;
import com.devotedworker.Generation.Rooms.BigRoom;
import com.devotedworker.Generation.Utility.Enums.RoomConnection;
import com.devotedworker.Generation.Utility.Enums.RoomDirection;
import com.devotedworker.Generation.Utility.Enums.RoomType;
import com.devotedworker.Generation.Utility.PerformanceUtility;
import com.devotedworker.Generation.Utility.RoomLocation;
import com.devotedworker.plugin.DevDungeon;

import java.util.*;

public class StoneBigRoomGenerator extends AbstractRoomGenerator {


    @Override
    public void geneneratorActions(DungeonGenerationMap generationMap, HashMap<GeneratorType, AbstractRoomGenerator> generators, GenAction genAction, Random random) {

        switch(genAction)
        {
            case GStoneBigRoom:
                DevDungeon.log("BigRoomGen: Placing Rooms");
                PerformanceUtility.startLogging("BigRoomPlaceTime");
                final double bigRoomPercentage = 0.16;
                // Real percentage is 0.08
                // Attempt to spawn x rooms

                int currentY = 1;
                for (int i = 0; i < (generationMap.getRoomCapacity()/8.0 * bigRoomPercentage); i++) {

                    boolean completed = false;
                    final int MAX_ATTEMPTS = 50;
                    int timeoutCount = 0;
                    while(!completed)
                    {
                        int x = random.nextInt(generationMap.getWidth());
                        int z = random.nextInt(generationMap.getLength());
                        int y = ++currentY%(generationMap.getHeight());

                        if (canGenerateStructure(4, 4, 2, x - 1, z - 1, y, generationMap)) {
                            generateBigRoom(x, z, y, generationMap);
                            completed = true;
                        }
                        else
                        {
                            timeoutCount++;
                            if(timeoutCount > MAX_ATTEMPTS)
                            {
                                completed = true;
                            }
                        }
                    }

                }

                DevDungeon.log("BigRoomGen: Attempted Generations " + (int)(generationMap.getRoomCapacity() * bigRoomPercentage/8.0) + " completed " + createdRooms.size()/8);
                PerformanceUtility.endLogging("BigRoomPlaceTime");
                DevDungeon.log("BigRoomGen: Placing time: " + PerformanceUtility.getTimings("BigRoomPlaceTime"));
                break;
            case FStoneBigRoom:
                DevDungeon.log("BigRoomFix: Fixing Rooms to connect to Hallways");
                PerformanceUtility.startLogging("BigRoomFixTime");
                for(int i = 0; i < createdRooms.size()/8.0; i++)
                {
                    ArrayList<BigRoom> structure = new ArrayList<>();
                    for(int a = 0; a < 8; a++)
                    {
                        if(i != 0)
                        {
                            structure.add((BigRoom) createdRooms.get(i * 8 + a));
                        }
                        else
                        {
                            structure.add((BigRoom) createdRooms.get(a));

                        }
                    }
                    HashMap<BigRoom,ArrayList<RoomDirection>> currentWalls = new HashMap<>();
                    for(BigRoom room: structure)
                    {
                        for(RoomDirection direction: RoomDirection.getFloorRoomDirections())
                        {
                            if(room.getRoomOrientation().getDirectionConnection(direction) == RoomConnection.WALL)
                            {
                                if(!currentWalls.containsKey(room))
                                {
                                    currentWalls.put(room,new ArrayList<RoomDirection>());
                                }
                                currentWalls.get(room).add(direction);
                            }
                        }
                    }
                    final int minimumOpening = 2;
                    final int range = 8;
                    for(int x = 0; x < minimumOpening + random.nextInt(range + 1); x++)
                    {
                        if(currentWalls.size() != 0) {
                            int select = random.nextInt(currentWalls.keySet().size());
                            BigRoom room = (BigRoom) currentWalls.keySet().toArray()[select];
                            RoomDirection direction = currentWalls.get(room).get(random.nextInt(currentWalls.get(room).size()));

                            room.getRoomOrientation().setDirectionConnection(direction, RoomConnection.ENTRANCE);
                            generationMap.getRoom(room.getRoomLocation().getDirectionLocation(direction)).getRoomOrientation().setDirectionConnection(direction.reverse(),RoomConnection.ENTRANCE);
                            currentWalls.get(room).remove(direction);
                            if (currentWalls.get(room).size() == 0) {
                                currentWalls.remove(room);
                            }
                        }
                    }


                    int changes = 0;
                    for(BigRoom bigRoom: structure)
                    {
                        for(RoomDirection roomDirection: RoomDirection.getFloorRoomDirections())
                        {
                            if(bigRoom.getRoomOrientation().getDirectionConnection(roomDirection) == RoomConnection.ENTRANCE)
                            {
                                if(generationMap.getRoom(bigRoom.getRoomLocation().getDirectionLocation(roomDirection)).getRoomType() == RoomType.HALLWAY) {
                                    changes++;
                                }
                            }
                        }
                    }
                    if(changes == 0)
                    {
                        for(BigRoom bigRoom: structure)
                        {
                            generationMap.setRooms(bigRoom.getRoomLocation(),null);
                        }
                    }


                }
                PerformanceUtility.endLogging("BigRoomFixTime");
                DevDungeon.log("BigRoomFix: Fixing Time: " + PerformanceUtility.getTimings("BigRoomFixTime"));

                // Grab list of rooms

                break;
        }

    }

    public void generateBigRoom(int x, int z, int y, DungeonGenerationMap dungeonGenerationMap)
    {
        int num = 0;
        for(int y1 = 0; y1 < 2; y1++)
        {
            for(int z1 = 0; z1 < 2; z1++)
            {
                for(int x1 = 0; x1 < 2; x1++)
                {
                    createRoom(dungeonGenerationMap, new RoomLocation(x + x1, z + z1, y + y1), new BigRoom(new RoomLocation(x + x1, z + z1, y + y1),num));
                    num++;
                }
            }
        }
    }


}
