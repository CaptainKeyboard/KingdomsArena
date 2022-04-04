package net.velion.kingdoms_arena.arena.condition.arena.win;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.score.ArenaScoreTable;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;

import java.util.Set;

public class ArenaMostXGained extends ArenaWinCondition
{
    ScoreType scoreType;

    public ArenaMostXGained(Arena arena, ScoreType scoreType)
    {
        super(arena);
        this.scoreType = scoreType;
    }

    @Override
    public void setup()
    {

    }

    @Override
    public Set<ArenaEntity> check()
    {
        ArenaScoreTable arenaScoreTable = arena.getArenaScoreTable();

        Set<ArenaEntity> winners = new NoNullHashSet<>();
        Set<ArenaEntity> entities = arena.getEntities();

        double max = -1;
        for (ArenaEntity arenaEntity : entities)
        {
            double points = arenaScoreTable.getScore(arenaEntity, scoreType);
            if (max == points)
            {
                winners.add(arenaEntity);
            } else if (max < points)
            {
                max = points;
                winners.clear();
                winners.add(arenaEntity);
            }
        }

        return winners;
    }

    @Override
    public void reset()
    {
    }
}
