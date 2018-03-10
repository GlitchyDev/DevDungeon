package com.devotedworker.Generation.Rooms;

import com.devotedworker.Generation.Utility.Enums.RoomConnection;
import com.devotedworker.Generation.Utility.Enums.RoomDirection;
import com.devotedworker.Generation.Utility.Enums.RoomType;
import com.devotedworker.Generation.Utility.RoomLocation;

/**
 * A room with a clear defined entrance filled with bounds of treasure, the only question is who put it all here and whats inside
 *
 * 2 Possible states, "Vault" "Looted Vault" "Collap "Decreed
 */
public class TreasureRoom extends AbstractRoom {

    private RoomDirection openingDirection;

    public TreasureRoom(RoomLocation roomLocation, RoomDirection direction) {
        super(roomLocation);
        roomType = RoomType.TREASUREROOM;
        this.openingDirection = direction;
        this.roomOrientation.setDirectionConnection(direction, RoomConnection.ENTRANCE);
    }

    public RoomDirection getOpeningDirection() {
        return openingDirection;
    }
}
