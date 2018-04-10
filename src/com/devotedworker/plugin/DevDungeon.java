package com.devotedworker.plugin;


import com.devotedworker.FileIO.GenerationMapImageIO;
import com.devotedworker.FileIO.SchematicLoader;
import com.devotedworker.Generation.Building.DungeonBuilder;
import com.devotedworker.Generation.DungeonGenerator;
import com.devotedworker.Generation.DungeonTemplate;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

/**
 * Minecraft Implementation of the DungeonGenerationMap Generation.
 * Should function as a stand in interface to allow the creation of a DungeonGenerationMap, and usage of Builders
 * Should be able to efficiently place blocks, and keep track of generation modules
 */
public class DevDungeon extends JavaPlugin {
    public static DevDungeon instance;
    private FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        instance = this;
        config.addDefault("DefaultWorld", "world");
        config.options().copyDefaults(true);
        saveConfig();
        SchematicLoader.loadSchematics();

    }

    @Override
    public void onDisable(){

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean isPlayer = sender instanceof Player;

        if (command.getName().equals("generate")) {
            DungeonGenerator dungeonGenerator = new DungeonGenerator();
            dungeonGenerator.generate(DungeonTemplate.STONE_DUNGEON,5,5,2);
            GenerationMapImageIO.writeGenerationMapToFile(dungeonGenerator.getDungeonGenerationMap(),false);

            DevDungeon.log("Beginning Dungeon Generation");
            DungeonBuilder dungeonBuilder = new DungeonBuilder(dungeonGenerator.getDungeonGenerationMap());

            Location location;
            if(isPlayer)
            {
                location = ((Player) sender).getLocation().add(0,10,0);
            }
            else {
                location = DevDungeon.instance.getServer().getWorlds().get(0).getSpawnLocation();
            }

            WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
            World weWorld = null;
            for(World world: we.getWorldEdit().getServer().getWorlds())
            {
                if(world.getName().equals(location.getWorld().getName()))
                {
                    DevDungeon.log("Obtained Correct World");
                    weWorld = world;
                }
            }
            final EditSession buildSession = we.getWorldEdit().getEditSessionFactory().getEditSession(weWorld,Integer.MAX_VALUE);


            dungeonBuilder.build(location,buildSession);

        }



        return true;
    }





    public static void log(String string)
    {

        if(instance != null) {
            instance.getLogger().info(string);
        }
        else
        {
            System.out.println(string);
        }
    }

}
