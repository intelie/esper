/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;

/**
 * Filter defines the event type to be filtered for, and an optional expression that returns true if
 * the filter should consider the event, or false to reject the event. 
 */
public class Filter implements Serializable
{
    private static final long serialVersionUID = 0L;

    private String eventTypeAlias;
    private Expression filter;

    /**
     * Creates a filter to the given named event type.
     * @param eventTypeAlias is the event type name to filter for
     * @return filter
     */
    public static Filter create(String eventTypeAlias)
    {
        return new Filter(eventTypeAlias);
    }

    /**
     * Creates a filter to the given named event type and filter expression.
     * @param eventTypeAlias is the event type name to filter for
     * @param filter is the expression filtering out events
     * @return filter is the filter expression
     */
    public static Filter create(String eventTypeAlias, Expression filter)
    {
        return new Filter(eventTypeAlias, filter);
    }

    /**
     * Ctor.
     * @param eventTypeAlias is the event type name
     */
    public Filter(String eventTypeAlias)
    {
        this.eventTypeAlias = eventTypeAlias;
    }

    /**
     * Ctor.
     * @param eventTypeAlias is the event type name
     * @param filter is the filter expression
     */
    public Filter(String eventTypeAlias, Expression filter)
    {
        this.eventTypeAlias = eventTypeAlias;
        this.filter = filter;
    }

    /**
     * Returns the name of the event type to filter for.
     * @return event type alias name
     */
    public String getEventTypeAlias()
    {
        return eventTypeAlias;
    }

    /**
     * Sets the name of the event type to filter for.
     * @param eventTypeAlias name or alias of the event type to filter for
     */
    public void setEventTypeAlias(String eventTypeAlias)
    {
        this.eventTypeAlias = eventTypeAlias;
    }

    /**
     * Returns the optional filter expression that tests the event, or null if no filter expression was defined.
     * @return filter expression
     */
    public Expression getFilter()
    {
        return filter;
    }

    /**
     * Sets the optional filter expression that tests the event, or null if no filter expression is needed.
     * @param filter is the filter expression to set
     */
    public void setFilter(Expression filter)
    {
        this.filter = filter;
    }

    /**
     * Returns a textual representation of the filter.
     * @param writer to output to
     */
    public void toEQL(StringWriter writer)
    {
        writer.write(eventTypeAlias);
        if (filter != null)
        {
            writer.write('(');
            filter.toEQL(writer);
            writer.write(')');
        }
    }
}
