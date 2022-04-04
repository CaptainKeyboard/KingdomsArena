package net.velion.kingdoms_arena.arena.entity;

import net.velion.kingdoms_arena.arena.Arena;

import java.util.UUID;

public class DummyPlayer extends Player
{
    public DummyPlayer(Arena arena)
    {
        super(UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"), "DummyPlayer", arena, ArenaColor.DEFAULT());
    }

    public DummyPlayer(UUID uuid, String name, Arena arena)
    {
        super(uuid, name, arena, null);
    }
}
