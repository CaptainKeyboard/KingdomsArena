package net.velion.kingdoms_arena.arena.entity.inventory;

import org.bukkit.inventory.ItemStack;

public class Item
{
    private int slot;
    private ItemStack itemStack;

    public Item(int slot, ItemStack itemStack)
    {
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public int getSlot()
    {
        return slot;
    }

    public ItemStack getItemStack()
    {
        return itemStack;
    }
}
