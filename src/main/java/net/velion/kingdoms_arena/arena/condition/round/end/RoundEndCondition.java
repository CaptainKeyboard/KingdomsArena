package net.velion.kingdoms_arena.arena.condition.round.end;

import net.velion.kingdoms_arena.arena.condition.round.RoundCondition;
import net.velion.kingdoms_arena.arena.round.Round;

public abstract class RoundEndCondition extends RoundCondition
{
    public RoundEndCondition(Round round)
    {
        super(round);
    }

    public abstract boolean check();
}
