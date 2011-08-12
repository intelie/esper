/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.filter;

import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Event property value in a list of values following an in-keyword.
 */
public class InSetOfValuesEventPropIndexed implements FilterSpecParamInValue
{
    private static final Log log = LogFactory.getLog(InSetOfValuesEventPropIndexed.class);
    private final String resultEventAsName;
    private final int resultEventIndex;
    private final String resultEventProperty;
    private final boolean isMustCoerce;
    private final Class coercionType;
    private final String statementName;
    private static final long serialVersionUID = -4424097388643812241L;

    /**
     * Ctor.
     * @param resultEventAsName is the event tag
     * @param resultEventProperty is the event property name
     * @param isMustCoerce indicates on whether numeric coercion must be performed
     * @param coercionType indicates the numeric coercion type to use
     * @param resultEventindex index
     */
    public InSetOfValuesEventPropIndexed(String resultEventAsName, int resultEventindex, String resultEventProperty, boolean isMustCoerce, Class coercionType, String statementName)
    {
        this.resultEventAsName = resultEventAsName;
        this.resultEventProperty = resultEventProperty;
        this.resultEventIndex = resultEventindex;
        this.coercionType = coercionType;
        this.isMustCoerce = isMustCoerce;
        this.statementName = statementName;
    }

    public final Object getFilterValue(MatchedEventMap matchedEvents)
    {
        EventBean[] events = (EventBean[]) matchedEvents.getMatchingEventAsObject(resultEventAsName);

        Object value = null;
        if (events == null)
        {
            log.warn("Matching events for tag '" + resultEventAsName + "' returned a null result, using null value in filter criteria, for statement '" + statementName + "'");
        }
        else if (resultEventIndex > (events.length - 1))
        {
            log.warn("Matching events for tag '" + resultEventAsName + "' returned no result for index " + resultEventIndex + " at array length " + events.length + ", using null value in filter criteria, for statement '" + statementName + "'");
        }
        else
        {
            value = events[resultEventIndex].get(resultEventProperty);
        }

        // Coerce if necessary
        if (isMustCoerce)
        {
            value = JavaClassHelper.coerceBoxed((Number) value, coercionType);
        }
        return value;
    }

    /**
     * Returns the tag used for the event property.
     * @return tag
     */
    public String getResultEventAsName()
    {
        return resultEventAsName;
    }

    /**
     * Returns the event property name.
     * @return property name
     */
    public String getResultEventProperty()
    {
        return resultEventProperty;
    }

    public final String toString()
    {
        return "resultEventProp=" + resultEventAsName + '.' + resultEventProperty;
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof InSetOfValuesEventPropIndexed))
        {
            return false;
        }

        InSetOfValuesEventPropIndexed other = (InSetOfValuesEventPropIndexed) obj;
        if ( (other.resultEventAsName.equals(this.resultEventAsName)) &&
             (other.resultEventProperty.equals(this.resultEventProperty)))
        {
            return true;
        }

        return false;
    }

    public int hashCode()
    {
        return resultEventProperty.hashCode();
    }
}
