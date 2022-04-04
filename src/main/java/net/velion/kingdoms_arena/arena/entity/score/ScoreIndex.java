package net.velion.kingdoms_arena.arena.entity.score;

import net.velion.kingdoms_arena.arena.ArenaInvalidException;

import java.util.HashMap;
import java.util.Map;

public class ScoreIndex
{
    private Map<ScoreType, Double> actionPoints = new HashMap<>();

    public void setActionPoints(Map<ScoreType, Double> actionPoints)
    {
        this.actionPoints.clear();
        this.actionPoints.putAll(actionPoints);
    }

    public void putActionPoints(ScoreType scoreType, double value)
    {
        actionPoints.put(scoreType, value);
    }

    public void validate() throws ArenaInvalidException
    {
        if (actionPoints.isEmpty())
        {
            throw new ArenaInvalidException("No action points available.");
        }
    }

    public double getPoints(ScoreType scoreType)
    {
        return actionPoints.containsKey(scoreType) ? actionPoints.get(scoreType) : 0;
    }
}
