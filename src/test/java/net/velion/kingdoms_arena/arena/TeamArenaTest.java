package net.velion.kingdoms_arena.arena;

import net.velion.kingdoms_arena.arena.entity.ArenaColor;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;
import net.velion.kingdoms_arena.builder.Factory;
import net.velion.kingdoms_arena.builder.zone.SelectorException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TeamArenaTest
{
    private static MockedStatic<Bukkit> bukkit;
    private static World world;

    private UUID arenaUuid;

    @BeforeAll
    public static void beforeAll() throws SQLException
    {
        bukkit = mockStatic(Bukkit.class);
        world = mock(World.class);
        when(world.getName()).thenReturn("TestWorld");
        bukkit.when(() -> Bukkit.getWorld("TestWorld")).thenReturn(world);
    }

    @AfterEach
    public void afterEach() throws Exception
    {
    }

    @AfterAll
    public static void afterAll()
    {
        bukkit.close();
    }

    @Test
    public void shouldBeValid() throws SQLException, ArenaInvalidException, SelectorException
    {
        arenaUuid = UUID.randomUUID();
        UUID team1 = UUID.randomUUID();
        UUID team2 = UUID.randomUUID();
        String spawnPoint1 = "spawnPoint1";
        String spawnPoint2 = "spawnPoint2";
        UUID location1 = UUID.randomUUID();
        UUID location2 = UUID.randomUUID();
        UUID round1 = UUID.randomUUID();
        UUID stage1 = UUID.randomUUID();
        UUID order1 = UUID.randomUUID();
        UUID order2 = UUID.randomUUID();
        UUID pointObjective1 = UUID.randomUUID();
        UUID pointObjective2 = UUID.randomUUID();
        UUID roundWinCondition = UUID.randomUUID();
        UUID roundEndCondition = UUID.randomUUID();
        UUID spawnPointMap1 = UUID.randomUUID();
        UUID spawnPointMap2 = UUID.randomUUID();
        UUID arenaWinCondition = UUID.randomUUID();
        UUID arenaStartCondition = UUID.randomUUID();
        UUID arenaEndCondition = UUID.randomUUID();

        TeamArena teamArena = (TeamArena) Factory.NEW.TeamArena(arenaUuid, arenaUuid.toString(), 2)
                .setTeams(
                        Factory.NEW.ENTITY.Team(team1, team1.toString(), ArenaColor.Test()),
                        Factory.NEW.ENTITY.Team(team2, team2.toString(), ArenaColor.Test()))
                .setSpawnPoints(
                        Factory.NEW.SpawnPoint(spawnPoint1,
                                Factory.NEW.ArenaLocation(location1, new Location(world, 1, 5, 11)), 7),
                        Factory.NEW.SpawnPoint(spawnPoint2,
                                Factory.NEW.ArenaLocation(location2, new Location(world, -2, 4, 13)), 6)
                )
                .setRounds(
                        Factory.NEW.ROUND.LinearStaging(round1)
                                .setStages(
                                        Factory.NEW.Stage(stage1)
                                                .putOrder(
                                                        Factory.SELECT.Entity(team1),
                                                        Factory.NEW.Order(order1)
                                                                .setObjectives(
                                                                        Factory.NEW.OBJECTIVE.PointObjective(
                                                                                pointObjective1,
                                                                                3)
                                                                )
                                                )
                                                .putOrder(
                                                        Factory.SELECT.Entity(team2),
                                                        Factory.NEW.Order(order2)
                                                                .setObjectives(
                                                                        Factory.NEW.OBJECTIVE.PointObjective(
                                                                                pointObjective2,
                                                                                2)
                                                                )
                                                )
                                )
                                .setWinConditions(
                                        Factory.NEW.ROUND.WINCONDITION.MostXGained(ScoreType.POINTS)
                                )
                                .setEndConditions(
                                        Factory.NEW.ROUND.ENDCONDITION.LastStageOver()
                                )
                                .setSpawnPoints(
                                        Factory.SELECT.Entity(team1),
                                        Factory.SELECT.TriggerZone(spawnPoint1)
                                )
                                .setSpawnPoints(
                                        Factory.SELECT.Entity(team2),
                                        Factory.SELECT.TriggerZone(spawnPoint2)
                                )
                )
                .setScoreIndexBuilder(
                        Factory.NEW.ArenaScoreIndex()
                                .addScorePoints(ScoreType.KILLS, 3)
                                .addScorePoints(ScoreType.DEATHS, -1)
                )
                .setWinConditions(
                        Factory.NEW.ARENA.WINCONDITION.MostRoundsWon()
                )
                .setStartConditions(
                        Factory.NEW.ARENA.STARTCONDITION.MinimalPlayerRequirement()
                )
                .setEndConditions(
                        Factory.NEW.ARENA.ENDCONDITION.LastRoundOver()
                )
                .build();

        assertTrue(teamArena.validate());
    }
}
