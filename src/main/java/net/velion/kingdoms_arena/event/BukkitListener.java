package net.velion.kingdoms_arena.event;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.Status;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.entity.score.PlayerScoreEntry;
import net.velion.kingdoms_arena.arena.entity.score.ScoreIndex;
import net.velion.kingdoms_arena.arena.entity.score.ScoreType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import static net.velion.kingdoms_arena.Kingdoms_arena.ARENA_PLAYERS;

public class BukkitListener implements Listener
{
    @EventHandler
    public static void onItemDrop(PlayerDropItemEvent event)
    {
        org.bukkit.entity.Player player = event.getPlayer();

        if (ARENA_PLAYERS.containsKey(player))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEvent(PlayerInteractEvent e)
    {
        //TODO add skills
    }

    @EventHandler
    public static void onPlayerDeath(PlayerRespawnEvent event)
    {
        org.bukkit.entity.Player player = event.getPlayer();
        if (ARENA_PLAYERS.containsKey(player))
        {
            Arena arena = ARENA_PLAYERS.get(player);
            if (arena.getStatus() == Status.RUNNING)
            {
                Player arenaPlayer = arena.getArenaPlayer(player);
                event.setRespawnLocation(arenaPlayer.getRandomSpawnPoint().getArenaLocation().getBukkitLocation());
            }
        }
    }

    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event)
    {
        org.bukkit.entity.Player player = event.getEntity().getPlayer();
        org.bukkit.entity.Player killer = event.getEntity().getKiller();

        if (ARENA_PLAYERS.containsKey(player))
        {
            Arena arena = ARENA_PLAYERS.get(player);

            if (arena.getStatus() == Status.RUNNING)
            {
                Player arenaPlayer = arena.getArenaPlayer(player);
                PlayerScoreEntry playerScoreEntry = arenaPlayer.getScoreEntry();
                ScoreIndex scoreIndex = arena.getScoreIndex();

                if (killer != null)
                {
                    if (ARENA_PLAYERS.containsKey(killer))
                    {
                        if (player != killer)
                        {
                            Player killerPlayer = arena.getArenaPlayer(killer);
                            PlayerScoreEntry killerScoreEntry = killerPlayer.getScoreEntry();

                            if (arenaPlayer.getTeam().equals(killerPlayer.getTeam()))
                            {
                                killerScoreEntry.addScore(ScoreType.TEAM_KILLS, 1);
                                killerScoreEntry.addScore(ScoreType.POINTS, scoreIndex.getPoints(ScoreType.TEAM_KILLS));
                            } else
                            {
                                killerScoreEntry.addScore(ScoreType.KILLS, 1);
                                killerScoreEntry.addScore(ScoreType.POINTS, scoreIndex.getPoints(ScoreType.KILLS));
                            }
                            killerScoreEntry.updateScoreboard(false);
                        }

                        playerScoreEntry.addScore(ScoreType.DEATHS, 1);
                        playerScoreEntry.addScore(ScoreType.POINTS, scoreIndex.getPoints(ScoreType.DEATHS));

                        playerScoreEntry.updateScoreboard(true);
                    } else
                    {
                        event.setCancelled(true);
                    }
                }
                event.setKeepInventory(true);
                event.setKeepLevel(true);
                event.getDrops().clear();
            } else
            {
                killer.sendMessage("Arena not running!");
                killer.sendMessage("Arena status: " + arena.getStatus().toString());


                event.setCancelled(true);
            }
        }
    }
}