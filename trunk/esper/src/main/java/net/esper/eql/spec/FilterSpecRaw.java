/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.spec;

import net.esper.eql.expression.ExprNode;
import net.esper.util.MetaDefItem;

import java.util.List;

/**
 * Filter definition in an un-validated and un-resolved form.
 * <p>
 * Event type and expression nodes in this filter specification are not yet validated, optimized for resolved
 * against actual streams.
 */
public class FilterSpecRaw implements MetaDefItem
{
    private String eventTypeAlias;
    private List<ExprNode> filterExpressions;

    /**
     * Ctor.
     * @param eventTypeAlias is the name of the event type
     * @param filterExpressions is a list of expression nodes representing individual filter expressions
     */
    public FilterSpecRaw(String eventTypeAlias, List<ExprNode> filterExpressions)
    {
        this.eventTypeAlias = eventTypeAlias;
        this.filterExpressions = filterExpressions;
    }

    /**
     * Default ctor.
     */
    public FilterSpecRaw()
    {
    }

    /**
     * Returns the event type alias of the events we are looking for.
     * @return event name
     */
    public String getEventTypeAlias()
    {
        return eventTypeAlias;
    }

    /**
     * Returns the list of filter expressions.
     * @return filter expression list
     */
    public List<ExprNode> getFilterExpressions()
    {
        return filterExpressions;
    }
}
