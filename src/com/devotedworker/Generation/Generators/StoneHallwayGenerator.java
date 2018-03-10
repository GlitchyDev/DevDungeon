package com.devotedworker.Generation.Generators;

import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.GenAction;
import com.devotedworker.Generation.Rooms.AbstractRoom;
import com.devotedworker.Generation.Rooms.DebugRoom;
import com.devotedworker.Generation.Rooms.HallwayRoom;
import com.devotedworker.Generation.Utility.Enums.RoomConnection;
import com.devotedworker.Generation.Utility.Enums.RoomDirection;
import com.devotedworker.Generation.Utility.Enums.RoomType;
import com.devotedworker.Generation.Utility.PerformanceUtility;
import com.devotedworker.Generation.Utility.RoomLocation;
import com.devotedworker.plugin.DevDungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


/**
 * Fills the empty spaces between structures
 */
public class StoneHallwayGenerator extends AbstractRoomGenerator {


    @Override
    public void geneneratorActions(DungeonGenerationMap generationMap, HashMap<GeneratorType, AbstractRoomGenerator> generators, GenAction genAction, Random random) {

        switch(genAction)
        {
            case GStoneHallway:
                DevDungeon.log("HallRoomGen: Filling Hallways");
                PerformanceUtility.startLogging("HallwayFillTime");


                int roomID = 0;
                ArrayList<HallwayRoom> generatedHallwayRooms = new ArrayList<>();
                HashMap<Integer,ArrayList<HallwayRoom>> roomTrees = new HashMap<>();
                for(int x = 0; x < generationMap.getWidth(); x++)
                {
                    for(int z = 0; z < generationMap.getLength(); z++)
                    {
                        for(int y = 0; y < generationMap.getHeight(); y++)
                        {
                            if(generationMap.getRoom(new RoomLocation(x,z,y)) == null || generationMap.getRoom(new RoomLocation(x,z,y)).getRoomType() == RoomType.HALLWAY) {
                                RoomLocation location = new RoomLocation(x,z,y);

                                generationMap.setRooms(location, new HallwayRoom(location,roomID));
                                generatedHallwayRooms.add((HallwayRoom) generationMap.getRoom(location));
                                createdRooms.add(generationMap.getRoom(location));

                                roomTrees.put(roomID,new ArrayList<HallwayRoom>());
                                roomTrees.get(roomID).add((HallwayRoom) generationMap.getRoom(location));
                                roomID++;
                            }
                        }
                    }
                }
                PerformanceUtility.endLogging("HallwayFillTime");
                DevDungeon.log("HallRoomGen: Fill time: " + PerformanceUtility.getTimings("HallwayFillTime"));

                PerformanceUtility.startLogging("HallwayShuffleTime");
                DevDungeon.log("HallRoomGen: Starting Shuffle");
                Collections.shuffle(generatedHallwayRooms,random);
                PerformanceUtility.endLogging("HallwayShuffleTime");
                DevDungeon.log("HallRoomGen: Shuffle time: " + PerformanceUtility.getTimings("HallwayShuffleTime"));


                DevDungeon.log("HallRoomGen: Starting ArrangingTime");
                PerformanceUtility.startLogging("HallwayArrangingTime");

                for(HallwayRoom room: generatedHallwayRooms)
                {
                    checkRoom(generationMap,room,random,roomTrees);
                }

                Collections.shuffle(generatedHallwayRooms,random);
                for(HallwayRoom room: generatedHallwayRooms)
                {
                    checkRoom(generationMap,room,random,roomTrees);
                }
                while(roomTrees.size() > generationMap.getHeight()) {
                    int lowestNum = Integer.MAX_VALUE;
                    int currentID = 0;
                    for (ArrayList<HallwayRoom> rooms : roomTrees.values()) {
                        if (lowestNum > rooms.size()) {
                            lowestNum = rooms.size();
                            currentID = rooms.get(0).getRoomID();
                        }
                    }
                    for(HallwayRoom room: roomTrees.get(currentID))
                    {
                        generationMap.setRooms(room.getRoomLocation(),new DebugRoom(room.getRoomLocation()));
                    }
                    roomTrees.remove(currentID);
                }


                PerformanceUtility.endLogging("HallwayArrangingTime");
                DevDungeon.log("HallRoomGen: Arranging Time: " + PerformanceUtility.getTimings("HallwayArrangingTime"));
                // Verify the 3 remaining trees are each floor, and not multiple trees per floor
                // DevDungeon.log("HallRoomGen: Errors : " + roomTrees.size() + ":" + generationMap.getHeight());
                break;
            case CStoneHallway:
                DevDungeon.log("HallRoomClean: Starting Pruning");
                PerformanceUtility.startLogging("HallwayPruning");

                for(int x = 0; x < 5; x++) {
                    ArrayList<AbstractRoom> removedRooms = new ArrayList<>();
                    for (AbstractRoom room : createdRooms)
                    {

                        HallwayRoom hallwayRoom = (HallwayRoom) room;

                        int totalOpenings = 0;
                        for(RoomDirection roomDirection: RoomDirection.getFloorRoomDirections())
                        {
                            if(hallwayRoom.getRoomOrientation().getDirectionConnection(roomDirection) == RoomConnection.ENTRANCE)
                            {
                                totalOpenings++;
                            }
                        }
                        if(totalOpenings <= 1)
                        {
                            for(RoomDirection roomDirection: RoomDirection.getFloorRoomDirections())
                            {
                                if(hallwayRoom.getRoomOrientation().getDirectionConnection(roomDirection) == RoomConnection.ENTRANCE)
                                {
                                    if(generationMap.getRoom(hallwayRoom.getRoomLocation().getDirectionLocation(roomDirection)).getRoomType() == RoomType.HALLWAY) {
                                        HallwayRoom soleConnection = (HallwayRoom) generationMap.getRoom(hallwayRoom.getRoomLocation().getDirectionLocation(roomDirection));
                                        soleConnection.getRoomOrientation().setDirectionConnection(roomDirection.reverse(), RoomConnection.WALL);

                                        generationMap.setRooms(hallwayRoom.getRoomLocation(), null);
                                        removedRooms.add(room);
                                    }
                                }
                            }


                        }
                    }
                    createdRooms.removeAll(removedRooms);
                    removedRooms.clear();
                }

                PerformanceUtility.endLogging("HallwayPruning");
                DevDungeon.log("HallRoomClean: Pruning Time: " +  PerformanceUtility.getTimings("HallwayPruning"));

                break;
    }


    }


    public static void checkRoom(DungeonGenerationMap dungeon, HallwayRoom room, Random random, HashMap<Integer,ArrayList<HallwayRoom>> roomTrees)
    {
        HashMap<RoomDirection, HallwayRoom> potentialRooms = room.findPotentialConnections(dungeon);
        if(potentialRooms.size() != 0)
        {
            RoomDirection selectedDirection = (RoomDirection) potentialRooms.keySet().toArray()[random.nextInt(potentialRooms.size())];
            HallwayRoom selectedRoom = potentialRooms.get(selectedDirection);
            int primaryID = room.getRoomID();
            int secondaryID = selectedRoom.getRoomID();
            room.getRoomOrientation().setDirectionConnection(selectedDirection, RoomConnection.ENTRANCE);
            selectedRoom.getRoomOrientation().setDirectionConnection(selectedDirection.reverse(),RoomConnection.ENTRANCE);

            for(HallwayRoom adjustedRooms: roomTrees.get(secondaryID))
            {
                adjustedRooms.setRoomID(primaryID);
            }
            roomTrees.get(primaryID).addAll(roomTrees.get(secondaryID));
            roomTrees.remove(secondaryID);
        }
    }


}
