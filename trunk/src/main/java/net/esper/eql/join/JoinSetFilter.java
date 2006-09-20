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
            filter(filterExprNode, newEvents);
            filter(filterExprNode, oldEvents);
        }
    }

    /**
     * Filter event by applying the filter nodes evaluation method.
     * @param filterExprNode - top node of the filter expression tree.
     * @param events - set of tuples of events
     */
    protected static void filter(ExprNode filterExprNode, Set<MultiKey<EventBean>> events)
    {
        for (Iterator<MultiKey<EventBean>> it = events.iterator(); it.hasNext();)
        {
            MultiKey<EventBean> key = it.next();
            EventBean[] eventArr = key.getArray();

            boolean matched = (Boolean) filterExprNode.evaluate(eventArr);

            if (!matched)
            {
                it.remove();
            }
        }
    }
}
