package net.velion.kingdoms_arena.arena.condition.arena.end;

import jakarta.persistence.Transient;
import net.velion.kingdoms_arena.arena.Arena;
import org.bukkit.scheduler.BukkitRunnable;

import static net.velion.kingdoms_arena.Kingdoms_arena.KINGDOMS_ARENA;

public class ArenaTimeOver extends ArenaEndCondition
{
    protected boolean done;
    protected boolean forceEnd;
    protected double timeInSeconds;

    @Transient
    protected BukkitRunnable timer;


    public ArenaTimeOver(Arena arena, double timeInSeconds, boolean forceEnd)
    {
        super(arena);
        this.timeInSeconds = timeInSeconds;
        this.forceEnd = forceEnd;
        done = false;
    }

    @Override
    public void setup()
    {
        timer = new TimeOverTask();
        timer.runTaskTimer(KINGDOMS_ARENA, 20, 20);
    }

    @Override
    public boolean check()
    {
        return done;
    }

    @Override
    public void reset()
    {
        if (!timer.isCancelled())
        {
            timer.cancel();
        }
        timer = null;
        done = false;
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
                cancel();
                if (forceEnd)
                {
                    arena.end();
                }
            }
        }
    }
}
