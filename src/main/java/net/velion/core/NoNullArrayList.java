package net.velion.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class NoNullArrayList<E> extends ArrayList<E>
{
    public NoNullArrayList(Set<E> players)
    {
        super(players);
    }

    public NoNullArrayList()
    {
        super();
    }

    @Override
    public boolean add(E e)
    {
        if (e != null)
        {
            return super.add(e);
        } else
        {
            throw new IllegalArgumentException("No null object allowed");
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c)
    {
        if (c.contains(null))
        {
            throw new IllegalArgumentException("No null objects allowed");
        } else
        {
            return super.addAll(c);
        }
    }
}
