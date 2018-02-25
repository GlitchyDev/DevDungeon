package com.devotedworker.Old.DungeonBuilding.Builders;

import com.devotedworker.FileIO.SchematicLoader;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.registry.WorldData;
import org.bukkit.Location;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AbstractBuilder {


    public static Vector translateVector(Location location) {
        return new Vector(location.getX(), location.getY(), location.getZ());
    }


    /*

    public static void pasteSchematic(String schematicName, EditSession buildSession, Location location, int rotation)
    {

        try {
            CuboidClipboard clipboard = SchematicFormat.getFormat(SchematicLoader.getSchematic(schematicName)).load(SchematicLoader.getSchematic(schematicName));
            clipboard.paste(buildSession,translateVector(location),true,false);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataException e) {
            e.printStackTrace();
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }
    }
    */

    /*
    public static void pasteSchematic(String schematicName, EditSession buildSession, Location location, int rotation) {
        try {
            CuboidClipboard clipboard = SchematicFormat.getFormat(SchematicLoader.getSchematic(schematicName)).load(SchematicLoader.getSchematic(schematicName));
            clipboard.rotate2D(rotation);
            clipboard.paste(buildSession, translateVector(location), true, false);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataException e) {
            e.printStackTrace();
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }

    }
    */


    public static void pasteSchematic(String schematicName, EditSession buildSession, Location location, int rotation) {

        try {
            ClipboardReader reader = ClipboardFormat.SCHEMATIC.getReader(new FileInputStream(SchematicLoader.getSchematic(schematicName)));

            WorldData worldData = buildSession.getWorld().getWorldData();
            Clipboard clipboard = reader.read(worldData);
            ClipboardHolder holder = new ClipboardHolder(clipboard, worldData);
            buildSession.enableQueue();
            buildSession.setFastMode(true);
            Vector to = translateVector(location);

            AffineTransform transform = new AffineTransform();
            transform = transform.rotateY(-rotation);
            holder.setTransform(holder.getTransform().combine(transform));

            final Operation operation = holder
                    .createPaste(buildSession, worldData)
                    .to(to)
                    .ignoreAirBlocks(false)
                    .build();
            Operations.completeBlindly(operation);
            buildSession.flushQueue();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}














    /*

    public static void pasteSchematic(String schematicName, EditSession buildSession, Location location, int rotation)
    {
        File schematic = SchematicLoader.getSchematic(schematicName);
        LocalSession session = new LocalSession();
        try {
            FileInputStream fis = new FileInputStream(schematic);

            BufferedInputStream bis = new BufferedInputStream(fis);
            ClipboardReader reader = ClipboardFormat.SCHEMATIC.getReader(bis);
            Clipboard clipboard = reader.read(buildSession.getWorld().getWorldData());
            session.setClipboard(new ClipboardHolder(clipboard, buildSession.getWorld().getWorldData()));
            ClipboardHolder holder = session.getClipboard();
            rotate(session, (double) rotation);
            Operation operation = holder
                    .createPaste(buildSession, buildSession.getWorld().getWorldData())
                    .to(translateVector(location))
                    .ignoreAirBlocks(false)
                    .build();
            Operations.completeLegacy(operation);
        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (EmptyClipboardException e) {
            e.printStackTrace();
        }
    }

    public static void rotate(LocalSession session, Double yRotate) throws EmptyClipboardException {
        ClipboardHolder holder = session.getClipboard();
        AffineTransform transform = new AffineTransform();
        transform = transform.rotateY(-(yRotate != null ? yRotate : 0));
        holder.setTransform(holder.getTransform().combine(transform));
    }

*/







