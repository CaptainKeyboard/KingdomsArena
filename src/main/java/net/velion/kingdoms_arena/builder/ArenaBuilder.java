package net.velion.kingdoms_arena.builder;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.builder.condition.arena.end.ArenaEndConditionParser;
import net.velion.kingdoms_arena.builder.condition.arena.start.ArenaStartConditionParser;
import net.velion.kingdoms_arena.builder.condition.arena.win.ArenaWinConditionBuilder;
import net.velion.kingdoms_arena.builder.entity.PlayerBuilder;
import net.velion.kingdoms_arena.builder.entity.TeamBuilder;
import net.velion.kingdoms_arena.builder.round.RoundBuilder;
import net.velion.kingdoms_arena.builder.score.ArenaScoreIndexBuilder;
import net.velion.kingdoms_arena.builder.score.ArenaScoreTableBuilder;
import net.velion.kingdoms_arena.builder.zone.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class ArenaBuilder
{
    protected UUID uuid;
    protected List<TeamBuilder> teamBuilders = new ArrayList<>();
    protected List<PlayerBuilder> playerBuilders = new ArrayList<>();
    protected int minPlayers;
    protected String name;
    protected ArenaScoreTableBuilder arenaScoreTableBuilder;
    protected ArenaScoreIndexBuilder scoreIndexBuilder;
    protected List<RoundBuilder> roundsBuilders = new ArrayList<>();
    protected List<SpawnPointBuilder> spawnPointBuilders = new ArrayList<>();
    protected List<CapturePointBuilder> capturePointBuilders = new ArrayList<>();
    protected List<CheckpointBuilder> checkpointBuilders = new ArrayList<>();
    protected List<ZoneBuilder> zoneBuilders = new ArrayList<>();
    protected List<ArenaStartConditionParser> startConditionBuilders = new ArrayList<>();
    protected List<ArenaWinConditionBuilder> winConditionBuilders = new ArrayList<>();
    protected List<ArenaEndConditionParser> endConditionBuilders = new ArrayList<>();

    protected ArenaBuilder(UUID uuid, String name, int minPlayers)
    {
        this.uuid = uuid;
        this.name = name;
        this.minPlayers = minPlayers;
    }

    public abstract Arena build() throws SelectorException;

    public ArenaBuilder setCapturePoints(CapturePointBuilder... capturePoints)
    {
        this.capturePointBuilders = List.of(capturePoints);
        return this;
    }

    public ArenaBuilder setScoreTable(ArenaScoreTableBuilder arenaScoreTableBuilder)
    {
        this.arenaScoreTableBuilder = arenaScoreTableBuilder;
        return this;
    }

    public ArenaBuilder setTeams(TeamBuilder... teamBuilders)
    {
        this.teamBuilders = List.of(teamBuilders);
        return this;
    }

    public ArenaBuilder setPlayers(PlayerBuilder... playerBuilders)
    {
        this.playerBuilders = List.of(playerBuilders);
        return this;
    }

    public ArenaBuilder setSpawnPoints(SpawnPointBuilder... spawnPointBuilders)
    {
        this.spawnPointBuilders = List.of(spawnPointBuilders);
        return this;
    }

    public ArenaBuilder setCheckpoints(CheckpointBuilder... checkpointBuilders)
    {
        this.checkpointBuilders = List.of(checkpointBuilders);
        return this;
    }

    public ArenaBuilder setZones(ZoneBuilder... zoneBuilders)
    {
        this.zoneBuilders = List.of(zoneBuilders);
        return this;
    }

    public ArenaBuilder setEndConditions(ArenaEndConditionParser... conditions)
    {
        this.endConditionBuilders = List.of(conditions);
        return this;
    }

    public ArenaBuilder setRounds(RoundBuilder... roundsBuilders)
    {
        this.roundsBuilders = List.of(roundsBuilders);
        return this;
    }

    public ArenaBuilder setScoreIndexBuilder(ArenaScoreIndexBuilder scoreIndexBuilder)
    {
        this.scoreIndexBuilder = scoreIndexBuilder;
        return this;
    }

    public ArenaBuilder setStartConditions(ArenaStartConditionParser... conditions)
    {
        this.startConditionBuilders = List.of(conditions);
        return this;
    }

    public ArenaBuilder setWinConditions(ArenaWinConditionBuilder... conditions)
    {
        this.winConditionBuilders = List.of(conditions);
        return this;
    }
}
