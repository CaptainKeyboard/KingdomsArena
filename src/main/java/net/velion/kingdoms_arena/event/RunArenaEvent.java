package net.velion.kingdoms_arena.event;

import net.velion.kingdoms_arena.arena.Arena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RunArenaEvent extends Event
{
    private static final HandlerList HANDLERS = new HandlerList();
    private Arena arena;

    public RunArenaEvent(Arena arena)
    {
        this.arena = arena;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLERS;
    }

    public Arena getArena()
    {
        return arena;
    }

    @Override
    public @NotNull HandlerList getHandlers()
    {
        return HANDLERS;
    }
}
