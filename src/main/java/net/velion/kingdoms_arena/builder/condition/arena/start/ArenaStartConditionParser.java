package net.velion.kingdoms_arena.builder.condition.arena.start;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.start.ArenaStartCondition;
import net.velion.kingdoms_arena.builder.condition.arena.ArenaConditionBuilder;

public abstract class ArenaStartConditionParser extends ArenaConditionBuilder
{
    public abstract ArenaStartCondition build(Arena arena);
}
