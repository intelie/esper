///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.core;

namespace net.esper.pattern
{
	/// <summary>
	/// Factory for pattern context instances, creating context objects for each distinct pattern based on the
	/// patterns root node and stream id.
	/// </summary>
	public interface PatternContextFactory
	{
	    /// <summary>Create a pattern context.</summary>
	    /// <param name="statementContext">is the statement information and services</param>
	    /// <param name="streamId">is the stream id</param>
	    /// <param name="rootNode">is the pattern root node</param>
	    /// <returns>pattern context</returns>
	    PatternContext CreateContext(StatementContext statementContext,
	                                        int streamId,
	                                        EvalRootNode rootNode);
	}
} // End of namespace
