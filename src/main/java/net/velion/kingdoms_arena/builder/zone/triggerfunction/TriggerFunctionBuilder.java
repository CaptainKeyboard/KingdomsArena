package net.velion.kingdoms_arena.builder.zone.triggerfunction;

import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.ExecutionType;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.TriggerFunction;

public abstract class TriggerFunctionBuilder<T extends TriggerFunctionBuilder>
{
    protected ExecutionType executionType;

    public abstract TriggerFunction build(Zone zone);

    public T executeOnceFor(ExecutionType type)
    {
        this.executionType = type;
        return (T) this;
    }
}
