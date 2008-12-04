package com.espertech.esper.support.epl.join;

import com.espertech.esper.epl.join.exec.TableLookupStrategy;
import com.espertech.esper.epl.join.rep.Cursor;
import com.espertech.esper.client.EventBean;

import java.util.Set;

public class SupportTableLookupStrategy implements TableLookupStrategy
{
    private final int numResults;

    public SupportTableLookupStrategy(int numResults)
    {
        this.numResults = numResults;
    }

    public Set<EventBean> lookup(EventBean event, Cursor cursor)
    {
        return SupportJoinResultNodeFactory.makeEventSet(numResults);
    }
}
