package com.devotedworker.DungeonBuilding.Builders;

import com.devotedworker.DungeonBuilding.Enums.RoomPosition;
import com.devotedworker.GenerationMap.Dungeon;
import com.devotedworker.GenerationMap.Rooms.HallwayRoom;
import com.devotedworker.GenerationMap.Utility.RoomLocation;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;

public class HallwayBuilder extends AbstractBuilder {

    public static void buildRoom(EditSession buildSession, Location roomBase, Dungeon dungeon, int x, int z, int y)
    {
        Location location = roomBase.clone();

        HallwayRoom room = (HallwayRoom) dungeon.getRoom(new RoomLocation(x,z,y));


        for(RoomPosition position: RoomPosition.values())
        {
            if(position.isDiagnal())
            {
                pasteSchematic("Hallway_Corner_Normal",buildSession,RoomPosition.getLocationOffset(location,position),position.getRotation());
                pasteSchematic("Hallway_Corner_Normal",buildSession,RoomPosition.getLocationOffset(location,position),position.getReversedRotation());
                pasteSchematic("Hallway_Floor_Normal",buildSession,RoomPosition.getLocationOffset(location,position),0);
            }
            else
            {
                if(position != RoomPosition.CENTER) {
                    switch (room.getRoomOrientation().getDirectionConnection(position.getRoomDirection()))
                    {
                        case ENTRANCE:
                            pasteSchematic("Hallway_Door_Normal",buildSession,RoomPosition.getLocationOffset(location,position),position.getRotation());
                            break;
                        case WALL:
                            pasteSchematic("Hallway_Wall_Normal",buildSession,RoomPosition.getLocationOffset(location,position),position.getReversedRotation());
                            break;
                    }
                }
                pasteSchematic("Hallway_Floor_Normal",buildSession,RoomPosition.getLocationOffset(location,position),0);

            }
        }





    }


}
