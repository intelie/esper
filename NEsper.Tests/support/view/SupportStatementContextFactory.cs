// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using net.esper.core;
using net.esper.schedule;
using net.esper.support.events;
using net.esper.support.schedule;
using net.esper.view;

namespace net.esper.support.view
{
	public class SupportStatementContextFactory
	{
	    public static StatementContext MakeContext()
	    {
	        SupportSchedulingServiceImpl sched = new SupportSchedulingServiceImpl();
	        return MakeContext(sched);
	    }

	    public static StatementContext MakeContext(SchedulingService stub)
	    {
	        return new StatementContext("engURI", "engInstId", "stmtId", "stmtName", "exprHere", stub, stub.AllocateBucket(),
	                SupportEventAdapterService.GetService(), null, new ViewResolutionServiceImpl(null), null, null, null, null, null);
	    }
	}
} // End of namespace
