package net.velion.kingdoms_arena.builder.score;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.Team;
import net.velion.kingdoms_arena.arena.entity.score.TeamScoreEntry;
import net.velion.kingdoms_arena.builder.entity.EntitySelector;
import net.velion.kingdoms_arena.builder.zone.SelectorException;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TeamScoreEntryBuilder
{
    protected UUID uuid;
    protected EntitySelector entitySelector;
    protected Set<PlayerScoreEntryBuilder> playerScoreEntryBuilders;

    public TeamScoreEntryBuilder(UUID uuid, EntitySelector entitySelector)
    {
        this.uuid = uuid;
        this.entitySelector = entitySelector;
        playerScoreEntryBuilders = new HashSet<>();
    }

    public TeamScoreEntry build(Set<ArenaEntity> entities) throws SQLException, SelectorException
    {
        Team team = (Team) entitySelector.select(entities);

        TeamScoreEntry teamScoreEntry = new TeamScoreEntry(uuid, team);
        for (PlayerScoreEntryBuilder playerScoreEntryBuilder : playerScoreEntryBuilders)
        {
            teamScoreEntry.addPlayerScoreEntry(playerScoreEntryBuilder.build(entities));
        }
        return teamScoreEntry;
    }


    public void setPlayerEntries(Set<PlayerScoreEntryBuilder> playerScoreEntryBuilders)
    {
        this.playerScoreEntryBuilders = playerScoreEntryBuilders;
    }
}
