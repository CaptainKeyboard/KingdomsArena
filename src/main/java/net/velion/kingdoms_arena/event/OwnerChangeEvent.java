package net.velion.kingdoms_arena.event;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.TriggerFunction;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class OwnerChangeEvent extends TriggerEvent
{
    protected static final HandlerList HANDLERS = new HandlerList();

    protected Zone zone;
    protected TriggerFunction triggerFunction;
    protected ArenaEntity newOwner;
    protected ArenaEntity oldOwner;

    public OwnerChangeEvent(Zone zone, TriggerFunction triggerFunction, ArenaEntity newOwner,
                            ArenaEntity oldOwner)
    {
        super(zone, triggerFunction, null);
        this.zone = zone;
        this.triggerFunction = triggerFunction;
        this.newOwner = newOwner;
        this.oldOwner = oldOwner;
    }

    public Zone getZone()
    {
        return zone;
    }

    public TriggerFunction getTriggerFunction()
    {
        return triggerFunction;
    }

    public ArenaEntity getNewOwner()
    {
        return newOwner;
    }

    public ArenaEntity getOldOwner()
    {
        return oldOwner;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLERS;
    }

    @Override
    public @NotNull
    HandlerList getHandlers()
    {
        return HANDLERS;
    }
}
