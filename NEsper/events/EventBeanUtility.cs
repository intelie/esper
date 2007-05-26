using System;
using System.Collections.Generic;
using System.IO;

using net.esper.compat;
using net.esper.collection;

namespace net.esper.events
{
	/// <summary>
    /// Method to GetSelectListEvents events in collections to other collections or other event types.
    /// </summary>

    public class EventBeanUtility
    {
        /// <summary>Flatten the vector of arrays to an array. Return null if an empty vector was passed, elsereturn an array containing all the events.</summary>
        /// <param name="eventVector">vector</param>
        /// <returns>array with all events</returns>

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
			foreach( EventBean[] array in eventVector )
            {
                totalElements += array.Length;
            }

            EventBean[] result = new EventBean[totalElements];
            int destPos = 0;
			foreach( EventBean[] array in eventVector )
            {
                Array.Copy(src, 0, result, destPos, array.Length);
                destPos += eventVector[i].Length;
            }

            return result;
        }

        /// <summary>Append arrays.</summary>
        /// <param name="source">list of source events</param>
        /// <param name="append">list of events to append</param>
        /// <returns>appended array</returns>

        public static EventBean[] Append(EventBean[] source, EventBean[] append)
        {
            EventBean[] result = new EventBean[source.Length + append.Length];
            Array.Copy(source, 0, result, 0, source.Length);
            Array.Copy(append, 0, result, source.Length, append.Length);
            return result;
        }

        /// <summary>Convert list of events to array, returning null for empty or null lists.</summary>
        /// <param name="eventList">a list of events to convert</param>
        /// <returns>array of events</returns>

        public static EventBean[] ToArray(IList<EventBean> eventList)
        {
            if ((eventList == null) || (eventList.Count == 0))
            {
                return null;
            }

            return CollectionHelper.ToArray(eventList);
        }

        /// <summary>
        /// Returns object array containing property values of given properties, retrieved via EventPropertyGetterinstances.
        /// </summary>
        /// <param name="ev">event to get property values from</param>
        /// <param name="propertyGetters">getters to use for getting property values</param>
        /// <returns>object array with property values</returns>

        public static Object[] GetPropertyArray(EventBean ev, EventPropertyGetter[] propertyGetters)
        {
            Object[] keyValues = new Object[propertyGetters.Length];
            for (int i = 0; i < propertyGetters.Length; i++)
            {
            	keyValues[i] = propertyGetters[i].GetValue(ev) ;
            }
            return keyValues;
        }

        /// <summary>
        /// Returns Multikey instance for given event and getters.
        /// </summary>
        /// <param name="ev">event to get property values from</param>
        /// <param name="propertyGetters">getters for access to properties</param>
        /// <returns>MultiKey with property values</returns>

        public static MultiKeyUntyped GetMultiKey(EventBean ev, EventPropertyGetter[] propertyGetters)
        {
            Object[] keyValues = GetPropertyArray(ev, propertyGetters);
            return new MultiKeyUntyped(keyValues);
        }

        /// <summary>
        /// Format the event and return a string representation.
        /// </summary>
        /// <param name="ev">is the event to format.</param>
        /// <returns>string representation of event</returns>

        public static String PrintEvent(EventBean ev)
        {
            StringWriter writer = new StringWriter();
            PrintEvent(writer, ev);
            return writer.ToString();
        }

        /// <summary>
        /// Prints the event.
        /// </summary>
        /// <param name="writer">The writer.</param>
        /// <param name="ev">The ev.</param>
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
