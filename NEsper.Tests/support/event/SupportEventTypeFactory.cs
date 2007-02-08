using System;

using net.esper.compat;
using net.esper.events;

namespace net.esper.support.events
{

    public class SupportEventTypeFactory
    {
        public static EventType createBeanType(Type clazz)
        {
            return SupportEventAdapterService.Service.AddBeanType(clazz.FullName, clazz);
        }

        public static EventType createMapType(EDictionary<String, Type> map)
        {
            return SupportEventAdapterService.Service.CreateAnonymousMapType(map);
        }
    }
}
