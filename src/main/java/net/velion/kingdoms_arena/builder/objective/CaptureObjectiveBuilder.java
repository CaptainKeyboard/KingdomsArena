package net.velion.kingdoms_arena.builder.objective;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.objective.CaptureObjective;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.CaptureFunction;
import net.velion.kingdoms_arena.builder.zone.SelectorException;
import net.velion.kingdoms_arena.builder.zone.ZoneSelector;

import java.util.Set;
import java.util.UUID;

public class CaptureObjectiveBuilder extends ObjectiveBuilder
{
    protected ZoneSelector zoneSelector;

    public CaptureObjectiveBuilder(UUID uuid, ZoneSelector zoneSelector)
    {
        super(uuid);
        this.zoneSelector = zoneSelector;
    }

    @Override
    public CaptureObjective build(ArenaEntity entity, Set<Zone> zones) throws SelectorException
    {
        Zone zone = zoneSelector.select(zones);

        CaptureFunction captureFunction = zone.getCaptureFunction();

        CaptureObjective captureObjective = new CaptureObjective(uuid, entity, captureFunction);

        return captureObjective;
    }
}
