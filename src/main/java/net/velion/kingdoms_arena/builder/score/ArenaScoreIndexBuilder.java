package net.velion.kingdoms_arena.builder.score;

import net.velion.kingdoms_arena.arena.entity.score.ScoreIndex;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;

import java.util.HashMap;
import java.util.Map;

public class ArenaScoreIndexBuilder
{

    protected Map<ScoreType, Double> scorePoints;

    public ArenaScoreIndexBuilder()
    {
        scorePoints = new HashMap<>();
    }

    public ArenaScoreIndexBuilder addScorePoints(ScoreType scoreType, double value)
    {
        scorePoints.put(scoreType, value);
        return this;
    }

    public ScoreIndex build()
    {
        ScoreIndex scoreIndex = new ScoreIndex();

        for (Map.Entry<ScoreType, Double> entry : scorePoints.entrySet())
        {
            ScoreType scoreType = entry.getKey();
            double value = entry.getValue();

            scoreIndex.putActionPoints(scoreType, value);
        }

        return scoreIndex;
    }

    public void setScorePointBuilders(Map<ScoreType, Double> scorePoints)
    {
        this.scorePoints = scorePoints;
    }
}
