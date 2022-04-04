package net.velion.kingdoms_arena.arena.stage;

import net.velion.core.NoNullHashSet;
import net.velion.kingdoms_arena.arena.ArenaInvalidException;
import net.velion.kingdoms_arena.arena.condition.stage.end.StageEndCondition;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.order.Order;
import net.velion.kingdoms_arena.arena.round.Round;
import net.velion.kingdoms_arena.arena.zone.Zone;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Stage
{
    protected UUID uuid;
    protected Map<ArenaEntity, Order> orderMap = new HashMap<>();
    protected Round round;
    protected boolean over;
    protected Map<ArenaEntity, Set<Zone>> spawnPointMap = new HashMap<>();
    protected Set<StageEndCondition> endConditions = new NoNullHashSet<>();

    public Stage(UUID uuid)
    {
        this.uuid = uuid;
        over = false;
    }

    public void addEndCondition(StageEndCondition stageEndCondition)
    {
        endConditions.add(stageEndCondition);
    }

    public Set<StageEndCondition> getEndConditions()
    {
        return endConditions;
    }

    public Round getRound()
    {
        return round;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setRound(Round round)
    {
        this.round = round;
    }

    public void setSpawnPoints(Map<ArenaEntity, Set<Zone>> spawnPointMap)
    {
        this.spawnPointMap.clear();
        this.spawnPointMap.putAll(spawnPointMap);
    }

    public Map<ArenaEntity, Order> getOrderMap()
    {
        return orderMap;
    }

    public void setOrders(
            Map<ArenaEntity, Order> orderMap)
    {
        this.orderMap = orderMap;
    }

    public void putOrder(ArenaEntity arenaEntity, Order order)
    {
        orderMap.put(arenaEntity, order);
    }

    public void reset()
    {
        for (Map.Entry<ArenaEntity, Order> orderEntry : orderMap.entrySet())
        {
            orderEntry.getValue().disable();
        }

        over = false;
    }

    public boolean isOver()
    {
        return over;
    }

    public void enable()
    {
        for (Map.Entry<ArenaEntity, Order> orderEntry : orderMap.entrySet())
        {
            ArenaEntity arenaEntity = orderEntry.getKey();
            Order order = orderEntry.getValue();

            order.enable();

            arenaEntity.setOrder(order);
        }
    }

    public boolean check()
    {
        if (orderMap.values().stream().anyMatch(order -> order.check()))
        {
            over = true;
        }
        return over;
    }

    public Set<ArenaEntity> getFinisher()
    {
        Set<ArenaEntity> finisher = new NoNullHashSet<>();

        for (Map.Entry<ArenaEntity, Order> orderEntry : orderMap.entrySet())
        {
            ArenaEntity arenaEntity = orderEntry.getKey();
            Order order = orderEntry.getValue();

            if (order.check())
            {
                finisher.add(arenaEntity);
            }
        }
        return finisher;
    }

    public void validate() throws ArenaInvalidException
    {
        if (orderMap == null || orderMap.isEmpty())
        {
            throw new ArenaInvalidException("OrderMap is either null or empty");
        } else
        {
            for (Order order : orderMap.values())
            {
                order.validate();
            }
        }
    }
}
