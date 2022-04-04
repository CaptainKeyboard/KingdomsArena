package net.velion.kingdoms_arena.arena.zone;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.Player;

import java.util.Collection;
import java.util.Set;

public class TriggerSphere extends Zone
{
    protected double radius;

    public TriggerSphere(String name, Arena arena, ArenaLocation arenaLocation, double radius)
    {
        super(name, arena, arenaLocation);
        this.arenaLocation = arenaLocation;
        this.radius = radius;
    }

    @Override
    public Set<Player> getNearbyPlayers()
    {
        Set<Player> entities = new NoNullHashSet<>();

        Collection<org.bukkit.entity.Player> playerCollection =
                arenaLocation.getBukkitLocation().getNearbyPlayers(radius);

        if (playerCollection != null && !playerCollection.isEmpty())
        {
            for (org.bukkit.entity.Player player : playerCollection)
            {
                if (arena.isArenaPlayer(player))
                {
                    entities.add(arena.getArenaPlayer(player));
                }
            }
        }
        return entities;
    }
}
