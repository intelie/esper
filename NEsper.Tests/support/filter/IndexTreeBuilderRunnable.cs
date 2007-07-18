///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Threading;

using NUnit.Framework;

using net.esper.compat;
using net.esper.events;
using net.esper.filter;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.support.filter
{
	public class IndexTreeBuilderRunnable : Runnable
	{
	    protected static readonly Random random = new Random(System.Diagnostics.Process.GetCurrentProcess().Id);

	    private FilterHandleSetNode topNode;
        private IList<FilterSpecCompiled> testFilterSpecs;
        private IList<EventBean> matchedEvents;
        private IList<EventBean> unmatchedEvents;
	    private readonly EventType eventType;

        public IndexTreeBuilderRunnable(EventType eventType, FilterHandleSetNode topNode, IList<FilterSpecCompiled> testFilterSpecs, IList<EventBean> matchedEvents, IList<EventBean> unmatchedEvents)
	    {
	        this.eventType = eventType;
	        this.topNode = topNode;
	        this.testFilterSpecs = testFilterSpecs;
	        this.matchedEvents = matchedEvents;
	        this.unmatchedEvents = unmatchedEvents;
	    }

	    public void Run()
	    {
	        long currentThreadId = Thread.CurrentThread.ManagedThreadId;

	        // Choose one of filter specifications, randomly, then reserve to make sure no one else has the same
	        FilterSpecCompiled filterSpec = null;
	        EventBean unmatchedEvent = null;
	        EventBean matchedEvent = null;

	        int index = 0;
	        do
	        {
	            index = random.Next(testFilterSpecs.Count);
	            filterSpec = testFilterSpecs[index];
	            unmatchedEvent = unmatchedEvents[index];
	            matchedEvent = matchedEvents[index];
	        }
	        while(!ObjectReservationSingleton.Instance.Reserve(filterSpec));

	        // Add expression
	        FilterValueSet filterValues = filterSpec.GetValueSet(null);
	        FilterHandle filterCallback = new SupportFilterHandle();
	        IndexTreeBuilder treeBuilder = new IndexTreeBuilder();
	        IndexTreePath pathAddedTo = treeBuilder.Add(filterValues, filterCallback, topNode);

	        // Fire a no-match
	        List<FilterHandle> matches = new List<FilterHandle>();
	        topNode.MatchEvent(unmatchedEvent, matches);

	        if (matches.Count != 0)
	        {
	            log.Fatal(".run (" + currentThreadId + ") Got a match but expected no-match, matchCount=" + matches.Count + "  bean=" + unmatchedEvent +
	                      "  match=" + matches[0].GetHashCode());
	            Assert.IsFalse(true);
	        }

	        // Fire a match
	        topNode.MatchEvent(matchedEvent, matches);

	        if (matches.Count != 1)
	        {
	            log.Fatal(".run (" + currentThreadId + ") Got zero or two or more match but expected a match, count=" + matches.Count +
	                    "  bean=" + matchedEvent);
	            Assert.IsFalse(true);
	        }

	        // Remove the same expression again
	        treeBuilder.Remove(filterCallback, pathAddedTo, topNode);
	        log.Debug(".run (" + Thread.CurrentThread.ManagedThreadId + ")" + " Completed");

	        ObjectReservationSingleton.Instance.Unreserve(filterSpec);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
