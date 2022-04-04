package net.velion.kingdoms_arena.builder.entity;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.builder.zone.SelectorException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class EntitySelector implements IEntitySelector
{
    protected UUID uuid;

    public EntitySelector(UUID uuid)
    {
        this.uuid = uuid;
    }

    public ArenaEntity select(Set<ArenaEntity> entities) throws SelectorException
    {
        List<ArenaEntity> entityList =
                entities.stream().filter(entity -> entity.getUuid().equals(uuid)).toList();

        if (entityList.size() == 1)
        {
            return entityList.get(0);
        } else if (entityList.size() >= 2)
        {
            throw new SelectorException(
                    "Entity cannot be identified. UUID was not unique for this Entity.");
        } else
        {
            throw new SelectorException(
                    "Entity cannot be identified. UUID does not match any Entity. UUID: [" + uuid + "]");
        }
    }
}
