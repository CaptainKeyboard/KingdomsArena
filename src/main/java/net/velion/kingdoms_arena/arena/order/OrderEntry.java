package net.velion.kingdoms_arena.arena.order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;

import java.util.UUID;

@Entity
public class OrderEntry
{
    @Id
    protected UUID uuid;

    @ManyToOne(cascade = CascadeType.ALL)
    protected ArenaEntity arenaEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    protected Order order;

    public OrderEntry(UUID uuid, ArenaEntity arenaEntity, Order order)
    {
        this.uuid = uuid;
        this.arenaEntity = arenaEntity;
        this.order = order;
    }

    public OrderEntry(UUID uuid, ArenaEntity arenaEntity)
    {
        this.uuid = uuid;
        this.arenaEntity = arenaEntity;
    }

    public OrderEntry(UUID uuid)
    {
        this.uuid = uuid;
    }

    protected OrderEntry()
    {

    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public ArenaEntity getArenaEntity()
    {
        return arenaEntity;
    }

    public void setArenaEntity(ArenaEntity arenaEntity)
    {
        this.arenaEntity = arenaEntity;
    }
}
