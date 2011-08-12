/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.support.filter;

import java.util.Random;
import java.util.Vector;
import java.util.List;
import java.util.LinkedList;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.filter.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.util.ObjectReservationSingleton;

public class IndexTreeBuilderRunnable implements Runnable
{
    protected final static Random random = new Random(System.currentTimeMillis());

    private FilterHandleSetNode topNode;
    private Vector<FilterSpecCompiled> testFilterSpecs;
    private Vector<EventBean> matchedEvents;
    private Vector<EventBean> unmatchedEvents;
    private final EventType eventType;

    public IndexTreeBuilderRunnable(EventType eventType, FilterHandleSetNode topNode, Vector<FilterSpecCompiled> testFilterSpecs, Vector<EventBean> matchedEvents, Vector<EventBean> unmatchedEvents)
    {
        this.eventType = eventType;
        this.topNode = topNode;
        this.testFilterSpecs = testFilterSpecs;
        this.matchedEvents = matchedEvents;
        this.unmatchedEvents = unmatchedEvents;
    }

    public void run()
    {
        long currentThreadId = Thread.currentThread().getId();

        // Choose one of filter specifications, randomly, then reserve to make sure no one else has the same
        FilterSpecCompiled filterSpec = null;
        EventBean unmatchedEvent = null;
        EventBean matchedEvent = null;

        int index = 0;
        do
        {
            index = random.nextInt(testFilterSpecs.size());
            filterSpec = testFilterSpecs.get(index);
            unmatchedEvent = unmatchedEvents.get(index);
            matchedEvent = matchedEvents.get(index);
        }
        while(!ObjectReservationSingleton.getInstance().reserve(filterSpec));

        // Add expression
        FilterValueSet filterValues = filterSpec.getValueSet(null);
        FilterHandle filterCallback = new SupportFilterHandle();
        IndexTreeBuilder treeBuilder = new IndexTreeBuilder();
        IndexTreePath pathAddedTo = treeBuilder.add(filterValues, filterCallback, topNode);

        // Fire a no-match
        List<FilterHandle> matches = new LinkedList<FilterHandle>();
        topNode.matchEvent(unmatchedEvent, matches, null);

        if (matches.size() != 0)
        {
            log.fatal(".run (" + currentThreadId + ") Got a match but expected no-match, matchCount=" + matches.size() + "  bean=" + unmatchedEvent +
                      "  match=" + matches.get(0).hashCode());
            TestCase.assertFalse(true);
        }

        // Fire a match
        topNode.matchEvent(matchedEvent, matches, null);

        if (matches.size() != 1)
        {
            log.fatal(".run (" + currentThreadId + ") Got zero or two or more match but expected a match, count=" + matches.size() +
                    "  bean=" + matchedEvent);
            TestCase.assertFalse(true);
        }

        // Remove the same expression again
        treeBuilder.remove(filterCallback, pathAddedTo, topNode);
        log.debug(".run (" + Thread.currentThread().getId() + ")" + " Completed");

        ObjectReservationSingleton.getInstance().unreserve(filterSpec);
    }

    private static final Log log = LogFactory.getLog(IndexTreeBuilderRunnable.class);
}
