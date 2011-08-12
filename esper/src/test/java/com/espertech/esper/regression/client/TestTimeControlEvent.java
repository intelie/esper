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

package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.time.CurrentTimeSpanEvent;
import com.espertech.esper.core.EPRuntimeIsolatedSPI;
import com.espertech.esper.core.EPRuntimeSPI;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TestTimeControlEvent extends TestCase
{
    private EPServiceProvider epService;
    private EPRuntimeSPI runtimeSPI;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getViewResources().setShareViews(false);
        configuration.addEventType("SupportBean", SupportBean.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        runtimeSPI = (EPRuntimeSPI) epService.getEPRuntime();
        listener = new SupportUpdateListener();
    }

    public void testSendTimeSpan() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd HH:mm:ss SSS");
        Date d = format.parse("2010 01 01 00:00:00 000");
        d.getTime();

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select current_timestamp() as ct from pattern[every timer:interval(1.5 sec)]");
        stmtOne.addListener(listener);
        
        epService.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(3500));
        assertEquals(2, listener.getNewDataList().size());
        assertEquals(1500L, listener.getNewDataList().get(0)[0].get("ct"));
        assertEquals(3000L, listener.getNewDataList().get(1)[0].get("ct"));
        listener.reset();

        epService.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(4500));
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(4500L, listener.getNewDataList().get(0)[0].get("ct"));
        listener.reset();

        epService.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(9000));
        assertEquals(3, listener.getNewDataList().size());
        assertEquals(6000L, listener.getNewDataList().get(0)[0].get("ct"));
        assertEquals(7500L, listener.getNewDataList().get(1)[0].get("ct"));
        assertEquals(9000L, listener.getNewDataList().get(2)[0].get("ct"));
        listener.reset();

        epService.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(10499));
        assertEquals(0, listener.getNewDataList().size());

        epService.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(10499));
        assertEquals(0, listener.getNewDataList().size());

        epService.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(10500));
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(10500L, listener.getNewDataList().get(0)[0].get("ct"));
        listener.reset();

        epService.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(10500));
        assertEquals(0, listener.getNewDataList().size());

        epService.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(14000, 200));
        assertEquals(14000, epService.getEPRuntime().getCurrentTime());
        assertEquals(2, listener.getNewDataList().size());
        assertEquals(12100L, listener.getNewDataList().get(0)[0].get("ct"));
        assertEquals(13700L, listener.getNewDataList().get(1)[0].get("ct"));
        listener.reset();
    }

    public void testSendTimeSpanIsolated() {

        EPServiceProviderIsolated isolated = epService.getEPServiceIsolated("I1");
        isolated.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        EPStatement stmtOne = isolated.getEPAdministrator().createEPL("select current_timestamp() as ct from pattern[every timer:interval(1.5 sec)]", null, null);
        stmtOne.addListener(listener);

        isolated.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(3500));
        assertEquals(2, listener.getNewDataList().size());
        assertEquals(1500L, listener.getNewDataList().get(0)[0].get("ct"));
        assertEquals(3000L, listener.getNewDataList().get(1)[0].get("ct"));
        listener.reset();

        isolated.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(4500));
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(4500L, listener.getNewDataList().get(0)[0].get("ct"));
        listener.reset();

        isolated.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(9000));
        assertEquals(3, listener.getNewDataList().size());
        assertEquals(6000L, listener.getNewDataList().get(0)[0].get("ct"));
        assertEquals(7500L, listener.getNewDataList().get(1)[0].get("ct"));
        assertEquals(9000L, listener.getNewDataList().get(2)[0].get("ct"));
        listener.reset();

        isolated.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(10499));
        assertEquals(0, listener.getNewDataList().size());

        isolated.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(10499));
        assertEquals(10499, isolated.getEPRuntime().getCurrentTime());
        assertEquals(0, listener.getNewDataList().size());

        isolated.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(10500));
        assertEquals(1, listener.getNewDataList().size());
        assertEquals(10500L, listener.getNewDataList().get(0)[0].get("ct"));
        listener.reset();

        isolated.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(10500));
        assertEquals(0, listener.getNewDataList().size());

        isolated.getEPRuntime().sendEvent(new CurrentTimeSpanEvent(14000, 200));
        assertEquals(14000, isolated.getEPRuntime().getCurrentTime());
        assertEquals(2, listener.getNewDataList().size());
        assertEquals(12100L, listener.getNewDataList().get(0)[0].get("ct"));
        assertEquals(13700L, listener.getNewDataList().get(1)[0].get("ct"));
        listener.reset();
    }

    public void testNextScheduledTime() {

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));
        assertNull(epService.getEPRuntime().getNextScheduledTime());
        assertSchedules(runtimeSPI.getStatementNearestSchedules(), new Object[0][]);

        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select * from pattern[timer:interval(2 sec)]");
        assertEquals(2000L, (long) epService.getEPRuntime().getNextScheduledTime());
        assertSchedules(runtimeSPI.getStatementNearestSchedules(), new Object[][] {{stmtOne.getName(), 2000L}});

        EPStatement stmtTwo = epService.getEPAdministrator().createEPL("@Name('s2') select * from pattern[timer:interval(150 msec)]");
        assertEquals(150L, (long) epService.getEPRuntime().getNextScheduledTime());
        assertSchedules(runtimeSPI.getStatementNearestSchedules(), new Object[][] {{"s2", 150L}, {stmtOne.getName(), 2000L}});

        stmtTwo.destroy();
        assertEquals(2000L, (long) epService.getEPRuntime().getNextScheduledTime());
        assertSchedules(runtimeSPI.getStatementNearestSchedules(), new Object[][] {{stmtOne.getName(), 2000L}});

        EPStatement stmtThree = epService.getEPAdministrator().createEPL("select * from pattern[timer:interval(3 sec) and timer:interval(4 sec)]");
        assertEquals(2000L, (long) epService.getEPRuntime().getNextScheduledTime());
        assertSchedules(runtimeSPI.getStatementNearestSchedules(), new Object[][] {{stmtOne.getName(), 2000L}, {stmtThree.getName(), 3000L}});

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(2500));
        assertEquals(3000L, (long) epService.getEPRuntime().getNextScheduledTime());
        assertSchedules(runtimeSPI.getStatementNearestSchedules(), new Object[][] {{stmtThree.getName(), 3000L}});

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(3500));
        assertEquals(4000L, (long) epService.getEPRuntime().getNextScheduledTime());
        assertSchedules(runtimeSPI.getStatementNearestSchedules(), new Object[][] {{stmtThree.getName(), 4000L}});

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(4500));
        assertEquals(null, epService.getEPRuntime().getNextScheduledTime());
        assertSchedules(runtimeSPI.getStatementNearestSchedules(), new Object[0][]);
        
        // test isolated service
        EPServiceProviderIsolated isolated = epService.getEPServiceIsolated("I1");
        EPRuntimeIsolatedSPI isolatedSPI = (EPRuntimeIsolatedSPI) isolated.getEPRuntime();

        isolated.getEPRuntime().sendEvent(new CurrentTimeEvent(0));
        assertNull(isolated.getEPRuntime().getNextScheduledTime());
        assertSchedules(isolatedSPI.getStatementNearestSchedules(), new Object[0][]);

        EPStatement stmtFour = isolated.getEPAdministrator().createEPL("select * from pattern[timer:interval(2 sec)]", null, null);
        assertEquals(2000L, (long) isolatedSPI.getNextScheduledTime());
        assertSchedules(isolatedSPI.getStatementNearestSchedules(), new Object[][] {{stmtFour.getName(), 2000L}});
    }

    private void assertSchedules(Map<String, Long> schedules, Object[][] expected) {
        ArrayAssertionUtil.assertUnorderedMap(schedules, expected);
    }
}
