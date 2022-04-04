package net.velion.kingdoms_arena.builder.condition.arena.end;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.end.LastRoundOver;

public class LastRoundOverParser extends ArenaEndConditionParser
{
    @Override
    public LastRoundOver build(Arena arena)
    {
        LastRoundOver lastRoundOver = new LastRoundOver(arena);
        return lastRoundOver;
    }
}
