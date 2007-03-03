using System;
using System.Collections.Generic;
using System.Text;

using net.esper.client;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.example.linearroad
{
    public class AccidentNotifyListener
    {
        private List<AccidentNotifyResult> newEventList = new List<AccidentNotifyResult>();
        private List<AccidentNotifyResult> oldEventList = new List<AccidentNotifyResult>();

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

        private static void Process(String description, EventBean[] events, List<AccidentNotifyResult> store)
        {
            StringBuilder buffer = new StringBuilder();

            for (int i = 0; i < events.Length; i++)
            {
                CarLocEvent curCarSeg = (CarLocEvent)events[i]["curCarSeg"];

                int expressway = (int)curCarSeg.Expressway;
                int direction = (int)curCarSeg.Direction;
                int segment = (int)curCarSeg.Segment;

                /*
                int expressway = (Integer) curCarSeg.get("expressway");
                int direction = (Integer) curCarSeg.get("direction");
                int segment = (Integer) curCarSeg.get("segment");
                */

                AccidentNotifyResult result = new AccidentNotifyResult(expressway, direction, segment);
                store.Add(result);

                buffer.Append("event#" + i + " " + result + "\n");
            }

            log.Warn(".update " + description + "...\n" + buffer.ToString());
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}