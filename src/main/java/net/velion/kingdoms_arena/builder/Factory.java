package net.velion.kingdoms_arena.builder;

import net.velion.kingdoms_arena.arena.entity.ArenaColor;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;
import net.velion.kingdoms_arena.builder.condition.arena.end.LastRoundOverParser;
import net.velion.kingdoms_arena.builder.condition.arena.start.MinimalPlayerRequirementParser;
import net.velion.kingdoms_arena.builder.condition.arena.win.MostRoundsWonBuilder;
import net.velion.kingdoms_arena.builder.condition.round.end.LastStageOverBuilder;
import net.velion.kingdoms_arena.builder.condition.round.win.RoundMostXGainedBuilder;
import net.velion.kingdoms_arena.builder.entity.EntitySelector;
import net.velion.kingdoms_arena.builder.entity.TeamBuilder;
import net.velion.kingdoms_arena.builder.objective.PointObjectiveBuilder;
import net.velion.kingdoms_arena.builder.order.OrderBuilder;
import net.velion.kingdoms_arena.builder.round.LinearRoundBuilder;
import net.velion.kingdoms_arena.builder.round.RoundSpawnPointsSelectorBuilder;
import net.velion.kingdoms_arena.builder.score.ArenaScoreIndexBuilder;
import net.velion.kingdoms_arena.builder.stage.StageBuilder;
import net.velion.kingdoms_arena.builder.zone.ArenaLocationBuilder;
import net.velion.kingdoms_arena.builder.zone.SpawnPointBuilder;
import net.velion.kingdoms_arena.builder.zone.ZoneSelector;
import org.bukkit.Location;

import java.util.UUID;

public class Factory
{
    public static final class NEW
    {
        public static ArenaLocationBuilder ArenaLocation(UUID uuid, Location location)
        {
            return new ArenaLocationBuilder(uuid, location);
        }

        public static ArenaScoreIndexBuilder ArenaScoreIndex()
        {
            return new ArenaScoreIndexBuilder();
        }

        public static OrderBuilder Order(UUID uuid)
        {
            return new OrderBuilder(uuid);
        }

        public static SpawnPointBuilder SpawnPoint(String name, ArenaLocationBuilder arenaLocation,
                                                   double radius)
        {
            return new SpawnPointBuilder(name, arenaLocation, radius);
        }

        public static StageBuilder Stage(UUID uuid)
        {
            return new StageBuilder(uuid);
        }

        public static TeamArenaBuilder TeamArena(UUID uuid, String name, int minPlayers)
        {
            return new TeamArenaBuilder(uuid, name, minPlayers);
        }

        public static final class ENTITY
        {

            public static TeamBuilder Team(UUID uuid, String name, ArenaColor arenaColor)
            {
                return new TeamBuilder(uuid, name, arenaColor);
            }
        }

        public static final class ROUND
        {
            public static LinearRoundBuilder LinearStaging(UUID uuid)
            {
                return new LinearRoundBuilder(uuid);
            }

            public static final class WINCONDITION
            {
                public static RoundMostXGainedBuilder MostXGained(ScoreType scoreType)
                {
                    return new RoundMostXGainedBuilder(scoreType);
                }
            }

            public static final class ENDCONDITION
            {

                public static LastStageOverBuilder LastStageOver()
                {
                    return new LastStageOverBuilder();
                }
            }

            public static final class STARTCONDITION
            {

            }
        }

        public static final class OBJECTIVE
        {

            public static PointObjectiveBuilder PointObjective(UUID uuid, int value)
            {
                return new PointObjectiveBuilder(uuid, value);
            }
        }

        public static final class ARENA
        {
            public static final class WINCONDITION
            {

                public static MostRoundsWonBuilder MostRoundsWon()
                {
                    return new MostRoundsWonBuilder();
                }
            }

            public static final class STARTCONDITION
            {

                public static MinimalPlayerRequirementParser MinimalPlayerRequirement()
                {
                    return new MinimalPlayerRequirementParser();
                }
            }

            public static final class ENDCONDITION
            {
                public static LastRoundOverParser LastRoundOver()
                {
                    return new LastRoundOverParser();
                }
            }
        }
    }

    public static final class SELECT
    {

        public static EntitySelector Entity(UUID uuid)
        {
            return new EntitySelector(uuid);
        }

        public static RoundSpawnPointsSelectorBuilder RoundSpawnPointSelector(UUID uuid, EntitySelector entity)
        {
            return new RoundSpawnPointsSelectorBuilder();
        }

        public static ZoneSelector TriggerZone(String name)
        {
            return new ZoneSelector(name);
        }
    }
}
