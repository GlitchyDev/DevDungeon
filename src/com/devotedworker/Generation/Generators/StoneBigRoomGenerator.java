package com.devotedworker.Generation.Generators;

import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.GenAction;
import com.devotedworker.Generation.Rooms.BigRoom;
import com.devotedworker.Generation.Rooms.HallwayRoom;
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

                for(int i = 0; i < createdRooms.size()/8.0; i++) {
                    ArrayList<BigRoom> structure = new ArrayList<>();
                    for (int a = 0; a < 8; a++) {
                        if (i != 0) {
                            structure.add((BigRoom) createdRooms.get(i * 8 + a));
                        } else {
                            structure.add((BigRoom) createdRooms.get(a));

                        }
                    }

                    int hallwayCount = 0;
                    HashMap<BigRoom, ArrayList<RoomDirection>> connectedHallways = new HashMap<>();
                    for (BigRoom bigRoom : structure) {
                        for (RoomDirection roomDirection : RoomDirection.getFloorRoomDirections()) {
                            if (bigRoom.getRoomOrientation().getDirectionConnection(roomDirection) == RoomConnection.WALL) {
                                if (generationMap.doesRoomExist(bigRoom.getRoomLocation().getDirectionLocation(roomDirection))) {
                                    if (!generationMap.isRoomNull(bigRoom.getRoomLocation().getDirectionLocation(roomDirection))) {
                                        if (generationMap.getRoom(bigRoom.getRoomLocation().getDirectionLocation(roomDirection)).getRoomType() == RoomType.HALLWAY) {

                                            if (!connectedHallways.containsKey(bigRoom)) {
                                                connectedHallways.put(bigRoom, new ArrayList<RoomDirection>());
                                            }
                                            connectedHallways.get(bigRoom).add(roomDirection);
                                            hallwayCount++;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (hallwayCount != 0) {
                        int minimumConnections = 2;
                        int rangeConnections = 8;
                        int suggestedConnections = minimumConnections + random.nextInt(rangeConnections + 1);
                        if (hallwayCount < suggestedConnections) {
                            suggestedConnections = hallwayCount;
                        }

                        for (int a = 0; a < suggestedConnections; a++) {
                            BigRoom bigRoom = (BigRoom) connectedHallways.keySet().toArray()[random.nextInt(connectedHallways.keySet().size())];
                            RoomDirection roomDirection = connectedHallways.get(bigRoom).get(random.nextInt(connectedHallways.get(bigRoom).size()));

                            bigRoom.getRoomOrientation().setDirectionConnection(roomDirection, RoomConnection.ENTRANCE);
                            HallwayRoom hallwayRoom = (HallwayRoom) generationMap.getRoom(bigRoom.getRoomLocation().getDirectionLocation(roomDirection));
                            hallwayRoom.getRoomOrientation().setDirectionConnection(roomDirection.reverse(), RoomConnection.ENTRANCE);

                            connectedHallways.get(bigRoom).remove(roomDirection);
                            if (connectedHallways.get(bigRoom).size() == 0) {
                                connectedHallways.remove(bigRoom);
                            }
                        }


                    }
                    else
                    {
                        for (BigRoom bigRoom : structure) {
                            generationMap.setRoom(bigRoom.getRoomLocation(),null);
                        }
                    }
                }
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
