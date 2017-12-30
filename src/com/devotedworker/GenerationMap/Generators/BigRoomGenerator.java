package com.devotedworker.GenerationMap.Generators;

import com.devotedworker.GenerationMap.Dungeon;
import com.devotedworker.GenerationMap.Rooms.BigRoom;
import com.devotedworker.GenerationMap.Rooms.HallwayRoom;
import com.devotedworker.GenerationMap.Utility.Enums.RoomConnection;
import com.devotedworker.GenerationMap.Utility.Enums.RoomDirection;
import com.devotedworker.GenerationMap.Utility.RoomLocation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class BigRoomGenerator extends AbstractRoomGenerator {
    private static final double dungeonAttemptsPer100 = 3.0;
    private static ArrayList<BigRoom[]> generatedBigRooms;

    public static void generateRooms(Dungeon dungeon, Random random)
    {
        int dungeonVolume = dungeon.getWidth() * dungeon.getLength() * dungeon.getHeight();
        int attemptedGenerations = (int)((dungeonVolume/100.0)*dungeonAttemptsPer100);
        System.out.println("BigRoomGen: Attempting " + attemptedGenerations + " Generations");

        generatedBigRooms = new ArrayList<>();

        long roomGenerateTime = System.currentTimeMillis();
        for(int i = 0; i < attemptedGenerations; i++) {
            int x = random.nextInt(dungeon.getWidth());
            int z = random.nextInt(dungeon.getLength());
            int y = random.nextInt(dungeon.getHeight());
            if (canGenerateStructure(4, 4, 1, x, z, y, dungeon))
            {
                BigRoom[] bigArray = new BigRoom[4];
                for(int x1 = 0; x1 < 4; x1++)
                {
                    for(int z1 = 0; z1 < 4; z1++)
                    {
                        if((x1 == 0 || x1 == 3) || (z1 == 0 || z1 == 3))
                        {
                            dungeon.setRooms(new RoomLocation(x + x1,z + z1,y),new HallwayRoom(dungeon,new RoomLocation(x + x1,z + z1,y),0));
                        }
                        else
                        {
                            BigRoom bigRoom = new BigRoom(dungeon,new RoomLocation(x + x1,z + z1,y),(z1-1)*2 + (x1-1));
                            dungeon.setRooms(new RoomLocation(x + x1,z + z1,y),bigRoom);
                            bigArray[(z1-2)*2 + (x1+1)] = bigRoom;
                        }
                    }
                }
                generatedBigRooms.add(bigArray);
            }
        }
        NumberFormat formatter = new DecimalFormat("#0.00");
        System.out.println("BigRoomGen: Succeed " + generatedBigRooms.size() + " Times ( " + formatter.format((100.0/attemptedGenerations*generatedBigRooms.size())) + "% )");
        long roomEndTime = System.currentTimeMillis();
        System.out.println("BigRoomGen: Generate Time took " + getSeconds(roomGenerateTime,roomEndTime));

    }

    private static final int minOpening = 2;
    private static final int maxVariance = 4;

    public static void fixRooms(Dungeon dungeon, Random random) {
        System.out.println("BigRoomFix: Starting Room Fix");
        long fixStart = System.currentTimeMillis();
        int suggestedOpenings = minOpening + random.nextInt(maxVariance+1);

        for(BigRoom[] rooms: generatedBigRooms)
        {
            for(int i = 0; i < suggestedOpenings; i++)
            {
                boolean foundRoom = false;
                while(!foundRoom)
                {
                    BigRoom room = rooms[random.nextInt(4)];
                    RoomDirection direction = RoomDirection.randomDirection(random);
                    if(room.getRoomOrientation().getDirectionConnection(direction) == RoomConnection.WALL)
                    {
                        HallwayRoom hallwayRoom = (HallwayRoom) dungeon.getRoom(room.getRoomLocation().getDirectionLocation(direction));
                        room.getRoomOrientation().setDirectionConnection(direction,RoomConnection.ENTRANCE);
                        hallwayRoom.getRoomOrientation().setDirectionConnection(direction.reverse(),RoomConnection.ENTRANCE);
                        foundRoom = true;
                    }

                }
            }
        }

        long fixStop = System.currentTimeMillis();
        System.out.println("BigRoomFix: Fix Time: " + getSeconds(fixStart,fixStop));



    }



        /*

        for(int x = 0; x < dungeon.getWidth(); x++)
        {
            for(int z = 0; z < dungeon.getLength(); z++)
            {
                for(int y = 0; y < dungeon.getHeight(); y++)
                {
                    if(dungeon.getRoom(new RoomLocation(x,z,y)).getRoomType() == RoomType.BIGROOM) {
                        BigRoom bigRoom = (BigRoom) dungeon.getRoom(new RoomLocation(x,z,y));
                        int roomActive = random.nextInt(3);

                        for(RoomDirection direction: RoomDirection.getFloorRoomDirections())
                        {
                            if(roomActive == 0) {
                                if (dungeon.doesRoomExist(bigRoom.getRoomLocation().getDirectionLocation(direction))) {

                                    int connectionActive = random.nextInt(2);
                                    if(connectionActive == 0) {
                                        if (dungeon.getRoom(bigRoom.getRoomLocation().getDirectionLocation(direction)).getRoomType() == RoomType.HALLWAY) {
                                            HallwayRoom hallwayRoom = (HallwayRoom) dungeon.getRoom(bigRoom.getRoomLocation().getDirectionLocation(direction));

                                            bigRoom.getRoomOrientation().setDirectionConnection(direction, RoomConnection.ENTRANCE);
                                            hallwayRoom.getRoomOrientation().setDirectionConnection(direction.reverse(), RoomConnection.ENTRANCE);

                                        }
                                    }
                                    else {
                                        if(bigRoom.getRoomOrientation().getDirectionConnection(direction) == RoomConnection.ENTRANCE)
                                        {
                                            bigRoom.getRoomOrientation().setDirectionConnection(direction,RoomConnection.WALL);
                                        }
                                    }

                                }
                            }
                            else
                            {
                               if(bigRoom.getRoomOrientation().getDirectionConnection(direction) == RoomConnection.ENTRANCE)
                               {
                                   bigRoom.getRoomOrientation().setDirectionConnection(direction,RoomConnection.WALL);
                               }
                            }
                        }
                    }

                }
            }
        }
        */





}
