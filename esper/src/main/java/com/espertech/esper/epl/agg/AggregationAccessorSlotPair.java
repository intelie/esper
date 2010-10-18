package com.espertech.esper.epl.agg;

public class AggregationAccessorSlotPair
{
    private final int slot;
    private final AggregationAccessor accessor;

    public AggregationAccessorSlotPair(int slot, AggregationAccessor accessor)
    {
        this.slot = slot;
        this.accessor = accessor;
    }

    public int getSlot()
    {
        return slot;
    }

    public AggregationAccessor getAccessor()
    {
        return accessor;
    }
}
