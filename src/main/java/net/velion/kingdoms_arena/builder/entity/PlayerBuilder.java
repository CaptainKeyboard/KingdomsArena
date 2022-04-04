package net.velion.kingdoms_arena.builder.entity;

import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.ArenaColor;
import net.velion.kingdoms_arena.arena.entity.Player;

import java.util.UUID;

@Deprecated
public class PlayerBuilder extends EntityBuilder
{
    public PlayerBuilder(UUID uuid, String name, ArenaColor arenaColor)
    {
        this.uuid = uuid;
        this.name = name;
        this.arenaColor = arenaColor;
    }

    @Override
    public Player build(Arena arena)
    {
        Player player = new Player(uuid, name, arena, arenaColor);
        return player;
    }
}
