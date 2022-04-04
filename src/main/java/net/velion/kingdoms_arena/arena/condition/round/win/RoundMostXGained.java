package net.velion.kingdoms_arena.arena.condition.round.win;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.score.RoundScoreTable;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;
import net.velion.kingdoms_arena.arena.round.Round;

import java.util.Set;

public class RoundMostXGained extends RoundWinCondition
{
    protected ScoreType scoreType;

    public RoundMostXGained(Round round, ScoreType scoreType)
    {
        super(round);
        this.scoreType = scoreType;
    }

    @Override
    public void setup()
    {
    }

    @Override
    public Set<ArenaEntity> check()
    {
        Set<ArenaEntity> winners = new NoNullHashSet<>();
        Set<ArenaEntity> entities = round.getEntities();
        RoundScoreTable roundScoreTable = round.getRoundScoreTable();

        double max = -1;
        for (ArenaEntity arenaEntity : entities)
        {
            double points = roundScoreTable.getScore(arenaEntity, scoreType);
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
