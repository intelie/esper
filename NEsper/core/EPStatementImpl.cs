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
	/// <summary>Statement implementation for EQL statements.</summary>
	public class EPStatementImpl : EPStatementSPI
	{
		/// <summary>
		/// Using a copy-on-write set here:
		/// When the engine dispatches events to a set of listeners, then while iterating through the set there
		/// may be listeners added or removed (the listener may remove itself).
		/// Additionally, events may be dispatched by multiple threads to the same listener.
		/// </summary>
		private Set<UpdateListener> listeners = new CopyOnWriteArraySet<UpdateListener>();

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
			this.dispatchChildView = new UpdateDispatchView(this.Listeners, dispatchService);
			this.currentState = EPStatementState.STOPPED;
		}

		public String StatementId
		{
			get { return statementId; }
		}

		public void Start()
		{
			if (statementLifecycleSvc == null)
			{
				throw new IllegalStateException("Cannot start statement, statement is in destroyed state");
			}
			statementLifecycleSvc.Start(statementId);
		}

		public void Stop()
		{
			if (statementLifecycleSvc == null)
			{
				throw new IllegalStateException("Cannot stop statement, statement is in destroyed state");
			}
			statementLifecycleSvc.Stop(statementId);
			dispatchChildView.Clear();
		}

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

		public EPStatementState State
		{
			get { return currentState; }
		}
		
		public EPStatementState CurrentState
		{
			set { this.currentState = value; }
		}

		public Viewable ParentView
		{
			set
			{
				if (viewable == null)
				{
					parentView.RemoveView(dispatchChildView);
					parentView = null;
				}
				else
				{
					parentView = viewable;
					parentView.AddView(dispatchChildView);
					eventType = parentView.EventType;
				}
			}
		}

		public String Text
		{
			get { return expressionText; }
		}

		public String Name
		{
			get { return statementName; }
		}

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

		public EventType EventType
		{
			get { return eventType; }
		}

		/// <summary>Returns the set of listeners to the statement.</summary>
		/// <returns>statement listeners</returns>
		public Set<UpdateListener> Listeners
		{
			get { return listeners; }
			set
			{
				this.listeners = value;
				if (dispatchChildView != null)
				{
					dispatchChildViewUpdateListeners = value;
				}
			}
		}

		/// <summary>Add a listener to the statement.</summary>
		/// <param name="listener">to add</param>
		public void AddListener(UpdateListener listener)
		{
			if (listener == null)
			{
				throw new ArgumentException("Null listener reference supplied");
			}

			listeners.Add(listener);
			statementLifecycleSvc.UpdatedListeners(statementId, listeners);
		}

		/// <summary>Remove a listeners to a statement.</summary>
		/// <param name="listener">to remove</param>
		public void RemoveListener(UpdateListener listener)
		{
			if (listener == null)
			{
				throw new ArgumentException("Null listener reference supplied");
			}

			listeners.Remove(listener);
		}

		/// <summary>Remove all listeners to a statement.</summary>
		public void RemoveAllListeners()
		{
			listeners.Clear();
		}
	}
} // End of namespace
