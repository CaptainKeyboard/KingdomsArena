package net.velion.kingdoms_arena.builder.condition.arena.start;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.start.MinimalPlayerRequirement;

public class MinimalPlayerRequirementParser extends ArenaStartConditionParser
{
    @Override
    public MinimalPlayerRequirement build(Arena arena)
    {
        MinimalPlayerRequirement minimalPlayerRequirement = new MinimalPlayerRequirement(arena);
        return minimalPlayerRequirement;
    }
}
