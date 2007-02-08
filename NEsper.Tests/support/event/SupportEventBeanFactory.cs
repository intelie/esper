using System;

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;

namespace net.esper.support.events
{
    public class SupportEventBeanFactory
    {
        public static EventBean createObject(Object _event)
        {
            return SupportEventAdapterService.Service.AdapterForBean(_event);
        }

        public static EventBean CreateMapFromValues(EDataDictionary testValuesMap, EventType eventType)
        {
            return SupportEventAdapterService.Service.CreateMapFromValues(testValuesMap, eventType);
        }

        public static EventBean[] MakeEvents(String[] ids)
        {
            EventBean[] events = new EventBean[ids.Length];
            for (int i = 0; i < events.Length; i++)
            {
                SupportBean bean = new SupportBean();
                bean.StringValue = ids[i];
                events[i] = createObject(bean);
            }
            return events;
        }

        public static EventBean[] MakeEvents(bool[] boolPrimitiveValues)
        {
            EventBean[] events = new EventBean[boolPrimitiveValues.Length];
            for (int i = 0; i < events.Length; i++)
            {
                SupportBean bean = new SupportBean();
                bean.BoolPrimitive = boolPrimitiveValues[i];
                events[i] = createObject(bean);
            }
            return events;
        }

        public static EventBean[] makeMarketDataEvents(String[] ids)
        {
            EventBean[] events = new EventBean[ids.Length];
            for (int i = 0; i < events.Length; i++)
            {
                SupportMarketDataBean bean = new SupportMarketDataBean(ids[i], 0, 0L, null);
                events[i] = createObject(bean);
            }
            return events;
        }

        public static EventBean[] makeEvents_A(String[] ids)
        {
            EventBean[] events = new EventBean[ids.Length];
            for (int i = 0; i < events.Length; i++)
            {
                SupportBean_A bean = new SupportBean_A(ids[i]);
                events[i] = createObject(bean);
            }
            return events;
        }

        public static EventBean[] makeEvents_B(String[] ids)
        {
            EventBean[] events = new EventBean[ids.Length];
            for (int i = 0; i < events.Length; i++)
            {
                SupportBean_B bean = new SupportBean_B(ids[i]);
                events[i] = createObject(bean);
            }
            return events;
        }

        public static EventBean[] makeEvents_C(String[] ids)
        {
            EventBean[] events = new EventBean[ids.Length];
            for (int i = 0; i < events.Length; i++)
            {
                SupportBean_C bean = new SupportBean_C(ids[i]);
                events[i] = createObject(bean);
            }
            return events;
        }

        public static EventBean[] makeEvents_D(String[] ids)
        {
            EventBean[] events = new EventBean[ids.Length];
            for (int i = 0; i < events.Length; i++)
            {
                SupportBean_D bean = new SupportBean_D(ids[i]);
                events[i] = createObject(bean);
            }
            return events;
        }
    }
}
