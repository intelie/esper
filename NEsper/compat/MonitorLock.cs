using System;
using System.Threading;

namespace net.esper.compat
{
    /// <summary>
    /// Disposable object that acquires a write lock and disposes
    /// of the lock when it goes out of scope.
    /// </summary>

    public class MonitorLock : IDisposable
    {
        private Object m_lockObj;
        private bool m_lockAcquired;

        /// <summary>
        /// Initializes a new instance of the <see cref="MonitorLock"/> class.
        /// </summary>
        /// <param name="monitorObj">The monitor object.</param>
        public MonitorLock(Object monitorObj)
        {
            this.m_lockAcquired = false;
            this.m_lockObj = monitorObj;
            if (!Monitor.TryEnter(this.m_lockObj, LockConstants.MonitorTimeout))
            {
                throw new ApplicationException("timeout expired before the lock request was granted.");
            }
            this.m_lockAcquired = true;
        }

        /// <summary>
        /// Releases unmanaged resources and performs other cleanup operations before the
        /// <see cref="WriterLock"/> is reclaimed by garbage collection.
        /// </summary>
        ~MonitorLock()
        {
            Dispose();
        }

        /// <summary>
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>
        public void Dispose()
        {
            lock (this)
            {
                if (this.m_lockAcquired && (this.m_lockObj != null))
                {
                    Monitor.Exit(m_lockObj);
                    this.m_lockObj = null;
                    this.m_lockAcquired = false;
                }
            }
        }
    }

}
