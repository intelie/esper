/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

import java.util.ArrayList;
import java.util.List;
import java.io.StringWriter;

/**
 * A stream upon which projections (views) can be added that selects events by name and filter expression.
 */
public class FilterStream extends ProjectedStream
{
    private Filter filter;

    /**
     * Creates a stream using a filter that provides the event type name and filter expression to filter for.
     * @param filter defines what to look for
     * @return stream
     */
    public static FilterStream create(Filter filter)
    {
        return new FilterStream(filter);
    }

    /**
     * Creates a stream of events of the given name.
     * @param eventTypeAlias is the event type name or alias to filter for
     * @return stream
     */
    public static FilterStream create(String eventTypeAlias)
    {
        return new FilterStream(Filter.create(eventTypeAlias));
    }

    /**
     * Creates a stream of events of the given event type name and names that stream. Example: "select * from MyEventTypeAlias as StreamName".
     * @param eventTypeAlias is the event type name or alias to filter for
     * @param streamName is an optional stream name
     * @return stream
     */
    public static FilterStream create(String eventTypeAlias, String streamName)
    {
        return new FilterStream(Filter.create(eventTypeAlias), streamName);
    }

    /**
     * Creates a stream using a filter that provides the event type name and filter expression to filter for.
     * @param filter defines what to look for
     * @param streamName is an optional stream name
     * @return stream
     */
    public static FilterStream create(Filter filter, String streamName)
    {
        return new FilterStream(filter, streamName);
    }

    /**
     * Creates a stream of events of the given event type name and names that stream. Example: "select * from MyEventTypeAlias as StreamName".
     * @param eventTypeAlias is the event type name or alias to filter for
     * @param filter is the filter expression removing events from the stream
     * @return stream
     */
    public static FilterStream create(String eventTypeAlias, Expression filter)
    {
        return new FilterStream(Filter.create(eventTypeAlias, filter));
    }

    /**
     * Ctor.
     * @param filter specifies what events to look for
     */
    public FilterStream(Filter filter)
    {
        super(new ArrayList<View>(), null);
        this.filter = filter;
    }

    /**
     * Ctor.
     * @param filter specifies what events to look for
     * @param name is the as-name for the stream
     */
    public FilterStream(Filter filter, String name)
    {
        super(new ArrayList<View>(), name);
        this.filter = filter;
    }

    /**
     * Ctor.
     * @param filter specifies what events to look for
     * @param name is the as-name for the stream
     * @param views is a list of projections onto the stream
     */
    public FilterStream(Filter filter, String name, List<View> views)
    {
        super(views, name);
        this.filter = filter;
    }

    /**
     * Returns the filter.
     * @return filter
     */
    public Filter getFilter()
    {
        return filter;
    }

    /**
     * Sets the filter to use.
     * @param filter to set
     */
    public void setFilter(Filter filter)
    {
        this.filter = filter;
    }

    public void toEQLProjectedStream(StringWriter writer)
    {
        filter.toEQL(writer);
    }
}
