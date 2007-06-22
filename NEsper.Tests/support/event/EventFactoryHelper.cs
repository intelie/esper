using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.support.events
{
    public class EventFactoryHelper
    {
        public static EventBean MakeEvent(String id)
        {
            SupportBeanString bean = new SupportBeanString(id);
            return SupportEventBeanFactory.CreateObject(bean);
        }

        public static EventBean[] MakeEvents(String[] ids)
        {
            EventBean[] events = new EventBean[ids.Length];
            for (int i = 0; i < ids.Length; i++)
            {
                events[i] = MakeEvent(ids[i]);
            }
            return events;
        }

        public static EDictionary<String, EventBean> MakeEventMap(String[] ids)
        {
            EDictionary<String, EventBean> events = new HashDictionary<String, EventBean>();
            for (int i = 0; i < ids.Length; i++)
            {
                String id = ids[i];
                EventBean _eventBean = MakeEvent(id);
                events.Put(id, _eventBean);
            }
            return events;
        }

        public static IList<EventBean> MakeEventList(String[] ids)
        {
            EventBean[] events = MakeEvents(ids);
            return events;
        }

        public static EventBean[] MakeArray(EDictionary<String, EventBean> events, String[] ids)
        {
            EventBean[] eventArr = new EventBean[ids.Length];
            for (int i = 0; i < eventArr.Length; i++)
            {
                eventArr[i] = events.Fetch(ids[i]);
                if (eventArr[i] == null)
                {
                    Assert.Fail();
                }
            }
            return eventArr;
        }

        public static IList<EventBean> makeList(EDictionary<String, EventBean> events, String[] ids)
        {
            IList<EventBean> eventList = new List<EventBean>();
            for (int i = 0; i < ids.Length; i++)
            {
                EventBean bean = events.Fetch(ids[i]);
                if (bean == null)
                {
                    Assert.Fail();
                }
                eventList.Add(bean);
            }
            return eventList;
        }
    }
}
