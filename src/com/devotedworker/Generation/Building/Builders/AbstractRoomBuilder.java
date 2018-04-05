package com.devotedworker.Generation.Building.Builders;

import com.devotedworker.FileIO.SchematicLoader;
import com.devotedworker.Generation.DungeonGenerationMap;
import com.devotedworker.Generation.Rooms.AbstractRoom;
import com.devotedworker.plugin.DevDungeon;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.patterns.SingleBlockPattern;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.registry.WorldData;
import org.bukkit.Location;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractRoomBuilder {

    public abstract void build(DungeonGenerationMap dungeonGenerationMap, AbstractRoom room, BuildingPhase phase, Location location, Random random, EditSession editSession);

    public static Vector translateVector(Location location) {
        return new Vector(location.getX(), location.getY(), location.getZ());
    }

    public static void pasteSchematic(String schematicName, EditSession buildSession, Random random, Location location, int rotation) {
        DevDungeon.log("Attempting Paste! " + SchematicLoader.getSchematic(schematicName,random).toString());
        try {
            ClipboardReader reader = ClipboardFormat.SCHEMATIC.getReader(new FileInputStream(SchematicLoader.getSchematic(schematicName,random)));

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
                    .ignoreAirBlocks(true)
                    .build();
            Operations.completeBlindly(operation);
            buildSession.flushQueue();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pasteSchematicWithAir(String schematicName, EditSession buildSession, Random random, Location location, int rotation) {
        DevDungeon.log("Attempting Paste! " + SchematicLoader.getSchematic(schematicName,random).toString());
        try {
            ClipboardReader reader = ClipboardFormat.SCHEMATIC.getReader(new FileInputStream(SchematicLoader.getSchematic(schematicName,random)));


            if(reader == null)
            {
                DevDungeon.log("OBAMA");
            }
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

    public static void fillArea(EditSession buildSession, Location location, int radius, ArrayList<BaseBlock> blocks)
    {

        CuboidRegion cuboidRegion = new CuboidRegion(translateVector(location.add(2,0,2)),translateVector(location.add(-2,0,-2)));
        ArrayList<SingleBlockPattern> patterns = new ArrayList<>();
        for(BaseBlock block: blocks)
        {
            patterns.add(new SingleBlockPattern(block));
        }
        RandomPattern randomPattern = new RandomPattern();
        for(SingleBlockPattern pattern: patterns)
        {
            randomPattern.add(randomPattern,1.0/patterns.size());
        }
        buildSession.setBlocks(cuboidRegion,randomPattern);

    }
}
