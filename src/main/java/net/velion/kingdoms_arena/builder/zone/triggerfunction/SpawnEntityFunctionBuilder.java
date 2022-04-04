package net.velion.kingdoms_arena.builder.zone.triggerfunction;

import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.SpawnEntityFunction;
import net.velion.kingdoms_arena.builder.zone.ArenaLocationBuilder;
import org.bukkit.entity.EntityType;

public class SpawnEntityFunctionBuilder extends TriggerFunctionBuilder
{
    protected ArenaLocationBuilder arenaLocationBuilder;
    protected EntityType entityType;
    protected int amount;

    public SpawnEntityFunctionBuilder(ArenaLocationBuilder arenaLocationBuilder, EntityType entityType, int amount)
    {
        this.arenaLocationBuilder = arenaLocationBuilder;
        this.entityType = entityType;
        this.amount = amount;
    }

    @Override
    public SpawnEntityFunction build(Zone zone)
    {
        SpawnEntityFunction spawnEntityFunction =
                new SpawnEntityFunction(zone, arenaLocationBuilder.build(), entityType, amount);

        return spawnEntityFunction;
    }
}
