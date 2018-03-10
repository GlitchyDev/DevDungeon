package com.devotedworker.Generation;

public enum DungeonTemplate {
    STONE_DUNGEON(new GenAction[]{GenAction.GStoneBigRoom, GenAction.GTreasureRoomRoom, GenAction.GStoneHallway, GenAction.FStoneBigRoom, GenAction.CStoneHallway});


    private GenAction[] actionList;
    DungeonTemplate(GenAction[] actionList)
    {
        this.actionList = actionList;
    }
    public GenAction[] getActionList()
    {
        return actionList;
    }
}
