using System;
using System.Threading;

using net.esper.util;

namespace net.esper.compat
{
    /// <summary>
    /// Disposable object that acquires a write lock and disposes
    /// of the lock when it goes out of scope.
    /// </summary>

    public class WriterLock : BaseLock
    {
        /// <summary>
        /// Unmanaged lock name
        /// </summary>
        private readonly String m_uLockName;
        /// <summary>
        /// Unmanaged lock object
        /// </summary>
        private ReaderWriterLock m_uLockObj;
        /// <summary>
        /// Managed lock object
        /// </summary>
        private ManagedReadWriteLock m_mLockObj;
        /// <summary>
        /// Indicates if we acquired the lock
        /// </summary>
        private bool m_lockAcquired;

        /// <summary>
        /// Initializes a new instance of the <see cref="WriterLock"/> class.
        /// </summary>
        /// <param name="lockObj">The lock obj.</param>
        public WriterLock(ReaderWriterLock lockObj)
            : this( lockObj.ToString(), lockObj )
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="WriterLock"/> class.
        /// </summary>
        /// <param name="name">The name of the lock.</param>
        /// <param name="lockObj">The lock obj.</param>
        public WriterLock(String name, ReaderWriterLock lockObj)
        {
            this.m_lockAcquired = false;
            this.m_mLockObj = null;
            this.m_uLockObj = lockObj;
            this.m_uLockName = name;

            if (ThreadLogUtil.ENABLED_TRACE)
            {
                ThreadLogUtil.TraceLock(ACQUIRE_TEXT + " write " + name, lockObj);
            }
            
            this.m_uLockObj.AcquireWriterLock(LockConstants.WriterTimeout);
            this.m_lockAcquired = true;

            if (ThreadLogUtil.ENABLED_TRACE)
            {
                ThreadLogUtil.TraceLock(ACQUIRED_TEXT + " write " + name, lockObj);
            }
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="WriterLock"/> class.
        /// </summary>
        /// <param name="lockObj">The lock obj.</param>
        public WriterLock(ManagedReadWriteLock lockObj)
        {
            this.m_lockAcquired = false;
            this.m_uLockObj = null;
            this.m_mLockObj = lockObj;
            this.m_mLockObj.AcquireWriteLock();
            this.m_lockAcquired = true;
        }

        /// <summary>
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>
        public override void Dispose()
        {
            lock (this)
            {
                // Release the locks.  Only one of the locks will be allocated
                // so there isn't any real risk to having this fire for two
                // lock objects.

                if (this.m_lockAcquired)
                {
                    if ((this.m_uLockObj != null) && (this.m_uLockObj.IsWriterLockHeld))
                    {
                        if (ThreadLogUtil.ENABLED_TRACE)
                        {
                            ThreadLogUtil.TraceLock(RELEASE_TEXT + " write " + m_uLockName, m_uLockObj);
                        }

                        this.m_uLockObj.ReleaseWriterLock();
                        this.m_uLockObj = null;
                        this.m_lockAcquired = false;

                        if (ThreadLogUtil.ENABLED_TRACE)
                        {
                            ThreadLogUtil.TraceLock(RELEASED_TEXT + " write " + m_uLockName, m_uLockObj);
                        }
                    }

                    if ((this.m_mLockObj != null) && (this.m_mLockObj.IsWriterLockHeld))
                    {
                        this.m_mLockObj.ReleaseWriteLock();
                        this.m_mLockObj = null;
                        this.m_lockAcquired = false;
                    }
                }
            }
        }
    }
}
