package net.esper.core;

import net.esper.eql.expression.ExprSubselectNode;
import net.esper.view.Viewable;
import net.esper.view.ViewFactoryChain;

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
     * Add subquery.
     * @param subselectNode is the subselect expression node
     * @param streamNumber is the subquery stream number
     * @param viewable is the subquery viewable
     * @param viewFactoryChain is the chain of view factories
     */
    public void add(ExprSubselectNode subselectNode, int streamNumber, Viewable viewable, ViewFactoryChain viewFactoryChain)
    {
        subqueries.put(subselectNode, new SubSelectHolder(streamNumber, viewable, viewFactoryChain));
    }

    /**
     * Returns stream number.
     * @param subqueryNode is the subquery node's stream number
     * @return number of stream
     */
    public int getStreamNumber(ExprSubselectNode subqueryNode)
    {
        return subqueries.get(subqueryNode).getStreamNumber();
    }

    /**
     * Returns the subquery viewable, child-most view.
     * @param subqueryNode is the expression node to get this for
     * @return child viewable
     */
    public Viewable getRootViewable(ExprSubselectNode subqueryNode)
    {
        return subqueries.get(subqueryNode).getViewable();
    }

    /**
     * Returns the subquery's view factory chain.
     * @param subqueryNode is the node to look for
     * @return view factory chain
     */
    public ViewFactoryChain getViewFactoryChain(ExprSubselectNode subqueryNode)
    {
        return subqueries.get(subqueryNode).getViewFactoryChain();
    }
}
