package com.devotedworker.DungeonBuilding;

import com.devotedworker.DungeonBuilding.Builders.AbstractBuilder;
import com.devotedworker.DungeonBuilding.Builders.BigRoomBuilder;
import com.devotedworker.DungeonBuilding.Builders.HallwayBuilder;
import com.devotedworker.DungeonBuilding.Utility.DefaultRoomSize;
import com.devotedworker.GenerationMap.Dungeon;
import com.devotedworker.GenerationMap.Utility.RoomLocation;
import com.devotedworker.plugin.DevDungeon;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class DungeonBuild extends AbstractBuilder {




    public static void buildDungeon(Dungeon dungeon, Location dungeonBaseLocation, int tickRoomBuildingDelay)
    {

        WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        World weWorld = null;
        for(World world: we.getWorldEdit().getServer().getWorlds())
        {
            if(world.getName().equals(dungeonBaseLocation.getWorld().getName()))
            {
                weWorld = world;
            }
        }
        final EditSession buildSession = we.getWorldEdit().getEditSessionFactory().getEditSession(weWorld,Integer.MAX_VALUE);
        final Dungeon finalDungeon = dungeon;
        final Location finalDungeonBaseLocation = dungeonBaseLocation;

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

        int delay = 0;
        for(int y = 0; y < dungeon.getHeight(); y++)
        {
            for(int x = 0; x < dungeon.getWidth(); x++)
            {
                for(int z = 0; z < dungeon.getLength(); z++)
                {
                    final int finalX = x;
                    final int finalZ = z;
                    final int finalY = y;
                    final long tickDelay = (long)(delay*tickRoomBuildingDelay);


                    BukkitTask task = scheduler.runTaskLater(DevDungeon.instance, new Runnable() {
                        @Override
                        public void run() {
                            buildRoom(buildSession,finalDungeon,finalDungeonBaseLocation,finalX,finalZ,finalY);
                        }
                    }, tickDelay);
                    delay++;
                }
            }
        }

    }

    public static void buildRoom( EditSession buildSession,Dungeon dungeon, Location dungeonBaseLocation, int x, int z, int y)
    {
        Location roomBaseLocation = dungeonBaseLocation.clone().add(x * DefaultRoomSize.defaultRoomWidth, y * DefaultRoomSize.defaultRoomHeight, z *DefaultRoomSize.defaultRoomLength);
        switch(dungeon.getRoom(new RoomLocation(x,z,y)).getRoomType())
        {
            case HALLWAY:
                HallwayBuilder.buildRoom(buildSession,roomBaseLocation,dungeon,x,z,y);
                break;
            case BIGROOM:
                BigRoomBuilder.buildRoom(buildSession,roomBaseLocation,dungeon,x,z,y);
                break;
            default:
                break;

        }
    }

    public static void clearDungeon(Dungeon dungeon, Location location)
    {
        EditSession clearSession = new EditSession((LocalWorld) location.getWorld(),Integer.MAX_VALUE);

        int dungeonBlockWidth = dungeon.getWidth()* DefaultRoomSize.defaultRoomWidth;
        int dungeonBlockLength = dungeon.getLength()* DefaultRoomSize.defaultRoomLength;
        int dungeonBlockHeight = dungeon.getHeight()* DefaultRoomSize.defaultRoomHeight;

        CuboidRegion clearRegion = new CuboidRegion(translateVector(location),translateVector(location.add(dungeonBlockWidth,dungeonBlockHeight,dungeonBlockLength)));

        try {
            clearSession.setBlocks(clearRegion, new BaseBlock((0)));
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }

    }



}
