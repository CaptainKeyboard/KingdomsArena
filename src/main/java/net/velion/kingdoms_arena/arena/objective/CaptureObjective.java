package net.velion.kingdoms_arena.arena.objective;

import net.velion.kingdoms_arena.arena.ArenaInvalidException;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.Player;
import net.velion.kingdoms_arena.arena.entity.Team;
import net.velion.kingdoms_arena.arena.zone.triggerfunction.CaptureFunction;

import java.util.UUID;

public class CaptureObjective extends Objective
{
    protected CaptureFunction captureFunction;

    public CaptureObjective(UUID uuid, ArenaEntity operator, CaptureFunction captureFunction)
    {
        super(uuid, operator);
        this.captureFunction = captureFunction;
    }

    public CaptureFunction getCaptureFunction()
    {
        return captureFunction;
    }

    public void setCaptureFunction(CaptureFunction captureFunction)
    {
        this.captureFunction = captureFunction;
    }

    @Override
    public void enable()
    {
        captureFunction.registerObjective(this);
    }

    @Override
    public void disable()
    {
        captureFunction.unregisterObjective(this);
    }

    @Override
    public void validate() throws ArenaInvalidException
    {
        super.validate();
        if (captureFunction == null)
        {
            throw new ArenaInvalidException("CaptureFunction is null");
        }
    }

    @Override
    public boolean check()
    {
        return captureFunction.getOwner() != null ? captureFunction.getOwner().equals(operator) : false;
    }

    public void updateScoreboard()
    {
        if (operator instanceof Team)
        {
            Team team = (Team) operator;
            team.getScoreEntry().updateScoreboard(true);
        } else
        {
            Player player = (Player) operator;
            player.getScoreEntry().updateScoreboard(true);
        }

    }

    @Override
    public String toString()
    {
        return "Capture [" + captureFunction.getTriggerZone().getName() + "]: " + (check() ? "1" : "0") + " / 1";
    }
}
