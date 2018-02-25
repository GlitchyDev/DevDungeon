package com.devotedworker.Generation.Utility.Enums;
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

    DEFAULT(new Color(1.0f,1.0f,0.0f,1.0f)),
    HALLWAY(new Color(1.0f,0.0f,0.0f,1.0f)),
    BIGROOM(new Color(0.0f,1.0f,0.0f,1.0f)),
    SPAWNROOM(new Color(0.0f,1.0f,0.0f,1.0f)),
    HIDDENROOM(new Color(0.0f,1.0f,0.0f,1.0f));



    private Color color;
    RoomType(Color color)
    {
        this.color = color;
    }

    public Color getBaseColor(int i)
    {
        if(i == 0)
        {
            return color;
        }
        else
        {
            return color.darker();
        }
    }
}
