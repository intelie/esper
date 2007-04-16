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
        private static Queue<Dispatchable> threadDispatchQueueInternal = new Queue<Dispatchable>();
        [ThreadStatic]
        private static Queue<Dispatchable> threadDispatchQueueExternal = new Queue<Dispatchable>();

        private static Queue<Dispatchable> QueueInternal
        {
            get
            {
                if (threadDispatchQueueInternal == null)
                {
                    threadDispatchQueueInternal = new Queue<Dispatchable>();
                }
                return threadDispatchQueueInternal;
            }
        }

        private static Queue<Dispatchable> QueueExternal
        {
            get
            {
                if (threadDispatchQueueExternal == null)
                {
                    threadDispatchQueueExternal = new Queue<Dispatchable>();
                }
                return threadDispatchQueueExternal;
            }
        }

        /// <summary>
        /// Dispatches events in either the internal or external queue.
        /// </summary>

        public void Dispatch()
        {
            DispatchFromQueue(QueueInternal);
            DispatchFromQueue(QueueExternal);
        }

        /// <summary>
        /// Add an item to be dispatched.  The item is added to
        /// the external dispatch queue.
        /// </summary>
        /// <param name="dispatchable">to execute later</param>
        public void AddExternal(Dispatchable dispatchable)
        {
            Queue<Dispatchable> dispatchQueue = QueueExternal;
            AddToQueue(dispatchable, dispatchQueue);
        }

        /// <summary>
        /// Add an item to be dispatched.  The item is added to
        /// the internal dispatch queue.
        /// </summary>
        /// <param name="dispatchable">to execute later</param>
        public void AddInternal(Dispatchable dispatchable)
        {
            Queue<Dispatchable> dispatchQueue = QueueInternal;
            AddToQueue(dispatchable, dispatchQueue);
        }

        private void AddToQueue(Dispatchable dispatchable, Queue<Dispatchable> dispatchQueue)
        {
            // Make sure the same dispatchable is added once.
            // Could this be a performance problem when the list gets large, it should not get large.
            AssertionFacility.AssertFalse(dispatchQueue.Contains(dispatchable), "Dispatchable instance already in queue");

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
                    Dispatchable next = dispatchQueue.Dequeue();
                    next.Execute();
                }
            }
            catch (InvalidOperationException)
            {
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
