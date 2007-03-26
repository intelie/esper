using System;
using System.Collections.Generic;
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

        /// <summary>
        /// Initializes a new instance of the <see cref="WriterLock"/> class.
        /// </summary>
        /// <param name="lockObj">The lock obj.</param>
        public WriterLock(ReaderWriterLock lockObj)
        {
            this.m_lockObj = lockObj;
            this.m_lockObj.AcquireWriterLock(LockConstants.ReaderTimeout);
        }

        /// <summary>
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>
        public void Dispose()
        {
            this.m_lockObj.ReleaseWriterLock();
        }
    }
}
