package net.velion.kingdoms_arena.arena.condition.arena.win;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.condition.arena.ArenaCondition;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;

import java.util.Set;

public abstract class ArenaWinCondition extends ArenaCondition
{
    public ArenaWinCondition(Arena arena)
    {
        super(arena);
    }

    public abstract Set<ArenaEntity> check();
}
