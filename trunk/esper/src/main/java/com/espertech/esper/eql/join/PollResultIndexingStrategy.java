package com.espertech.esper.eql.join;

import com.espertech.esper.eql.join.table.EventTable;
import com.espertech.esper.event.EventBean;

import java.util.List;

/**
 * A strategy for converting a poll-result into a potentially indexed table.
 * <p>
 * Some implementations may decide to not index the poll result and simply hold a reference to the result.
 * Other implementations may use predetermined index properties to index the poll result for faster lookup.
 */
public interface PollResultIndexingStrategy
{
    /**
     * Build and index of a poll result.
     * @param pollResult result of a poll operation
     * @param isActiveCache true to indicate that caching is active and therefore index building makes sense as
     *   the index structure is not a throw-away.
     * @return indexed collection of poll results
     */
    public EventTable index(List<EventBean> pollResult, boolean isActiveCache);
}
