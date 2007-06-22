using System;

using net.esper.compat;
using net.esper.events;

namespace net.esper.support.events
{

    public class SupportEventTypeFactory
    {
        public static EventType CreateBeanType(Type clazz)
        {
            return SupportEventAdapterService.Service.AddBeanType(clazz.FullName, clazz);
        }

        public static EventType CreateMapType(EDictionary<String, Type> map)
        {
            return SupportEventAdapterService.Service.CreateAnonymousMapType(map);
        }
    }
}
