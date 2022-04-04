package net.velion.kingdoms_arena.builder.condition.arena.end;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.end.ArenaTimeOver;

public class ArenaTimeOverParser extends ArenaEndConditionParser
{
    protected boolean forceEnd = false;
    protected double timeInSeconds;

    public ArenaTimeOverParser(double timeInSeconds, boolean forceEnd)
    {
        this.timeInSeconds = timeInSeconds;
        this.forceEnd = forceEnd;
    }

    @Override
    public ArenaTimeOver build(Arena arena)
    {
        ArenaTimeOver arenaTimeOver = new ArenaTimeOver(arena, timeInSeconds, forceEnd);

        return arenaTimeOver;
    }

    public void setForceEnd(boolean forceEnd)
    {
        this.forceEnd = forceEnd;
    }
}
