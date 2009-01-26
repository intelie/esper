/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.std;

import com.espertech.esper.collection.OneEventCollection;
import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.view.CloneableView;
import com.espertech.esper.view.View;
import com.espertech.esper.view.ViewSupport;
import com.espertech.esper.view.Viewable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;

/**
 * This view includes only the most recent among events having the same value for the specified field or fields.
 * The view accepts the field name as parameter from which the unique values are obtained.
 * For example, a trade's symbol could be used as a unique value.
 * In this example, the first trade for symbol IBM would be posted as new data to child views.
 * When the second trade for symbol IBM arrives the second trade is posted as new data to child views,
 * and the first trade is posted as old data.
 * Should more than one trades for symbol IBM arrive at the same time (like when batched)
 * then the child view will get all new events in newData and all new events in oldData minus the most recent event.
 * When the current new event arrives as old data, the the current unique event gets thrown away and
 * posted as old data to child views.
 * Iteration through the views data shows only the most recent events received for the unique value in the order
 * that events arrived in.
 * The type of the field returning the unique value can be any type but should override equals and hashCode()
 * as the type plays the role of a key in a map storing unique values.
 */
public final class UniqueByPropertyView extends ViewSupport implements CloneableView
{
    private final String[] uniqueFieldNames;
    private final int numKeys;
    private EventPropertyGetter uniqueFieldGetter[];
    private final Map<MultiKey<Object>, EventBean> mostRecentEvents = new LinkedHashMap<MultiKey<Object>, EventBean>();

    /**
     * Constructor.
     * @param uniqueFieldNames is the fields from which to pull the unique value
     */
    public UniqueByPropertyView(String[] uniqueFieldNames)
    {
        this.uniqueFieldNames = uniqueFieldNames;
        numKeys = uniqueFieldNames.length;
    }

    public View cloneView(StatementContext statementContext)
    {
        return new UniqueByPropertyView(uniqueFieldNames);
    }

    public void setParent(Viewable parent)
    {
        super.setParent(parent);
        if (parent != null)
        {
            uniqueFieldGetter = new EventPropertyGetter[uniqueFieldNames.length];
            for (int i = 0; i < uniqueFieldNames.length; i++)
            {
                uniqueFieldGetter[i] = parent.getEventType().getGetter(uniqueFieldNames[i]);
            }
        }
    }

    /**
     * Returns the name of the field supplying the unique value to keep the most recent record for.
     * @return field name for unique value
     */
    public final String[] getUniqueFieldNames()
    {
        return uniqueFieldNames;
    }

    public final EventType getEventType()
    {
        // The schema is the parent view's schema
        return parent.getEventType();
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".update Updating view");
            dumpUpdateParams("UniqueByPropertyView", newData, oldData);
        }

        OneEventCollection postOldData = null;

        if (this.hasViews())
        {
            postOldData = new OneEventCollection();
        }

        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                // Obtain unique value
                MultiKey<Object> key = getUniqueKey(newData[i]);

                // If there are no child views, just update the own collection
                if (!this.hasViews())
                {
                    mostRecentEvents.put(key, newData[i]);
                    continue;
                }

                // Post the last value as old data
                EventBean lastValue = mostRecentEvents.get(key);
                if (lastValue != null)
                {
                    postOldData.add(lastValue);
                }

                // Override with recent event
                mostRecentEvents.put(key, newData[i]);
            }
        }

        if (oldData != null)
        {
            for (int i = 0; i < oldData.length; i++)
            {
                // Obtain unique value
                MultiKey<Object> key = getUniqueKey(oldData[i]);

                // If the old event is the current unique event, remove and post as old data
                EventBean lastValue = mostRecentEvents.get(key);
                if (lastValue != oldData[i])
                {
                    continue;
                }

                postOldData.add(lastValue);
                mostRecentEvents.remove(key);
            }
        }


        // If there are child views, fireStatementStopped update method
        if (this.hasViews())
        {
            if (postOldData.isEmpty())
            {
                updateChildren(newData, null);
            }
            else
            {
                updateChildren(newData, postOldData.toArray());
            }
        }
    }

    /**
     * Returns true if the view is empty.
     * @return true if empty
     */
    public boolean isEmpty()
    {
        return mostRecentEvents.isEmpty();
    }

    public final Iterator<EventBean> iterator()
    {
        return mostRecentEvents.values().iterator();
    }

    public final String toString()
    {
        return this.getClass().getName() + " uniqueFieldNames=" + Arrays.toString(uniqueFieldNames);
    }

    private MultiKey<Object> getUniqueKey(EventBean event)
    {
        Object[] values = new Object[numKeys];
        for (int i = 0; i < numKeys; i++)
        {
            values[i] = uniqueFieldGetter[i].get(event);
        }
        return new MultiKey<Object>(values);
    }

    private static final Log log = LogFactory.getLog(UniqueByPropertyView.class);
}
