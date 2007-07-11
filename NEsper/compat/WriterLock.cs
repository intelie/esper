using System;
using System.Threading;

namespace net.esper.compat
{
    /// <summary>
    /// Disposable object that acquires a write lock and disposes
    /// of the lock when it goes out of scope.
    /// </summary>

    public class WriterLock : IDisposable
    {
        private ReaderWriterLock m_lockObj;
        private bool m_lockAcquired;

        /// <summary>
        /// Initializes a new instance of the <see cref="WriterLock"/> class.
        /// </summary>
        /// <param name="lockObj">The lock obj.</param>
        public WriterLock(ReaderWriterLock lockObj)
        {
            this.m_lockAcquired = false;
            this.m_lockObj = lockObj;
            this.m_lockObj.AcquireWriterLock(LockConstants.WriterTimeout);
            this.m_lockAcquired = true;
        }

        /// <summary>
        /// Releases unmanaged resources and performs other cleanup operations before the
        /// <see cref="WriterLock"/> is reclaimed by garbage collection.
        /// </summary>
        ~WriterLock()
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
                if (this.m_lockAcquired && (this.m_lockObj != null) && (this.m_lockObj.IsWriterLockHeld))
                {
                    this.m_lockObj.ReleaseWriterLock();
                    this.m_lockObj = null;
                    this.m_lockAcquired = false;
                }
            }
        }
    }
}
