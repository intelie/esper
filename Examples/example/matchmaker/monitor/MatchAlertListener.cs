using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.example.matchmaker.eventbean;

using org.apache.commons.logging;

namespace net.esper.example.matchmaker.monitor
{
    public class MatchAlertListener
    {
        private List<MatchAlertBean> emittedList = new List<MatchAlertBean>();

        public void Emitted(Object obj)
        {
            log.Info(".emitted Emitted object=" + obj);
            emittedList.Add((MatchAlertBean)obj);
        }

        public int Size
        {
            get { return emittedList.Count; }
        }

        public List<MatchAlertBean> EmittedList
        {
            get { return emittedList; }
        }

        public int GetAndClearEmittedCount()
        {
            int count = emittedList.Count;
            emittedList.Clear();
            return count;
        }

        public void ClearEmitted()
        {
            emittedList.Clear();
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}