package net.velion.kingdoms_arena.arena.zone;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.CaptureFunction;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.HookType;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.PassiveFunction;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.TriggerFunction;
import net.velion.kingdoms_arena.event.*;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static net.velion.kingdoms_arena.Kingdoms_arena.KINGDOMS_ARENA;

public abstract class Zone
{
    protected Arena arena;
    protected String name;
    protected ArenaLocation arenaLocation;
    protected Set<Player> playersInside = new NoNullHashSet<>();
    protected Set<Player> playersEntered = new NoNullHashSet<>();
    protected Set<Player> playersLeft = new NoNullHashSet<>();
    protected ZoneRunnable zoneRunnable;
    protected Map<HookType, Set<TriggerFunction>> functions = new HashMap<>();
    protected CaptureFunction captureFunction;
    protected boolean enabled;
    protected ArenaEntity owner;

    public Zone(String name, Arena arena, ArenaLocation arenaLocation)
    {
        this.name = name;
        this.arena = arena;
        this.arenaLocation = arenaLocation;
        enabled = true;
        for (HookType hooktype : HookType.values())
        {
            functions.put(hooktype, new NoNullHashSet<>());
        }
    }

    public void setArena(Arena arena)
    {
        this.arena = arena;
    }

    public ArenaLocation getArenaLocation()
    {
        return arenaLocation;
    }

    public Arena getArena()
    {
        return arena;
    }

    public Set<TriggerFunction> getFunctions(HookType hookType)
    {
        return functions.get(hookType);
    }

    public void addPassiveFunction(PassiveFunction passiveFunction)
    {
        putFunction(HookType.PASSIVE, passiveFunction);
    }

    public void putFunction(HookType hookType, TriggerFunction triggerFunction)
    {
        functions.get(hookType).add(triggerFunction);
    }

    public CaptureFunction getCaptureFunction()
    {
        return captureFunction;
    }

    public void setCaptureFunction(CaptureFunction captureFunction)
    {
        this.captureFunction = captureFunction;
    }

    public String getName()
    {
        return name;
    }

    public ArenaEntity getOwner()
    {
        return owner;
    }

    public void setOwner(ArenaEntity owner)
    {
        ArenaEntity oldOwner = this.owner;
        this.owner = owner;
        if (owner != null && functions != null && functions.containsKey(HookType.OWNER_CHANGE))
        {
            functions.get(HookType.OWNER_CHANGE).forEach(triggerFunction ->
            {
                Bukkit.getPluginManager().callEvent(new OwnerChangeEvent(this, triggerFunction, owner, oldOwner));
            });
        }
    }

    public void enable()
    {
        zoneRunnable = new ZoneRunnable();
        zoneRunnable.runTaskTimer(KINGDOMS_ARENA, 0, 5);
    }

    public void tick()
    {
        if (functions != null && !functions.isEmpty())
        {
            Set<Player> nearbyPlayers = getNearbyPlayers();

            playersEntered = new NoNullHashSet<>(nearbyPlayers);
            playersEntered.removeAll(playersInside);

            playersLeft = new NoNullHashSet<>(playersInside);
            playersLeft.removeAll(nearbyPlayers);

            playersInside = new NoNullHashSet<>(nearbyPlayers);

            if (!playersEntered.isEmpty())
            {
                for (TriggerFunction triggerFunction : functions.get(HookType.ENTER))
                {
                    Bukkit.getPluginManager()
                            .callEvent(new EnterTriggerEvent(this, triggerFunction, playersEntered));
                }
            }

            if (!playersInside.isEmpty())
            {
                for (TriggerFunction triggerFunction : functions.get(HookType.INSIDE))
                {
                    Bukkit.getPluginManager()
                            .callEvent(new InsideTriggerEvent(this, triggerFunction, playersInside));
                }
            }

            if (!playersLeft.isEmpty())
            {
                for (TriggerFunction triggerFunction : functions.get(HookType.LEAVE))
                {
                    Bukkit.getPluginManager()
                            .callEvent(new LeaveTriggerEvent(this, triggerFunction, playersLeft));
                }
            }

            for (TriggerFunction triggerFunction : functions.get(HookType.PASSIVE))
            {
                Bukkit.getPluginManager()
                        .callEvent(new PassiveTriggerEvent(this, triggerFunction, playersInside));
            }

            if (captureFunction != null)
            {
                captureFunction._execute(playersInside);
            }
        }
    }

    public void reset()
    {
        zoneRunnable.cancel();
        zoneRunnable = null;

        for (TriggerFunction function : functions.values().stream()
                .flatMap(triggerFunctions -> triggerFunctions.parallelStream())
                .collect(Collectors.toList()))
        {
            function.reset();
        }
    }


    public abstract Set<Player> getNearbyPlayers();

    protected class ZoneRunnable extends BukkitRunnable
    {
        @Override
        public void run()
        {
            tick();
        }
    }
}
