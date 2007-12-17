package net.esper.eql.join;

import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.UnindexedEventTableList;
import net.esper.event.EventBean;

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
