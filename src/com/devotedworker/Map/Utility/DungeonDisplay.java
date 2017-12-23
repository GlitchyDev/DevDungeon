package com.devotedworker.Map.Utility;

import com.devotedworker.Map.Dungeon;
import com.devotedworker.Map.Rooms.AbstractRoom;
import com.devotedworker.Map.Rooms.HallwayRoom;
import com.devotedworker.Map.Utility.Enums.RoomType;

import java.awt.*;
import java.awt.image.BufferedImage;


public class DungeonDisplay {
    private static final int pixelRoomSize = 5;

    private static final int pixelFloorSeperaterWidth = 5;
    private static final Color floorSeperaterColor =  Color.pink;


    public static BufferedImage generateDisplay(Dungeon dungeon)
    {
        int imageHeightCalculation = dungeon.getLength() * dungeon.getHeight() * pixelRoomSize + (dungeon.getHeight()-1) * pixelFloorSeperaterWidth;
        BufferedImage mapImage = new BufferedImage(dungeon.getWidth() * pixelRoomSize,imageHeightCalculation,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = mapImage.createGraphics();

        for(int y = 0; y < dungeon.getHeight(); y++)
        {

            for(int z = 0; z < dungeon.getLength(); z++)
            {

                for(int x = 0; x < dungeon.getWidth(); x++)
                {
                    drawRoom(dungeon,x,z,y,g);
                }
            }
            if(y != 0)
            {
                //g.fillRect(0,y*dungeon.getLength()*pixelRoomSize,dungeon.getWidth()*pixelRoomSize,pixelFloorSeperaterWidth);
            }
        }


        return mapImage;
    }



    private static void drawRoom(Dungeon dungeon, int x, int z, int y, Graphics2D g)
    {
        AbstractRoom room = dungeon.getRoom(new RoomLocation(x,z,y));
        if(room == null)
        {
            return;
        }
        int baseX = x * pixelRoomSize;
        int baseY = (z * pixelRoomSize) + (y * dungeon.getLength() * pixelRoomSize) + (y*pixelFloorSeperaterWidth);


        int i = ((x+z)%2);
        if(i==0)
        {
            g.setPaint(Color.WHITE);
        }
        else
        {
            g.setPaint(new Color(0.8f,0.8f,0.8f,1.0f));
        }
        g.fillRect(baseX,baseY,pixelRoomSize,pixelRoomSize);


        g.setPaint(room.getRoomType().getBaseColor(i));

        switch(room.getRoomOrientation().getConnectNorth())
        {
            case ABSOLUTE_WALL:
                g.fillRect(baseX,baseY,pixelRoomSize,1);
                break;
            case POSSIBLE_ENTRANCE:
            case ABSOLUTE_ENTRANCE:
                g.fillRect(baseX,baseY,2,1);
                g.fillRect(baseX + (pixelRoomSize-2),baseY,2,1);
                break;
        }
        switch(room.getRoomOrientation().getConnectEast())
        {
            case ABSOLUTE_WALL:
                g.fillRect(baseX+(pixelRoomSize-1),baseY,1,pixelRoomSize);
                break;
            case POSSIBLE_ENTRANCE:
            case ABSOLUTE_ENTRANCE:
                g.fillRect(baseX+(pixelRoomSize-1),baseY,1,2);
                g.fillRect(baseX+(pixelRoomSize-1),baseY + (pixelRoomSize-2),1,2);
                break;
        }

        switch(room.getRoomOrientation().getConnectSouth())
        {
            case ABSOLUTE_WALL:
                g.fillRect(baseX,baseY + (pixelRoomSize-1),pixelRoomSize,1);
                break;
            case POSSIBLE_ENTRANCE:
            case ABSOLUTE_ENTRANCE:
                g.fillRect(baseX,baseY + (pixelRoomSize-1),2,1);
                g.fillRect(baseX + (pixelRoomSize-2),baseY + (pixelRoomSize-1),2,1);
                break;
        }
        switch(room.getRoomOrientation().getConnectWest())
        {
            case ABSOLUTE_WALL:
                g.fillRect(baseX,baseY,1,pixelRoomSize);
                break;
            case POSSIBLE_ENTRANCE:
            case ABSOLUTE_ENTRANCE:
                g.fillRect(baseX,baseY,1,2);
                g.fillRect(baseX,baseY + (pixelRoomSize-2),1,2);
                break;
        }
        if(room.getRoomType() == RoomType.HALLWAY)
        {
            HallwayRoom hallwayRoom = (HallwayRoom) room;
            if(hallwayRoom.getConnectionDirection() != null) {
                g.setPaint(Color.YELLOW);
                switch (hallwayRoom.getConnectionDirection()) {
                    case NORTH:
                        g.fillRect(baseX + 2, baseY + 1, 1, 1);
                        break;
                    case EAST:
                        g.fillRect(baseX + 3, baseY + 2, 1, 1);
                        break;
                    case SOUTH:
                        g.fillRect(baseX + 2, baseY + 3, 1, 1);
                        break;
                    case WEST:
                        g.fillRect(baseX + 1, baseY + 2, 1, 1);
                        break;
                    case UP:
                        g.setPaint(Color.BLUE);
                        g.fillRect(baseX + 2, baseY + 2, 1, 1);
                        break;
                }
            }
        }





    }



}
