package net.velion.kingdoms_arena.arena.condition.round.end;

import net.velion.kingdoms_arena.arena.round.Round;

public class LastStageOver extends RoundEndCondition
{
    public LastStageOver(Round round)
    {
        super(round);
    }

    @Override
    public void setup()
    {

    }

    @Override
    public boolean check()
    {
        if (round.getCurrentStage().isOver() && !round.hasNextStage())
        {
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public void reset()
    {
    }
}
