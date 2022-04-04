package net.velion.kingdoms_arena.command;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import static net.velion.kingdoms_arena.Kingdoms_arena.ARENA_PLAYERS;

public class ArenaScoreboardCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String[] args)
    {
        org.bukkit.entity.Player player = (org.bukkit.entity.Player) sender;

        if (ARENA_PLAYERS.containsKey(player))
        {
            Arena arena = ARENA_PLAYERS.get(player);
            Player arenaPlayer = arena.getArenaPlayer(player);

            Scoreboard scoreboard = player.getScoreboard();

            Objective objective = scoreboard.registerNewObjective("Order", "dummy", "Order");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            for (net.velion.kingdoms_arena.arena.objective.Objective arenaObjective : arenaPlayer.getOrder()
                    .getObjectives())
            {
                Score score = objective.getScore(arenaObjective.toString());
                score.setScore(1);
            }
        }

        return true;
    }
}
