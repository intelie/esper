package net.esper.eql.spec;

import net.esper.eql.expression.ExprNode;

import java.util.List;

public class FilterSpecRaw
{
    private String eventTypeAlias;
    private List<ExprNode> filterExpressions;

    public FilterSpecRaw(String eventTypeAlias, List<ExprNode> filterExpressions)
    {
        this.eventTypeAlias = eventTypeAlias;
        this.filterExpressions = filterExpressions;
    }

    public String getEventTypeAlias()
    {
        return eventTypeAlias;
    }

    public List<ExprNode> getFilterExpressions()
    {
        return filterExpressions;
    }
}
