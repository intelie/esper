/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import com.espertech.esper.event.EventType;
import com.espertech.esper.epl.join.exec.ExecNode;
import com.espertech.esper.epl.join.exec.TableLookupExecNode;
import com.espertech.esper.epl.join.exec.TableLookupStrategy;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.HistoricalStreamIndexList;
import com.espertech.esper.util.IndentWriter;
import com.espertech.esper.view.Viewable;

/**
 * Specifies exection of a table lookup using the supplied plan for performing the lookup.
 */
public class TableLookupNode extends QueryPlanNode
{
    private TableLookupPlan tableLookupPlan;

    /**
     * Ctor.
     * @param tableLookupPlan - plan for performing lookup
     */
    public TableLookupNode(TableLookupPlan tableLookupPlan)
    {
        this.tableLookupPlan = tableLookupPlan;
    }

    /**
     * Returns lookup plan.
     * @return lookup plan
     */
    protected TableLookupPlan getLookupStrategySpec()
    {
        return tableLookupPlan;
    }

    public void print(IndentWriter writer)
    {
        writer.println("TableLookupNode " +
               " tableLookupPlan=" + tableLookupPlan);
    }

    public ExecNode makeExec(EventTable[][] indexesPerStream, EventType[] streamTypes, Viewable[] streamViews, HistoricalStreamIndexList[] historicalStreamIndexLists)
    {
        TableLookupStrategy lookupStrategy = tableLookupPlan.makeStrategy(indexesPerStream, streamTypes);

        return new TableLookupExecNode(tableLookupPlan.getIndexedStream(), lookupStrategy);
    }
}
