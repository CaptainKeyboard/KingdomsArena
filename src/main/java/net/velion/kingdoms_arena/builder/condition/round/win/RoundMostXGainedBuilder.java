package net.velion.kingdoms_arena.builder.condition.round.win;

import net.velion.kingdoms_arena.arena.condition.round.win.RoundMostXGained;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;
import net.velion.kingdoms_arena.arena.round.Round;

public class RoundMostXGainedBuilder extends RoundWinConditionBuilder
{
    protected ScoreType scoreType;

    public RoundMostXGainedBuilder(ScoreType scoreType)
    {
        this.scoreType = scoreType;
    }

    @Override
    public RoundMostXGained build(Round round)
    {
        RoundMostXGained roundMostXGained = new RoundMostXGained(round, scoreType);

        return roundMostXGained;
    }
}
