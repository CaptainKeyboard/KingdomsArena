package net.velion.kingdoms_arena.arena.condition.arena.end;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.ArenaCondition;

import java.sql.SQLException;

public abstract class ArenaEndCondition extends ArenaCondition
{
    public ArenaEndCondition(Arena arena)
    {
        super(arena);
    }

    public abstract boolean check() throws SQLException;
}
