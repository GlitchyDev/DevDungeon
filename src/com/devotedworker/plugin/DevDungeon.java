package com.devotedworker.plugin;


import com.devotedworker.DungeonBuilding.DungeonBuild;
import com.devotedworker.FileIO.GenerationMapImageIO;
import com.devotedworker.FileIO.SchematicLoader;
import com.devotedworker.GenerationMap.Dungeon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

/**
 * Minecraft Implementation of the Dungeon Generator.
 * Should function as a stand in interface to allow the creation of a GenerationMap, and usage of Builders
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

        // Load Schematics


        SchematicLoader.loadSchematics();

    }

    @Override
    public void onDisable(){

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean isPlayer = sender instanceof Player;

        if (command.getName().equals("generate")) {

            if(args.length == 6)
            {
                Random random = new Random(Long.valueOf(args[4]));
                Dungeon dungeon = new Dungeon(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]), random);


                if(args[3].toUpperCase().contains("T"))
                {
                    // Pass to a builder
                    World world;
                    Location location;
                    if(isPlayer)
                    {
                        Player player = (Player) sender;
                        location = player.getLocation().add(0,0,0);
                    }
                    else
                    {
                        world = Bukkit.getWorld((String) config.get("defaultWorld"));
                        location = new Location(world,0,0,0);

                    }


                    DungeonBuild.buildDungeon(dungeon, random, location,Integer.valueOf(args[5]));


                }
                else
                {
                    GenerationMapImageIO.writeGenerationMapToFile(dungeon,true);
                }

            }
            else {
                if(isPlayer) {
                    sender.sendMessage("Not enough arguments to generate");
                }
                else
                {
                    getLogger().info("Not enough arguments to generate");
                }
            }

        }



        return true;
    }





    public static void log(String string)
    {
        System.out.println(string);
        if(instance != null) {
            instance.getLogger().info(string);
        }
    }
}
