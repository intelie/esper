using System;
using System.Collections.Generic;
using System.Text;

using net.esper.client;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.example.linearroad
{
    public class CarSegmentCountListener
    {
        private List<CarSegmentCountResult> newEventList = new List<CarSegmentCountResult>();
        private List<CarSegmentCountResult> oldEventList = new List<CarSegmentCountResult>();

        public void Update(EventBean[] newData, EventBean[] oldData)
        {
            if (newData != null)
            {
                Process("new", newData, newEventList);
            }

            if (oldData != null)
            {
                Process("old", oldData, oldEventList);
            }
        }

        private static void Process(String description, EventBean[] events, List<CarSegmentCountResult> store)
        {
            StringBuilder buffer = new StringBuilder();

            for (int i = 0; i < events.Length; i++)
            {
                IDictionary<String, Object> segVolResult = (IDictionary<String, Object>)events[i]["segVolView"];
                IDictionary<String, Object> segAvgResult = (IDictionary<String, Object>)events[i]["segAvgSpeed"];

                int expressway = (int)segVolResult["expressway"];
                int direction = (int)segVolResult["direction"];
                int segment = (int)segVolResult["segment"];
                double avgSpeed = (double)segAvgResult["average"];
                long size = (long)segVolResult["size"];

                CarSegmentCountResult result = new CarSegmentCountResult(expressway, direction, segment, avgSpeed, size);
                store.Add(result);

                buffer.Append("event#" + i + " " + result + "\n");
            }

            log.Warn(".update " + description + "...\n" + buffer.ToString());
        }

        public void Reset()
        {
            newEventList = new List<CarSegmentCountResult>();
            oldEventList = new List<CarSegmentCountResult>();
        }

        public Object[] GetAndClearNew()
        {
            Object[] result = newEventList.ToArray();
            newEventList = new List<CarSegmentCountResult>();
            return result;
        }

        public Object[] GetAndClearOld()
        {
            Object[] result = oldEventList.ToArray();
            oldEventList = new List<CarSegmentCountResult>();
            return result;
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}