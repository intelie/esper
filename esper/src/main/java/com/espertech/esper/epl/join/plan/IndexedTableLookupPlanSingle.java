/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.exec.IndexedTableLookupStrategy;
import com.espertech.esper.epl.join.exec.IndexedTableLookupStrategySingle;
import com.espertech.esper.epl.join.exec.JoinExecTableLookupStrategy;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTableSingle;

import java.util.Arrays;
import java.util.Map;

/**
 * Plan to perform an indexed table lookup.
 */
public class IndexedTableLookupPlanSingle extends TableLookupPlan
{
    private String keyProperty;

    /**
     * Ctor.
     * @param lookupStream - stream that generates event to look up for
     * @param indexedStream - stream to index table lookup
     * @param indexNum - index number for the table containing the full unindexed contents
     * @param keyProperty - properties to use in lookup event to access index
     */
    public IndexedTableLookupPlanSingle(int lookupStream, int indexedStream, String indexNum, String keyProperty)
    {
        super(lookupStream, indexedStream, indexNum);
        this.keyProperty = keyProperty;
    }

    public JoinExecTableLookupStrategy makeStrategy(Map<String,EventTable>[] indexesPerStream, EventType[] eventTypes)
    {
        PropertyIndexedEventTableSingle index = (PropertyIndexedEventTableSingle) indexesPerStream[this.getIndexedStream()].get(getIndexNum());
        return new IndexedTableLookupStrategySingle(eventTypes[this.getLookupStream()], keyProperty, index);
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public String toString()
    {
        return "IndexedTableLookupPlan " +
                super.toString() +
               " keyProperty=" + keyProperty;
    }
}
