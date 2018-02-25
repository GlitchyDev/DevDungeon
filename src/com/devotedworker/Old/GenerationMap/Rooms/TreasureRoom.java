package com.devotedworker.Old.GenerationMap.Rooms;

import com.devotedworker.Old.GenerationMap.Dungeon;
import com.devotedworker.Old.GenerationMap.Utility.Enums.RoomDirection;
import com.devotedworker.Old.GenerationMap.Utility.RoomLocation;

/**
 * A room with a clear defined entrance filled with bounds of treasure, the only question is who put it all here and whats inside
 *
 * 2 Possible states, "Vault" "Looted Vault" "Collap "Decreed
 */
public class TreasureRoom extends AbstractRoom {

    private RoomDirection direction;

    public TreasureRoom(Dungeon dungeon, RoomLocation roomLocation, RoomDirection direction) {
        super(dungeon, roomLocation);
        this.direction = direction;
    }

    public RoomDirection getDirection() {
        return direction;
    }

    public void setDirection(RoomDirection direction) {
        this.direction = direction;
    }
}
