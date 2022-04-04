package net.velion.kingdoms_arena.builder.condition.arena.start;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.start.TimeTillStart;

public class TimeTillStartBuilder extends ArenaStartConditionParser
{
    protected double timeInSeconds;

    public TimeTillStartBuilder(double timeInSeconds)
    {
        this.timeInSeconds = timeInSeconds;
    }

    @Override
    public TimeTillStart build(Arena arena)
    {
        TimeTillStart timeTillStart = new TimeTillStart(arena, timeInSeconds);
        return timeTillStart;
    }
}
