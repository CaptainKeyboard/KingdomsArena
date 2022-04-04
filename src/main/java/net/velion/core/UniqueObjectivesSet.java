package net.velion.core;

import net.velion.kingdoms_arena.arena.objective.NonUniqueObjective;
import net.velion.kingdoms_arena.arena.objective.Objective;

import java.util.Collection;

public class UniqueObjectivesSet extends NoNullHashSet<Objective>
{
    @Override
    public boolean add(Objective objective)
    {
        if (!(objective instanceof NonUniqueObjective && containsType(objective.getClass())))
        {
            return super.add(objective);
        } else
        {
            throw new IllegalArgumentException("Objective of type " + objective.getClass() +
                    " already exists. There can't be duplicates of non-unique objectives.");
        }
    }

    @Override
    public boolean addAll(Collection<? extends Objective> objectives)
    {
        forEach(objective -> add(objective));
        return true;
    }

    public boolean containsType(Class clazz)
    {
        return this.stream().anyMatch(objective -> objective.getClass().equals(clazz));
    }
}
