package com.devotedworker.Map.Generators;

import com.devotedworker.Map.Dungeon;
import com.devotedworker.Map.Rooms.BigRoom;
import com.devotedworker.Map.Utility.RoomLocation;

import java.util.Random;

public class BigRoomGenerator extends AbstractRoomGenerator {
    private static final int dungeonAttemptsPer100 = 6;


    public static void generateRooms(Dungeon dungeon, Random random)
    {

        //canGenerateStructure()
        int dungeonVolume = dungeon.getWidth() * dungeon.getLength() * dungeon.getHeight();
        int attemptedGenerations = (int)(dungeonVolume/100.0*dungeonAttemptsPer100);

        System.out.println("BigRoomGen: Attempting " + attemptedGenerations + " Generations");
        int numSuccesses = 0;
        for(int i = 0; i < attemptedGenerations; i++) {
            int x = random.nextInt(dungeon.getWidth());
            int z = random.nextInt(dungeon.getLength());
            int y = random.nextInt(dungeon.getHeight());
            if (canGenerateStructure(2, 2, 1, x, z, y, dungeon))
            {
                numSuccesses++;
                dungeon.setRooms(new RoomLocation(x + 0,z + 0,y + 0),new BigRoom(dungeon,new RoomLocation(x + 0,z + 0,y + 0),0));
                dungeon.setRooms(new RoomLocation(x + 1,z + 0,y + 0),new BigRoom(dungeon,new RoomLocation(x + 1,z + 0,y + 0),1));
                dungeon.setRooms(new RoomLocation(x + 0,z + 1,y + 0),new BigRoom(dungeon,new RoomLocation(x + 0,z + 1,y + 0),2));
                dungeon.setRooms(new RoomLocation(x + 1,z + 1,y + 0),new BigRoom(dungeon,new RoomLocation(x + 1,z + 1,y + 0),3));
            }
        }
        System.out.println("BigRoomGen: Completed " + numSuccesses + " Generations");




    }




}
