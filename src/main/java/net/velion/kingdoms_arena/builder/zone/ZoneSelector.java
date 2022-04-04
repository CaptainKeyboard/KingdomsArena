package net.velion.kingdoms_arena.builder.zone;

import net.velion.kingdoms_arena.arena.zone.Zone;

import java.util.List;
import java.util.Set;

public class ZoneSelector
{
    protected String name;

    public ZoneSelector(String name)
    {
        this.name = name;
    }

    public Zone select(Set<Zone> zones) throws SelectorException
    {
        List<Zone> zoneList =
                zones.stream().filter(zone -> zone.getName().equals(name)).toList();

        if (zoneList.size() == 1)
        {
            return zoneList.get(0);
        } else if (zoneList.size() >= 2)
        {
            throw new SelectorException(
                    "TriggerZone cannot be identified. UUID was not unique for this TriggerZone. Name: [" + name + "]");
        } else
        {
            throw new SelectorException(
                    "TriggerZone cannot be identified. UUID does not match any TriggerZone. Name: [" + name + "]");
        }
    }
}
