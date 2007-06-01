///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.eql.core;
using net.esper.pattern;
using net.esper.schedule;
using net.esper.util;
using net.esper.view;

namespace net.esper.core
{
	/// <summary>
	/// Default implementation for making a statement-specific context class.
	/// </summary>
	public class StatementContextFactoryDefault : StatementContextFactory
	{
	    public StatementContext MakeContext(String statementId,
	                                    String statementName,
	                                    String expression,
	                                    EPServicesContext engineServices)
	    {
	        // Allocate the statement's schedule bucket which stays constant over it's lifetime.
	        // The bucket allows callbacks for the same time to be ordered (within and across statements) and thus deterministic.
	        ScheduleBucket scheduleBucket = engineServices.SchedulingService.AllocateBucket();

	        // Create a lock for the statement
	        ManagedLock statementResourceLock = engineServices.StatementLockFactory.GetStatementLock(statementName, expression);
	        EPStatementHandle epStatementHandle = new EPStatementHandle(statementId, statementResourceLock, expression);

	        MethodResolutionService methodResolutionService = new MethodResolutionServiceImpl(engineServices.EngineImportService);

	        PatternContextFactory patternContextFactory = new PatternContextFactoryDefault();

	        // Create statement context
	        return new StatementContext(engineServices.EngineURI,
	                engineServices.EngineInstanceId, statementId, statementName, expression, engineServices.SchedulingService,
	                scheduleBucket, engineServices.EventAdapterService, epStatementHandle,
	                engineServices.ViewResolutionService, engineServices.ExtensionServicesContext,
	                new StatementStopServiceImpl(), methodResolutionService, patternContextFactory, engineServices.FilterService);
	    }
	}
} // End of namespace
