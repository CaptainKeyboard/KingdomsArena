package net.velion.kingdoms_arena.arena.round;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.ArenaInvalidException;
import net.velion.kingdoms_arena.arena.condition.round.end.RoundEndCondition;
import net.velion.kingdoms_arena.arena.condition.round.win.RoundWinCondition;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.inventory.Inventory;
import net.velion.kingdoms_arena.arena.entity.score.RoundScoreTable;
import net.velion.kingdoms_arena.arena.stage.Stage;
import net.velion.kingdoms_arena.arena.zone.Zone;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class Round
{
    protected UUID uuid;
    protected Arena arena;
    protected Map<ArenaEntity, Inventory> invetoryMap;
    protected boolean over;
    protected RoundScoreTable roundScoreTable;
    protected Set<RoundEndCondition> roundEndConditions = new NoNullHashSet<>();
    protected Set<RoundWinCondition> roundWinConditions = new NoNullHashSet<>();
    protected Map<ArenaEntity, Set<Zone>> spawnPointMap = new HashMap<>();

    public Round(Arena arena, UUID uuid)
    {
        this.arena = arena;
        this.uuid = uuid;
        over = false;
        roundScoreTable = new RoundScoreTable(UUID.randomUUID());
        arena.getArenaScoreTable().addRoundScoreTable(roundScoreTable);
    }

    public void addRoundEndCondition(RoundEndCondition roundEndCondition)
    {
        roundEndConditions.add(roundEndCondition);
    }

    public Set<RoundEndCondition> getRoundEndConditions()
    {
        return roundEndConditions;
    }

    public void addRoundWinCondition(RoundWinCondition roundWinCondition)
    {
        roundWinConditions.add(roundWinCondition);
    }

    public Set<RoundWinCondition> getRoundWinConditions()
    {
        return roundWinConditions;
    }

    public abstract Stage getCurrentStage();

    public Set<ArenaEntity> getEntities()
    {
        return arena.getEntities();
    }

    public abstract Set<ArenaEntity> getFinisher();

    public RoundScoreTable getRoundScoreTable()
    {
        return roundScoreTable;
    }

    public abstract Set<Zone> getSpawnPoints(ArenaEntity arenaEntity);

    public boolean isOver()
    {
        return over;
    }

    public void setEndConditions(Set<RoundEndCondition> roundEndConditions)
    {
        this.roundEndConditions = roundEndConditions;
    }

    public void setInvetoryMap(Map<ArenaEntity, Inventory> invetoryMap)
    {
        this.invetoryMap = invetoryMap;
    }

    public void setScoreTable(RoundScoreTable roundScoreTable)
    {
        this.roundScoreTable = roundScoreTable;
    }

    public void setSpawnPoints(Map<ArenaEntity, Set<Zone>> spawnPointMap)
    {
        this.spawnPointMap = spawnPointMap;
    }

    public void setWinConditions(Set<RoundWinCondition> roundWinConditions)
    {
        this.roundWinConditions = roundWinConditions;
    }

    public abstract boolean validate() throws ArenaInvalidException;

    public abstract void enable();

    public abstract boolean check();

    public abstract void end();

    public abstract void reset();

    public abstract boolean hasNextStage();

    public UUID getUuid()
    {
        return uuid;
    }
}
