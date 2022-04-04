package net.velion.kingdoms_arena.arena.order;

import net.velion.core.UniqueObjectivesSet;
import net.velion.kingdoms_arena.arena.ArenaInvalidException;
import net.velion.kingdoms_arena.arena.entity.ArenaEntity;
import net.velion.kingdoms_arena.arena.objective.Objective;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Order
{
    protected UUID uuid;
    protected ArenaEntity entity;
    protected Set<Objective> objectives = new UniqueObjectivesSet();

    public Order(UUID uuid)
    {
        this.uuid = uuid;
    }

    public void addObjective(Objective objective)
    {
        objectives.add(objective);
    }

    public boolean check()
    {
        return objectives.stream().allMatch(Objective::check);
    }

    public Set<Objective> getObjectives()
    {
        return objectives;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public boolean hasObjective(Objective objective)
    {
        return objectives.contains(objective);
    }

    @Override
    public String toString()
    {
        return "Order: " +
                objectives.stream().map(objective -> objective.toString()).collect(Collectors.toList()).toString();
    }

    public void validate() throws ArenaInvalidException
    {
        if (objectives == null || objectives.isEmpty())
        {
            throw new ArenaInvalidException("Objectives either null or empty");
        } else
        {
            for (Objective objective : objectives)
            {
                objective.validate();
            }
        }
    }

    public void enable()
    {
        for (Objective objective : objectives)
        {
            objective.enable();
        }
    }

    public void disable()
    {
        for (Objective objective : objectives)
        {
            objective.disable();
        }
    }
}
