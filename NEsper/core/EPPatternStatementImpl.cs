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
        virtual public EventType EventType
		{
			get { return eventType; }
		}

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
		/// <param name="expressionText">- expression</param>
		/// <param name="eventType">- event type of events the pattern will fire</param>
		/// <param name="dispatchService">- service for dispatching events</param>
		/// <param name="eventAdapterService">- service for generating events or event wrappers and types</param>
		/// <param name="StartMethod">- method to Start the pattern</param>

		public EPPatternStatementImpl( String expressionText, EventType eventType, DispatchService dispatchService, EventAdapterService eventAdapterService, EPPatternStmtStartMethod StartMethod )
		{
			this.expressionText = expressionText;
			this.eventType = eventType;
			this.dispatchService = dispatchService;
			this.eventAdapterService = eventAdapterService;
			this.startMethod = startMethod;

			dispatch = new PatternListenerDispatch( this.Listeners );

			Start();
		}

		public void matchFound( EDictionary<String, EventBean> matchEvent )
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
				if ( !( dispatch.hasData() ) )
				{
					dispatchService.AddExternal( dispatch );
				}
				dispatch.Add( aggregateEvent );
			}
		}

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

		public override void listenerStop()
		{
			// No need to take action
		}

		public override void listenerStart()
		{
			// No need to take action
		}

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
