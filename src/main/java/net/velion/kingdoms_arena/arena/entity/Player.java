package net.velion.kingdoms_arena.arena.entity;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.inventory.Inventory;
import net.velion.kingdoms_arena.arena.entity.score.PlayerScoreEntry;
import net.velion.kingdoms_arena.arena.zone.Zone;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class Player extends ArenaEntity
{
    protected Team team;
    protected PlayerStorage playerStorage;

    public Player(org.bukkit.entity.Player bukkitPlayer, Arena arena, ArenaColor arenaColor)
    {
        super(bukkitPlayer.getUniqueId(), bukkitPlayer.getName(), arena, arenaColor);
        playerStorage = new PlayerStorage(this);
    }

    /**
     * For testing only!
     * <p>
     * Use {@link #Player(org.bukkit.entity.Player, Arena, ArenaColor)} insead.
     */
    @Deprecated
    public Player(UUID uuid, String name, Arena arena, ArenaColor arenaColor)
    {
        super(uuid, name, arena, arenaColor);
    }

    /**
     * Only for testing!
     * <p>
     * Use {@link #Player(org.bukkit.entity.Player, Arena, ArenaColor)} instead.
     */
    @Deprecated
    protected Player(UUID uuid)
    {
        super(uuid);
    }

    public Team getTeam()
    {
        return team;
    }

    @Override
    public Inventory getRespawnInventory()
    {
        return respawnInventory;
    }

    @Override
    public void setRespawnInventory(Inventory inventory)
    {
        respawnInventory = inventory;
    }

    public PlayerStorage getPlayerStorage()
    {
        return playerStorage;
    }

    public void setPlayerStorage(PlayerStorage playerStorage)
    {
        this.playerStorage = playerStorage;
        playerStorage.setPlayer(this);
    }

    public void setPlayerScoreEntry(PlayerScoreEntry playerScoreEntry)
    {
        this.scoreEntry = playerScoreEntry;
        playerScoreEntry.setPlayer(this);
    }

    @Override
    public void setScoreboardTeam(org.bukkit.scoreboard.Team scoreboardTeam)
    {
        this.scoreboardTeam = scoreboardTeam;
        scoreboardTeam.addEntry(name);
    }

    @Override
    public void setSpawnPoints(Set<Zone> spawnPoints)
    {
        this.spawnPoints = spawnPoints;
    }

    public void setScoreboard(Scoreboard scoreboard)
    {
        this.scoreboard = scoreboard;
        getBukkitPlayer().setScoreboard(scoreboard);
    }

    @Override
    public PlayerScoreEntry getScoreEntry()
    {
        return (PlayerScoreEntry) scoreEntry;
    }

    public org.bukkit.entity.Player getBukkitPlayer()
    {
        org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(uuid);
        return bukkitPlayer;
    }

    public void setTeam(Team team)
    {
        this.team = team;
    }

    public void resetInventory()
    {
        getBukkitPlayer().getInventory().setContents(respawnInventory.getContent());
    }


    @Override
    public void sendMessage(String message)
    {
        getBukkitPlayer().sendMessage(message);
    }

    @Override
    public boolean validate()
    {
        return true;
    }

    public void restore()
    {
        playerStorage.restore();
    }

    public void store()
    {
        playerStorage.store();
    }

    public org.bukkit.scoreboard.Team getScoreboardTeam()
    {
        return scoreboardTeam;
    }

    public Set<Zone> getSpawnPoints()
    {
        return spawnPoints;
    }

    @Override
    public void respawn()
    {
        Random rand = new Random();
        List<Zone> spawnPointList = spawnPoints.stream().toList();
        Zone spawnPoint = spawnPointList.get(rand.nextInt(spawnPoints.size()));
        getBukkitPlayer().teleport(spawnPoint.getArenaLocation().getBukkitLocation());
        if (respawnInventory != null)
        {
            getBukkitPlayer().getInventory().setContents(respawnInventory.getContent());
        }
    }
}
