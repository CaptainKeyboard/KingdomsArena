package net.velion.kingdoms_arena.arena.entity;

import net.velion.kingdoms_arena.arena.entity.inventory.Inventory;
import net.velion.kingdoms_arena.arena.zone.ArenaLocation;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class PlayerStorage
{
    protected Player player;
    protected ArenaLocation bedLocation;
    protected Inventory inventory;
    protected ArenaLocation currentLocation;
    protected Team scoreboardTeam;
    protected GameMode gamemode;

    public PlayerStorage(ArenaLocation currentLocation, ArenaLocation bedLocation, Inventory inventory,
                         Team scoreboardTeam, GameMode gamemode)
    {
        this.currentLocation = currentLocation;
        this.bedLocation = bedLocation;
        this.inventory = inventory;
        this.scoreboardTeam = scoreboardTeam;
        this.gamemode = gamemode;
    }

    public PlayerStorage(Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public ArenaLocation getBedLocation()
    {
        return bedLocation;
    }

    public void setBedLocation(ArenaLocation bedLocation)
    {
        this.bedLocation = bedLocation;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
    }

    public ArenaLocation getCurrentLocation()
    {
        return currentLocation;
    }

    public void setCurrentLocation(ArenaLocation currentLocation)
    {
        this.currentLocation = currentLocation;
    }

    public Team getScoreboardTeam()
    {
        return scoreboardTeam;
    }

    public void setScoreboardTeam(Team scoreboardTeam)
    {
        this.scoreboardTeam = scoreboardTeam;
    }

    public GameMode getGamemode()
    {
        return gamemode;
    }

    public void setGamemode(GameMode gamemode)
    {
        this.gamemode = gamemode;
    }

    public void store()
    {
        org.bukkit.entity.Player bukkitPlayer = player.getBukkitPlayer();

        currentLocation = new ArenaLocation(UUID.randomUUID(), bukkitPlayer.getLocation());
        Location bukkitBedLocation = bukkitPlayer.getBedSpawnLocation();
        if (bukkitBedLocation != null)
        {
            bedLocation = new ArenaLocation(UUID.randomUUID(), bukkitBedLocation);
        }
        inventory = new Inventory(bukkitPlayer.getInventory().getContents());
        scoreboardTeam = bukkitPlayer.getScoreboard().getPlayerTeam(bukkitPlayer);
        gamemode = bukkitPlayer.getGameMode();
    }

    public void restore()
    {
        org.bukkit.entity.Player bukkitPlayer = player.getBukkitPlayer();

        if (bedLocation != null)
        {
            bukkitPlayer.setBedSpawnLocation(bedLocation.getBukkitLocation());
        }
        bukkitPlayer.setGameMode(gamemode);
        if (bukkitPlayer.isDead())
        {
            PlayerRespawnEvent playerRespawnEvent =
                    new PlayerRespawnEvent(bukkitPlayer, currentLocation.getBukkitLocation(), false, false);
            playerRespawnEvent.callEvent();
        } else
        {
            bukkitPlayer.teleport(currentLocation.getBukkitLocation());
        }
        bukkitPlayer.getInventory().setContents(inventory.getContent());
        Objective currentObjective = bukkitPlayer.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
        if (currentObjective != null)
        {
            currentObjective.unregister();
        }
        bukkitPlayer.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }
}
