package net.velion.kingdoms_arena.arena.entity.inventory;

import net.velion.core.NoNullHashSet;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class Inventory
{
    private Set<Item> items = new NoNullHashSet<>();

    public Inventory(Set<Item> items)
    {
        this.items = items;
    }

    public Inventory(ItemStack[] contents)
    {
        setItems(contents);
    }

    public Set<Item> getItems()
    {
        return items;
    }

    public void setItems(Set<Item> items)
    {
        this.items = items;
    }

    public void setItems(ItemStack[] contents)
    {
        for (int i = 0; i < contents.length; i++)
        {
            ItemStack itemStack = contents[i];
            if (itemStack != null)
            {
                items.add(new Item(i, itemStack));
            }
        }
    }

    public void addItem(Item item)
    {
        items.add(item);
    }

    public ItemStack[] getContent()
    {
        ItemStack[] itemStacks = new ItemStack[41];

        for (Item item : items)
        {
            int inventorySlot = item.getSlot();
            itemStacks[inventorySlot] = item.getItemStack();
        }

        return itemStacks;
    }
}
