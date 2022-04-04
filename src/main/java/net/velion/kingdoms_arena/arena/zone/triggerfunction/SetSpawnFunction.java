package net.velion.kingdoms_arena.arena.zone.triggerfunction;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.entity.Team;
import net.velion.kingdoms_arena.arena.zone.Zone;

import java.util.Collections;
import java.util.Set;

public class SetSpawnFunction extends TriggerFunction
{
    public SetSpawnFunction(Zone zone)
    {
        super(zone);
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
                // TODO For all players? For each team? WHAT?
                break;
            case TEAM:
                for (Player player : players)
                {
                    Team team = player.getTeam();
                    if (team != null && !isRegistered(team))
                    {
                        attachEntity(team);
                        __execute(team);
                    }
                }
                break;
            case PLAYER:
                for (Player player : players)
                {
                    if (!isRegistered(player))
                    {
                        attachEntity(player);
                        __execute(player);
                    }
                }
                break;
            default:
                for (Player player : players)
                {
                    __execute(player);
                }
        }
    }

    protected void __execute(ArenaEntity arenaEntity)
    {
        arenaEntity.setSpawnPoints(Collections.singleton(zone));
        arenaEntity.sendMessage("Spawn points set to: " + zone.getName());
    }
}
