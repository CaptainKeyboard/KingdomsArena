package net.velion.kingdoms_arena.arena.zone.triggerfunction;

import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.zone.Zone;

import java.util.Set;

public class CaptureEnterFunction extends TriggerFunction
{
    protected CaptureFunction captureFunction;

    public CaptureFunction getCaptureFunction()
    {
        return captureFunction;
    }

    public CaptureEnterFunction(Zone zone, CaptureFunction captureFunction)
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
            if (captureFunction.getCaptureObjectives().stream()
                    .anyMatch(captureObjective -> player.getOrder().hasObjective(captureObjective)))
            {
                captureFunction.getBossBar().addPlayer(player.getBukkitPlayer());
            }
        }
    }
}
