package com.devotedworker.Old;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.world.World;
import de.tr7zw.itemnbtapi.*;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
                        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                        NBTItem nbti = new NBTItem(head);
                        NBTCompound skull = nbti.addCompound("SkullOwner");
                        skull.setString("Name", "tr7zw");
                        skull.setString("Id", "fce0323d-7f50-4317-9720-5f6b14cf78ea");
                        NBTListCompound itemTexture = skull.addCompound("Properties").getList("textures", NBTType.NBTTagCompound).addCompound();
                        itemTexture.setString("Signature", "XpRfRz6/vXE6ip7/vq+40H6W70GFB0yjG6k8hG4pmFdnJFR+VQhslE0gXX/i0OAGThcAVSIT+/W1685wUxNofAiy+EhcxGNxNSJkYfOgXEVHTCuugpr+EQCUBI6muHDKms3PqY8ECxdbFTUEuWxdeiJsGt9VjHZMmUukkGhk0IobjQS3hjQ44FiT1tXuUU86oAxqjlKFpXG/iXtpcoXa33IObSI1S3gCKzVPOkMGlHZqRqKKElB54I2Qo4g5CJ+noudIDTzxPFwEEM6XrbM0YBi+SOdRvTbmrlkWF+ndzVWEINoEf++2hkO0gfeCqFqSMHuklMSgeNr/YtFZC5ShJRRv7zbyNF33jZ5DYNVR+KAK9iLO6prZhCVUkZxb1/BjOze6aN7kyN01u3nurKX6n3yQsoQQ0anDW6gNLKzO/mCvoCEvgecjaOQarktl/xYtD4YvdTTlnAlv2bfcXUtc++3UPIUbzf/jpf2g2wf6BGomzFteyPDu4USjBdpeWMBz9PxVzlVpDAtBYClFH/PFEQHMDtL5Q+VxUPu52XlzlUreMHpLT9EL92xwCAwVBBhrarQQWuLjAQXkp3oBdw6hlX6Fj0AafMJuGkFrYzcD7nNr61l9ErZmTWnqTxkJWZfZxmYBsFgV35SKc8rkRSHBNjcdKJZVN4GA+ZQH5B55mi4=");
                        itemTexture.setString("Value", "eyJ0aW1lc3RhbXAiOjE0OTMwNDkwMTcxNTIsInByb2ZpbGVJZCI6ImZjZTAzMjNkN2Y1MDQzMTc5NzIwNWY2YjE0Y2Y3OGVhIiwicHJvZmlsZU5hbWUiOiJ0cjd6dyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTI3NDZlNWU5OGMwZWRmZTU1YTI3ZGRjNjUxMmJmNjllYzJiYmNlNmM3ZmNhNTQ5YmEzNjZkYThiNTRjZTRkYiJ9fX0=");
                        head = nbti.getItem();
                        player.getInventory().addItem(head);
                        break;
                    case "TEST2":
                        if (args.length != 1) {
                            setSkullUrl(args[3], player.getLocation().getBlock(), Integer.valueOf(args[1]), Integer.valueOf(args[2]));
                        } else {
                            setSkullUrl("http://textures.minecraft.net/texture/58c206e29924b9916d4d24dfbbc38f28b44d6d3cfa23adec9ed3a8fce1b7b2", player.getLocation().getBlock(), Integer.valueOf(args[1]), Integer.valueOf(args[2]));
                        }
                        break;
                    case "TEST3":
                        getLogger().info(new NBTTileEntity(player.getLocation().getBlock().getState()).toString());
                        break;
                    case "TEST4":
                        File file = new File("plugins/WorldEdit/schematics/" + args[1] + ".schematic");
                        Vector position = translateVector(player.getLocation());
                        boolean noAir = true;
                        boolean entities = false;
                        World worldEditWorld = new BukkitWorld(player.getWorld());
                        EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(worldEditWorld, -1);
                        try {
                            CuboidClipboard c = SchematicFormat.getFormat(file).load(file);
                            c.flip(CuboidClipboard.FlipDirection.WEST_EAST);
                            SchematicFormat.getFormat(file).load(file).paste(editSession, position, noAir, entities);
                        } catch (MaxChangedBlocksException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (com.sk89q.worldedit.data.DataException e) {
                            e.printStackTrace();
                        }
                        editSession.flushQueue();
                        break;
                    case "TEST5":
                        File imageFile = new File(getDataFolder() + "/" + "Image.png");
                        BufferedImage bi = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g = bi.createGraphics();


                        g.setPaint(Color.black);
                        for (int x = 0; x < 50; x++)
                        {
                            for(int y = 0; y < 50; y++)
                            {
                                if(x % 2 == 0 && y % 2 == 0)
                                {
                                    g.drawRect(x,y,0,0);
                                }
                            }
                        }


                        try {
                            ImageIO.write(bi, "PNG", imageFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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

    public Vector translateVector(Location location)
    {
        return new Vector(location.getX(),location.getY(),location.getZ());
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
