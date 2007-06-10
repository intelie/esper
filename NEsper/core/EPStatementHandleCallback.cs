///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.filter;
using net.esper.schedule;
using net.esper.util;

namespace net.esper.core
{
	/// <summary>
	/// Statement resource handle and callback for use with {@link net.esper.filter.FilterService} and
	/// {@link net.esper.schedule.SchedulingService}.
	/// <p>
	/// Links the statement handle identifying a statement and containing the statement resource lock,
	/// with the actual callback to invoke for a statement together.
	/// </summary>
	public class EPStatementHandleCallback : FilterHandle, ScheduleHandle, MetaDefItem
	{
	    private EPStatementHandle epStatementHandle;
	    private FilterHandleCallback filterCallback;
	    private ScheduleHandleCallback scheduleCallback;

	    /// <summary>Ctor.</summary>
	    /// <param name="epStatementHandle">is a statement handle</param>
	    /// <param name="callback">is a filter callback</param>
	    public EPStatementHandleCallback(EPStatementHandle epStatementHandle, FilterHandleCallback callback)
	    {
	        this.epStatementHandle = epStatementHandle;
	        this.filterCallback = callback;
	    }

	    /// <summary>Ctor.</summary>
	    /// <param name="epStatementHandle">is a statement handle</param>
	    /// <param name="callback">is a schedule callback</param>
	    public EPStatementHandleCallback(EPStatementHandle epStatementHandle, ScheduleHandleCallback callback)
	    {
	        this.epStatementHandle = epStatementHandle;
	        this.scheduleCallback = callback;
	    }

	    /// <summary>Returns the statement handle.</summary>
	    /// <returns>handle containing a statement resource lock</returns>
	    public EPStatementHandle EpStatementHandle
	    {
	    	get { return epStatementHandle; }
	    }

	    /// <summary>
	    /// Returns the statement filter callback, or null if this is a schedule callback handle.
	    /// </summary>
	    /// <returns>filter callback</returns>
	    public FilterHandleCallback FilterCallback
	    {
	    	get { return filterCallback; }
	    }

	    /// <summary>
	    /// Returns the statement schedule callback, or null if this is a filter callback handle.
	    /// </summary>
	    /// <returns>schedule callback</returns>
	    public ScheduleHandleCallback ScheduleCallback
	    {
	    	get { return scheduleCallback; }
	    }
	}
} // End of namespace
