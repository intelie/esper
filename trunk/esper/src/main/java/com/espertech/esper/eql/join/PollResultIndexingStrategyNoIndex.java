package com.espertech.esper.eql.join;

import com.espertech.esper.eql.join.table.EventTable;
import com.espertech.esper.eql.join.table.UnindexedEventTableList;
import com.espertech.esper.event.EventBean;

import java.util.List;

/**
 * Strategy of indexing that simply builds an unindexed table of poll results.
 * <p>
 * For use when caching is disabled or when no proper index could be build because no where-clause or on-clause exists or
 * these clauses don't yield indexable columns on analysis.
 */
public class PollResultIndexingStrategyNoIndex implements PollResultIndexingStrategy
{
    public EventTable index(List<EventBean> pollResult, boolean isActiveCache)
    {
        return new UnindexedEventTableList(pollResult);
    }
}
