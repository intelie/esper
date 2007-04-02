using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.collection;
using net.esper.dispatch;
using net.esper.events;
using net.esper.pattern;

using org.apache.commons.logging;

namespace net.esper.core
{
	/// <summary>
	/// Statement for patterns.
	/// </summary>

	public class EPPatternStatementImpl
		: EPStatementSupport
		, EPStatement
		, PatternMatchCallback
	{
        /// <summary>
        /// Returns the type of events the iterable returns.
        /// </summary>
        /// <value></value>
        /// <returns> event type of events the iterator returns
        /// </returns>
        virtual public EventType EventType
		{
			get { return eventType; }
		}

        /// <summary>
        /// Returns the underlying expression text or XML.
        /// </summary>
        /// <value></value>
        /// <returns> expression text
        /// </returns>
		virtual public String Text
		{
			get { return expressionText;}
		}

		private String expressionText;
		private EventType eventType;
		private DispatchService dispatchService;
		private EventAdapterService eventAdapterService;
		private EPPatternStmtStartMethod startMethod;

		private EPStatementStopMethod stopMethod;

		private EventBean lastEvent;
		private PatternListenerDispatch dispatch;

        /// <summary>
        /// Constructor.
        /// </summary>
        /// <param name="expressionText">expression</param>
        /// <param name="eventType">event type of events the pattern will fire</param>
        /// <param name="dispatchService">service for dispatching events</param>
        /// <param name="eventAdapterService">service for generating events or event wrappers and types</param>
        /// <param name="startMethod">The start method.</param>

		public EPPatternStatementImpl( String expressionText, EventType eventType, DispatchService dispatchService, EventAdapterService eventAdapterService, EPPatternStmtStartMethod startMethod )
		{
			this.expressionText = expressionText;
			this.eventType = eventType;
			this.dispatchService = dispatchService;
			this.eventAdapterService = eventAdapterService;
			this.startMethod = startMethod;

			dispatch = new PatternListenerDispatch( this.Listeners );

			Start();
		}

        /// <summary>
        /// Indicate matching events.
        /// </summary>
        /// <param name="matchEvent">contains a map of event tags and event objects</param>
		public void MatchFound( EDictionary<String, EventBean> matchEvent )
		{
			if ( log.IsDebugEnabled )
			{
				log.Debug( ".matchFound Listeners=" + Listeners.Count + "  dispatch=" + dispatch );
			}

			EventBean aggregateEvent = eventAdapterService.CreateMapFromUnderlying( matchEvent, eventType );
			lastEvent = aggregateEvent;

			if ( Listeners.Count > 0 )
			{
				// The dispatch has no data after initialization and after it fired
				if ( !( dispatch.HasData ) )
				{
					dispatchService.AddExternal( dispatch );
				}
				dispatch.Add( aggregateEvent );
			}
		}

        /// <summary>
        /// Stop the statement.
        /// </summary>
		public virtual void Stop()
		{
			if ( stopMethod == null )
			{
				throw new SystemException( "Pattern statement already stopped" );
			}

			stopMethod();
			stopMethod = null;
			lastEvent = null;
		}

        /// <summary>
        /// Start the statement.
        /// </summary>
		public virtual void Start()
		{
			if ( stopMethod != null )
			{
				throw new SystemException( "Pattern statement already Started" );
			}

			stopMethod = startMethod.Start( (PatternMatchCallback) this );

			// Since the pattern Start itself may have generated an event, dispatch
			dispatchService.Dispatch();
		}

        /// <summary>
        /// Called when the last listener is removed.
        /// </summary>
		public override void ListenerStop()
		{
			// No need to take action
		}

        /// <summary>
        /// Called when the first listener is added.
        /// </summary>
		public override void ListenerStart()
		{
			// No need to take action
		}

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
		public IEnumerator<EventBean> GetEnumerator()
		{
			if ( stopMethod != null )
			{
				// When Started, return iterator even if lastEvent is null (no event received, hasNext returns false)
				return new SingleEventIterator( lastEvent );
			}
			else
			{
				// When not Started, no iterator is available
				return null;
			}
		}

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return this.GetEnumerator();
        }

        #endregion

		private static readonly Log log = LogFactory.GetLog( typeof( EPPatternStatementImpl ) );
	}
}
