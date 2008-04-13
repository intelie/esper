/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.filter;

import com.espertech.esper.event.EventType;
import com.espertech.esper.pattern.MatchedEventMap;

import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Contains the filter criteria to sift through events. The filter criteria are the event class to look for and
 * a set of parameters (attribute names, operators and constant/range values).
 */
public final class FilterSpecCompiled
{
    private final EventType eventType;
    private final String eventTypeAlias;
    private final List<FilterSpecParam> parameters;

    /**
     * Constructor - validates parameter list against event type, throws exception if invalid
     * property names or mismatcing filter operators are found.
     * @param eventType is the event type
     * @param parameters is a list of filter parameters
     * @param eventTypeAlias is the alias name of the event type
     * @throws IllegalArgumentException if validation invalid
     */
    public FilterSpecCompiled(EventType eventType, String eventTypeAlias, List<FilterSpecParam> parameters)
    {
        this.eventType = eventType;
        this.eventTypeAlias = eventTypeAlias;
        this.parameters = parameters;
    }

    /**
     * Returns type of event to filter for.
     * @return event type
     */
    public final EventType getEventType()
    {
        return eventType;
    }

    /**
     * Returns list of filter parameters.
     * @return list of filter params
     */
    public final List<FilterSpecParam> getParameters()
    {
        return parameters;
    }

    /**
     * Returns the event type alias name.
     * @return event type alias
     */
    public String getEventTypeAlias()
    {
        return eventTypeAlias;
    }

    /**
     * Returns the values for the filter, using the supplied result events to ask filter parameters
     * for the value to filter for.
     * @param matchedEvents contains the result events to use for determining filter values
     * @return filter values
     */
    public FilterValueSet getValueSet(MatchedEventMap matchedEvents)
    {
        List<FilterValueSetParam> valueList = new LinkedList<FilterValueSetParam>();

        // Ask each filter specification parameter for the actual value to filter for
        for (FilterSpecParam specParam : parameters)
        {
            Object filterForValue = specParam.getFilterValue(matchedEvents);

            FilterValueSetParam valueParam = new FilterValueSetParamImpl(specParam.getPropertyName(),
                    specParam.getFilterOperator(), filterForValue);
            valueList.add(valueParam);
        }
        return new FilterValueSetImpl(eventType, valueList);
    }

    @SuppressWarnings({"StringConcatenationInsideStringBufferAppend"})
    public final String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("FilterSpecCompiled type=" + this.eventType);
        buffer.append(" parameters=" + Arrays.toString(parameters.toArray()));
        return buffer.toString();
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof FilterSpecCompiled))
        {
            return false;
        }

        FilterSpecCompiled other = (FilterSpecCompiled) obj;

        if (this.eventType != other.eventType)
        {
            return false;
        }
        if (this.parameters.size() != other.parameters.size())
        {
            return false;
        }

        Iterator<FilterSpecParam> iterOne = parameters.iterator();
        Iterator<FilterSpecParam> iterOther = other.parameters.iterator();
        while (iterOne.hasNext())
        {
            if (!iterOne.next().equals(iterOther.next()))
            {
                return false;
            }
        }

        return true;
    }

    public int hashCode()
    {
        int hashCode = eventType.hashCode();
        for (FilterSpecParam param : parameters)
        {
            hashCode = hashCode ^ param.getPropertyName().hashCode();
        }
        return hashCode;
    }
}
