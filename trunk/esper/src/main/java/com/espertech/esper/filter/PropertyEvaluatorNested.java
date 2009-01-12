package com.espertech.esper.filter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.FragmentEventType;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esper.collection.ArrayDequeJDK6Backport;
import com.espertech.esper.epl.expression.ExprNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class PropertyEvaluatorNested implements PropertyEvaluator
{
    private final EventPropertyGetter[] getter;
    private final FragmentEventType[] fragmentEventType;
    private final boolean isArrayResult;
    private final int lastLevel;

    public PropertyEvaluatorNested(EventPropertyGetter[] getter, FragmentEventType[] fragmentEventType, ExprNode[] whereClauses)
    {
        this.fragmentEventType = fragmentEventType;
        this.getter = getter;
        isArrayResult = fragmentEventType[fragmentEventType.length - 1].isIndexed();
        lastLevel = fragmentEventType.length - 1;
    }

    public EventBean[] getProperty(EventBean event)
    {
        if (isArrayResult)
        {
            ArrayDequeJDK6Backport<EventBean[]> resultEvents = new ArrayDequeJDK6Backport<EventBean[]>();
            populateEventsArray(event, 0, resultEvents);
            if (resultEvents.isEmpty())
            {
                return null;
            }
            return EventBeanUtility.flatten(resultEvents);
        }
        else
        {
            ArrayDequeJDK6Backport<EventBean> resultEvents = new ArrayDequeJDK6Backport<EventBean>();
            populateEventsNonArray(event, 0, resultEvents);
            if (resultEvents.isEmpty())
            {
                return null;
            }
            return resultEvents.toArray(new EventBean[resultEvents.size()]);
        }
    }

    private void populateEventsArray(EventBean leaf, int level, Collection<EventBean[]> events)
    {
        try
        {
            Object result = getter[level].getFragment(leaf);

            if (fragmentEventType[level].isIndexed())
            {
                EventBean[] fragments = (EventBean[]) result;
                if (level == lastLevel)
                {
                    events.add(fragments);
                }
                else
                {
                    for (EventBean next : fragments)
                    {
                        populateEventsArray(next, level+1, events);
                    }                    
                }
            }
            else
            {
                EventBean fragment = (EventBean) result;
                if (level == lastLevel)
                {
                    events.add(new EventBean[] {fragment });
                }
                else
                {
                    populateEventsArray(fragment, level+1, events);
                }
            }
        }
        catch (RuntimeException ex)
        {
            ex.printStackTrace();
            // TODO, also test reuse of properties
        }
    }

    private void populateEventsNonArray(EventBean leaf, int level, Collection<EventBean> events)
    {
        try
        {
            Object result = getter[level].getFragment(leaf);

            if (fragmentEventType[level].isIndexed())
            {
                EventBean[] fragments = (EventBean[]) result;
                if (level == lastLevel)
                {
                    for (EventBean event : fragments)
                    {
                        events.add(event);
                    }
                }
                else
                {
                    for (EventBean next : fragments)
                    {
                        populateEventsNonArray(next, level+1, events);
                    }
                }
            }
            else
            {
                EventBean fragment = (EventBean) result;
                if (level == lastLevel)
                {
                    events.add(fragment);
                }
                else
                {
                    populateEventsNonArray(fragment, level+1, events);
                }
            }
        }
        catch (RuntimeException ex)
        {
            ex.printStackTrace();
            // TODO, also test reuse of properties
        }
    }

    public FragmentEventType getFragmentEventType()
    {
        return fragmentEventType[lastLevel];
    }
}