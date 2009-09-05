package com.espertech.esper.support.util;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.client.EventBean;

import java.util.List;
import java.util.ArrayList;

public class OccuranceIntermediate
{
    private Long low;
    private Long high;
    private List<Pair<Long, EventBean[]>> items;

    public OccuranceIntermediate(Long low, Long high)
    {
        this.low = low;
        this.high = high;
        this.items = new ArrayList<Pair<Long, EventBean[]>>();
    }

    public List<Pair<Long, EventBean[]>> getItems()
    {
        return items;
    }

    public Long getLow()
    {
        return low;
    }

    public Long getHigh()
    {
        return high;
    }
}
