package net.velion.kingdoms_arena.arena.entity.score;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.entity.Team;

import java.util.Set;
import java.util.UUID;

public class TeamScoreEntry extends ScoreEntry
{
    protected Set<PlayerScoreEntry> scoreEntrySet = new NoNullHashSet<>();

    public TeamScoreEntry(UUID uuid, Team team)
    {
        super(uuid, team);
    }

    public Team getTeam()
    {
        return (Team) entity;
    }

    public void addPlayerScoreEntry(PlayerScoreEntry playerScoreEntry)
    {
        scoreEntrySet.add(playerScoreEntry);
    }

    @Override
    public UUID getUuid()
    {
        return uuid;
    }

    @Override
    public double getScore(ScoreType scoreType)
    {
        double test =
                ((Team) entity).getPlayers().stream().mapToDouble(player -> player.getScoreEntry().getScore(scoreType))
                        .sum();
        if (scores.containsKey(scoreType))
        {
            test += scores.get(scoreType);
        }
        return test;
    }

    public void setPlayerEntries(Set<PlayerScoreEntry> playerScoreEntries)
    {
        this.scoreEntrySet.addAll(playerScoreEntries);
    }

    public void setTeam(Team team)
    {

    }
}
