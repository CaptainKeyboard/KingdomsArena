package net.velion.kingdoms_arena.command;

import net.velion.kingdoms_arena.Kingdoms_arena;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class WatchItem implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String[] args)
    {
        Player player = (Player) sender;
        PlayerResourcePackStatusEvent.Status status = player.getResourcePackStatus();

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        ItemMeta itemMeta = itemStack.getItemMeta();

        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();

        persistentDataContainer.set(new NamespacedKey(Kingdoms_arena.KINGDOMS_ARENA, "baum"),
                PersistentDataType.INTEGER, 5);

        itemStack.setItemMeta(itemMeta);

        return true;
    }
}
