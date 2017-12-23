package com.devotedworker.Map.Utility.Enums;
/*
§0	Black
§1	Dark Blue
§2	Dark Green
§3	Dark Aqua
§4	Dark Red
§5	Dark Purple
§6	Gold
§7	Gray             Hallway
§8	Dark Gray
§9	Blue
§a	Green
§b	Aqua             Big Room
§c	Red
§d	Light Purple
§e	Yellow
§f	White
§r RESET
 */

import java.awt.*;

public enum RoomType {

    DEFAULT,
    HALLWAY,
    BIGROOM,
    SPAWNROOM;

    public Color getBaseColor(int i)
    {
        switch(this)
        {
            case HALLWAY:
                switch(i)
                {
                    case 0:
                        return new Color(1.0f,0.0f,0.0f,1.0f);
                    case 1:
                        return new Color(0.8f,0.0f,0.0f,1.0f);
                }
            case BIGROOM:
                switch(i)
                {
                    case 0:
                        return new Color(0.0f,1.0f,0.0f,1.0f);
                    case 1:
                        return new Color(0.0f,0.8f,0.0f,1.0f);
                }
            case SPAWNROOM:
                switch(i)
                {
                    case 0:
                        return new Color(0.0f,0.0f,1.0f,1.0f);
                    case 1:
                        return new Color(0.0f,0.0f,0.8f,1.0f);
                }
            default:
                return Color.RED;
        }
    }
}
