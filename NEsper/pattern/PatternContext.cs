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

	    /**
	     * Constructor.
	     * @param patternStateFactory is the state node factory for the pattern
	     * @param statementContext is the statement context
	     * @param streamNumber is the stream number
	     */
	    public PatternContext(StatementContext statementContext,
	                          int streamNumber,
	                          PatternStateFactory patternStateFactory)
	    {
	        this.streamNumber = streamNumber;
	        this.statementContext = statementContext;
	        this.patternStateFactory = patternStateFactory;
	    }

	    /**
	     * Gets the service to use for filter evaluation.
	     * @return filter evaluation service implemetation
	     */
	    public FilterService FilterService
	    {
	        get { return statementContext.FilterService; }
	    }

	    /**
	     * Gets the service to use for schedule evaluation.
	     * @return schedule evaluation service implemetation
	     */
	    public SchedulingService SchedulingService
	    {
	        get { return statementContext.getSchedulingService; }
	    }

	    /**
	     * Gets the schedule bucket for ordering schedule callbacks within this pattern.
	     * @return schedule bucket
	     */
	    public ScheduleBucket ScheduleBucket
	    {
	        get { return statementContext.ScheduleBucket; }
	    }

	    /**
	     * Gets the service providing event adaptering or wrapping.
	     * @return event adapter service
	     */
	    public EventAdapterService EventAdapterService
	    {
	        get { return statementContext.EventAdapterService; }
	    }

	    /**
	     * Gets the statement's resource handle for locking.
	     * @return handle of statement
	     */
	    public EPStatementHandle EpStatementHandle
	    {
	        get { return statementContext.EpStatementHandle; }
	    }

	    /**
	     * Gets the pattern state node factory to use.
	     * @return factory for pattern state
	     */
	    public PatternStateFactory PatternStateFactory
	    {
	        get { return patternStateFactory; }
	    }

	    /**
	     * Gets the statement id.
	     * @return statement id
	     */
	    public String StatementId
	    {
	        get { return statementContext.StatementId; }
	    }

	    /**
	     * Gets the statement name.
	     * @return statement name
	     */
	    public String StatementName
	    {
	        get { return statementContext.StatementName; }
	    }

	    /**
	     * Gets the stream number.
	     * @return stream number
	     */
	    public int StreamNumber
	    {
	        get { return streamNumber; }
	    }

	    /**
	     * Gets the engine URI.
	     * @return engine URI
	     */
	    public String EngineURI
	    {
	        get { return statementContext.EngineURI; }
	    }

	    /**
	     * Gets the engine instance id.
	     * @return engine instance id
	     */
	    public String EngineInstanceId
	    {
	        get { return statementContext.EngineInstanceId; }
	    }

	    /**
	     * Gets extension services context.
	     * @return extension services
	     */
	    public ExtensionServicesContext ExtensionServicesContext
	    {
	        get { return statementContext.ExtensionServicesContext; }
	    }
	}
}
