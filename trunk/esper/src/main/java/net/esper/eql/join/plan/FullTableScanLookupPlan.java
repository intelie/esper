package net.esper.eql.join.plan;

import net.esper.eql.join.exec.FullTableScanLookupStrategy;
import net.esper.eql.join.exec.TableLookupStrategy;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.event.EventType;

/**
 * Plan for a full table scan.
 */
public class FullTableScanLookupPlan extends TableLookupPlan
{
    /**
     * Ctor.
     * @param lookupStream - stream that generates event to look up for
     * @param indexedStream - stream to full table scan
     * @param indexNum - index number for the table containing the full unindexed contents
     */
    public FullTableScanLookupPlan(int lookupStream, int indexedStream, int indexNum)
    {
        super(lookupStream, indexedStream, indexNum);
    }

    public TableLookupStrategy makeStrategy(EventTable[][] indexesPerStream, EventType[] eventTypes)
    {
        UnindexedEventTable index = (UnindexedEventTable) indexesPerStream[this.getIndexedStream()][this.getIndexNum()];
        return new FullTableScanLookupStrategy(index);
    }

    public String toString()
    {
        return "FullTableScanLookupPlan " +
                super.toString();
    }

}
