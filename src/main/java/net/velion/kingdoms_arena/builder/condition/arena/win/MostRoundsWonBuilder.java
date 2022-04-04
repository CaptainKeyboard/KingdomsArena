package net.velion.kingdoms_arena.builder.condition.arena.win;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.win.MostRoundsWon;

public class MostRoundsWonBuilder extends ArenaWinConditionBuilder
{
    @Override
    public MostRoundsWon build(Arena arena)
    {
        MostRoundsWon mostRoundsWon = new MostRoundsWon(arena);
        return mostRoundsWon;
    }
}
