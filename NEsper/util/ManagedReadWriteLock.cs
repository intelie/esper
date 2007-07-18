///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Threading;

using net.esper.compat;

namespace net.esper.util
{
	/// <summary>
	/// Simple read-write lock based on ReaderWriterLock that associates a
	/// name with the lock and traces read/write locking and unlocking.
	/// </summary>
	public class ManagedReadWriteLock
	{
	    /// <summary>Acquire text.</summary>
	    internal const string ACQUIRE_TEXT  = "Acquire ";

	    /// <summary>Acquired text.</summary>
        internal const string ACQUIRED_TEXT = "Got     ";

	    /// <summary>Release text.</summary>
        internal const string RELEASE_TEXT = "Release ";

	    /// <summary>Released text.</summary>
        internal const string RELEASED_TEXT = "Freed   ";

	    private readonly ReaderWriterLock lockObj;
	    private readonly String name;

	    /// <summary>Ctor.</summary>
	    /// <param name="name">of lock</param>
	    public ManagedReadWriteLock(String name)
	    {
	        this.name = name;
	        this.lockObj = new ReaderWriterLock();
	    }

	    /// <summary>Lock write lock.</summary>
	    public void AcquireWriteLock()
	    {
	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.TraceLock(ACQUIRE_TEXT + " write " + name, lockObj);
	        }

	        lockObj.AcquireWriterLock( LockConstants.WriterTimeout ) ;

	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.TraceLock(ACQUIRED_TEXT + " write " + name, lockObj);
	        }
	    }

	    /// <summary>Unlock write lock.</summary>
	    public void ReleaseWriteLock()
	    {
	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.TraceLock(RELEASE_TEXT + " write " + name, lockObj);
	        }

	        lockObj.ReleaseWriterLock() ;

	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.TraceLock(RELEASED_TEXT + " write " + name, lockObj);
	        }
	    }

	    /// <summary>Lock read lock.</summary>
	    public void AcquireReadLock()
	    {
	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.TraceLock(ACQUIRE_TEXT + " read " + name, lockObj);
	        }

	        lockObj.AcquireReaderLock( LockConstants.ReaderTimeout ) ;

	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.TraceLock(ACQUIRED_TEXT + " read " + name, lockObj);
	        }
	    }

	    /// <summary>Unlock read lock.</summary>
	    public void ReleaseReadLock()
	    {
	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.TraceLock(RELEASE_TEXT + " read " + name, lockObj);
	        }

	        lockObj.ReleaseReaderLock() ;

	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.TraceLock(RELEASED_TEXT + " read " + name, lockObj);
	        }
	    }

        /// <summary>
        /// Gets a value indicating whether this instance has its reader lock held.
        /// </summary>

	    public bool IsReaderLockHeld
	    {
	        get
	        {
	            return
	                lockObj != null
	                    ? lockObj.IsReaderLockHeld
	                    : false;
	        }
	    }

        /// <summary>
        /// Gets a value indicating whether this instance has its writer lock held.
        /// </summary>

        public bool IsWriterLockHeld
        {
            get
            {
                return
                    lockObj != null
                        ? lockObj.IsWriterLockHeld
                        : false;
            }
        }
    }
} // End of namespace
