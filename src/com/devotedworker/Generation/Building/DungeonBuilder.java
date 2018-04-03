package com.devotedworker.Generation.Building;

import com.devotedworker.Generation.Building.Builders.AbstractRoomBuilder;
import com.devotedworker.Generation.Building.Builders.BigRoom.StoneBigRoomBuilder;
import com.devotedworker.Generation.Building.Builders.BuildingPhase;
import com.devotedworker.Generation.Building.Builders.StoneHallway.StoneHallwayBuilder;
import com.devotedworker.Generation.Building.Builders.TreasureRoom.TreasureRoomBuilder;
import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.Rooms.AbstractRoom;
import com.devotedworker.Generation.Utility.Enums.RoomType;
import com.devotedworker.Generation.Utility.RoomLocation;
import com.devotedworker.plugin.DevDungeon;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DungeonBuilder {
    public static final int roomWidth = 15;
    public static final int roomHeight = 10;
    private HashMap<RoomType,AbstractRoomBuilder> builders;
    private DungeonGenerationMap dungeonGenerationMap;
    private Random random;


    public DungeonBuilder(DungeonGenerationMap dungeonGenerationMap)
    {
        builders = new HashMap<>();
        random = new Random();
        ArrayList<RoomType> utilizedRooms = dungeonGenerationMap.getUtilizedRooms();
        for(RoomType roomType: utilizedRooms)
        {
            builders.put(roomType,createNativeBuilder(roomType));
        }
        this.dungeonGenerationMap = dungeonGenerationMap;
    }

    public DungeonBuilder(DungeonGenerationMap dungeonGenerationMap, long seed)
    {
        builders = new HashMap<>();
        random = new Random();
        ArrayList<RoomType> utilizedRooms = dungeonGenerationMap.getUtilizedRooms();
        for(RoomType roomType: utilizedRooms)
        {
            builders.put(roomType,createNativeBuilder(roomType));
        }
        this.dungeonGenerationMap = dungeonGenerationMap;
    }


    public void build(Location dungeonLocation, EditSession editSession) {

        for (BuildingPhase phase : BuildingPhase.values()) {
            for (int y = 0; y < dungeonGenerationMap.getHeight(); y++) {
                for (int x = 0; x < dungeonGenerationMap.getWidth(); x++) {
                    for (int z = 0; z < dungeonGenerationMap.getLength(); z++) {
                        Location roomCenter = dungeonLocation.clone().add(x * roomWidth, (dungeonGenerationMap.getHeight() - y) * roomHeight, z * roomWidth);
                        if(!dungeonGenerationMap.isRoomNull(new RoomLocation(x, z, y))) {
                            AbstractRoom room = dungeonGenerationMap.getRoom(new RoomLocation(x, z, y));

                            DevDungeon.log("DungeonBuilder: Phase " + phase.toString()  + " Building Room " + room.getRoomType() + " X:" + x + " Z:" + z + " Y:" + y);
                            builders.get(room.getRoomType()).build(dungeonGenerationMap, room, phase, roomCenter, random, editSession);


                        }
                        else
                        {
                            //AbstractRoomBuilder.pasteSchematic("NULL",editSession,roomCenter,0);
                        }
                    }
                }
            }
        }
    }

    public AbstractRoomBuilder createNativeBuilder(RoomType roomType)
    {
        switch(roomType)
        {
            case HALLWAY:
                return new StoneHallwayBuilder();
            case BIGROOM:
                return new StoneBigRoomBuilder();
            case TREASUREROOM:
                return new TreasureRoomBuilder();
        }
        return null;
    }
}
