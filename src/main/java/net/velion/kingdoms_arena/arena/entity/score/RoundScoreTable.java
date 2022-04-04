package net.velion.kingdoms_arena.arena.entity.score;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;

import java.util.Set;
import java.util.UUID;

public class RoundScoreTable
{
    protected UUID uuid;

    protected Set<ArenaEntity> winner = new NoNullHashSet<>();

    protected Set<ScoreEntry> scoreEntries = new NoNullHashSet<>();

    protected ArenaScoreTable arenaScoreTable;

    public ArenaScoreTable getArenaScoreTable()
    {
        return arenaScoreTable;
    }

    public double getScore(ArenaEntity entity, ScoreType scoreType)
    {
        ScoreEntry scoreEntry =
                scoreEntries.stream().filter(scoreEntry1 -> scoreEntry1.getEntity().equals(entity)).findFirst()
                        .orElse(null);

        if (scoreEntry != null)
        {
            return scoreEntry.getScore(scoreType);
        } else
        {
            return 0;
        }
    }

    public Set<ScoreEntry> getScoreEntries()
    {
        return scoreEntries;
    }

    public Set<ArenaEntity> getWinners()
    {
        return winner;
    }

    public void setArenaScoreTable(ArenaScoreTable arenaScoreTable)
    {
        this.arenaScoreTable = arenaScoreTable;
    }

    public void setScoreEntries(Set<ScoreEntry> scoreEntries)
    {
        this.scoreEntries.clear();
        this.scoreEntries.addAll(scoreEntries);
    }

    public void addScoreEntry(ScoreEntry scoreEntry)
    {
        this.scoreEntries.add(scoreEntry);
    }

    public void setWinner(Set<ArenaEntity> winner)
    {
        this.winner.clear();
        this.winner.addAll(winner);
    }

    public RoundScoreTable(UUID uuid)
    {
        this.uuid = uuid;
    }
}
