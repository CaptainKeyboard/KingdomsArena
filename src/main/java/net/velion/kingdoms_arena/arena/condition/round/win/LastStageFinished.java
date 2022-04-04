package net.velion.kingdoms_arena.arena.condition.round.win;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.round.Round;

import java.util.Set;

public class LastStageFinished extends RoundWinCondition
{
    public LastStageFinished(Round round)
    {
        super(round);
    }

    @Override
    public void setup()
    {

    }

    @Override
    public Set<ArenaEntity> check()
    {
        return round.getFinisher();
    }

    @Override
    public void reset()
    {

    }
}
