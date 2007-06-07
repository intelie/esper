/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join.plan;

import net.esper.event.EventType;
import net.esper.eql.join.exec.TableLookupStrategy;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.PropertyIndexedEventTable;
import net.esper.eql.join.exec.IndexedTableLookupStrategy;

import java.util.Arrays;

/**
 * Plan to perform an indexed table lookup.
 */
public class IndexedTableLookupPlan extends TableLookupPlan
{
    private String[] keyProperties;

    /**
     * Ctor.
     * @param lookupStream - stream that generates event to look up for
     * @param indexedStream - stream to index table lookup
     * @param indexNum - index number for the table containing the full unindexed contents
     * @param keyProperties - properties to use in lookup event to access index
     */
    public IndexedTableLookupPlan(int lookupStream, int indexedStream, int indexNum, String[] keyProperties)
    {
        super(lookupStream, indexedStream, indexNum);
        this.keyProperties = keyProperties;
    }

    /**
     * Returns property names to use for lookup in index.
     * @return property names.
     */
    protected String[] getKeyProperties()
    {
        return keyProperties;
    }

    public TableLookupStrategy makeStrategy(EventTable[][] indexesPerStream, EventType[] eventTypes)
    {
        PropertyIndexedEventTable index = (PropertyIndexedEventTable) indexesPerStream[this.getIndexedStream()][this.getIndexNum()];
        return new IndexedTableLookupStrategy(eventTypes[this.getLookupStream()], keyProperties, index);
    }

    public String toString()
    {
        return "IndexedTableLookupPlan " +
                super.toString() +
               " keyProperties=" + Arrays.toString(keyProperties);
    }
}
