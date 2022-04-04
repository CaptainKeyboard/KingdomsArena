package net.velion.kingdoms_arena.arena.zone.triggerfunction;

import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.zone.Zone;

import java.util.Set;

public class CaptureLeaveFunction extends TriggerFunction
{
    protected CaptureFunction captureFunction;

    public CaptureLeaveFunction(Zone zone, CaptureFunction captureFunction)
    {
        super(zone);
        this.captureFunction = captureFunction;
    }

    @Override
    public void enable()
    {

    }

    @Override
    public void disable()
    {

    }

    @Override
    public void reset()
    {

    }

    @Override
    protected void _execute(Set<Player> players)
    {
        for (Player player : players)
        {
            captureFunction.getBossBar().removePlayer(player.getBukkitPlayer());
        }
    }
}