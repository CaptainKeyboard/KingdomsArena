package net.velion.kingdoms_arena.builder.condition.round.end;

import net.velion.kingdoms_arena.arena.condition.round.end.LastStageOver;
import net.velion.kingdoms_arena.arena.round.Round;

public class LastStageOverBuilder extends RoundEndConditionBuilder
{
    @Override
    public LastStageOver build(Round round)
    {
        LastStageOver lastStageOver = new LastStageOver(round);
        return lastStageOver;
    }

}
