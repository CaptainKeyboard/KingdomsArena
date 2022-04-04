package net.velion.kingdoms_arena.builder.zone;

import net.velion.kingdoms_arena.arena.zone.ArenaLocation;
import org.bukkit.Location;

import java.util.UUID;

public class ArenaLocationBuilder
{
    protected UUID uuid;
    protected Location location;

    public ArenaLocationBuilder(UUID uuid, Location location)
    {
        this.uuid = uuid;
        this.location = location;
    }

    public ArenaLocation build()
    {
        ArenaLocation arenaLocation = new ArenaLocation(uuid, location);

        return arenaLocation;
    }
}
