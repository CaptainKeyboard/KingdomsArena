package net.velion.kingdoms_arena.arena.condition.arena.end;

import net.velion.kingdoms_arena.arena.Arena;

public class LastRoundOver extends ArenaEndCondition
{
    public LastRoundOver(Arena arena)
    {
        super(arena);
    }

    @Override
    public boolean check()
    {
        return arena.getCurrentRound().isOver() && !arena.hasNextRound();
    }

    @Override
    public void setup()
    {

    }

    @Override
    public void reset()
    {

    }
}
