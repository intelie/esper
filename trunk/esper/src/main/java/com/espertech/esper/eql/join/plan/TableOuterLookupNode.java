/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.eql.join.plan;

import com.espertech.esper.event.EventType;
import com.espertech.esper.eql.join.exec.ExecNode;
import com.espertech.esper.eql.join.exec.TableLookupStrategy;
import com.espertech.esper.eql.join.exec.TableOuterLookupExecNode;
import com.espertech.esper.eql.join.table.EventTable;
import com.espertech.esper.util.IndentWriter;

/**
 * Specifies exection of a table lookup with outer join using the a specified lookup plan.
 */
public class TableOuterLookupNode extends QueryPlanNode
{
    private TableLookupPlan tableLookupPlan;

    /**
     * Ctor.
     * @param tableLookupPlan - plan for performing lookup
     */
    public TableOuterLookupNode(TableLookupPlan tableLookupPlan)
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
        writer.println("TableOuterLookupNode " +
               " tableLookupPlan=" + tableLookupPlan);
    }

    public ExecNode makeExec(EventTable[][] indexesPerStream, EventType[] streamTypes)
    {
        TableLookupStrategy lookupStrategy = tableLookupPlan.makeStrategy(indexesPerStream, streamTypes);

        return new TableOuterLookupExecNode(tableLookupPlan.getIndexedStream(), lookupStrategy);
    }
}
