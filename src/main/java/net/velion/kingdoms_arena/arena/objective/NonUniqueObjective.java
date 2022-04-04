package net.velion.kingdoms_arena.arena.objective;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;

import java.util.UUID;

public abstract class NonUniqueObjective extends Objective
{
    public NonUniqueObjective(UUID uuid, ArenaEntity operator)
    {
        super(uuid, operator);
    }
}
