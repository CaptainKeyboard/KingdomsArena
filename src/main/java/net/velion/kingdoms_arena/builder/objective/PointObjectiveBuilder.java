package net.velion.kingdoms_arena.builder.objective;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.objective.PointObjective;
import net.velion.kingdoms_arena.arena.zone.Zone;

import java.util.Set;
import java.util.UUID;

public class PointObjectiveBuilder extends ObjectiveBuilder
{
    protected int value;

    public PointObjectiveBuilder(UUID uuid, int value)
    {
        super(uuid);
        this.value = value;
    }

    @Override
    public PointObjective build(ArenaEntity entity, Set<Zone> zones)
    {
        PointObjective pointObjective = new PointObjective(uuid, entity, value);

        return pointObjective;
    }
}
