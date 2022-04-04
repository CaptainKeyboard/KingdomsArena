package net.velion.kingdoms_arena.builder.round;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.builder.zone.SelectorException;
import net.velion.kingdoms_arena.builder.zone.ZoneSelector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RoundSpawnPointsSelectorBuilder
{
    protected Set<ZoneSelector> zoneSelectors;

    public Set<Zone> build(Set<Zone> zones) throws SelectorException
    {
        Set<Zone> spawnPoints = new HashSet<>();
        for (ZoneSelector zoneSelector : zoneSelectors)
        {
            spawnPoints.add(zoneSelector.select(zones));
        }
        return spawnPoints;
    }

    public RoundSpawnPointsSelectorBuilder setTriggerZoneSelectors(
            ZoneSelector... zoneSelectors)
    {
        this.zoneSelectors = new NoNullHashSet(Arrays.asList(zoneSelectors));
        return this;
    }
}
