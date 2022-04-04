package net.velion.kingdoms_arena.arena.condition.arena.start;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.ArenaCondition;

public abstract class ArenaStartCondition extends ArenaCondition
{
    public ArenaStartCondition(Arena arena)
    {
        super(arena);
    }

    public abstract boolean check();
}
