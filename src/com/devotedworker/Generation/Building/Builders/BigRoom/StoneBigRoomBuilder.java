package com.devotedworker.Generation.Building.Builders.BigRoom;

import com.devotedworker.Generation.Building.Builders.AbstractRoomBuilder;
import com.devotedworker.Generation.Building.Builders.BuildingPhase;
import com.devotedworker.Generation.Building.Builders.StoneHallway.HallwayRoomSection;
import com.devotedworker.Generation.Building.RoomSection;
import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.Rooms.AbstractRoom;
import com.devotedworker.Generation.Rooms.BigRoom;
import com.devotedworker.Generation.Rooms.HallwayRoom;
import com.devotedworker.Generation.Utility.Enums.RoomDirection;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class StoneBigRoomBuilder extends AbstractRoomBuilder {
    private HashMap<BigRoom, HashMap<RoomSection,BigRoomSection>> roomData;
    private HashMap<BigRoom,ArrayList<BigRoom>> completeStructure;


    public StoneBigRoomBuilder()
    {
        roomData = new HashMap<>();
        completeStructure = new HashMap<>();
    }


    @Override
    public void build(DungeonGenerationMap dungeonGenerationMap, AbstractRoom room, BuildingPhase phase, Location location, Random random, EditSession editSession) {
        BigRoom bigRoom = (BigRoom) room;
        if(bigRoom.getRoomPos() == 0)
        {
            completeStructure.put(bigRoom,new ArrayList<BigRoom>());
            completeStructure.get(bigRoom).add(bigRoom);
            completeStructure.get(bigRoom).add((BigRoom) dungeonGenerationMap.getRoom(bigRoom.getRoomLocation().getEast()));
            completeStructure.get(bigRoom).add((BigRoom) dungeonGenerationMap.getRoom(bigRoom.getRoomLocation().getSouth()));
            completeStructure.get(bigRoom).add((BigRoom) dungeonGenerationMap.getRoom(bigRoom.getRoomLocation().getEast().getSouth()));
            completeStructure.get(bigRoom).add((BigRoom) dungeonGenerationMap.getRoom(bigRoom.getRoomLocation().getUp()));
            completeStructure.get(bigRoom).add((BigRoom) dungeonGenerationMap.getRoom(bigRoom.getRoomLocation().getUp().getEast()));
            completeStructure.get(bigRoom).add((BigRoom) dungeonGenerationMap.getRoom(bigRoom.getRoomLocation().getUp().getSouth()));
            completeStructure.get(bigRoom).add((BigRoom) dungeonGenerationMap.getRoom(bigRoom.getRoomLocation().getUp().getEast().getSouth()));
            for(BigRoom structureRoom: completeStructure.get(bigRoom))
            {


            }

        }


        /*
        switch(phase)
        {
            case PREBUILD:
                roomData.get(room).put(RoomSection.CENTER_SECTION,HallwayRoomSection.CENTER);


                // Places the rooms original path
                for(RoomSection roomSection: RoomSection.getAllNonCenter())
                {
                    roomData.get(room).put(roomSection,HallwayRoomSection.WALL);
                }
                for(RoomDirection direction: RoomDirection.getFloorRoomDirections())
                {
                    if(room.getRoomOrientation().getDirectionConnection(direction).isOpen())
                    {
                        roomData.get(room).put(RoomSection.getEquivalent(direction), HallwayRoomSection.ORIGINALPATH);
                    }
                }


                for(RoomSection roomSection: RoomSection.getAllNonCenter())
                {
                    if(roomData.get(room).get(roomSection) == HallwayRoomSection.WALL)
                    {
                        if(random.nextInt(2) == 0)
                        {
                            roomData.get(room).put(roomSection,HallwayRoomSection.ADDEDPATH);
                        }
                    }
                }

                for(RoomSection roomSection: RoomSection.getAllNonCenter())
                {
                    if(roomData.get(room).get(roomSection) == HallwayRoomSection.ADDEDPATH) {
                        int connections = 0;
                        for (RoomSection touchingSection : roomSection.getTouchingSections()) {
                            if (touchingSection != RoomSection.CENTER_SECTION) {
                                if (roomData.get(room).get(touchingSection) == HallwayRoomSection.ADDEDPATH) {
                                    connections++;
                                }
                            }
                        }
                        if (connections == 0) {
                            roomData.get(room).put(roomSection, HallwayRoomSection.WALL);
                        }
                    }
                }
                break;
            case BUILD:
                for(RoomSection roomSection: RoomSection.values())
                {
                    Location adjustedLocation = roomSection.getLocationOffset(location);
                    if(roomSection.isDiagnal())
                    {
                        pasteSchematic("Stone_Filler",editSession,random,adjustedLocation,random.nextInt(4) * 90);

                    }
                    if(roomSection.isCarnal())
                    {

                    }
                    if(roomSection == RoomSection.CENTER_SECTION)
                    {

                    }

                    Location adjustedLocation = roomSection.getLocationOffset(location);
                    if(!room.getRoomOrientation().getConnectDown().isOpen()) {
                        if(room.getRoomLocation().getY() == 0 || (dungeonGenerationMap.doesRoomExist(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN))&& dungeonGenerationMap.isRoomNull(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)))) {
                            pasteSchematic("SubFloor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                        }
                        pasteSchematic("Floor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                    }
                    else
                    {
                        pasteSchematic("Ceiling", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                    }

                }
                break;
        }
        */


    }



}
