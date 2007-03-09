package net.esper.filter;

import net.esper.eql.expression.ExprNode;
import net.esper.pattern.MatchedEventMap;
import net.esper.event.EventType;
import net.esper.event.EventBean;

import java.util.LinkedHashMap;

/**
 * This class represents an arbitrary expression node returning a boolean value as a filter parameter in an {@link FilterSpecCompiled} filter specification.
 */
public final class FilterSpecParamExprNode extends FilterSpecParam
{
    private final ExprNode exprNode;
    private final ExprNodeAdapter adapter;
    private final LinkedHashMap<String, EventType> taggedEventTypes;

    /**
     * Ctor.
     * @param propertyName is the event property name
     * @param filterOperator is expected to be the IN-list operator
     * @throws IllegalArgumentException for illegal args
     */
    public FilterSpecParamExprNode(String propertyName,
                             FilterOperator filterOperator,
                             ExprNode exprNode,
                             LinkedHashMap<String, EventType> taggedEventTypes)
        throws IllegalArgumentException
    {
        super(propertyName, filterOperator);
        if (filterOperator != FilterOperator.BOOLEAN_EXPRESSION)
        {
            throw new IllegalArgumentException("Invalid filter operator for filter expression node");
        }
        this.exprNode = exprNode;
        this.taggedEventTypes = taggedEventTypes;

        adapter = new ExprNodeAdapter(exprNode);
    }

    public ExprNode getExprNode()
    {
        return exprNode;
    }

    public LinkedHashMap<String, EventType> getTaggedEventTypes()
    {
        return taggedEventTypes;
    }

    public final Object getFilterValue(MatchedEventMap matchedEvents)
    {
        if (taggedEventTypes != null)
        {
            EventBean[] events = new EventBean[taggedEventTypes.size() + 1];
            int count = 1;
            for (String tag : taggedEventTypes.keySet())
            {
                events[count] = matchedEvents.getMatchingEvent(tag);
                count++;
            }
            adapter.setPrototype(events);
        }
        return adapter;
    }

    public final String toString()
    {
        return super.toString() + "  exprNode=" + exprNode.toString();
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof FilterSpecParamExprNode))
        {
            return false;
        }

        FilterSpecParamExprNode other = (FilterSpecParamExprNode) obj;
        if (!super.equals(other))
        {
            return false;
        }

        if (exprNode != other.exprNode)
        {
            return false;
        }

        return true;
    }
}
