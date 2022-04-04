package net.velion.kingdoms_arena.arena.entity.score;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;

import java.util.Set;
import java.util.UUID;

public class ArenaScoreTable
{
    protected UUID uuid;

    protected Set<RoundScoreTable> roundScoreTables = new NoNullHashSet<>();

    public ArenaScoreTable(UUID uuid)
    {
        this.uuid = uuid;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setRoundScoreTables(Set<RoundScoreTable> roundScoreTables)
    {
        this.roundScoreTables.clear();
        this.roundScoreTables.addAll(roundScoreTables);
    }

    public void addRoundScoreTable(RoundScoreTable roundScoreTable)
    {
        roundScoreTables.add(roundScoreTable);
        roundScoreTable.setArenaScoreTable(this);
    }

    public Set<RoundScoreTable> getRoundScoreTables()
    {
        return roundScoreTables;
    }

    public double getScore(ArenaEntity arenaEntity, ScoreType scoreType)
    {
        return roundScoreTables.stream().map(scoreTable -> scoreTable.getScore(arenaEntity, scoreType))
                .reduce(0.0, Double::sum);
    }
}
