///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.core;
using net.esper.pattern;
using net.esper.support.events;
using net.esper.support.schedule;
using net.esper.support.view;

namespace net.esper.support.pattern
{
	public class SupportPatternContextFactory
	{
	    public static PatternContext MakeContext()
	    {
	        StatementContext stmtContext = SupportStatementContextFactory.MakeContext();
	        return new PatternContext(stmtContext, 1, null);
	    }
	}
} // End of namespace
