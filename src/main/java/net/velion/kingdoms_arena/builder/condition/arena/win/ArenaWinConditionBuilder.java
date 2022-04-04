package net.velion.kingdoms_arena.builder.condition.arena.win;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.win.ArenaWinCondition;
import net.velion.kingdoms_arena.builder.condition.arena.ArenaConditionBuilder;

public abstract class ArenaWinConditionBuilder extends ArenaConditionBuilder
{
    public abstract ArenaWinCondition build(Arena arena);
}
