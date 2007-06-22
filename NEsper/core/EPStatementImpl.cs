///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.dispatch;
using net.esper.events;
using net.esper.view;

namespace net.esper.core
{
	/// <summary>
	/// Statement implementation for EQL statements.
	/// </summary>
	public class EPStatementImpl : EPStatementSPI
	{
		/// <summary>
		/// Using a copy-on-write set here:
		/// When the engine dispatches events to a set of listeners, then while iterating through the set there
		/// may be listeners added or removed (the listener may remove itself).
		/// Additionally, events may be dispatched by multiple threads to the same listener.
		/// </summary>
		private UpdateEventHandlerSet eventHandlers = new UpdateEventHandlerSet();

		private readonly String statementId;
		private readonly String statementName;
		private readonly String expressionText;
		private bool isPattern;
		private UpdateDispatchView dispatchChildView;
		private StatementLifecycleSvc statementLifecycleSvc;

		private Viewable parentView;
		private EPStatementState currentState;
		private EventType eventType;

		/// <summary>Ctor.</summary>
		/// <param name="statementId">
		/// is a unique ID assigned by the engine for the statement
		/// </param>
		/// <param name="statementName">
		/// is the statement name assigned during creation, or the statement id if none was assigned
		/// </param>
		/// <param name="expressionText">is the EQL and/or pattern expression</param>
		/// <param name="isPattern">is true to indicate this is a pure pattern expression</param>
		/// <param name="dispatchService">
		/// for dispatching events to listeners to the statement
		/// </param>
		/// <param name="statementLifecycleSvc">
		/// handles lifecycle transitions for the statement
		/// </param>
		public EPStatementImpl(String statementId,
		                       String statementName,
		                       String expressionText,
		                       bool isPattern,
		                       DispatchService dispatchService,
		                       StatementLifecycleSvc statementLifecycleSvc)
		{
			this.isPattern = isPattern;
			this.statementId = statementId;
			this.statementName = statementName;
			this.expressionText = expressionText;
			this.statementLifecycleSvc = statementLifecycleSvc;
			this.dispatchChildView = new UpdateDispatchView(this.eventHandlers, dispatchService);
			this.currentState = EPStatementState.STOPPED;
		}

        /// <summary>
        /// Returns the statement id.
        /// </summary>
        /// <value></value>
        /// <returns>statement id</returns>
		public String StatementId
		{
			get { return statementId; }
		}

        /// <summary>
        /// Start the statement.
        /// </summary>
		public void Start()
		{
			if (statementLifecycleSvc == null)
			{
				throw new IllegalStateException("Cannot start statement, statement is in destroyed state");
			}
			statementLifecycleSvc.Start(statementId);
		}

        /// <summary>
        /// Stop the statement.
        /// </summary>
		public void Stop()
		{
			if (statementLifecycleSvc == null)
			{
				throw new IllegalStateException("Cannot stop statement, statement is in destroyed state");
			}
			statementLifecycleSvc.Stop(statementId);
			dispatchChildView.Clear();
		}

        /// <summary>
        /// Destroy the statement releasing all statement resources.
        /// <p>A destroyed statement cannot be started again.</p>
        /// </summary>
		public void Destroy()
		{
			if (currentState == EPStatementState.DESTROYED)
			{
				throw new IllegalStateException("Statement already destroyed");
			}
			statementLifecycleSvc.Destroy(statementId);
			parentView = null;
			eventType = null;
			dispatchChildView = null;
			statementLifecycleSvc = null;
		}

        /// <summary>
        /// Gets the statement's current state
        /// </summary>
        /// <value></value>
		public EPStatementState State
		{
			get { return currentState; }
		}

        /// <summary>
        /// Set statement state.
        /// </summary>
        /// <value></value>
		public EPStatementState CurrentState
		{
			set { this.currentState = value; }
		}

        /// <summary>
        /// Sets the parent view.
        /// </summary>
        /// <value></value>
		public Viewable ParentView
		{
			set
			{
                if (value == null)
				{
					parentView.RemoveView(dispatchChildView);
					parentView = null;
				}
				else
				{
					parentView = value;
					parentView.AddView(dispatchChildView);
					eventType = parentView.EventType;
				}
			}
		}

        /// <summary>
        /// Returns the underlying expression text or XML.
        /// </summary>
        /// <value></value>
        /// <returns> expression text</returns>
		public String Text
		{
			get { return expressionText; }
		}

        /// <summary>
        /// Returns the statement name.
        /// </summary>
        /// <value></value>
        /// <returns> statement name</returns>
		public String Name
		{
			get { return statementName; }
		}

        /// <summary>
        /// Returns an enumerator that iterates through a collection.
        /// </summary>
        /// <returns>
        /// An <see cref="T:System.Collections.IEnumerator"></see> object that can be used to iterate through the collection.
        /// </returns>
		System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
		{
			// Return null if not started
			if (parentView == null)
			{
				return null;
			}

			if (isPattern)
			{
				return dispatchChildView.GetEnumerator();        // Which simply keeps the last event
			}
			else
			{
				return parentView.GetEnumerator();
			}
		}

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
		public IEnumerator<EventBean> GetEnumerator()
		{
			// Return null if not started
			if (parentView == null)
			{
				return null;
			}

			if (isPattern)
			{
				return dispatchChildView.GetEnumerator();        // Which simply keeps the last event
			}
			else
			{
				return parentView.GetEnumerator();
			}
		}

        /// <summary>
        /// Returns the type of events the iterable returns.
        /// </summary>
        /// <value></value>
        /// <returns> event type of events the iterator returns
        /// </returns>
		public EventType EventType
		{
			get { return eventType; }
		}

        /// <summary>
        /// Occurs when there are events to be observed.
        /// </summary>

        public event UpdateEventHandler Update
        {
            add
            {
                eventHandlers.Add(value);
                statementLifecycleSvc.UpdatedEventHandlers(statementId, eventHandlers);
            }
            remove
            { 
                eventHandlers.Remove(value);
            }
        }

		/// <summary>Add a listener to the statement.</summary>
		/// <param name="listener">to add</param>
		public void AddListener(UpdateListener listener)
		{
		    eventHandlers.AddListener(listener);
            statementLifecycleSvc.UpdatedEventHandlers(statementId, eventHandlers);
        }

		/// <summary>Remove a listeners to a statement.</summary>
		/// <param name="listener">to remove</param>
		public void RemoveListener(UpdateListener listener)
		{
            eventHandlers.RemoveListener(listener);
		}

		/// <summary>Remove all listeners to a statement.</summary>
		public void RemoveAllListeners()
		{
            eventHandlers.Clear();
		}
	}
} // End of namespace
