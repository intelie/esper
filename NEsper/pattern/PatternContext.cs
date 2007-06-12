using System;

using net.esper.core;
using net.esper.events;
using net.esper.filter;
using net.esper.schedule;

namespace net.esper.pattern
{
	/// <summary>
	/// Contains handles to implementations of services needed by
	/// evaluation nodes.
	/// </summary>

	public sealed class PatternContext
	{
	    private readonly int streamNumber;
	    private readonly StatementContext statementContext;
	    private readonly PatternStateFactory patternStateFactory;

	    /// <summary>Constructor.</summary>
	    /// <param name="patternStateFactory">is the state node factory for the pattern</param>
	    /// <param name="statementContext">is the statement context</param>
	    /// <param name="streamNumber">is the stream number</param>
	    public PatternContext(StatementContext statementContext,
	                          int streamNumber,
	                          PatternStateFactory patternStateFactory)
	    {
	        this.streamNumber = streamNumber;
	        this.statementContext = statementContext;
	        this.patternStateFactory = patternStateFactory;
	    }

	    /// <summary>Gets the service to use for filter evaluation.</summary>
	    /// <returns>filter evaluation service implemetation</returns>
	    public FilterService FilterService
	    {
	        get { return statementContext.FilterService; }
	    }

	    /// <summary>Gets the service to use for schedule evaluation.</summary>
	    /// <returns>schedule evaluation service implemetation</returns>
	    public SchedulingService SchedulingService
	    {
	        get { return statementContext.SchedulingService; }
	    }

	    /// <summary>
	    /// Gets the schedule bucket for ordering schedule callbacks within this pattern.
	    /// </summary>
	    /// <returns>schedule bucket</returns>
	    public ScheduleBucket ScheduleBucket
	    {
	        get { return statementContext.ScheduleBucket; }
	    }

	    /// <summary>Gets the service providing event adaptering or wrapping.</summary>
	    /// <returns>event adapter service</returns>
	    public EventAdapterService EventAdapterService
	    {
	        get { return statementContext.EventAdapterService; }
	    }

	    /// <summary>Gets the statement's resource handle for locking.</summary>
	    /// <returns>handle of statement</returns>
	    public EPStatementHandle EpStatementHandle
	    {
	        get { return statementContext.EpStatementHandle; }
	    }

	    /// <summary>Gets the pattern state node factory to use.</summary>
	    /// <returns>factory for pattern state</returns>
	    public PatternStateFactory PatternStateFactory
	    {
	        get { return patternStateFactory; }
	    }

	    /// <summary>Gets the statement id.</summary>
	    /// <returns>statement id</returns>
	    public String StatementId
	    {
	        get { return statementContext.StatementId; }
	    }

	    /// <summary>Gets the statement name.</summary>
	    /// <returns>statement name</returns>
	    public String StatementName
	    {
	        get { return statementContext.StatementName; }
	    }

	    /// <summary>Gets the stream number.</summary>
	    /// <returns>stream number</returns>
	    public int StreamNumber
	    {
	        get { return streamNumber; }
	    }

	    /// <summary>Gets the engine URI.</summary>
	    /// <returns>engine URI</returns>
	    public String EngineURI
	    {
	        get { return statementContext.EngineURI; }
	    }

	    /// <summary>Gets the engine instance id.</summary>
	    /// <returns>engine instance id</returns>
	    public String EngineInstanceId
	    {
	        get { return statementContext.EngineInstanceId; }
	    }

	    /// <summary>Gets extension services context.</summary>
	    /// <returns>extension services</returns>
	    public ExtensionServicesContext ExtensionServicesContext
	    {
	        get { return statementContext.ExtensionServicesContext; }
	    }
	}
}
