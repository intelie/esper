///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.core;
using net.esper.compat;
using net.esper.collection;
using net.esper.eql.core;
using net.esper.eql.spec;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.eql.view
{
	/// <summary>
	/// A view that prepares output events, batching incoming
	/// events and invoking the result set processor as necessary.
	/// <p>
	/// Handles output rate limiting or stabilizing.
	/// </p>
	/// </summary>
	public class OutputProcessViewPolicy : OutputProcessView
	{
	    private readonly bool outputLastOnly;
	    private readonly OutputCondition outputCondition;
	    private List<EventBean> newEventsList = new List<EventBean>();
		private List<EventBean> oldEventsList = new List<EventBean>();
		private Set<MultiKey<EventBean>> newEventsSet = new LinkedHashSet<MultiKey<EventBean>>();
		private Set<MultiKey<EventBean>> oldEventsSet = new LinkedHashSet<MultiKey<EventBean>>();

		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    /// <summary>Ctor.</summary>
	    /// <param name="resultSetProcessor">
	    /// is processing the result set for publishing it out
	    /// </param>
	    /// <param name="streamCount">
	    /// is the number of streams, indicates whether or not this view participates in a join
	    /// </param>
	    /// <param name="outputLimitSpec">
	    /// is the specification for limiting output (the output condition and the result set processor)
	    /// </param>
	    /// <param name="statementContext">
	    /// is the services the output condition may depend on
	    /// </param>
	    public OutputProcessViewPolicy(ResultSetProcessor resultSetProcessor,
	    					  int streamCount,
	    					  OutputLimitSpec outputLimitSpec,
	    					  StatementContext statementContext)
	        : base(resultSetProcessor)
	    {
	        log.Debug(".ctor");

	    	if(streamCount < 1)
	    	{
	    		throw new ArgumentException("Output process view is part of at least 1 stream");
	    	}

	    	OutputCallback outputCallback = GetCallbackToLocal(streamCount);
	    	outputCondition = OutputConditionFactory.CreateCondition(outputLimitSpec, statementContext, outputCallback);
	        outputLastOnly = (outputLimitSpec != null) && (outputLimitSpec.IsDisplayLastOnly);
	    }

	    /// <summary>
	    /// The update method is called if the view does not participate in a join.
	    /// </summary>
	    /// <param name="newData">new events</param>
	    /// <param name="oldData">old events</param>
	    public override void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".update Received update, " +
	                    "  newData.Length==" + ((newData == null) ? 0 : newData.Length) +
	                    "  oldData.Length==" + ((oldData == null) ? 0 : oldData.Length));
	        }

	        // add the incoming events to the event batches
	        int newDataLength = 0;
	        int oldDataLength = 0;
	        if(newData != null)
	        {
	        	newDataLength = newData.Length;
	        	foreach (EventBean _event in newData)
	        	{
	        		newEventsList.Add(_event);
	        	}
	        }
	        if(oldData != null)
	        {
	        	oldDataLength = oldData.Length;
	        	foreach (EventBean _event in oldData)
	        	{
	        		oldEventsList.Add(_event);
	        	}
	        }

	        outputCondition.UpdateOutputCondition(newDataLength, oldDataLength);
	    }

	    /// <summary>This process (update) method is for participation in a join.</summary>
	    /// <param name="newEvents">new events</param>
	    /// <param name="oldEvents">old events</param>
	    public override void Process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
	    {
	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".process Received update, " +
	                      "  newData.Length==" + ((newEvents == null) ? 0 : newEvents.Count) +
	                      "  oldData.Length==" + ((oldEvents == null) ? 0 : oldEvents.Count));
	        }

	        // add the incoming events to the event batches
	        if (newEvents != null)
            {
	            foreach (MultiKey<EventBean> _event in newEvents)
	            {
	                newEventsSet.Add(_event);
	            }
    	    }

            if (oldEvents != null)
            {
                foreach (MultiKey<EventBean> _event in oldEvents)
                {
                    oldEventsSet.Add(_event);
                }
            }

	        outputCondition.UpdateOutputCondition(
	            newEvents != null ? newEvents.Count : 0,
	            oldEvents != null ? oldEvents.Count : 0);
	    }

		/// <summary>
		/// Called once the output condition has been met.
		/// Invokes the result set processor.
		/// Used for non-join event data.
		/// </summary>
		/// <param name="doOutput">
		/// true if the batched events should actually be output as well as processed, false if they should just be processed
		/// </param>
		/// <param name="forceUpdate">
		/// true if output should be made even when no updating events have arrived
		/// </param>
		protected void ContinueOutputProcessingView(bool doOutput, bool forceUpdate)
		{
			log.Debug(".continueOutputProcessingView");

			// Get the arrays of new and old events, or null if none
			EventBean[] newEvents = (newEventsList.Count != 0) ? newEventsList.ToArray() : null;
			EventBean[] oldEvents = (oldEventsList.Count != 0) ? oldEventsList.ToArray() : null;

			if(resultSetProcessor != null)
			{
				// Process the events and get the result
				Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.ProcessViewResult(newEvents, oldEvents);
				newEvents = newOldEvents != null ? newOldEvents.First : null;
				oldEvents = newOldEvents != null ? newOldEvents.Second : null;
			}
			else if(outputLastOnly)
			{
				// Keep only the last event, if there is one
				newEvents = newEvents != null ? new EventBean[] { newEvents[newEvents.Length - 1] } : newEvents;
				oldEvents = oldEvents != null ? new EventBean[] { oldEvents[oldEvents.Length - 1] } : oldEvents;
			}

			if(doOutput)
			{
				Output(forceUpdate, newEvents, oldEvents);
			}
			ResetEventBatches();
		}

		private void Output(bool forceUpdate, EventBean[] newEvents, EventBean[] oldEvents)
		{
			if (newEvents != null || oldEvents != null)
			{
				UpdateChildren(newEvents, oldEvents);
			}
			else if (forceUpdate)
			{
				UpdateChildren(null, null);
			}
		}

		private void ResetEventBatches()
		{
			newEventsList.Clear();
			oldEventsList.Clear();
			newEventsSet.Clear();
			oldEventsSet.Clear();
		}

		/// <summary>
		/// Called once the output condition has been met.
		/// Invokes the result set processor.
		/// Used for join event data.
		/// </summary>
		/// <param name="doOutput">
		/// true if the batched events should actually be output as well as processed, false if they should just be processed
		/// </param>
		/// <param name="forceUpdate">
		/// true if output should be made even when no updating events have arrived
		/// </param>
		protected void ContinueOutputProcessingJoin(bool doOutput, bool forceUpdate)
		{
			log.Debug(".continueOutputProcessingJoin");

			EventBean[] newEvents = null;
			EventBean[] oldEvents = null;

			Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.ProcessJoinResult(newEventsSet, oldEventsSet);
			if (newOldEvents != null)
			{
				newEvents = newOldEvents.First;
				oldEvents = newOldEvents.Second;
			}

			if(doOutput)
			{
				Output(forceUpdate, newEvents, oldEvents);
			}
			ResetEventBatches();
		}

	    private OutputCallback GetCallbackToLocal(int streamCount)
	    {
	        // single stream means no join
	        // multiple streams means a join
	        if(streamCount == 1)
	        {
	            return new OutputCallback(
                    delegate(bool doOutput, bool forceUpdate)
	                {
                        ContinueOutputProcessingView(doOutput, forceUpdate);
	                });
	        }
	        else
	        {
	            return new OutputCallback(
                    delegate(bool doOutput, bool forceUpdate)
	                {
	                    ContinueOutputProcessingJoin(doOutput, forceUpdate);
	                });
	        }
	    }
	}
} // End of namespace
