package net.velion.kingdoms_arena.builder.zone.triggerfunction;

import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.AddSpawnFunction;

public class AddSpawnFunctionBuilder extends TriggerFunctionBuilder
{
    @Override
    public AddSpawnFunction build(Zone zone)
    {
        AddSpawnFunction addSpawnFunction = new AddSpawnFunction(zone);

        return addSpawnFunction;
    }
}
