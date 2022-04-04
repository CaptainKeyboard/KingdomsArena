package net.velion.kingdoms_arena.builder;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.TeamArena;
import net.velion.kingdoms_arena.arena.condition.arena.end.ArenaEndCondition;
import net.velion.kingdoms_arena.arena.condition.arena.start.ArenaStartCondition;
import net.velion.kingdoms_arena.arena.condition.arena.win.ArenaWinCondition;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.entity.Team;
import net.velion.kingdoms_arena.arena.entity.score.ScoreIndex;
import net.velion.kingdoms_arena.arena.round.Round;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.builder.condition.arena.end.ArenaEndConditionParser;
import net.velion.kingdoms_arena.builder.condition.arena.start.ArenaStartConditionParser;
import net.velion.kingdoms_arena.builder.condition.arena.win.ArenaWinConditionBuilder;
import net.velion.kingdoms_arena.builder.entity.PlayerBuilder;
import net.velion.kingdoms_arena.builder.entity.TeamBuilder;
import net.velion.kingdoms_arena.builder.round.RoundBuilder;
import net.velion.kingdoms_arena.builder.zone.*;

import java.util.*;

public class TeamArenaBuilder extends ArenaBuilder
{
    public TeamArenaBuilder(UUID uuid, String name, int minPlayers)
    {
        super(uuid, name, minPlayers);
    }

    @Override
    public TeamArena build() throws SelectorException
    {
        TeamArena teamArena = new TeamArena(uuid, name, minPlayers);

        Set<Team> teams = new NoNullHashSet();
        for (TeamBuilder teamBuilder : teamBuilders)
        {
            Team team = teamBuilder.build(teamArena);
            teams.add(team);
        }

        Set<Player> players = new NoNullHashSet<>();
        for (PlayerBuilder playerBuilder : playerBuilders)
        {
            Player player = playerBuilder.build(teamArena);
            players.add(player);
        }

        Set<ArenaEntity> entities = new NoNullHashSet<>();
        entities.addAll(teams);
        entities.addAll(players);

        Set<Zone> zones = new NoNullHashSet<>();
        for (SpawnPointBuilder spawnPointBuilder : spawnPointBuilders)
        {
            Zone spawnPoint = spawnPointBuilder.build(teamArena);
            zones.add(spawnPoint);
        }

        for (ZoneBuilder zoneBuilder : zoneBuilders)
        {
            Zone zone = zoneBuilder.build(teamArena);
            zones.add(zone);
        }

        for (CheckpointBuilder checkpointBuilder : checkpointBuilders)
        {
            Zone checkPoint = checkpointBuilder.build(teamArena);
            zones.add(checkPoint);
        }

        for (CapturePointBuilder capturePointBuilder : capturePointBuilders)
        {
            Zone capturePoint = capturePointBuilder.build(teamArena);
            zones.add(capturePoint);
        }

        List<Round> rounds = new ArrayList<>();
        for (RoundBuilder roundBuilder : roundsBuilders)
        {
            rounds.add(roundBuilder.build(teamArena, entities, zones));
        }

        Set<ArenaStartCondition> startConditions = new HashSet<>();
        for (ArenaStartConditionParser conditionBuilder : startConditionBuilders)
        {
            startConditions.add(conditionBuilder.build(teamArena));
        }

        Set<ArenaEndCondition> endConditions = new HashSet<>();
        for (ArenaEndConditionParser conditionBuilder : endConditionBuilders)
        {
            endConditions.add(conditionBuilder.build(teamArena));
        }

        Set<ArenaWinCondition> winConditions = new HashSet<>();
        for (ArenaWinConditionBuilder conditionBuilder : winConditionBuilders)
        {
            winConditions.add(conditionBuilder.build(teamArena));
        }

        ScoreIndex scoreIndex = scoreIndexBuilder.build();

        teamArena.setScoreIndex(scoreIndex);
        teamArena.setRounds(rounds);
        teamArena.setTriggerZones(zones);
        teamArena.setPlayers(players);
        teamArena.setTeams(teams);
        teamArena.setStartConditions(startConditions);
        teamArena.setEndConditions(endConditions);
        teamArena.setWinConditions(winConditions);

        return teamArena;
    }
}
