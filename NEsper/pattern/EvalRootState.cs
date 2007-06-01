///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary>
	/// Interface for a root state node accepting a callback to use to indicate pattern results.
	/// </summary>
	public interface EvalRootState : PatternStopCallback
	{
	    /// <summary>Accept callback to indicate pattern results.</summary>
	    /// <param name="callback">is a pattern result call</param>
	    void SetCallback(PatternMatchCallback callback);
	}
} // End of namespace
