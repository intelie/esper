///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Threading;

using net.esper.core;

namespace net.esper.util
{
	/// <summary>
	/// Simple lock based on {@link ReentrantLock} that associates a name with the lock and traces locking and unlocking.
	/// </summary>
	public class ManagedLockImpl : ManagedLock
	{
	    private readonly ReaderWriterLock lockObj;
	    private readonly String name;

	    /// <summary>Ctor.</summary>
	    /// <param name="name">of lock</param>
	    public ManagedLockImpl(String name)
	    {
	        this.name = name;
	        this.lockObj = new Object();
	    }

	    /// <summary>Lock.</summary>
	    public void AcquireLock(StatementLockFactory statementLockFactory)
	    {
	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.TraceLock(ManagedReadWriteLock.ACQUIRE_TEXT + name, lockObj);
	        }

	        Monitor.Enter( lockObj );

	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.TraceLock(ManagedReadWriteLock.ACQUIRED_TEXT + name, lockObj);
	        }
	    }

	    /// <summary>Unlock.</summary>
	    public void ReleaseLock(StatementLockFactory statementLockFactory)
	    {
	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.TraceLock(ManagedReadWriteLock.RELEASE_TEXT + name, lockObj);
	        }

	        Monitor.Exit();

	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.TraceLock(ManagedReadWriteLock.RELEASED_TEXT + name, lockObj);
	        }
	    }
	}
} // End of namespace
