/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.eql.expression.ExprNode;

import java.util.Set;
import java.util.Iterator;

/**
 * Processes join tuple set by filtering out tuples.
 */
public class JoinSetFilter implements JoinSetProcessor
{
    private ExprNode filterExprNode;

    /**
     * Ctor.
     * @param filterExprNode - filter tree
     */
    public JoinSetFilter(ExprNode filterExprNode)
    {
        this.filterExprNode = filterExprNode;
    }

    public void process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
    {
        // Filter
        if (filterExprNode != null)
        {
            filter(filterExprNode, newEvents, true);
            filter(filterExprNode, oldEvents, false);
        }
    }

    /**
     * Filter event by applying the filter nodes evaluation method.
     * @param filterExprNode - top node of the filter expression tree.
     * @param events - set of tuples of events
     * @param isNewData - true to indicate filter new data (istream) and not old data (rstream)
     */
    protected static void filter(ExprNode filterExprNode, Set<MultiKey<EventBean>> events, boolean isNewData)
    {
        for (Iterator<MultiKey<EventBean>> it = events.iterator(); it.hasNext();)
        {
            MultiKey<EventBean> key = it.next();
            EventBean[] eventArr = key.getArray();

            Boolean matched = (Boolean) filterExprNode.evaluate(eventArr, isNewData);
            if ((matched == null) || (!matched))
            {
                it.remove();
            }
        }
    }
}
