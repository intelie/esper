using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Threading;

namespace net.esper.compat
{
    /// <summary>
    /// MonitorLock is a class for assisting people with synchronized operations.
    /// Traditionally, code might have looked something like this:
    /// <code>
    /// lock( object ) { 
    ///   ...
    /// }
    /// </code>
    /// However, this has a few issues.  It's prone to deadlock because the lock
    /// operator does not have a timeout.  It's also difficult to determine who
    /// owns a lock at a given time.  So eventually people changed to this form:
    /// <code>
    /// if (Monitor.TryEnter(object, timeout)) {
    ///   try {
    ///    ...
    ///   } finally {
    ///     Monitor.Exit(object);
    ///   }
    /// }
    /// </code>
    /// It gets bulky and begins to become difficult to maintain over time.
    /// MonitorLock works much like the lock( object ) model except that it relies
    /// upon the IDisposable interface to help with scoping of the lock.  So to
    /// use MonitorLock, first instantiate one and then replace your lock(object)
    /// with this:
    /// <code>
    /// using(lockObj.Acquire()) {
    ///   ...
    /// }
    /// </code>
    /// Your code will work as before except that the monitorLock will use a timed
    /// entry into critical sections and it can be used to diagnose issues that
    /// may be occuring in your thread locking.
    /// <para>
    /// MonitorLock allows users to specify events that can be consumed on lock
    /// acquisition or release.  Additionally, it can inform you when a lock
    /// is acquired within an existing lock.  And last, if you want to know where
    /// your locks are being acquired, it can maintain a StackTrace of points
    /// where allocations are occuring.
    /// </para>
    /// </summary>

    public class MonitorLock
    {
        /// <summary>
        /// Options
        /// </summary>

        private static MonitorLockOptions s_uDefaultLockOptions = MonitorLockOptions.None;

        /// <summary>
        /// Gets the options.
        /// </summary>
        /// <value>The options.</value>
        public static MonitorLockOptions DefaultOptions
        {
            get { return s_uDefaultLockOptions; }
        }

        /// <summary>
        /// Default timeout for lock acquisition.
        /// </summary>

        private static int s_uDefaultLockTimeout = 5000;

        /// <summary>
        /// Gets the default timeout.
        /// </summary>
        /// <value>The default timeout.</value>
        public static int DefaultTimeout
        {
            get { return s_uDefaultLockTimeout; }
        }

        /// <summary>
        /// Initializes the <see cref="MonitorLock"/> class.
        /// </summary>
        static MonitorLock()
        {
        }

        /// <summary>
        /// Underlying object that is locked
        /// </summary>

        private Object m_uLockObj;

        /// <summary>
        /// Used to track allocations.
        /// </summary>

        private Stack<StackTrace> m_uLockStack;

        /// <summary>
        /// Number of milliseconds until the lock acquisition fails
        /// </summary>

        private int m_uLockTimeout;

        /// <summary>
        /// Gets the number of milliseconds until the lock acquisition fails.
        /// </summary>
        /// <value>The lock timeout.</value>

        public int LockTimeout
        {
            get { return m_uLockTimeout; }
        }

        /// <summary>
        /// Used to track recursive locks.
        /// </summary>

        private uint m_uLockDepth;

        /// <summary>
        /// Gets the lock depth.
        /// </summary>
        /// <value>The lock depth.</value>

        public uint LockDepth
        {
            get { return m_uLockDepth; }
        }

        /// <summary>
        /// Options
        /// </summary>

        private MonitorLockOptions m_uLockOptions;

        /// <summary>
        /// Gets the options.
        /// </summary>
        /// <value>The options.</value>
        public MonitorLockOptions Options
        {
            get { return m_uLockOptions; }
        }

        /// <summary>
        /// Gets a value indicating whether this instance has a stack trace for
        /// the current lock.
        /// </summary>
        /// <value>
        /// 	<c>true</c> if this instance has current lock trace; otherwise, <c>false</c>.
        /// </value>
        public bool HasCurrentLockTrace
        {
            get
            {
                return
                    m_uLockStack != null ?
                    m_uLockStack.Count > 0 :
                    false;
            }
        }

        /// <summary>
        /// Gets the stack trace for the current lock.
        /// </summary>
        /// <value>The current lock trace.</value>
        public StackTrace CurrentLockStackTrace
        {
            get
            {
                return
                    m_uLockStack != null ?
                    m_uLockStack.Count > 0 ?
                    m_uLockStack.Peek() :
                    null :
                    null;
            }
        }

        /// <summary>
        /// Gets all stack traces.
        /// </summary>
        /// <value>All stack traces.</value>
        public IEnumerable<StackTrace> AllStackTraces
        {
            get { return m_uLockStack; }
        }

