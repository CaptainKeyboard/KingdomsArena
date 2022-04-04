package net.velion.kingdoms_arena.arena.condition.arena.end;

import net.velion.kingdoms_arena.arena.Arena;

import java.sql.SQLException;

public class MissingPlayerRequirement extends ArenaEndCondition
{
    protected int minPlayers;

    public MissingPlayerRequirement(Arena arena)
    {
        super(arena);
        this.minPlayers = arena.getMinPlayers();
    }

    @Override
    public void setup()
    {

    }

    @Override
    public boolean check() throws SQLException
    {
        if (arena.getPlayers().size() < minPlayers)
        {
            arena.end();
            return true;
        }
        return false;
    }

    @Override
    public void reset()
    {
    }
}
