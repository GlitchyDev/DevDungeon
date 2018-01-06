package com.devotedworker.GenerationMap.Generators;

import com.devotedworker.GenerationMap.Dungeon;
import com.devotedworker.GenerationMap.Rooms.HallwayRoom;
import com.devotedworker.GenerationMap.Utility.Enums.RoomConnection;
import com.devotedworker.GenerationMap.Utility.Enums.RoomDirection;
import com.devotedworker.GenerationMap.Utility.Enums.RoomType;
import com.devotedworker.GenerationMap.Utility.RoomLocation;
import com.devotedworker.plugin.DevDungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;




public class HallwayRoomGenerator extends AbstractRoomGenerator {


    public static void generateRooms(Dungeon dungeon, Random random)
    {
        DevDungeon.log("HallRoomGen: Filling Hallways");
        long hallwayFillTime = System.currentTimeMillis();



        int roomID = 0;
        ArrayList<HallwayRoom> generatedHallwayRooms = new ArrayList<>();
        HashMap<Integer,ArrayList<HallwayRoom>> roomTrees = new HashMap<>();
        for(int x = 0; x < dungeon.getWidth(); x++)
        {
            for(int z = 0; z < dungeon.getLength(); z++)
            {
                for(int y = 0; y < dungeon.getHeight(); y++)
                {
                    if(dungeon.getRoom(new RoomLocation(x,z,y)) == null || dungeon.getRoom(new RoomLocation(x,z,y)).getRoomType() == RoomType.HALLWAY) {
                        dungeon.setRooms(new RoomLocation(x, z, y), new HallwayRoom(dungeon, new RoomLocation(x, z, y),roomID));
                        generatedHallwayRooms.add((HallwayRoom) dungeon.getRoom(new RoomLocation(x, z, y)));
                        roomTrees.put(roomID,new ArrayList<HallwayRoom>());
                        roomTrees.get(roomID).add((HallwayRoom) dungeon.getRoom(new RoomLocation(x, z, y)));
                        roomID++;
                     }
                }
            }
        }
        long sortStartTime = System.currentTimeMillis();
        DevDungeon.log("HallRoomGen: Fill time took " + getSeconds(hallwayFillTime,sortStartTime));
        DevDungeon.log("HallRoomGen: Starting Shuffle");
        Collections.shuffle(generatedHallwayRooms,random);
        long assigningTime = System.currentTimeMillis();
        DevDungeon.log("HallRoomGen: Shuffle time: " + getSeconds(sortStartTime,assigningTime));


        for(HallwayRoom room: generatedHallwayRooms)
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

        long generationEndTime = System.currentTimeMillis();
        DevDungeon.log("HallRoomGen: Arranging Time : " + getSeconds(assigningTime,generationEndTime));

    }
}

