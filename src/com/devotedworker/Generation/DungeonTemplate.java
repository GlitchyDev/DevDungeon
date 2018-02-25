package com.devotedworker.Generation;

public enum DungeonTemplate {
    StoneDungeon(new GenAction[]{GenAction.GStoneBigRoom, GenAction.GStoneHallway, GenAction.FStoneBigRoom});


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
