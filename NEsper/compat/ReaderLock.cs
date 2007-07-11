using System;
using System.Threading;

namespace net.esper.compat
{
    /// <summary>
    /// Disposable object that acquires a read lock and disposes
    /// of the lock when it goes out of scope.
    /// </summary>

    public class ReaderLock : IDisposable
    {
        private ReaderWriterLock m_lockObj;
        private bool m_lockAcquired;

        /// <summary>
        /// Initializes a new instance of the <see cref="ReaderLock"/> class.
        /// </summary>
        /// <param name="lockObj">The lock obj.</param>
        public ReaderLock(ReaderWriterLock lockObj)
        {
            this.m_lockAcquired = false;
            this.m_lockObj = lockObj;
            this.m_lockObj.AcquireReaderLock(LockConstants.ReaderTimeout);
            this.m_lockAcquired = true;
        }

        /// <summary>
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>
        public void Dispose()
        {
            lock (this)
            {
                if (this.m_lockAcquired && (this.m_lockObj != null) && (this.m_lockObj.IsReaderLockHeld))
                {
                    this.m_lockObj.ReleaseReaderLock();
                    this.m_lockObj = null;
                    this.m_lockAcquired = false;

                }
            }
        }
    }
}
