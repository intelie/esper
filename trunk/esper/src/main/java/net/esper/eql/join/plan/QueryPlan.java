/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join.plan;

/**
 * Contains the query plan for all streams.
 */
public class QueryPlan
{
    private QueryPlanIndex[] indexSpecs;
    private QueryPlanNode[] execNodeSpecs;

    /**
     * Ctor.
     * @param indexSpecs - specs for indexes to create
     * @param execNodeSpecs - specs for execution nodes to create
     */
    public QueryPlan(QueryPlanIndex[] indexSpecs, QueryPlanNode[] execNodeSpecs)
    {
        this.indexSpecs = indexSpecs;
        this.execNodeSpecs = execNodeSpecs;
    }

    /**
     * Return index specs.
     * @return index specs
     */
    public QueryPlanIndex[] getIndexSpecs()
    {
        return indexSpecs;
    }

    /**
     * Return execution node specs.
     * @return execution node specs
     */
    public QueryPlanNode[] getExecNodeSpecs()
    {
        return execNodeSpecs;
    }

    public String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("QueryPlanNode\n");
        buffer.append(QueryPlanIndex.print(indexSpecs));
        buffer.append(QueryPlanNode.print(execNodeSpecs));
        return buffer.toString();
    }
}