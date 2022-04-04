package net.velion.kingdoms_arena.builder.condition.arena.end;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.end.ArenaEndCondition;
import net.velion.kingdoms_arena.builder.condition.arena.ArenaConditionBuilder;

public abstract class ArenaEndConditionParser extends ArenaConditionBuilder
{
    public abstract ArenaEndCondition build(Arena arena);
}
