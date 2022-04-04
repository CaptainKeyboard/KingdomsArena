package net.velion.kingdoms_arena.builder.condition.round.win;

import net.velion.kingdoms_arena.arena.condition.round.win.LastStageFinished;
import net.velion.kingdoms_arena.arena.round.Round;

public class LastStageFinishedBuilder extends RoundWinConditionBuilder
{
    @Override
    public LastStageFinished build(Round round)
    {
        LastStageFinished lastStageFinished = new LastStageFinished(round);
        return lastStageFinished;
    }
}
