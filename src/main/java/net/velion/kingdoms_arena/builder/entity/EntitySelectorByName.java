package net.velion.kingdoms_arena.builder.entity;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.builder.zone.SelectorException;

import java.util.List;
import java.util.Set;

public class EntitySelectorByName implements IEntitySelector
{
    protected String name;

    public EntitySelectorByName(String name)
    {
        this.name = name;
    }

    public ArenaEntity select(Set<ArenaEntity> entities) throws SelectorException
    {
        List<ArenaEntity> entityList =
                entities.stream().filter(entity -> entity.getName().equals(name)).toList();


        if (entityList.size() == 1)
        {
            return entityList.get(0);
        } else if (entityList.size() >= 2)
        {
            throw new SelectorException(
                    "Entity cannot be identified. Name was not unique for this Entity. Name: [" + name + "]");
        } else
        {
            throw new SelectorException(
                    "Entity cannot be identified. Name does not match any Entity. Name: [" + name + "]");
        }
    }
}
