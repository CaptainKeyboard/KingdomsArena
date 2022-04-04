package net.velion.kingdoms_arena.arena.objective;

import net.velion.kingdoms_arena.arena.ArenaInvalidException;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;

import java.util.UUID;

public class PointObjective extends NonUniqueObjective
{
    protected Double points;

    public PointObjective(UUID uuid, ArenaEntity operator, double points)
    {
        super(uuid, operator);
        this.points = points;
    }

    @Override
    public void enable()
    {

    }

    @Override
    public void disable()
    {

    }

    @Override
    public void validate() throws ArenaInvalidException
    {
        super.validate();
        if (points == 0)
        {
            throw new ArenaInvalidException("Value must not be zero");
        }
    }

    @Override
    public boolean check()
    {
        boolean test = operator.getScoreEntry().getScore(ScoreType.POINTS) >= points;
        return test;
    }

    @Override
    public String toString()
    {
        return "Points: " + operator.getScoreEntry().getScore(ScoreType.POINTS) + " / " + points;
    }
}
