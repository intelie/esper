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

package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestOrderBySelfJoin extends TestCase
{
	private static final Log log = LogFactory.getLog(TestOrderBySelfJoin.class);
	private EPServiceProvider epService;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testOrderedSelfJoin()
	{
        String[] fields = new String[] {"prio", "cnt"};
        String statementString = "select c1.event_criteria_id as ecid, " +
                    "c1.priority as priority, " +
                    "c2.priority as prio, cast(count(*), int) as cnt from " +
    	            SupportHierarchyEvent.class.getName() + ".std:lastevent() as c1, " +
    	            SupportHierarchyEvent.class.getName() + ".std:groupwin(event_criteria_id).std:lastevent() as c2, " +
    	            SupportHierarchyEvent.class.getName() + ".std:groupwin(event_criteria_id).std:lastevent() as p " +
                    "where c2.event_criteria_id in (c1.event_criteria_id,2,1) " +
                    "and p.event_criteria_id in (c1.parent_event_criteria_id, c1.event_criteria_id) " +
                    "order by c2.priority asc";
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);

        sendEvent(1, 1, null);
        sendEvent(3, 2, 2);
        sendEvent(3, 2, 2);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{1,2},{2,2}});
    }

    private void sendEvent(Integer ecid, Integer priority, Integer parent)
	{
	    SupportHierarchyEvent ev = new SupportHierarchyEvent(ecid,priority,parent);
	    epService.getEPRuntime().sendEvent(ev);
	}

    public static class SupportHierarchyEvent {
        private Integer event_criteria_id;
        private Integer priority;
        private Integer parent_event_criteria_id;

        public SupportHierarchyEvent(Integer event_criteria_id, Integer priority, Integer parent_event_criteria_id)
        {
            this.event_criteria_id = event_criteria_id;
            this.priority = priority;
            this.parent_event_criteria_id = parent_event_criteria_id;
        }

        public Integer getEvent_criteria_id()
        {
            return event_criteria_id;
        }

        public Integer getPriority()
        {
            return priority;
        }

        public Integer getParent_event_criteria_id()
        {
            return parent_event_criteria_id;
        }

        public String toString()
        {
            return "ecid=" + event_criteria_id +
                   " prio=" + priority +
                   " parent=" + parent_event_criteria_id;
        }
    }

}
