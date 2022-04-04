package net.velion.kingdoms_arena.builder.score;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.entity.score.PlayerScoreEntry;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;
import net.velion.kingdoms_arena.builder.entity.EntitySelector;
import net.velion.kingdoms_arena.builder.zone.SelectorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PlayerScoreEntryBuilder
{
    protected UUID uuid;
    protected EntitySelector entitySelector;
    protected Map<ScoreType, Double> scores;

    public PlayerScoreEntryBuilder(UUID uuid, EntitySelector entitySelector)
    {
        this.uuid = uuid;
        this.entitySelector = entitySelector;
        scores = new HashMap<>();
    }

    public void setScores(Map<ScoreType, Double> scores)
    {
        this.scores = scores;
    }

    public PlayerScoreEntry build(Set<ArenaEntity> entities) throws SelectorException
    {
        Player arenaPlayer = (Player) entitySelector.select(entities);

        PlayerScoreEntry playerScoreEntry = new PlayerScoreEntry(uuid, arenaPlayer);

        return playerScoreEntry;
    }

    public void setScore(ScoreType scoreType, double value)
    {
        scores.put(scoreType, value);
    }
}
