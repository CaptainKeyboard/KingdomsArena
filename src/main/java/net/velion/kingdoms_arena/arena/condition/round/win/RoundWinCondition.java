package net.velion.kingdoms_arena.arena.condition.round.win;

import net.velion.kingdoms_arena.arena.condition.round.RoundCondition;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.round.Round;

import java.util.Set;

public abstract class RoundWinCondition extends RoundCondition
{
    public RoundWinCondition(Round round)
    {
        super(round);
    }

    public abstract Set<ArenaEntity> check();
}
