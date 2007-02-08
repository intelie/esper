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

        public void Dispatch()
        {
            DispatchFromQueue(QueueInternal);
            DispatchFromQueue(QueueExternal);
        }

        public void AddExternal(Dispatchable dispatchable)
        {
            Queue<Dispatchable> dispatchQueue = QueueExternal;
            AddToQueue(dispatchable, dispatchQueue);
        }

        public void AddInternal(Dispatchable dispatchable)
        {
            Queue<Dispatchable> dispatchQueue = QueueInternal;
            AddToQueue(dispatchable, dispatchQueue);
        }

        private void AddToQueue(Dispatchable dispatchable, Queue<Dispatchable> dispatchQueue)
        {
            // Make sure the same dispatchable is added once.
            // Could this be a performance problem when the list gets large, it should not get large.
            AssertionFacility.assertFalse(dispatchQueue.Contains(dispatchable), "Dispatchable instance already in queue");

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
                    next.execute();
                }
            }
            catch (InvalidOperationException)
            {
            }
        }

        private static readonly Log log = LogFactory.GetLog(typeof(DispatchServiceImpl));
    }
}
