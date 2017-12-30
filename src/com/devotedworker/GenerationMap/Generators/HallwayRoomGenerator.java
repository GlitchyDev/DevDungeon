package com.devotedworker.GenerationMap.Generators;

import com.devotedworker.GenerationMap.Dungeon;
import com.devotedworker.GenerationMap.Rooms.HallwayRoom;
import com.devotedworker.GenerationMap.Utility.Enums.RoomConnection;
import com.devotedworker.GenerationMap.Utility.Enums.RoomDirection;
import com.devotedworker.GenerationMap.Utility.Enums.RoomType;
import com.devotedworker.GenerationMap.Utility.RoomLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;




public class HallwayRoomGenerator extends AbstractRoomGenerator {


    public static void generateRooms(Dungeon dungeon, Random random)
    {
        System.out.println("HallRoomGen: Filling Hallways");
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
        System.out.println("HallRoomGen: Fill time took " + getSeconds(hallwayFillTime,sortStartTime));
        System.out.println("HallRoomGen: Starting Shuffle");
        Collections.shuffle(generatedHallwayRooms,random);
        long assigningTime = System.currentTimeMillis();
        System.out.println("HallRoomGen: Shuffle time: " + getSeconds(sortStartTime,assigningTime));


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
        System.out.println("HallRoomGen: Arranging Time : " + getSeconds(assigningTime,generationEndTime));



    }




}


    /*
    public static void generateRooms(Dungeon dungeon, Random random)
    {
        System.out.println("HallRoomGen: Filling Hallways");

        int numHallways = 0;
        for(int x = 0; x < dungeon.getWidth(); x++)
        {
            for(int z = 0; z < dungeon.getLength(); z++)
            {
                for(int y = 0; y < dungeon.getHeight(); y++)
                {
                    if(dungeon.getRoom(new RoomLocation(x,z,y)) == null) {
                        dungeon.setRooms(new RoomLocation(x, z, y), new HallwayRoom(dungeon, new RoomLocation(x, z, y)));
                        numHallways++;
                    }
                }
            }
        }

        System.out.println("HallRoomGen: Arranging Hallways");

        for(int y = 0; y < dungeon.getHeight(); y++)
        {
            //int volume = dungeon.getWidth() * dungeon.getLength();
            ArrayList<HallwayRoom> completedRooms = new ArrayList<>();


            for(int i = 0; i < numHallways; i++)
            {
                System.out.println("i: " + i);

                boolean completed = false;
                while(!completed)
                {

                    int x = random.nextInt(dungeon.getWidth());
                    int z = random.nextInt(dungeon.getLength());
                    System.out.println("x: " + x + " z: " + z);
                    if(dungeon.getRoom(new RoomLocation(x,z,y)).getRoomType() == RoomType.HALLWAY)
                    {
                        System.out.println("Found Hallway");

                        if(!completedRooms.contains(dungeon.getRoom(new RoomLocation(x,z,y))))
                        {
                            System.out.println("Found Virgin Hallway");
                            HallwayRoom room = (HallwayRoom) dungeon.getRoom(new RoomLocation(x,z,y));
                            HashMap<RoomDirection,HallwayRoom> connectingRooms = getPossibleRooms(dungeon,room,completedRooms);
                            if(connectingRooms.size() != 0)
                            {
                                System.out.println("Size != 0");
                                int ind = random.nextInt(connectingRooms.size());
                                RoomDirection chosenDirection = (RoomDirection) connectingRooms.keySet().toArray()[ind];
                                room.setConnectedRoom(connectingRooms.get(chosenDirection),chosenDirection);
                                completedRooms.add(room);
                                completed = true;
                            }
                            else
                            {
                                System.out.println("Size == 0");
                                room.setConnectedRoom(null,RoomDirection.UP);
                                completedRooms.add(room);
                                completed = true;
                            }
                        }
                    }
                }
            }


            fixOrientation(dungeon);


        }
    }

    public static void fixOrientation(Dungeon dungeon)
    {
        System.out.println("HallRoomGen: Fixing Orientation");

        for(int x = 0; x < dungeon.getWidth(); x++)
        {
            for(int z = 0; z < dungeon.getLength(); z++)
            {
                for(int y = 0; y < dungeon.getHeight(); y++)
                {
                    if(dungeon.getRoom(new RoomLocation(x,z,y)).getRoomType() == RoomType.HALLWAY) {
                        HallwayRoom room = (HallwayRoom) dungeon.getRoom(new RoomLocation(x,z,y));
                        if(room.getConnectionDirection() != null)
                        {
                            RoomDirection direction = room.getConnectionDirection();
                            room.getRoomOrientation().setDirectionConnection(direction,RoomConnection.ABSOLUTE_ENTRANCE);
                            if(room.getConnectedRoom() != null)
                            {
                                room.getConnectedRoom().getRoomOrientation().setDirectionConnection(direction.reverse(),RoomConnection.ABSOLUTE_ENTRANCE);
                            }
                        }

                    }
                }
            }
        }
    }


    public static HashMap<RoomDirection,HallwayRoom> getPossibleRooms(Dungeon dungeon, HallwayRoom room, ArrayList<HallwayRoom> completedRooms) {
        HashMap<RoomDirection,HallwayRoom> possibleRooms = new HashMap<>();

        for (RoomDirection direction : RoomDirection.getFloorRoomDirections())
        {
            RoomLocation location = room.getRoomLocation().getDirectionLocation(direction);
            if(dungeon.doesRoomExist(location) && dungeon.getRoom(location).getRoomType() == RoomType.HALLWAY)
            {
                  HallwayRoom possibleRoom = (HallwayRoom) dungeon.getRoom(location);
                  if(!possibleRoom.isRoomConnected(room))
                  {
                      possibleRooms.put(direction,possibleRoom);
                  }
            }
        }

        return possibleRooms;
    }

}
*/