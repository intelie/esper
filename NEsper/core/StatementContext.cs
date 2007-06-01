///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.eql.core;
using net.esper.events;
using net.esper.filter;
using net.esper.pattern;
using net.esper.schedule;
using net.esper.view;

namespace net.esper.core
{
	/// <summary>
	/// Contains handles to the implementation of the the scheduling service for use in view evaluation.
	/// </summary>
	public sealed class StatementContext
	{
	    private readonly String engineURI;
	    private readonly String engineInstanceId;
	    private readonly String statementId;
	    private readonly String statementName;
	    private readonly String expression;
	    private readonly SchedulingService schedulingService;
	    private readonly ScheduleBucket scheduleBucket;
	    private readonly EventAdapterService eventAdapterService;
	    private readonly EPStatementHandle epStatementHandle;
	    private readonly ViewResolutionService viewResultionService;
	    private readonly ExtensionServicesContext extensionServicesContext;
	    private readonly StatementStopService statementStopService;
	    private readonly MethodResolutionService methodResolutionService;
	    private readonly PatternContextFactory patternContextFactory;
	    private readonly FilterService filterService;

	    /// <summary>Constructor.</summary>
	    /// <param name="engineURI">is the engine URI</param>
	    /// <param name="engineInstanceId">is the name of the engine instance</param>
	    /// <param name="statementId">
	    /// is the statement is assigned for the statement for which this context exists
	    /// </param>
	    /// <param name="statementName">is the statement name</param>
	    /// <param name="expression">is the EQL or pattern expression used</param>
	    /// <param name="schedulingService">implementation for schedule registration</param>
	    /// <param name="scheduleBucket">
	    /// is for ordering scheduled callbacks within the view statements
	    /// </param>
	    /// <param name="eventAdapterService">
	    /// service for generating events and handling event types
	    /// </param>
	    /// <param name="epStatementHandle">
	    /// is the statements-own handle for use in registering callbacks with services
	    /// </param>
	    /// <param name="viewResultionService">
	    /// is a service for resolving view namespace and name to a view factory
	    /// </param>
	    /// <param name="extensionServicesContext">
	    /// provide extension points for custom statement resources
	    /// </param>
	    /// <param name="statementStopService">
	    /// for registering a callback invoked when a statement is stopped
	    /// </param>
	    /// <param name="methodResolutionService">
	    /// is a service for resolving static methods and aggregation functions
	    /// </param>
	    /// <param name="patternContextFactory">
	    /// is the pattern-level services and context information factory
	    /// </param>
	    /// <param name="filterService">is the filtering service</param>
	    public StatementContext(String engineURI,
	                            String engineInstanceId,
	                              String statementId,
	                              String statementName,
	                              String expression,
	                              SchedulingService schedulingService,
	                              ScheduleBucket scheduleBucket,
	                              EventAdapterService eventAdapterService,
	                              EPStatementHandle epStatementHandle,
	                              ViewResolutionService viewResultionService,
	                              ExtensionServicesContext extensionServicesContext,
	                              StatementStopService statementStopService,
	                              MethodResolutionService methodResolutionService,
	                              PatternContextFactory patternContextFactory,
	                              FilterService filterService)
	    {
	        this.engineURI = engineURI;
	        this.engineInstanceId = engineInstanceId;
	        this.statementId = statementId;
	        this.statementName = statementName;
	        this.expression = expression;
	        this.schedulingService = schedulingService;
	        this.eventAdapterService = eventAdapterService;
	        this.scheduleBucket = scheduleBucket;
	        this.epStatementHandle = epStatementHandle;
	        this.viewResultionService = viewResultionService;
	        this.extensionServicesContext = extensionServicesContext;
	        this.statementStopService = statementStopService;
	        this.methodResolutionService = methodResolutionService;
	        this.patternContextFactory = patternContextFactory;
	        this.filterService = filterService;
	    }

	    /// <summary>Returns the statement id.</summary>
	    /// <returns>statement id</returns>
	    public String GetStatementId()
	    {
	        return statementId;
	    }

	    /// <summary>Returns the statement name</summary>
	    /// <returns>statement name</returns>
	    public String GetStatementName()
	    {
	        return statementName;
	    }

	    /// <summary>Returns service to use for schedule evaluation.</summary>
	    /// <returns>schedule evaluation service implemetation</returns>
	    public SchedulingService GetSchedulingService()
	    {
	        return schedulingService;
	    }

	    /// <summary>Returns service for generating events and handling event types.</summary>
	    /// <returns>event adapter service</returns>
	    public EventAdapterService GetEventAdapterService()
	    {
	        return eventAdapterService;
	    }

	    /// <summary>
	    /// Returns the schedule bucket for ordering schedule callbacks within this pattern.
	    /// </summary>
	    /// <returns>schedule bucket</returns>
	    public ScheduleBucket GetScheduleBucket()
	    {
	        return scheduleBucket;
	    }

	    /// <summary>Returns the statement's resource locks.</summary>
	    /// <returns>statement resource lock/handle</returns>
	    public EPStatementHandle GetEpStatementHandle()
	    {
	        return epStatementHandle;
	    }

	    /// <summary>Returns view resolution svc.</summary>
	    /// <returns>view resolution</returns>
	    public ViewResolutionService GetViewResultionService()
	    {
	        return viewResultionService;
	    }

	    /// <summary>Returns extension context.</summary>
	    /// <returns>context</returns>
	    public ExtensionServicesContext GetExtensionServicesContext()
	    {
	        return extensionServicesContext;
	    }

	    /// <summary>Returns statement stop subscription taker.</summary>
	    /// <returns>stop service</returns>
	    public StatementStopService GetStatementStopService()
	    {
	        return statementStopService;
	    }

	    /// <summary>
	    /// Returns service to look up static and aggregation methods or functions.
	    /// </summary>
	    /// <returns>method resolution</returns>
	    public MethodResolutionService GetMethodResolutionService()
	    {
	        return methodResolutionService;
	    }

	    /// <summary>Returns the pattern context factory for the statement.</summary>
	    /// <returns>pattern context factory</returns>
	    public PatternContextFactory GetPatternContextFactory()
	    {
	        return patternContextFactory;
	    }

	    /// <summary>Returns the statement expression text</summary>
	    /// <returns>expression text</returns>
	    public String GetExpression()
	    {
	        return expression;
	    }

	    /// <summary>Returns the engine URI.</summary>
	    /// <returns>engine URI</returns>
	    public String GetEngineURI()
	    {
	        return engineURI;
	    }

	    /// <summary>Returns the engine instance id.</summary>
	    /// <returns>instance id</returns>
	    public String GetEngineInstanceId()
	    {
	        return engineInstanceId;
	    }

	    /// <summary>Returns the filter service.</summary>
	    /// <returns>filter service</returns>
	    public FilterService GetFilterService()
	    {
	        return filterService;
	    }

	    public override String ToString()
	    {
	        return  " statementId=" + statementId +
	                " statementName=" + statementName;
	    }
	}
} // End of namespace
