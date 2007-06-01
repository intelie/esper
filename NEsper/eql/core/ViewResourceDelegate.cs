///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.view;

namespace net.esper.eql.core
{
	/// <summary>
	/// Service to expression nodes for indicating view resource requirements.
	/// </summary>
	public interface ViewResourceDelegate
	{
	    /// <summary>Request a view resource.</summary>
	    /// <param name="streamNumber">is the stream number to provide the resource</param>
	    /// <param name="requestedCabability">describes the view capability required</param>
	    /// <param name="resourceCallback">for the delegate to supply the resource</param>
	    /// <returns>true to indicate the resource can be granted</returns>
	    bool RequestCapability(int streamNumber, ViewCapability requestedCabability, ViewResourceCallback resourceCallback);
	}
} // End of namespace
