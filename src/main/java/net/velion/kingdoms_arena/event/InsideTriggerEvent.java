package net.velion.kingdoms_arena.event;

import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.TriggerFunction;

import java.util.Set;

public class InsideTriggerEvent extends TriggerEvent
{
    public InsideTriggerEvent(Zone zone, TriggerFunction triggerFunction, Set<Player> players)
    {
        super(zone, triggerFunction, players);
    }
}
