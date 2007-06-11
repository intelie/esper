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
	/// <summary>Default pattern context factory.</summary>
	public class PatternContextFactoryDefault : PatternContextFactory
	{
	    public PatternContext CreateContext(StatementContext statementContext,
	                                        int streamId,
	                                        EvalRootNode rootNode)
	    {
	        PatternStateFactory patternStateFactory = new PatternStateFactoryImpl();

	        PatternContext patternContext = new PatternContext(statementContext, streamId, patternStateFactory);

	        patternStateFactory.SetContext(patternContext);

	        return patternContext;
	    }
	}
} // End of namespace
