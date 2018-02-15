package com.devotedworker.FileIO;

import com.devotedworker.plugin.DevDungeon;

import java.io.File;
import java.util.HashMap;

/**
 * Loads Schematics into Memory for the builder
 */
public class SchematicLoader {
    private static HashMap<String,File> schematics = new HashMap<>();


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
                        schematics.put(schematic.getName().replace(".schematic", ""), schematic);
                    }
                }
            }
        }
    }

    public static File getSchematic(String name)
    {
        return schematics.get(name);

    }
}
