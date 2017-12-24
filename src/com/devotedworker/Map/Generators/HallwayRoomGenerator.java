package com.devotedworker.Map.Generators;

import com.devotedworker.Map.Dungeon;
import com.devotedworker.Map.Rooms.HallwayRoom;
import com.devotedworker.Map.Utility.Enums.RoomConnection;
import com.devotedworker.Map.Utility.Enums.RoomDirection;
import com.devotedworker.Map.Utility.Enums.RoomType;
import com.devotedworker.Map.Utility.RoomLocation;

import java.util.*;

public class HallwayRoomGenerator extends AbstractRoomGenerator {
    public static void generateRooms(Dungeon dungeon, Random random) {


        for(int y = 0; y < dungeon.getHeight(); y++)
        {
            System.out.println("Starting Floor " + y);

            int hallwayCount = 0;
            for(int x = 0; x < dungeon.getWidth(); x++)
            {
                for(int z = 0; z < dungeon.getLength(); z++)
                {
                    if(dungeon.getRoom(new RoomLocation(x,z,y)) == null)
                    {
                        dungeon.setRooms(new RoomLocation(x,z,y),new HallwayRoom(dungeon,new RoomLocation(x,z,y)));
                        hallwayCount++;
                    }
                }
            }
            System.out.println(hallwayCount + " hallways generated");


            HashMap<Integer,ArrayList<HallwayRoom>> currentTrees = new HashMap<>();
            int currentRoomKey = 0;
            boolean completed = false;
            while(!completed)
            {
                System.out.println("Checking if Tree is complete");

                if(currentTrees.size() == 1) {
                    System.out.println("Tree Key size is 1, checking if all rooms contained");

                    int key = (int) (currentTrees.keySet().toArray()[0]);
                    System.out.println("Using only key " + key);

                    if (currentTrees.get(key).size() == hallwayCount) {
                        System.out.println("COMPLETE, all " + hallwayCount + ":" + currentTrees.get(key).size());

                        // Removing Duplicates???
                        ArrayList<HallwayRoom> al = currentTrees.get(key);
                        // add elements to al, including duplicates
                        Set<HallwayRoom> hs = new HashSet<>();
                        hs.addAll(al);
                        al.clear();
                        al.addAll(hs);
                        if(al.size() ==  hallwayCount)
                        {
                            System.out.println("Confirmed COMPLETE, all " + hallwayCount + ":" + currentTrees.get(key).size());
                            completed = true;
                        }
                        else
                        {
                            System.out.println("FALSE ALARM!");
                        }



                    } else {
                        System.out.println("Not complete, off by " + (hallwayCount - currentTrees.get(key).size()));
                    }
                }

                // Cords of next chosen room
                int x = random.nextInt(dungeon.getWidth());
                int z = random.nextInt(dungeon.getLength());

                System.out.println("Random cords " + x + " " + y);

                // Check if random room is even a Hallway
                if (dungeon.getRoom(new RoomLocation(x, z, y)).getRoomType() == RoomType.HALLWAY) {
                    System.out.println("Room is hallway!");

                    HallwayRoom room = (HallwayRoom) dungeon.getRoom(new RoomLocation(x, z, y));
                    if(room.getTreeID() == -1)
                    {
                        System.out.println("Room hasn't been checked, assigning roomID " + currentRoomKey);

                        room.setTreeID(currentRoomKey);
                        ArrayList<HallwayRoom> rooms = new ArrayList<>();
                        rooms.add(room);
                        currentTrees.put(room.getTreeID(),rooms);
                        currentRoomKey++;
                    }
                    // Lets check if this hallway has any possible rooms
                    System.out.println("Obtaining possible rooms");
                    HashMap<RoomDirection,HallwayRoom> possibleRooms = room.getPossibleConnections(dungeon);
                    if (possibleRooms.size() != 0) {
                        System.out.println("Selecting Random room to pursue");

                        RoomDirection selectedDirection = (RoomDirection) possibleRooms.keySet().toArray()[random.nextInt(possibleRooms.size())];
                        HallwayRoom selection = possibleRooms.get(selectedDirection);
                        if (selection.getTreeID() == -1) {
                            System.out.println("Selection is virgin room, assigning ID, throwing in basket");

                            room.getConnectedRooms().put(selectedDirection,selection);
                            selection.setTreeID(room.getTreeID());
                            if(!currentTrees.containsKey(room.getTreeID()))
                            {
                                System.out.println("ERROR CURRENT TREES DOESN'T CONTAIN KEY " + room.getTreeID());
                            }
                            currentTrees.get(room.getTreeID()).add(selection);
                        } else {
                            System.out.println("Selection is experienced Room, adding whole family to basket ID " + selection.getTreeID());

                            int oldSelectID = selection.getTreeID();
                            ArrayList<HallwayRoom> affectedRooms = currentTrees.get(selection.getTreeID());
                            if(affectedRooms == null)
                            {
                                System.out.println("ERROR! CODE " + selection.getTreeID() + " NOT HERE");
                            }
                            for(HallwayRoom affectedRoom: affectedRooms)
                            {
                                affectedRoom.setTreeID(room.getTreeID());
                                currentTrees.get(room.getTreeID()).add(affectedRoom);
                            }
                            currentTrees.remove(oldSelectID);
                        }
                    }
                }
            }



        }


        fixOrientation(dungeon);

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
                        for(RoomDirection direction: room.getConnectedRooms().keySet())
                        {
                            room.getRoomOrientation().setDirectionConnection(direction,RoomConnection.ABSOLUTE_ENTRANCE);

                            room.getConnectedRooms().get(direction).getRoomOrientation().setDirectionConnection(direction.reverse(),RoomConnection.ABSOLUTE_ENTRANCE);
                        }

                    }
                }
            }
        }
    }

}

    /*
    private HashMap<RoomLocation,ArrayList<RoomLocation>> currentRooms;

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