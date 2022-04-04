package net.velion.kingdoms_arena.builder.stage;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.order.Order;
import net.velion.kingdoms_arena.arena.stage.Stage;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.builder.entity.IEntitySelector;
import net.velion.kingdoms_arena.builder.order.OrderBuilder;
import net.velion.kingdoms_arena.builder.zone.SelectorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class StageBuilder
{
    protected Map<IEntitySelector, OrderBuilder> orderParsers;
    protected UUID uuid;

    public StageBuilder(UUID uuid)
    {
        orderParsers = new HashMap<>();
        this.uuid = uuid;
    }

    public Stage build(Set<ArenaEntity> entities, Set<Zone> zones) throws SelectorException
    {
        Stage stage = new Stage(uuid);
        for (Map.Entry<IEntitySelector, OrderBuilder> orderEntry : orderParsers.entrySet())
        {
            ArenaEntity entity = orderEntry.getKey().select(entities);
            Order order = orderEntry.getValue().build(entity, zones);
            stage.putOrder(entity, order);
        }
        return stage;
    }

    public StageBuilder putOrder(IEntitySelector entitySelector, OrderBuilder orderBuilder)
    {
        this.orderParsers.put(entitySelector, orderBuilder);
        return this;
    }

    public StageBuilder setOrderBuilders(Map<IEntitySelector, OrderBuilder> orderBuilderMap)
    {
        this.orderParsers = orderBuilderMap;
        return this;
    }
}
