package net.velion.kingdoms_arena.builder.condition.round.end;

import net.velion.kingdoms_arena.arena.condition.round.end.RoundEndCondition;
import net.velion.kingdoms_arena.arena.round.Round;
import net.velion.kingdoms_arena.builder.condition.round.RoundConditionBuilder;

public abstract class RoundEndConditionBuilder extends RoundConditionBuilder
{
    @Override
    public abstract RoundEndCondition build(Round round);
}
