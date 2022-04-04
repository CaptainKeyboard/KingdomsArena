package net.velion.kingdoms_arena.builder.condition.arena.win;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.win.ArenaMostXGained;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;

public class MostXGainedBuilder extends ArenaWinConditionBuilder
{
    protected ScoreType scoreType;

    public MostXGainedBuilder(ScoreType scoreType)
    {
        this.scoreType = scoreType;
    }

    @Override
    public ArenaMostXGained build(Arena arena)
    {
        ArenaMostXGained roundMostXGained = new ArenaMostXGained(arena, scoreType);

        return roundMostXGained;
    }
}
