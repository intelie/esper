package com.espertech.esper.core;

import com.espertech.esper.eql.expression.ExprSubselectNode;
import com.espertech.esper.view.Viewable;
import com.espertech.esper.view.ViewFactoryChain;

import java.util.Map;
import java.util.HashMap;

/**
 * Holds stream information for subqueries.
 */
public class SubSelectStreamCollection
{
    private Map<ExprSubselectNode, SubSelectHolder> subqueries;

    /**
     * Ctor.
     */
    public SubSelectStreamCollection()
    {
        subqueries = new HashMap<ExprSubselectNode, SubSelectHolder>();
    }

    /**
     * Add lookup.
     * @param subselectNode is the subselect expression node
     * @param streamNumber is the lookup stream number
     * @param viewable is the lookup viewable
     * @param viewFactoryChain is the chain of view factories
     */
    public void add(ExprSubselectNode subselectNode, int streamNumber, Viewable viewable, ViewFactoryChain viewFactoryChain)
    {
        subqueries.put(subselectNode, new SubSelectHolder(streamNumber, viewable, viewFactoryChain));
    }

    /**
     * Returns stream number.
     * @param subqueryNode is the lookup node's stream number
     * @return number of stream
     */
    public int getStreamNumber(ExprSubselectNode subqueryNode)
    {
        return subqueries.get(subqueryNode).getStreamNumber();
    }

    /**
     * Returns the lookup viewable, child-most view.
     * @param subqueryNode is the expression node to get this for
     * @return child viewable
     */
    public Viewable getRootViewable(ExprSubselectNode subqueryNode)
    {
        return subqueries.get(subqueryNode).getViewable();
    }

    /**
     * Returns the lookup's view factory chain.
     * @param subqueryNode is the node to look for
     * @return view factory chain
     */
    public ViewFactoryChain getViewFactoryChain(ExprSubselectNode subqueryNode)
    {
        return subqueries.get(subqueryNode).getViewFactoryChain();
    }
}
