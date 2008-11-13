/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.std;

import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.view.CloneableView;
import com.espertech.esper.view.View;
import com.espertech.esper.view.ViewSupport;
import com.espertech.esper.view.Viewable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This view retains the first event for each multi-key of distinct property values.
 * <p>
 * The view does not post a remove stream unless explicitly deleted from.
 * <p>
 * The view swallows any insert stream events that provide no new distinct set of property values.
 */
public final class FirstUniqueByPropertyView extends ViewSupport implements CloneableView
{
    private final String[] uniqueFieldNames;
    private final int numKeys;
    private EventPropertyGetter uniqueFieldGetter[];
    private final Map<MultiKey<Object>, EventBean> firstEvents = new LinkedHashMap<MultiKey<Object>, EventBean>();

    /**
     * Constructor.
     * @param uniqueFieldNames is the fields from which to pull the unique value
     */
    public FirstUniqueByPropertyView(String[] uniqueFieldNames)
    {
        this.uniqueFieldNames = uniqueFieldNames;
        numKeys = uniqueFieldNames.length;
    }

    public View cloneView(StatementContext statementContext)
    {
        return new FirstUniqueByPropertyView(uniqueFieldNames);
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
            dumpUpdateParams("FirstUniqueByPropertyView", newData, oldData);
        }

        EventBean[] newDataToPost = null;
        EventBean[] oldDataToPost = null;

        if (newData != null)
        {
            for (EventBean newEvent : newData)
            {
                // Obtain unique value
                MultiKey<Object> key = getUniqueKey(newEvent);

                // already-seen key
                if (firstEvents.containsKey(key))
                {
                    continue;
                }

                // store
                firstEvents.put(key, newEvent);

                // Post the new value
                if (newDataToPost == null)
                {
                    newDataToPost = new EventBean[]{newEvent};
                }
                else
                {
                    newDataToPost = EventBeanUtility.addToArray(newDataToPost, newEvent);
                }
            }
        }

        if (oldData != null)
        {
            for (EventBean oldEvent : oldData)
            {
                // Obtain unique value
                MultiKey<Object> key = getUniqueKey(oldEvent);

                // If the old event is the current unique event, remove and post as old data
                EventBean lastValue = firstEvents.get(key);

                if (lastValue != oldEvent)
                {
                    continue;
                }

                if (oldDataToPost == null)
                {
                    oldDataToPost = new EventBean[]{oldEvent};
                }
                else
                {
                    oldDataToPost = EventBeanUtility.addToArray(oldDataToPost, oldEvent);
                }

                firstEvents.remove(key);
            }
        }

        if ((this.hasViews()) && ((newDataToPost != null) || (oldDataToPost != null)))
        {
            updateChildren(newDataToPost, oldDataToPost);
        }
    }

    public final Iterator<EventBean> iterator()
    {
        return firstEvents.values().iterator();
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

    /**
     * Returns true if empty.
     * @return true if empty
     */
    public boolean isEmpty()
    {
        return firstEvents.isEmpty();
    }

    private static final Log log = LogFactory.getLog(FirstUniqueByPropertyView.class);
}
