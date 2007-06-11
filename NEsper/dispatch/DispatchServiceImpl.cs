using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.compat;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.dispatch
{
    /// <summary>
    /// Implements dispatch service using a thread-local linked list of Dispatchable instances.
    /// </summary>

    public class DispatchServiceImpl : DispatchService
    {
        [ThreadStatic]
        private static Queue<Dispatchable> threadDispatchQueue = new Queue<Dispatchable>();

        private static Queue<Dispatchable> ThreadDispatchQueue
        {
            get
            {
                if (threadDispatchQueue == null)
                {
                    threadDispatchQueue = new Queue<Dispatchable>();
                }
                return threadDispatchQueue;
            }
        }

        /// <summary>
        /// Dispatches events in the queue.
        /// </summary>

        public void Dispatch()
        {
            DispatchFromQueue(ThreadDispatchQueue);
        }

        /// <summary>
        /// Add an item to be dispatched.  The item is added to
        /// the external dispatch queue.
        /// </summary>
        /// <param name="dispatchable">to execute later</param>
        public void AddExternal(Dispatchable dispatchable)
        {
            Queue<Dispatchable> dispatchQueue = ThreadDispatchQueue;
            dispatchQueue.Enqueue(dispatchable);
        }

        private void DispatchFromQueue(Queue<Dispatchable> dispatchQueue)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".dispatchFromQueue Dispatch queue is " + dispatchQueue.Count + " elements");
            }

            try
            {
                while (dispatchQueue.Count > 0)
                {
                    dispatchQueue.Dequeue().Execute();
                }
            }
            catch (InvalidOperationException)
            {
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
