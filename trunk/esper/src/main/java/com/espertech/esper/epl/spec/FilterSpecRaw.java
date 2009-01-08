/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.util.MetaDefItem;

import java.util.List;

/**
 * Filter definition in an un-validated and un-resolved form.
 * <p>
 * Event type and expression nodes in this filter specification are not yet validated, optimized for resolved
 * against actual streams.
 */
public class FilterSpecRaw implements MetaDefItem
{
    private String eventTypeName;
    private List<ExprNode> filterExpressions;

    /**
     * Ctor.
     * @param eventTypeName is the name of the event type
     * @param filterExpressions is a list of expression nodes representing individual filter expressions
     */
    public FilterSpecRaw(String eventTypeName, List<ExprNode> filterExpressions)
    {
        this.eventTypeName = eventTypeName;
        this.filterExpressions = filterExpressions;
    }

    /**
     * Default ctor.
     */
    public FilterSpecRaw()
    {
    }

    /**
     * Returns the event type name of the events we are looking for.
     * @return event name
     */
    public String geteventTypeName()
    {
        return eventTypeName;
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
