package net.velion.core;

import java.util.Collection;
import java.util.HashSet;

public class NoNullHashSet<E> extends HashSet<E>
{
    public NoNullHashSet()
    {
        super();
    }

    public NoNullHashSet(Collection<? extends E> c)
    {
        super(c);
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
