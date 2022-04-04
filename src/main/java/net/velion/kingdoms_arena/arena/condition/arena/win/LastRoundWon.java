package net.velion.kingdoms_arena.arena.condition.arena.win;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;

import java.util.Set;

public class LastRoundWon extends ArenaWinCondition
{
    public LastRoundWon(Arena arena)
    {
        super(arena);
    }

    @Override
    public void setup()
    {

    }

    @Override
    public Set<ArenaEntity> check()
    {
        return arena.getCurrentRound().getRoundScoreTable().getWinners();
    }

    @Override
    public void reset()
    {

    }
}
