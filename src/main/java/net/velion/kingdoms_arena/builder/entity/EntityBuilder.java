package net.velion.kingdoms_arena.builder.entity;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.ArenaColor;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;

import java.util.UUID;

public abstract class EntityBuilder
{
    protected UUID uuid;
    protected String name;
    protected ArenaColor arenaColor = null;

    public abstract ArenaEntity build(Arena arena);
}
