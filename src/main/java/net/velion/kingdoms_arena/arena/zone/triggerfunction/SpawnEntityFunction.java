package net.velion.kingdoms_arena.arena.zone.triggerfunction;

import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.entity.Team;
import net.velion.kingdoms_arena.arena.zone.ArenaLocation;
import net.velion.kingdoms_arena.arena.zone.Zone;
import org.bukkit.entity.EntityType;

import java.util.Set;

public class SpawnEntityFunction extends TriggerFunction
{
    protected ArenaLocation arenaLocation;
    protected EntityType entityType;
    protected int amount;

    public SpawnEntityFunction(Zone zone, ArenaLocation arenaLocation, EntityType entityType,
                               int amount)
    {
        super(zone);
        this.arenaLocation = arenaLocation;
        this.entityType = entityType;
        this.amount = amount;
    }

    public ArenaLocation getArenaLocation()
    {
        return arenaLocation;
    }

    @Override
    public void enable()
    {

    }

    @Override
    public void reset()
    {
        super.reset();
    }

    @Override
    protected void _execute(Set<Player> players)
    {
        switch (executeOnceFor)
        {
            case ALL:
                if (!executed)
                {
                    executed = true;
                    __execute();
                }
                break;
            case TEAM:
                for (Player player : players)
                {
                    Team team = player.getTeam();
                    if (team != null && !isRegistered(team))
                    {
                        attachEntity(team);
                        __execute();
                    }
                }
                break;
            case PLAYER:
                for (Player player : players)
                {
                    if (!isRegistered(player))
                    {
                        attachEntity(player);
                        __execute();
                    }
                }
                break;
            default:
                for (Player player : players)
                {
                    __execute();
                }
        }
    }

    protected void __execute()
    {
        for (int i = 0; i < amount; i++)
        {
            arenaLocation.getBukkitLocation().getWorld().spawnEntity(arenaLocation.getBukkitLocation(), entityType);
        }
    }
}
