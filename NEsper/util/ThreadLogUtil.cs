///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Threading;

using org.apache.commons.logging;

namespace net.esper.util
{
	/// <summary>
	/// Utility class for logging threading-related messages.
	/// <p>
	/// Prints thread information and lock-specific info.
	/// </summary>
	public class ThreadLogUtil
	{
	    /// <summary>Set trace log level.</summary>
	    public static int TRACE = 0;

	    /// <summary>Set info log level.</summary>
	    public static int INFO = 1;

	    /// <summary>Enable trace logging.</summary>
	    public readonly static Boolean ENABLED_TRACE = false;

	    /// <summary>Enable info logging.</summary>
	    public readonly static Boolean ENABLED_INFO = false;

	    /// <summary>If enabled, logs for trace level the given objects and text</summary>
	    /// <param name="text">to log</param>
	    /// <param name="objects">to write</param>
	    public static void Trace(String text, params Object[] objects)
	    {
	        if (!ENABLED_TRACE)
	        {
	            return;
	        }
	        Write(text, objects);
	    }

	    /// <summary>If enabled, logs for info level the given objects and text</summary>
	    /// <param name="text">to log</param>
	    /// <param name="objects">to write</param>
	    public static void Info(String text, params Object[] objects)
	    {
	        if (!ENABLED_INFO)
	        {
	            return;
	        }
	        Write(text, objects);
	    }

	    /// <summary>Logs the lock and action.</summary>
	    /// <param name="lockAction">is the action towards the lock</param>
	    /// <param name="lock">is the lock instance</param>
	    public static void TraceLock(String lockAction, ReentrantLock lockObj)
	    {
	        if (!ENABLED_TRACE)
	        {
	            return;
	        }
	        Write(lockAction + " " + GetLockInfo(lockObj));
	    }

	    /// <summary>Logs the lock and action.</summary>
	    /// <param name="lockAction">is the action towards the lock</param>
	    /// <param name="lock">is the lock instance</param>
	    public static void TraceLock(String lockAction, ReaderWriterLock lockObj)
	    {
	        if (!ENABLED_TRACE)
	        {
	            return;
	        }
	        Write(lockAction + " " + GetLockInfo(lockObj));
	    }

	    private static String GetLockInfo(ReentrantLock lockObj)
	    {
	        String lockid = "Lock@" + Integer.ToHexString(lockObj.GetHashCode());
	        return "lock " + lockid + " held=" + lockObj.HoldCount + " isHeldMe=" + lockObj.IsHeldByCurrentThread() +
	                " hasQueueThreads=" + lockObj.HasQueuedThreads();
	    }

	    private static String GetLockInfo(ReentrantReadWriteLock lockObj)
	    {
	        String lockid = "RWLock@" + Integer.ToHexString(lockObj.GetHashCode());
	        return lockid +
	               " readLockCount=" + lockObj.ReadLockCount +
	               " isWriteLocked=" + lockObj.IsWriteLocked();
	    }

	    private static void Write(String text, params Object[] objects)
	    {
	        StringBuffer buf = new StringBuffer();
	        buf.Append(text);
	        buf.Append(' ');
	        foreach (Object obj in objects)
	        {
	            if ((obj is String) || (obj is Number))
	            {
	                buf.Append(obj.ToString());
	            }
	            else
	            {
	                buf.Append(obj.Class.SimpleName);
	                buf.Append('@');
	                buf.Append(Integer.ToHexString(obj.GetHashCode()));
	            }
	            buf.Append(' ');
	        }
	        Write(buf.ToString());
	    }

	    private static void Write(String text)
	    {
	        log.Info(".write Thread " + Thread.CurrentThread.ManagedThreadId + " " + text);
	    }

	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
