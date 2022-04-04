package net.velion.kingdoms_arena.arena.condition.arena;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.Condition;

public abstract class ArenaCondition extends Condition
{
    protected Arena arena;

    public ArenaCondition(Arena arena)
    {
        this.arena = arena;
    }
}
