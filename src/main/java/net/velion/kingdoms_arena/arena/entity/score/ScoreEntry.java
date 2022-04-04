package net.velion.kingdoms_arena.arena.entity.score;

import net.kyori.adventure.text.Component;
import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.objective.Objective;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class ScoreEntry
{
    protected UUID uuid;
    protected ArenaEntity entity;
    protected Map<ScoreType, Double> scores = new HashMap<>();

    public ScoreEntry(UUID uuid, ArenaEntity entity)
    {
        this.uuid = uuid;
        this.entity = entity;
    }

    public ArenaEntity getEntity()
    {
        return entity;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public abstract double getScore(ScoreType scoreType);

    public void setScore(ScoreType scoreType, double value)
    {
        this.scores.put(scoreType, value);
    }

    public void addScore(ScoreType scoreType, double value)
    {
        if (scores.containsKey(scoreType))
        {
            scores.put(scoreType, scores.get(scoreType) + value);
        } else
        {
            scores.put(scoreType, value);
        }
    }

    public void updateScoreboard(boolean checkArena)
    {
        Arena arena = entity.getArena();
        Scoreboard scoreboard = entity.getScoreboard();
        org.bukkit.scoreboard.Objective scoreboardObjective = scoreboard.getObjective(DisplaySlot.SIDEBAR);

        if (scoreboardObjective != null)
        {
            scoreboardObjective.unregister();
        }

        org.bukkit.scoreboard.Objective newObjective =
                scoreboard.registerNewObjective("Order", "dummy", Component.text("Objectives"));
        newObjective.setDisplaySlot(DisplaySlot.SIDEBAR);


        for (Objective arenaObjective : entity.getOrder().getObjectives())
        {
            Score score = newObjective.getScore(arenaObjective.toString());
            score.setScore(1);
        }

        if (checkArena)
        {
            arena.check();
        }
    }
}
