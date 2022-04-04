package net.velion.kingdoms_arena.arena.round;

import net.velion.core.NoNullArrayList;
import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.ArenaInvalidException;
import net.velion.kingdoms_arena.arena.condition.round.end.RoundEndCondition;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.entity.Team;
import net.velion.kingdoms_arena.arena.entity.inventory.Inventory;
import net.velion.kingdoms_arena.arena.entity.score.PlayerScoreEntry;
import net.velion.kingdoms_arena.arena.entity.score.TeamScoreEntry;
import net.velion.kingdoms_arena.arena.stage.Stage;
import net.velion.kingdoms_arena.arena.zone.Zone;

import java.util.*;
import java.util.stream.Collectors;

public class LinearRound extends Round
{
    protected Integer currentStage = 0;

    protected List<Stage> stages = new NoNullArrayList<>();

    public LinearRound(Arena arena, UUID uuid)
    {
        super(arena, uuid);
        this.currentStage = 0;
    }

    @Override
    public Stage getCurrentStage()
    {
        return stages.get(currentStage);
    }

    @Override
    public Set<ArenaEntity> getFinisher()
    {
        Set<ArenaEntity> finisher = new NoNullHashSet<>();
        finisher.addAll(getCurrentStage().getFinisher());
        return finisher;
    }

    @Override
    public Set<Zone> getSpawnPoints(ArenaEntity arenaEntity)
    {
        return spawnPointMap.get(arenaEntity);
    }

    public void setStages(List<Stage> stages)
    {
        this.stages = stages;
    }

    public List<Stage> getStages()
    {
        return stages;
    }

    public void addStage(Stage stage)
    {
        this.stages.add(stage);
        stage.setRound(this);
    }

    @Override
    public boolean validate() throws ArenaInvalidException
    {
        if (stages == null || stages.isEmpty())
        {
            throw new ArenaInvalidException("Stages are either null or empty");
        } else
        {
            for (Stage stage : stages)
            {
                stage.validate();
            }
        }
        return true;
    }

    @Override
    public void enable()
    {
        reset();

        for (Map.Entry<ArenaEntity, Set<Zone>> entry : spawnPointMap.entrySet())
        {
            ArenaEntity arenaEntity = entry.getKey();
            arenaEntity.setSpawnPoints(entry.getValue());
        }

        for (Map.Entry<ArenaEntity, Inventory> entry : invetoryMap.entrySet())
        {
            ArenaEntity arenaEntity = entry.getKey();
            Inventory inventory = entry.getValue();

            arenaEntity.setRespawnInventory(inventory);
        }

        Set<Team> teams = arena.getArenaTeams();
        for (Team team : teams)
        {
            TeamScoreEntry teamScoreEntry = new TeamScoreEntry(UUID.randomUUID(), team);
            team.setTeamScoreEntry(teamScoreEntry);
            roundScoreTable.addScoreEntry(teamScoreEntry);

            for (Player player : team.getPlayers())
            {
                PlayerScoreEntry playerScoreEntry = new PlayerScoreEntry(UUID.randomUUID(), player);
                teamScoreEntry.addPlayerScoreEntry(playerScoreEntry);
                player.setPlayerScoreEntry(playerScoreEntry);
                player.setArenaColor(team.getArenaColor());
                player.setScoreboard(team.getScoreboard());
                player.setScoreboardTeam(team.getScoreboardTeam());
                player.setSpawnPoints(team.getSpawnPoints());
                player.setRespawnInventory(team.getRespawnInventory());
            }
        }

        stages.get(currentStage).enable();

        for (Player player : arena.getPlayers())
        {
            player.respawn();
        }
    }

    @Override
    public boolean check()
    {
        if (stages.get(currentStage).check())
        {
            if (hasNextStage())
            {
                getCurrentStage().reset();
                currentStage++;
                stages.get(currentStage).enable();
            } else
            {
                if (roundEndConditions.stream().allMatch(RoundEndCondition::check))
                {
                    over = true;
                    end();
                } else
                {
                }
            }
        }
        return over;
    }

    @Override
    public void end()
    {
        Set<ArenaEntity> winners;

        Set<Map.Entry<ArenaEntity, Long>> entityOccurencies =
                roundWinConditions.stream().flatMap(roundWinCondition -> roundWinCondition.check().stream())
                        .collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet();

        Long maxAmount =
                entityOccurencies.stream().map(Map.Entry::getValue).max(Comparator.comparing(aLong -> aLong)).get();

        winners =
                entityOccurencies.stream().filter(stringLongEntry -> stringLongEntry.getValue().equals(maxAmount))
                        .map(Map.Entry::getKey).collect(Collectors.toSet());

        roundScoreTable.setWinner(winners);

        Set<ArenaEntity> entities = arena.getEntities();
        for (ArenaEntity arenaEntity : entities)
        {
            //getCurrentStage(entity).end();
        }
    }

    @Override
    public void reset()
    {
        currentStage = 0;
        for (Stage stage : stages)
        {
            stage.reset();
        }
        over = false;
    }

    @Override
    public boolean hasNextStage()
    {
        return currentStage + 1 < stages.size();
    }
}
