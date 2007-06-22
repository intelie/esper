using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.regression.support
{
    /// <summary>
    /// Contains a set of events to send to the runtime for testing along with a time for each event.
    /// Each event has a string event id that can be obtained via the getParentEvent method.
    /// </summary>

    public class EventCollection : IEnumerable<KeyValuePair<String, Object>>
    {
        public const String ON_START__event_ID = "ON_START_ID";

        // Ordered map of string event id and event object
        // Events will be sent in the ordering maintained.
        private LinkedDictionary<String, Object> testEvents;

        // Optional time for each event
        private EDictionary<String, long> testEventTimes;

        public EventCollection(LinkedDictionary<String, Object> testEvents, EDictionary<String, long> testEventTimes)
        {
            this.testEvents = testEvents;
            this.testEventTimes = testEventTimes;
        }

        public virtual Object GetEvent(String eventId)
        {
            if (!testEvents.ContainsKey(eventId))
            {
                throw new ArgumentException("Event id " + eventId + " not found in data set");
            }
            return testEvents.Fetch(eventId);
        }

        public virtual Int64? GetTime(String eventId)
        {
            Int64 value;
            if (testEventTimes.TryGetValue(eventId, out value))
            {
                return value;
            }

            return null;
        }

        public IEnumerator<KeyValuePair<String, Object>> GetEnumerator()
        {
            foreach (KeyValuePair<String, Object> keyValuePair in testEvents)
            {
                yield return keyValuePair;
            }
        }

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            foreach (KeyValuePair<String, Object> keyValuePair in testEvents)
            {
                yield return keyValuePair;
            }
        }

        #endregion
    }
}
