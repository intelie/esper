///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.eql.spec;
using net.esper.filter;

namespace net.esper.eql.spec
{
	/// <summary>
	/// Specification for building an event stream out of a filter for events (supplying type and basic filter criteria)
	/// and views onto these events which are staggered onto each other to supply a final stream of events.
	/// </summary>
	public class FilterStreamSpecCompiled : StreamSpecBase, StreamSpecCompiled
	{
	    private FilterSpecCompiled filterSpec;

	    /// <summary>Ctor.</summary>
	    /// <param name="filterSpec">specifies what events we are interested in.</param>
	    /// <param name="viewSpecs">specifies what view to use to derive data</param>
	    /// <param name="optionalStreamName">stream name, or null if none supplied</param>
	    public FilterStreamSpecCompiled(FilterSpecCompiled filterSpec, List<ViewSpec> viewSpecs, String optionalStreamName)
	        : base(optionalStreamName, viewSpecs)
	    {
	        this.filterSpec = filterSpec;
	    }

	    /// <summary>
	    /// Returns filter specification for which events the stream will getSelectListEvents.
	    /// </summary>
	    /// <returns>filter spec</returns>
	    public FilterSpecCompiled GetFilterSpec()
	    {
	        return filterSpec;
	    }
	}
} // End of namespace
