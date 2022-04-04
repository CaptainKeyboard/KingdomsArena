package net.velion.kingdoms_arena.builder.condition.round.win;

import net.velion.kingdoms_arena.arena.condition.round.win.RoundWinCondition;
import net.velion.kingdoms_arena.arena.round.Round;
import net.velion.kingdoms_arena.builder.condition.round.RoundConditionBuilder;

public abstract class RoundWinConditionBuilder extends RoundConditionBuilder
{
    @Override
    public abstract RoundWinCondition build(Round round);
}
