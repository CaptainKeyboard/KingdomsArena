package net.velion.kingdoms_arena.event;

import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.TriggerFunction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public abstract class TriggerEvent extends Event
{
    protected static final HandlerList HANDLERS = new HandlerList();

    protected Zone zone;
    protected TriggerFunction triggerFunction;
    protected Set<Player> players;

    public TriggerEvent(Zone zone, TriggerFunction triggerFunction, Set<Player> players)
    {
        this.zone = zone;
        this.triggerFunction = triggerFunction;
        this.players = players;
    }


    public Zone getZone()
    {
        return zone;
    }

    public TriggerFunction getFunction()
    {
        return triggerFunction;
    }

    public Set<Player> getPlayers()
    {
        return players;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers()
    {
        return HANDLERS;
    }
}
