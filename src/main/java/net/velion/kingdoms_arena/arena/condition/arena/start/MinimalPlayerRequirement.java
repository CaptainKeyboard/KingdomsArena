package net.velion.kingdoms_arena.arena.condition.arena.start;

import net.velion.kingdoms_arena.arena.Arena;

public class MinimalPlayerRequirement extends ArenaStartCondition
{
    private int minPlayers;

    public MinimalPlayerRequirement(Arena arena)
    {
        super(arena);
        this.minPlayers = arena.getMinPlayers();
    }

    @Override
    public void setup()
    {

    }

    @Override
    public boolean check()
    {
        return arena.getPlayers().size() >= minPlayers;
    }

    @Override
    public void reset()
    {

    }
}
