package net.velion.kingdoms_arena.builder.entity;


import net.velion.kingdoms_arena.arena.Arena;
import net.velion.kingdoms_arena.arena.entity.ArenaColor;
import net.velion.kingdoms_arena.arena.entity.Team;

import java.util.UUID;

public class TeamBuilder extends EntityBuilder
{
    protected UUID uuid;
    protected String name;

    public TeamBuilder(UUID uuid, String name, ArenaColor arenaColor)
    {
        this.uuid = uuid;
        this.name = name;
        this.arenaColor = arenaColor;
    }

    @Override
    public Team build(Arena arena)
    {
        Team team = new Team(uuid, name, arena, arenaColor);
        return team;
    }
}
