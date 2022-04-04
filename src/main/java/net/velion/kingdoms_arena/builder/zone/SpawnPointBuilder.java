package net.velion.kingdoms_arena.builder.zone;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.zone.ArenaLocation;
import net.velion.kingdoms_arena.arena.zone.TriggerSphere;

public class SpawnPointBuilder extends TriggerSphereBuilder
{
    private boolean replace;

    public SpawnPointBuilder(String name, ArenaLocationBuilder arenaLocationBuilder, double radius)
    {
        super(name, arenaLocationBuilder, radius);
    }

    public TriggerSphere build(Arena arena)
    {
        ArenaLocation arenaLocation = arenaLocationBuilder.build();

        TriggerSphere triggerSphere = new TriggerSphere(name, arena, arenaLocation, radius);

        return triggerSphere;
    }
}