        /// <summary>
        /// Gets a value indicating whether the stack trace option is enabled.
        /// </summary>
        /// <value>
        /// 	<c>true</c> if [stack trace is enabled]; otherwise, <c>false</c>.
        /// </value>
        public bool IsStackTraceEnabled
        {
            get
            {
                return ((m_uLockOptions & MonitorLockOptions.StackTraces) == MonitorLockOptions.StackTraces);
            }
        }

        /// <summary>
        /// Gets a value indicating whether this instance has nested lock checking enabled.
        /// </summary>
        /// <value>
        /// 	<c>true</c> if this instance has nested lock checking enabled; otherwise, <c>false</c>.
        /// </value>
        public bool IsNestedLockCheckingEnabled
        {
            get
            {
                return ((m_uLockOptions & MonitorLockOptions.NestedLockChecking) == MonitorLockOptions.NestedLockChecking);
            }
        }

        /// <summary>
        /// Occurs when a lock is acquired while one is already being held by
        /// the thread.  Only occurs if nested lock checking is enabled.
        /// </summary>

        public event EventHandler NestedLockAcquired;

        /// <summary>
        /// Occurs when the lock is acquired.
        /// </summary>

        public event EventHandler LockAcquired;

        /// <summary>
        /// Occurs when the lock is released.
        /// </summary>

        public event EventHandler LockReleased;

        /// <summary>
        /// Initializes a new instance of the <see cref="MonitorLock"/> class.
        /// </summary>
        public MonitorLock()
            : this(DefaultOptions)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="MonitorLock"/> class.
        /// </summary>
        /// <param name="options">The options.</param>
        public MonitorLock(MonitorLockOptions options)
            : this(DefaultTimeout, options)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="MonitorLock"/> class.
        /// </summary>
        public MonitorLock(int lockTimeout, MonitorLockOptions options)
        {
            m_uLockObj = new Object();
            m_uLockOptions = options;
            m_uLockDepth = 0;
            m_uLockTimeout = 5000;

            if (IsStackTraceEnabled)
            {
                m_uLockStack = new Stack<StackTrace>();
            }
        }

        /// <summary>
        /// Acquires a lock against this instance.
        /// </summary>
        public IDisposable Acquire()
        {
            return new DisposableLock(this);
        }

        /// <summary>
        /// Internally acquires the lock.
        /// </summary>
        private void InternalAcquire()
        {
            if (!Monitor.TryEnter(m_uLockObj, m_uLockTimeout))
            {
                throw new ApplicationException("Unable to obtain lock before timeout occurred");
            }

            if (LockAcquired != null)
            {
                LockAcquired(this, null);
            }

            // Increase lock depth and fire the nested lock acquisition
            // event if it has been enabled.

            if ((++m_uLockDepth > 1) && IsNestedLockCheckingEnabled)
            {
                if (NestedLockAcquired != null)
                {
                    NestedLockAcquired(this, EventArgs.Empty);
                }
            }

            // Add the item to the stack if we have stack
            // tracing enabled.

            if (m_uLockStack != null)
            {
                m_uLockStack.Push(new StackTrace());
            }
        }

        /// <summary>
        /// Internally releases the lock.
        /// </summary>
        private void InternalRelease()
        {
            try
            {
                m_uLockDepth--;
                // Remove the stack trace
                if (m_uLockStack != null)
                {
                    m_uLockStack.Pop();
                }
            }
            finally // Just in case
            {
                Monitor.Exit(m_uLockObj);

                if (LockReleased != null)
                {
                    LockReleased(this, null);
                }
            }
        }

        /// <summary>
        /// A disposable object that is allocated and acquires the
        /// lock and automatically releases the lock when it is
        /// disposed.
        /// </summary>

        internal class DisposableLock : IDisposable
        {
            private MonitorLock m_lockObj;
            private bool m_lockAcquired;

            /// <summary>
            /// Initializes a new instance of the <see cref="DisposableLock"/> class.
            /// </summary>
            /// <param name="lockObj">The lock obj.</param>
            internal DisposableLock(MonitorLock lockObj)
            {
                this.m_lockAcquired = false;
                this.m_lockObj = lockObj;
                lockObj.InternalAcquire();
                this.m_lockAcquired = true;
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
                        this.m_lockObj.InternalRelease();
                        this.m_lockObj = null;
                        this.m_lockAcquired = false;
                    }
                }
            }
        }
    }

    /// <summary>
    /// A set of options that can be given to the monitor lock to
    /// determine behavior.
    /// </summary>

    public enum MonitorLockOptions
    {
        /// <summary>
        /// No options
        /// </summary>
        None = 0,
        /// <summary>
        /// MonitorLock will report whenever a nested lock has
        /// occurred.  Sometimes applications are not clear about
        /// when they perform nested locking.  This option will
        /// enable events for that.
        /// </summary>
        NestedLockChecking = 1,
        /// <summary>
        /// MonitorLock will keep track of the StackTrace each
        /// time the application acquires a lock.
        /// </summary>
        StackTraces = 2
    }
}
