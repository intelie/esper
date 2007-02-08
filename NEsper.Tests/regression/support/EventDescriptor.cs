using System;

using net.esper.compat;

namespace net.esper.regression.support
{
    public class EventDescriptor
    {
        private EDictionary<String, Object> eventProperties;

        public EventDescriptor()
        {
            eventProperties = new EHashDictionary<String, Object>();
        }

        public EDictionary<String, Object> EventProperties
        {
            get { return eventProperties; }
        }

        public virtual void Put(String propertyName, Object value)
        {
            eventProperties.Put(propertyName, value);
        }
    }
}
