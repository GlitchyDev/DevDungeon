package com.devotedworker;

import com.devotedworker.FileIO.GenerationMapImageIO;
import com.devotedworker.Generation.Building.DungeonBuilder;
import com.devotedworker.Generation.DungeonGenerator;
import com.devotedworker.Generation.DungeonTemplate;
import com.devotedworker.plugin.DevDungeon;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;


/**
 * Should be able to run a DungeonGenerationMap for testing purposes
 */
public class Main {
    public static void main(String[] args)
    {

        DungeonGenerator dungeonGenerator = new DungeonGenerator();
        dungeonGenerator.generate(DungeonTemplate.STONE_DUNGEON,10,10,2);
        GenerationMapImageIO.writeGenerationMapToFile(dungeonGenerator.getDungeonGenerationMap(),false);

        /*

        DungeonBuilder dungeonBuilder = new DungeonBuilder(dungeonGenerator.getDungeonGenerationMap());
        Location location = DevDungeon.instance.getServer().getWorlds().get(0).getSpawnLocation();

        WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        World weWorld = null;
        for(World world: we.getWorldEdit().getServer().getWorlds())
        {
            if(world.getName().equals(location.getWorld().getName()))
            {
                weWorld = world;
            }
        }
        final EditSession buildSession = we.getWorldEdit().getEditSessionFactory().getEditSession(weWorld,Integer.MAX_VALUE);

        dungeonBuilder.build(location,buildSession);
        */
    }

}
