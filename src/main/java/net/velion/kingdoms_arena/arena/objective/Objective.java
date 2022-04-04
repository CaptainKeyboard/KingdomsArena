package net.velion.kingdoms_arena.arena.objective;

import net.velion.kingdoms_arena.arena.ArenaInvalidException;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;

import java.util.UUID;

public abstract class Objective
{
    protected UUID uuid;
    protected ArenaEntity operator;

    public Objective(UUID uuid, ArenaEntity operator)
    {
        this.uuid = uuid;
        this.operator = operator;
    }

    public abstract void enable();

    public abstract void disable();

    public ArenaEntity getOperator()
    {
        return operator;
    }

    public void validate() throws ArenaInvalidException
    {
        if (operator == null)
        {
            throw new ArenaInvalidException("ExpectedOwner is null");
        }
    }

    public abstract boolean check();
}
