package com.devotedworker.Generation.Utility;

import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.Rooms.AbstractRoom;

import java.awt.*;
import java.awt.image.BufferedImage;


public class DungeonDisplay {
    private static final int pixelRoomSize = 5;

    private static final int pixelFloorSeperaterWidth = 5;
    private static final Color floorSeperaterColor =  Color.pink;


    public static BufferedImage generateDisplay(DungeonGenerationMap dungeonGenerationMap)
    {
        int imageHeightCalculation = dungeonGenerationMap.getLength() * dungeonGenerationMap.getHeight() * pixelRoomSize + (dungeonGenerationMap.getHeight()-1) * pixelFloorSeperaterWidth;
        BufferedImage mapImage = new BufferedImage(dungeonGenerationMap.getWidth() * pixelRoomSize,imageHeightCalculation,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = mapImage.createGraphics();

        for(int y = 0; y < dungeonGenerationMap.getHeight(); y++)
        {

            for(int z = 0; z < dungeonGenerationMap.getLength(); z++)
            {

                for(int x = 0; x < dungeonGenerationMap.getWidth(); x++)
                {
                    drawRoom(dungeonGenerationMap,x,z,y,g);
                }
            }
            if(y != 0)
            {
                g.setPaint(Color.YELLOW);
                g.fillRect(0,y*dungeonGenerationMap.getLength()*pixelRoomSize + (y-1)*pixelRoomSize,dungeonGenerationMap.getWidth()*pixelRoomSize,pixelFloorSeperaterWidth);
            }
        }


        return mapImage;
    }



    private static void drawRoom(DungeonGenerationMap dungeonGenerationMap, int x, int z, int y, Graphics2D g)
    {
        AbstractRoom room = dungeonGenerationMap.getRoom(new RoomLocation(x,z,y));
        if(room == null)
        {
            return;
        }
        int baseX = x * pixelRoomSize;
        int baseY = (z * pixelRoomSize) + (y * dungeonGenerationMap.getLength() * pixelRoomSize) + (y*pixelFloorSeperaterWidth);


        int i = ((x+z)%2);
        if(i==0)
        {
            g.setPaint(Color.WHITE);
        }
        else
        {
            g.setPaint(Color.WHITE.darker());
        }
        g.fillRect(baseX,baseY,pixelRoomSize,pixelRoomSize);


        g.setPaint(room.getRoomType().getBaseColor(i));

        switch(room.getRoomOrientation().getConnectNorth())
        {
            case WALL:
                g.fillRect(baseX,baseY,pixelRoomSize,1);
                break;
            case ENTRANCE:
                g.fillRect(baseX,baseY,2,1);
                g.fillRect(baseX + (pixelRoomSize-2),baseY,2,1);
                break;
        }
        switch(room.getRoomOrientation().getConnectEast())
        {
            case WALL:
                g.fillRect(baseX+(pixelRoomSize-1),baseY,1,pixelRoomSize);
                break;
            case ENTRANCE:
                g.fillRect(baseX+(pixelRoomSize-1),baseY,1,2);
                g.fillRect(baseX+(pixelRoomSize-1),baseY + (pixelRoomSize-2),1,2);
                break;
        }

        switch(room.getRoomOrientation().getConnectSouth())
        {
            case WALL:
                g.fillRect(baseX,baseY + (pixelRoomSize-1),pixelRoomSize,1);
                break;
            case ENTRANCE:
                g.fillRect(baseX,baseY + (pixelRoomSize-1),2,1);
                g.fillRect(baseX + (pixelRoomSize-2),baseY + (pixelRoomSize-1),2,1);
                break;
        }
        switch(room.getRoomOrientation().getConnectWest())
        {
            case WALL:
                g.fillRect(baseX,baseY,1,pixelRoomSize);
                break;
            case ENTRANCE:
                g.fillRect(baseX,baseY,1,2);
                g.fillRect(baseX,baseY + (pixelRoomSize-2),1,2);
                break;
        }
    }
}
