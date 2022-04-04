package net.velion.kingdoms_arena.arena.condition.arena.start;

import jakarta.persistence.Transient;
import net.velion.kingdoms_arena.arena.Arena;
import org.bukkit.scheduler.BukkitRunnable;

import static net.velion.kingdoms_arena.Kingdoms_arena.KINGDOMS_ARENA;

public class TimeTillStart extends ArenaStartCondition
{
    private boolean canStart;
    private double timeInSeconds;

    @Transient
    private BukkitRunnable timer;

    public TimeTillStart(Arena arena, double timeInSeconds)
    {
        super(arena);
        this.timeInSeconds = timeInSeconds;
        canStart = false;
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
        return canStart;
    }

    @Override
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
                canStart = true;
                cancel();
            }
        }
    }
}
