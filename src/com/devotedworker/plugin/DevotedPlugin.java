package com.devotedworker.plugin;


import com.devotedworker.GenerationMap.Dungeon;
import com.devotedworker.GenerationMap.Utility.DungeonDisplay;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.world.World;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.TileEntitySkull;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DevotedPlugin extends JavaPlugin {
    public static DevotedPlugin instance;
    private FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        instance = this;
        config.addDefault("youAreAwesome", true);
        config.options().copyDefaults(true);
        saveConfig();

    }

    @Override
    public void onDisable(){

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        /*
        /give @p skull 1 3 {display:{Name:"Machine Block"},SkullOwner:{
        Id:"b7d0e2f1-c0ea-4089-8ae2-f0ac6ad8f6cc"
        ,Properties:{textures:[{Value:"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRkZDRhMTJkYTFjYzJjOWY5ZDZjZDQ5ZmM3NzhlM2ExMWYzNzU3ZGU2ZGQzMTJkNzBhMGQ0Nzg4NTE4OWMwIn19fQ=="}]}}}
         */
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equals("test")) {

                switch (args[0].toUpperCase()) {
                    case "TEST1":
                        File file = new File(getDataFolder() + "/" + "Test.png");
                        Dungeon dungeon = new Dungeon(5,5,1);
                        try {
                            ImageIO.write(DungeonDisplay.generateDisplay(dungeon),"PNG",file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "TEST2":
                        break;
                    case "TEST3":
                        break;
                    case "TEST4":
                        break;
                    case "TEST5":
                        break;
                }
            }
            if (command.getName().equals("generate")) {
                switch (args[0].toUpperCase()) {
                    case "TEST":
                        File hallwayBase = new File("plugins/WorldEdit/schematics/" + "HallwayBase" +  ".schematic");
                        File hallwayCorner = new File("plugins/WorldEdit/schematics/" + "HallwayCorner" +  ".schematic");

                        Vector position = translateVector(player.getLocation());
                        boolean noAir = true;
                        boolean entities = false;
                        World worldEditWorld = new BukkitWorld(player.getWorld());
                        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(worldEditWorld, -1);


                        try {


                            // Roombase
                            CuboidClipboard clipboard  = SchematicFormat.getFormat(hallwayBase).load(hallwayBase);
                            //clipboard.flip(CuboidClipboard.FlipDirection.WEST_EAST);
                            //clipboard.rotate2D(90);

                            clipboard.paste(editSession, position, noAir, entities);

                            clipboard  = SchematicFormat.getFormat(hallwayCorner).load(hallwayCorner);
                            //clipboard.flip(CuboidClipboard.FlipDirection.WEST_EAST);
                            //clipboard.rotate2D(90);
                            clipboard.paste(editSession, position, noAir, entities);

                            clipboard  = SchematicFormat.getFormat(hallwayCorner).load(hallwayCorner);
                            clipboard.flip(CuboidClipboard.FlipDirection.WEST_EAST);
                            //clipboard.rotate2D(90);
                            clipboard.paste(editSession, position, noAir, entities);

                            clipboard  = SchematicFormat.getFormat(hallwayCorner).load(hallwayCorner);
                            //clipboard.flip(CuboidClipboard.FlipDirection.WEST_EAST);
                            clipboard.rotate2D(90);
                            clipboard.paste(editSession, position, noAir, entities);

                            clipboard  = SchematicFormat.getFormat(hallwayCorner).load(hallwayCorner);
                            clipboard.flip(CuboidClipboard.FlipDirection.WEST_EAST);
                            clipboard.rotate2D(90);
                            clipboard.paste(editSession, position, noAir, entities);

                            clipboard  = SchematicFormat.getFormat(hallwayCorner).load(hallwayCorner);
                            //clipboard.flip(CuboidClipboard.FlipDirection.WEST_EAST);
                            clipboard.rotate2D(180);
                            clipboard.paste(editSession, position, noAir, entities);

                            clipboard  = SchematicFormat.getFormat(hallwayCorner).load(hallwayCorner);
                            clipboard.flip(CuboidClipboard.FlipDirection.WEST_EAST);
                            clipboard.rotate2D(180);
                            clipboard.paste(editSession, position, noAir, entities);

                            clipboard  = SchematicFormat.getFormat(hallwayCorner).load(hallwayCorner);
                            //clipboard.flip(CuboidClipboard.FlipDirection.WEST_EAST);
                            clipboard.rotate2D(270);
                            clipboard.paste(editSession, position, noAir, entities);

                            clipboard  = SchematicFormat.getFormat(hallwayCorner).load(hallwayCorner);
                            clipboard.flip(CuboidClipboard.FlipDirection.WEST_EAST);
                            clipboard.rotate2D(270);
                            clipboard.paste(editSession, position, noAir, entities);



                        } catch (MaxChangedBlocksException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (com.sk89q.worldedit.data.DataException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

        }
        return true;
    }

    public com.sk89q.worldedit.Vector translateVector(Location location)
    {
        return new com.sk89q.worldedit.Vector(location.getX(),location.getY(),location.getZ());
    }

    public static void setSkullUrl(String skinUrl, Block block, int position, int rotation) {
        block.setType(Material.SKULL);
        block.setData((byte) position);
        Skull skullData = (Skull)block.getState();
        skullData.setSkullType(SkullType.PLAYER);

        TileEntitySkull skullTile = (TileEntitySkull)((CraftWorld)block.getWorld()).getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
        skullTile.setGameProfile(getNonPlayerProfile(skinUrl));
        skullTile.setRotation(rotation);
        block.getState().update(true);
    }

    public static GameProfile getNonPlayerProfile(String skinURL) {
        GameProfile newSkinProfile = new GameProfile(UUID.randomUUID(), null);
        newSkinProfile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"" + skinURL + "\"}}}")));
        return newSkinProfile;
    }
}
