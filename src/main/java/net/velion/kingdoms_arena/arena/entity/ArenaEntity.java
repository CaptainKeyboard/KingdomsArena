package net.velion.kingdoms_arena.arena.entity;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.inventory.Inventory;
import net.velion.kingdoms_arena.arena.entity.score.ScoreEntry;
import net.velion.kingdoms_arena.arena.order.Order;
import net.velion.kingdoms_arena.arena.zone.Zone;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public abstract class ArenaEntity
{
    protected UUID uuid;
    public String name;
    protected Arena arena;
    protected Inventory respawnInventory;
    protected ArenaColor arenaColor;
    protected Order order;
    protected Set<Zone> spawnPoints = new NoNullHashSet<>();
    protected ScoreEntry scoreEntry;
    protected Scoreboard scoreboard;
    protected org.bukkit.scoreboard.Team scoreboardTeam;

    protected ArenaEntity(UUID uuid, String name, Arena arena, ArenaColor arenaColor)
    {
        this.uuid = uuid;
        this.name = name;
        this.arena = arena;
        this.arenaColor = arenaColor;
    }

    protected ArenaEntity(UUID uuid, Arena arena, ArenaColor arenaColor)
    {
        this.uuid = uuid;
        this.arena = arena;
        this.arenaColor = arenaColor;
    }

    /**
     * Only for testing.
     */
    @Deprecated(since = "Testing")
    protected ArenaEntity(UUID uuid)
    {
        this.uuid = uuid;
    }

    public Arena getArena()
    {
        return arena;
    }

    public abstract Inventory getRespawnInventory();

    public abstract void setRespawnInventory(Inventory inventory);

    public void addAllSpawnPoints(Set<Zone> value)
    {
        spawnPoints.addAll(value);
    }

    public void addSpawnPoint(Zone spawnPoint)
    {
        spawnPoints.add(spawnPoint);
    }

    public abstract void setScoreboardTeam(Team scoreboardTeam);

    public ArenaColor getArenaColor()
    {
        return arenaColor;
    }

    public void setArenaColor(ArenaColor arenaColor)
    {
        this.arenaColor = arenaColor;
    }

    public String getName()
    {
        return name;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
        scoreEntry.updateScoreboard(false);
    }

    public Scoreboard getScoreboard()
    {
        return scoreboard;
    }

    public abstract void setScoreboard(Scoreboard scoreboard);

    public abstract ScoreEntry getScoreEntry();

    public Set<Zone> getSpawnPoints()
    {
        return spawnPoints;
    }

    public abstract void setSpawnPoints(Set<Zone> spawnPoints);

    public Zone getRandomSpawnPoint()
    {
        Random random = new Random();
        List<Zone> spawnPointList = spawnPoints.stream().toList();
        return spawnPointList.get(random.nextInt(spawnPoints.size()));
    }

    public UUID getUuid()
    {
        return uuid;
    }

    /**
     * Should only be used from {@link DummyPlayer}
     */
    @Deprecated
    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public Team getScoreboardTeam()
    {
        return scoreboardTeam;
    }

    public abstract void respawn();

    public abstract void sendMessage(String message);

    public abstract boolean validate();
}
