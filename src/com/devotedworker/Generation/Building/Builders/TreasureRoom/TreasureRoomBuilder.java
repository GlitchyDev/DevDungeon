package com.devotedworker.Generation.Building.Builders.TreasureRoom;

import com.devotedworker.Generation.Building.Builders.AbstractRoomBuilder;
import com.devotedworker.Generation.Building.Builders.BuildingPhase;
import com.devotedworker.Generation.Building.RoomSection;
import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.Rooms.AbstractRoom;
import com.devotedworker.Generation.Rooms.TreasureRoom;
import com.devotedworker.Generation.Utility.Enums.RoomConnection;
import com.devotedworker.Generation.Utility.Enums.RoomDirection;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;

import java.util.Random;

public class TreasureRoomBuilder extends AbstractRoomBuilder {
    @Override
    public void build(DungeonGenerationMap dungeonGenerationMap, AbstractRoom room, BuildingPhase phase, Location location, Random random, EditSession editSession) {
        switch(phase)
        {
            case BUILD:
                for(RoomSection roomSection: RoomSection.values())
                {
                    Location adjustedLocation = roomSection.getLocationOffset(location);
                    if(roomSection.isCarnal() && room.getRoomOrientation().getDirectionConnection(roomSection.getEquivalent()) == RoomConnection.ENTRANCE) {
                        {
                            if(room.getRoomLocation().getY() == 0 || (dungeonGenerationMap.doesRoomExist(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN))&& dungeonGenerationMap.isRoomNull(room.getRoomLocation().getDirectionLocation(RoomDirection.DOWN)))) {
                                pasteSchematic("SubFloor", editSession, random, adjustedLocation, random.nextInt(4) * 90);
                            }
                            pasteSchematic("Floor",editSession,random,adjustedLocation,random.nextInt(4) * 90);
                        }
                        pasteSchematic("BigRoom_Support",editSession,random,adjustedLocation,roomSection.getEquivalent().getRotation());
                        pasteSchematic("TreasureRoom_Chest",editSession,random,adjustedLocation,roomSection.getEquivalent().reverse().getRotation());

                    }
                    else {
                        pasteSchematic("Stone_Filler",editSession,random,adjustedLocation,roomSection.getEquivalent().getRotation());

                        boolean entranceFound = false;
                        RoomDirection direction = RoomDirection.DOWN;
                        int count = 0;
                        for(RoomSection touchingSection: roomSection.getTouchingSections())
                        {
                            if(touchingSection.isCarnal() && room.getRoomOrientation().getDirectionConnection(touchingSection.getEquivalent()) == RoomConnection.ENTRANCE) {
                                entranceFound = true;
                                direction = roomSection.getTouchingSectionDirections().get(count);
                            }
                            count++;
                        }

                        if(entranceFound)
                        {
                            if(roomSection == RoomSection.CENTER_SECTION)
                            {
                                pasteSchematic("Support_Beam", editSession, random, adjustedLocation, 0);
                                pasteSchematic("Engraved_Lower_Wall", editSession, random, adjustedLocation, ((TreasureRoom)room).getOpeningDirection().reverse().getRotation());
                            }
                            else {
                                pasteSchematic("Support_Beam", editSession, random, adjustedLocation, 0);
                                pasteSchematic("Engraved_Lower_Wall", editSession, random, adjustedLocation, direction.reverse().getRotation());
                            }
                        }
                    }

                }
        }
    }
}
