package net.velion.kingdoms_arena.arena.zone.triggerfunction;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.zone.Zone;

import java.util.Set;

public abstract class TriggerFunction
{
    protected Zone zone;
    protected Set<ArenaEntity> registeredEntities = new NoNullHashSet<>();
    protected boolean executed = false;
    protected ExecutionType executeOnceFor = ExecutionType.NONE;
    protected boolean enabled;

    public TriggerFunction(Zone zone)
    {
        this.zone = zone;
        enabled = false;
    }

    public Zone getTriggerZone()
    {
        return zone;
    }

    public void enable()
    {
        enabled = true;
    }

    public void disable()
    {
        enabled = false;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    protected void attachEntity(ArenaEntity arenaEntity)
    {
        registeredEntities.add(arenaEntity);
    }

    protected boolean isRegistered(ArenaEntity arenaEntity)
    {
        return registeredEntities.contains(arenaEntity);
    }

    public void reset()
    {
        registeredEntities.clear();
        executed = false;
    }

    protected void detachEntity(ArenaEntity arenaEntity)
    {
        registeredEntities.remove(arenaEntity);
    }

    public void execute(Set<Player> players)
    {
        _execute(players);
    }

    protected abstract void _execute(Set<Player> players);

    public void setExecuteOnceFor(ExecutionType executeOnceFor)
    {
        this.executeOnceFor = executeOnceFor;
    }
}
