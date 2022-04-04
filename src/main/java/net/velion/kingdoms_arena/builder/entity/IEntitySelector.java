package net.velion.kingdoms_arena.builder.entity;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.builder.zone.SelectorException;

import java.util.Set;

public interface IEntitySelector
{
    public ArenaEntity select(Set<ArenaEntity> entities) throws SelectorException;
}
