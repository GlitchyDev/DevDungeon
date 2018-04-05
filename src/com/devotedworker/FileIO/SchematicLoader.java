package com.devotedworker.FileIO;


import com.devotedworker.plugin.DevDungeon;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Loads Schematics into Memory for the builder
 */
public class SchematicLoader {
    private static HashMap<String,ArrayList<File>> schematics = new HashMap<>();


    public static void loadSchematics() {
        DevDungeon.log("SchemLoader: Loading Schematics");
        File folder = new File(DevDungeon.getPlugin(DevDungeon.class).getDataFolder() + "/Schematics");
        if (!folder.exists()) {
            DevDungeon.log("SchemLoader: No schematic folder detected, creating Folder");
            folder.mkdir();
        } else {
            for (File subFolder : folder.listFiles()) {
                DevDungeon.log("SchemLoader: Loading Folder " + subFolder.getName());
                for (File schematic : subFolder.listFiles()) {
                    if (schematic.getName().contains(".schematic")) {
                        DevDungeon.log("SchemLoader: Loading " + schematic.getName());

                        String properName = schematic.getName().replace(".schematic", "");

                        if(properName.contains(","))
                        {
                            String schemName = properName.split(",")[0];
                            int multiplier = Integer.valueOf(properName.split(",")[2]);
                            DevDungeon.log("************************************* " + multiplier);
                            if(!schematics.containsKey(schemName))
                            {
                                schematics.put(schemName,new ArrayList<File>());
                            }
                            for(int i = 0; i < multiplier; i++)
                            {
                                schematics.get(schemName).add(schematic);
                            }
                        }
                        else
                        {
                            if(!schematics.containsKey(properName))
                            {
                                schematics.put(properName,new ArrayList<File>());
                            }
                            schematics.get(properName).add(schematic);
                        }

                    }
                }
            }
        }
    }

    public static File getSchematic(String name,Random random)
    {
        return schematics.get(name).get(random.nextInt(schematics.get(name).size()));
    }
}
