package net.velion.kingdoms_arena.builder.condition.arena.win;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.win.LastRoundWon;

public class LastRoundWonBuilder extends ArenaWinConditionBuilder
{
    @Override
    public LastRoundWon build(Arena arena)
    {
        LastRoundWon lastRoundWon = new LastRoundWon(arena);

        return lastRoundWon;
    }
}
