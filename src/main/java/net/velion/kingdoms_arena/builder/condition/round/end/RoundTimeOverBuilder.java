package net.velion.kingdoms_arena.builder.condition.round.end;

import net.velion.kingdoms_arena.arena.condition.round.end.RoundTimeOver;
import net.velion.kingdoms_arena.arena.round.Round;

public class RoundTimeOverBuilder extends RoundEndConditionBuilder
{
    protected double timeInSeconds;

    public RoundTimeOverBuilder(double timeInSeconds)
    {
        this.timeInSeconds = timeInSeconds;
    }

    @Override
    public RoundTimeOver build(Round round)
    {
        RoundTimeOver roundTimeOver = new RoundTimeOver(round, timeInSeconds);

        return roundTimeOver;
    }
}
