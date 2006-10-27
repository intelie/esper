package net.esper.support.event;

import net.esper.event.EventBean;
import net.esper.support.bean.SupportBeanString;

import java.util.*;

import junit.framework.TestCase;

public class EventFactoryHelper
{
    public static EventBean makeEvent(String id)
    {
        SupportBeanString bean = new SupportBeanString(id);
        return SupportEventBeanFactory.createObject(bean);
    }

    public static EventBean[] makeEvents(String[] ids)
    {
        EventBean events[] = new EventBean[ids.length];
        for (int i = 0; i < ids.length; i++)
        {
            events[i] = makeEvent(ids[i]);
        }
        return events;
    }

    public static Map<String, EventBean> makeEventMap(String[] ids)
    {
        Map<String, EventBean> events = new HashMap<String, EventBean>();
        for (int i = 0; i < ids.length; i++)
        {
            String id = ids[i];
            EventBean eventBean = makeEvent(id);
            events.put(id, eventBean);
        }
        return events;
    }

    public static List<EventBean> makeEventList(String[] ids)
    {
        EventBean events[] = makeEvents(ids);
        return Arrays.asList(events);
    }

    public static EventBean[] makeArray(Map<String, EventBean> events, String[] ids)
    {
        EventBean[] eventArr = new EventBean[ids.length];
        for (int i = 0; i < eventArr.length; i++)
        {
            eventArr[i] = events.get(ids[i]);
            if (eventArr[i] == null)
            {
                TestCase.fail();
            }
        }
        return eventArr;
    }

    public static List<EventBean> makeList(Map<String, EventBean> events, String[] ids)
    {
        List<EventBean> eventList = new LinkedList<EventBean>();
        for (int i = 0; i < ids.length; i++)
        {
            EventBean bean = events.get(ids[i]);
            if (bean == null)
            {
                TestCase.fail();
            }
            eventList.add(bean);
        }
        return eventList;
    }
}
