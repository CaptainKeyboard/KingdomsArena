package net.velion.kingdoms_arena.builder.objective;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.objective.Objective;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.builder.zone.SelectorException;

import java.util.Set;
import java.util.UUID;

public abstract class ObjectiveBuilder
{
    protected UUID uuid;

    public ObjectiveBuilder(UUID uuid)
    {
        this.uuid = uuid;
    }

    public abstract Objective build(ArenaEntity entity, Set<Zone> zones) throws SelectorException;
}
