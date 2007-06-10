///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.eql.core;
using net.esper.events;
using org.apache.commons.logging;

namespace net.esper.eql.view
{
	/// <summary>
	/// Output process view that does not enforce any output policies and may simply
	/// hand over events to child views.
	/// </summary>
	public class OutputProcessViewDirect : OutputProcessView
	{
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    /// <summary>Ctor.</summary>
	    /// <param name="resultSetProcessor">
	    /// is processing the result set for publishing it out
	    /// </param>
	    public OutputProcessViewDirect(ResultSetProcessor resultSetProcessor)
	        : base(resultSetProcessor)
	    {
	        log.Debug(".ctor");
	        if (resultSetProcessor == null)
	        {
	            throw new ArgumentException("Null result set processor, no output processor required");
	        }
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

	        Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.ProcessViewResult(newData, oldData);

	        EventBean[] newEventArr = newOldEvents != null ? newOldEvents.First : null;
	        EventBean[] oldEventArr = newOldEvents != null ? newOldEvents.Second : null;

	        if(newEventArr != null || oldEventArr != null)
	        {
	            UpdateChildren(newEventArr, oldEventArr);
	        }
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

	        log.Debug(".continueOutputProcessingJoin");

	        Pair<EventBean[], EventBean[]> newOldEvents = resultSetProcessor.ProcessJoinResult(newEvents, oldEvents);

	        if (newOldEvents == null)
	        {
	            return;
	        }
	        EventBean[] newEventArr = newOldEvents.First;
	        EventBean[] oldEventArr = newOldEvents.Second;

	        if (newEventArr != null || oldEventArr != null)
	        {
	            UpdateChildren(newEventArr, oldEventArr);
	        }
	    }
	}
} // End of namespace
