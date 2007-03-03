using System;
using System.Collections;

using net.esper.client;

using org.apache.commons.logging;

namespace net.esper.example.stockticker.monitor
{
    public class StockTickerEmittedListener
    {
        private ArrayList matchEvents = ArrayList.Synchronized(new ArrayList());

        public void Emitted(Object obj)
        {
            log.Info(".emitted Received emitted " + obj);
            matchEvents.Add(obj);
        }

        public int Size
        {
            get { return matchEvents.Count; }
        }

        public ArrayList MatchEvents
        {
            get { return matchEvents; }
        }

        public void ClearMatched()
        {
            matchEvents.Clear();
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}