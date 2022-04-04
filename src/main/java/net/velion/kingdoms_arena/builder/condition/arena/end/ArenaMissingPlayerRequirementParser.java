package net.velion.kingdoms_arena.builder.condition.arena.end;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.end.MissingPlayerRequirement;

public class ArenaMissingPlayerRequirementParser extends ArenaEndConditionParser
{
    @Override
    public MissingPlayerRequirement build(Arena arena)
    {
        MissingPlayerRequirement missingPlayerRequirement = new MissingPlayerRequirement(arena);

        return missingPlayerRequirement;
    }
}
