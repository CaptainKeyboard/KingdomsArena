package net.velion.kingdoms_arena.builder.entity;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.entity.DummyPlayer;

public class DummyPlayerBuilder extends EntityBuilder
{
    @Override
    public ArenaEntity build(Arena arena)
    {
        return new DummyPlayer(arena);
    }
}
