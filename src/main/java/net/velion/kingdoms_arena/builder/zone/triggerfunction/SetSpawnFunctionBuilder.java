package net.velion.kingdoms_arena.builder.zone.triggerfunction;

import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.SetSpawnFunction;

public class SetSpawnFunctionBuilder extends TriggerFunctionBuilder
{
    @Override
    public SetSpawnFunction build(Zone zone)
    {
        SetSpawnFunction setSpawnFunction = new SetSpawnFunction(zone);

        return setSpawnFunction;
    }
}
