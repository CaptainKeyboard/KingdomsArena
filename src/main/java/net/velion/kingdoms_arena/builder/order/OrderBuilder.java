package net.velion.kingdoms_arena.builder.order;

import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.objective.Objective;
import net.velion.kingdoms_arena.arena.order.Order;
import net.velion.kingdoms_arena.arena.zone.Zone;
import net.velion.kingdoms_arena.builder.objective.ObjectiveBuilder;
import net.velion.kingdoms_arena.builder.zone.SelectorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class OrderBuilder
{
    protected UUID uuid;
    protected List<ObjectiveBuilder> objectiveBuilders;

    public OrderBuilder(UUID uuid)
    {
        this.uuid = uuid;
        objectiveBuilders = new ArrayList<>();
    }

    public Order build(ArenaEntity entity, Set<Zone> zones) throws SelectorException
    {
        Order order = new Order(uuid);
        for (ObjectiveBuilder objectiveBuilder : objectiveBuilders)
        {
            Objective objective = objectiveBuilder.build(entity, zones);
            order.addObjective(objective);
        }

        return order;
    }

    public OrderBuilder setObjectives(ObjectiveBuilder... objectives)
    {
        objectiveBuilders = List.of(objectives);
        return this;
    }
}
