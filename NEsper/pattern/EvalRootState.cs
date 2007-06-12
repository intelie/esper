///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.pattern;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary>
	/// Interface for a root state node accepting a callback to use to indicate pattern results.
	/// </summary>
	public interface EvalRootState : PatternStopCallback
	{
	    /// <summary>Accept callback to indicate pattern results.</summary>
	    PatternMatchCallback Callback
	    {
	    	get ;
	    	set ;
	    }
	}
} // End of namespace
