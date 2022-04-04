package net.velion.kingdoms_arena.arena.entity;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.inventory.Inventory;
import net.velion.kingdoms_arena.arena.entity.score.TeamScoreEntry;
import net.velion.kingdoms_arena.arena.order.Order;
import net.velion.kingdoms_arena.arena.zone.Zone;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Set;
import java.util.UUID;

public class Team extends ArenaEntity
{
    protected Set<Player> players = new NoNullHashSet<>();

    public Team(UUID uuid, String name, Arena arena, ArenaColor arenaColor)
    {
        super(uuid, name, arena, arenaColor);
        reset();
    }

    public void setup()
    {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.scoreboardTeam = scoreboard.registerNewTeam(name);
        scoreboardTeam.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY,
                org.bukkit.scoreboard.Team.OptionStatus.FOR_OTHER_TEAMS);
    }

    @Override
    public Inventory getRespawnInventory()
    {
        return respawnInventory;
    }

    @Override
    public void setRespawnInventory(Inventory inventory)
    {
        this.respawnInventory = inventory;
    }

    public Set<Player> getPlayers()
    {
        return players;
    }

    @Override
    public TeamScoreEntry getScoreEntry()
    {
        return (TeamScoreEntry) scoreEntry;
    }

    @Override
    public void addAllSpawnPoints(Set<Zone> spawnPoints)
    {
        this.spawnPoints.addAll(spawnPoints);
        for (Player player : players)
        {
            player.addAllSpawnPoints(spawnPoints);
        }
    }

    public void reset()
    {
        players = new NoNullHashSet<>();
    }

    @Override
    public void setScoreboard(Scoreboard scoreboard)
    {
        this.scoreboard = scoreboard;
    }

    public void setTeamScoreEntry(TeamScoreEntry teamScoreEntry)
    {
        this.scoreEntry = teamScoreEntry;
        teamScoreEntry.setTeam(this);
    }

    @Override
    public void setScoreboardTeam(org.bukkit.scoreboard.Team scoreboardTeam)
    {
        this.scoreboardTeam = scoreboardTeam;
    }

    @Override
    public void setOrder(Order order)
    {
        this.order = order;
        for (Player player : players)
        {
            player.setOrder(order);
        }
    }

    @Override
    public void addSpawnPoint(Zone spawnPoint)
    {
        this.spawnPoints.add(spawnPoint);
        for (Player player : players)
        {
            player.addSpawnPoint(spawnPoint);
        }
    }

    @Override
    public void setSpawnPoints(Set<Zone> spawnPoints)
    {
        this.spawnPoints = spawnPoints;
    }

    public void addPlayer(Player player)
    {
        players.add(player);
        player.setTeam(this);
    }

    public void removePlayer(Player player)
    {
        players.remove(player);
        player.setTeam(null);
    }

    @Override
    public void respawn()
    {
        for (Player player : players)
        {
            player.respawn();
        }
    }

    @Override
    public void sendMessage(String message)
    {
        for (Player player : players)
        {
            player.getBukkitPlayer().sendMessage(message);
        }
    }

    @Override
    public boolean validate()
    {
        if (players == null || players.isEmpty())
        {
            System.out.println("No players in ArenaTeam found");
            return false;
        }
        return true;
    }
}
