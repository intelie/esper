///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.core;
using net.esper.filter;
using net.esper.view;

namespace net.esper.view.stream
{
	/// <summary>
	/// Service on top of the filter service for reuseing filter callbacks and their associated EventStream instances.
	/// Same filter specifications (equal) do not need to be added to the filter service twice and the
	/// EventStream instance that is the stream of events for that filter can be reused.
	/// <para>
	/// We are re-using streams such that views under such streams can be reused for efficient resource use.
	/// </para>
	/// </summary>
	public interface StreamFactoryService
	{
        /// <summary>
        /// Create or reuse existing EventStream instance representing that event filter.
        /// When called for some filters, should return same stream.
        /// </summary>
        /// <param name="filterSpec">event filter definition</param>
        /// <param name="filterService">filter service to activate filter if not already active</param>
        /// <param name="epStatementHandle">is the statements-own handle for use in registering callbacks with services</param>
        /// <param name="isJoin">is indicatng whether the stream will participate in a join statement, information
        /// necessary for stream reuse and multithreading concerns</param>
        /// <returns>event stream representing active filter</returns>
	    EventStream CreateStream(FilterSpecCompiled filterSpec,
                                 FilterService filterService,
                                 EPStatementHandle epStatementHandle,
	                             bool isJoin);

        /// <summary>
        /// Drop the event stream associated with the filter passed in.
        /// Throws an exception if already dropped.
        /// </summary>
        /// <param name="filterSpec">is the event filter definition associated with the event stream to be dropped</param>
        /// <param name="filterService">to be used to deactivate filter when the last event stream is dropped</param>
        /// <param name="isJoin">is indicatng whether the stream will participate in a join statement, information
        /// necessary for stream reuse and multithreading concerns</param>
	    void DropStream(FilterSpecCompiled filterSpec, FilterService filterService, bool isJoin);
	}
} // End of namespace
