package net.velion.kingdoms_arena.arena.condition.round;

import jakarta.persistence.ManyToOne;
import net.velion.kingdoms_arena.arena.condition.Condition;
import net.velion.kingdoms_arena.arena.round.Round;

public abstract class RoundCondition extends Condition
{
    @ManyToOne
    protected Round round;

    public RoundCondition(Round round)
    {
        this.round = round;
    }

    public Round getRound()
    {
        return round;
    }

}
