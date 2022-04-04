package net.velion.kingdoms_arena.builder.entity;

import net.velion.kingdoms_arena.arena.entity.inventory.Inventory;
import net.velion.kingdoms_arena.arena.entity.inventory.InventorySlot;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilder
{
    protected ItemStack[] items = new ItemStack[41];

    public InventoryBuilder()
    {
        for (int i = 0; i < items.length; i++)
        {
            items[i] = new ItemStack(Material.AIR);
        }
    }

    public Inventory build()
    {
        Inventory inventory = new Inventory(items);
        return inventory;
    }

    public void setItems(ItemStack[] itemStacks)
    {
        items = itemStacks;
    }

    public InventoryBuilder setSlot(InventorySlot inventorySlot, ItemStack itemStack)
    {
        items[inventorySlot.get()] = itemStack;
        return this;
    }
}
