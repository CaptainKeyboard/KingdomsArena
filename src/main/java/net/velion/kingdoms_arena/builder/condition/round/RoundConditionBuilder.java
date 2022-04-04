package net.velion.kingdoms_arena.builder.condition.round;

import net.velion.kingdoms_arena.arena.condition.round.RoundCondition;
import net.velion.kingdoms_arena.arena.round.Round;
import net.velion.kingdoms_arena.builder.condition.ConditionInserter;

public abstract class RoundConditionBuilder extends ConditionInserter
{
    public abstract RoundCondition build(Round round);
}
