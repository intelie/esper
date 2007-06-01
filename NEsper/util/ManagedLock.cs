///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.core;

namespace net.esper.util
{
	/// <summary>Interface for a lock for use to perform statement-level locking.</summary>
	public interface ManagedLock
	{
	    /// <summary>Acquire a lock.</summary>
	    /// <param name="statementLockFactory">
	    /// is the engine lock factory service that the lock can use for engine lock services
	    /// </param>
	    void AcquireLock(StatementLockFactory statementLockFactory);

	    /// <summary>Release a lock.</summary>
	    /// <param name="statementLockFactory">
	    /// is the engine lock factory service that the lock can use for engine lock services
	    /// </param>
	    void ReleaseLock(StatementLockFactory statementLockFactory);
	}
} // End of namespace
