package net.velion.kingdoms_arena.builder.objective;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.objective.KillObjective;
import net.velion.kingdoms_arena.arena.zone.Zone;

import java.util.Set;
import java.util.UUID;

public class KillObjectiveBuilder extends ObjectiveBuilder
{
    protected int value;

    public KillObjectiveBuilder(UUID uuid, int value)
    {
        super(uuid);
        this.value = value;
    }

    @Override
    public KillObjective build(ArenaEntity entity, Set<Zone> zones)
    {
        KillObjective killObjective = new KillObjective(uuid, entity, value);

        return killObjective;
    }
}
