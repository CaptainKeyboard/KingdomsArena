package net.velion.kingdoms_arena.builder.score;

import net.velion.kingdoms_arena.arena.entity.score.ArenaScoreTable;

import java.util.UUID;

public class ArenaScoreTableBuilder
{
    protected UUID uuid;

    public ArenaScoreTableBuilder(UUID uuid)
    {
        this.uuid = uuid;
    }

    public ArenaScoreTable build()
    {
        ArenaScoreTable arenaScoreTable = new ArenaScoreTable(uuid);

        return arenaScoreTable;
    }
}
