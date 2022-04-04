package net.velion.kingdoms_arena.command;

import net.velion.kingdoms_arena.Kingdoms_arena;
import net.velion.kingdoms_arena.arena.Arena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String[] args)
    {
        Player player = (Player) sender;

        String arenaName = args[0];
        Arena arena = Kingdoms_arena.getArenaByName(arenaName);

        if (arena != null)
        {
            try
            {
                arena.start();
            } catch (Exception e)
            {
                player.sendMessage("Uuups, something went wrong!");
                e.printStackTrace();
            }
            player.sendMessage("Arena: " + arena.getName() + " started!");
        } else
        {
            {
                player.sendMessage("No arena with this name found");
            }
        }
        return true;
    }
}
