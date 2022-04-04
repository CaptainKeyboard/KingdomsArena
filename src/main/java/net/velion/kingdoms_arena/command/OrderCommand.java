package net.velion.kingdoms_arena.command;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static net.velion.kingdoms_arena.Kingdoms_arena.ARENA_PLAYERS;

public class OrderCommand implements CommandExecutor
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

            player.sendMessage(arenaPlayer.getOrder().toString());
        }
        return true;
    }
}
