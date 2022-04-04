package net.velion.kingdoms_arena.builder.score;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.score.RoundScoreTable;
import net.velion.kingdoms_arena.builder.zone.SelectorException;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RoundScoreTableBuilder
{
    protected UUID uuid;

    protected Set<TeamScoreEntryBuilder> scoreEntries;

    public RoundScoreTableBuilder()
    {
        scoreEntries = new HashSet<>();
        uuid = UUID.randomUUID();
    }

    public RoundScoreTableBuilder(UUID uuid)
    {
        this.uuid = uuid;
    }

    public RoundScoreTable build(Set<ArenaEntity> entities) throws SQLException, SelectorException
    {
        RoundScoreTable roundScoreTable = new RoundScoreTable(uuid);

        for (TeamScoreEntryBuilder teamScoreEntryBuilder : scoreEntries)
        {
            teamScoreEntryBuilder.build(entities);
        }

        return roundScoreTable;
    }

    public void setScoreEntries(Set<TeamScoreEntryBuilder> scoreEntries)
    {
        this.scoreEntries = scoreEntries;
    }
}
