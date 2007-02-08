using System;
using System.Collections.Generic;
using System.IO;

using net.esper.compat;
using net.esper.collection;

namespace net.esper.events
{
	/// <summary>
    /// Method to getSelectListEvents events in collections to other collections or other event types.
    /// </summary>

    public class EventBeanUtility
    {
        /**
         * Flatten the vector of arrays to an array. Return null if an empty vector was passed, else
         * return an array containing all the events.
         * @param eventVector vector
         * @return array with all events
         */
        public static EventBean[] Flatten(IList<EventBean[]> eventVector)
        {
            if (eventVector.Count == 0)
            {
                return null;
            }

            if (eventVector.Count == 1)
            {
            	return eventVector[0];
            }

            int totalElements = 0;
            for (int i = 0; i < eventVector.Count; i++)
            {
                totalElements += eventVector[i].Length;
            }

            EventBean[] result = new EventBean[totalElements];
            int destPos = 0;
            for (int i = 0; i < eventVector.Count; i++)
            {
                EventBean[] src = eventVector[i];
                Array.Copy(src, 0, result, destPos, src.Length);
                destPos += eventVector[i].Length;
            }

            return result;
        }

        /**
        * Append arrays.
        * @param source array
        * @param append array
        * @return appended array
        */
        public static EventBean[] Append(EventBean[] source, EventBean[] append)
        {
            EventBean[] result = new EventBean[source.Length + append.Length];
            Array.Copy(source, 0, result, 0, source.Length);
            Array.Copy(append, 0, result, source.Length, append.Length);
            return result;
        }

        /**
         * Convert list of events to array, returning null for empty or null lists.
         * @param eventList
         * @return array of events
         */
        public static EventBean[] ToArray(IList<EventBean> eventList)
        {
            if ((eventList == null) || (eventList.Count == 0))
            {
                return null;
            }

            return CollectionHelper.ToArray(eventList);
        }

        /**
         * Returns object array containing property values of given properties, retrieved via EventPropertyGetter
         * instances.
         * @param event - event to get property values from
         * @param propertyGetters - getters to use for getting property values
         * @return object array with property values
         */
        public static Object[] GetPropertyArray(EventBean ev, EventPropertyGetter[] propertyGetters)
        {
            Object[] keyValues = new Object[propertyGetters.Length];
            for (int i = 0; i < propertyGetters.Length; i++)
            {
            	keyValues[i] = propertyGetters[i].GetValue(ev) ;
            }
            return keyValues;
        }

        /**
         * Returns Multikey instance for given event and getters.
         * @param event - event to get property values from
         * @param propertyGetters - getters for access to properties
         * @return MultiKey with property values
         */
        public static MultiKeyUntyped GetMultiKey(EventBean ev, EventPropertyGetter[] propertyGetters)
        {
            Object[] keyValues = GetPropertyArray(ev, propertyGetters);
            return new MultiKeyUntyped(keyValues);
        }

        /**
         * Format the event and return a string representation.
         * @param event is the event to format.
         * @return string representation of event
         */
        public static String PrintEvent(EventBean ev)
        {
            StringWriter writer = new StringWriter();
            PrintEvent(writer, ev);
            return writer.ToString();
        }

        public static void PrintEvent(TextWriter writer, EventBean ev)
        {
            IEnumerable<String> properties = ev.EventType.PropertyNames;
            IEnumerator<String> propEnum = properties.GetEnumerator();

            for (int i = 0; propEnum.MoveNext(); i++)
            {
                String property = propEnum.Current;
                writer.WriteLine("#" + i + "  " + property + " = " + ev[property]);
            }
        }
    }
}
