package net.esper.eql.core;

import net.esper.collection.RefCountedSet;
import net.esper.eql.core.Aggregator;

/**
 * Aggregator for use on top of another aggregator that handles unique value aggregation (versus all-value aggregation)
 * for the underlying aggregator.
 */
public class UniqueValueAggregator implements Aggregator
{
    private final Aggregator inner;
    private final RefCountedSet<Object> valueSet;

    /**
     * Ctor.
     * @param inner is the aggregator function computing aggregation values 
     */
    public UniqueValueAggregator(Aggregator inner)
    {
        this.inner = inner;
        this.valueSet = new RefCountedSet<Object>();
    }

    public void enter(Object value)
    {
        // if value not already encountered, enter into aggregate
        if (valueSet.add(value))
        {
            inner.enter(value);
        }
    }

    public void leave(Object value)
    {
        // if last reference to the value is removed, remove from aggregate
        if (valueSet.remove(value))
        {
            inner.leave(value);
        }
    }

    public Object getValue()
    {
        return inner.getValue();
    }

    public Class getValueType()
    {
        return inner.getValueType();
    }

    public Aggregator newAggregator()
    {
        return new UniqueValueAggregator(inner.newAggregator());
    }
}
