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
using net.esper.util;

namespace net.esper.eql.spec
{
	/// <summary>
	/// Abstract base specification for a stream, consists simply of an optional stream name and a list of views
	/// on to of the stream.
	/// <p>
	/// Implementation classes for views and patterns add additional information defining the
	/// stream of events.
	/// </summary>
	public abstract class StreamSpecBase : MetaDefItem
	{
	    private String optionalStreamName;
	    private List<ViewSpec> viewSpecs = new List<ViewSpec>();

	    /// <summary>Ctor.</summary>
	    /// <param name="optionalStreamName">stream name, or null if none supplied</param>
	    /// <param name="viewSpecs">specifies what view to use to derive data</param>
	    public StreamSpecBase(String optionalStreamName, IList<ViewSpec> viewSpecs)
	    {
	        this.optionalStreamName = optionalStreamName;
	        this.viewSpecs.AddRange(viewSpecs);
	    }

	    /// <summary>Default ctor.</summary>
	    public StreamSpecBase()
	    {
	    }

	    /// <summary>Returns the name assigned.</summary>
	    /// <returns>stream name or null if not assigned</returns>
	    public String OptionalStreamName
	    {
            get { return optionalStreamName; }
	    }

	    /// <summary>
	    /// Returns view definitions to use to construct views to derive data on stream.
	    /// </summary>
	    /// <returns>view defs</returns>
	    public IList<ViewSpec> ViewSpecs
	    {
            get { return viewSpecs; }
	    }
	}
} // End of namespace
