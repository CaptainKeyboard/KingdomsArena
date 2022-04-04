package net.velion.kingdoms_arena.arena.condition.round.end;

import jakarta.persistence.Transient;
import net.velion.kingdoms_arena.Kingdoms_arena;
import net.velion.kingdoms_arena.arena.round.Round;
import org.bukkit.scheduler.BukkitRunnable;

public class RoundTimeOver extends RoundEndCondition
{
    protected boolean done;
    protected boolean forceEnd;
    protected double timeInSeconds;

    @Transient
    protected BukkitRunnable timer;

    public RoundTimeOver(Round round, double timeInSeconds)
    {
        super(round);
        this.timeInSeconds = timeInSeconds;
    }

    @Override
    public boolean check()
    {
        return false;
    }

    @Override
    public void setup()
    {
        timer = new TimeOverTask();
        timer.runTaskTimer(Kingdoms_arena.KINGDOMS_ARENA, 1000, 1000);
    }

    public void reset()
    {
        timer = null;
    }

    class TimeOverTask extends BukkitRunnable
    {
        private double currentTime;

        @Override
        public void run()
        {
            currentTime++;
            if (currentTime >= timeInSeconds)
            {
                done = true;
                if (forceEnd)
                {
                    round.end();
                }
                cancel();

            }
        }
    }
}
