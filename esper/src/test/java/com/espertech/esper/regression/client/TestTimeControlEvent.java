package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.core.EPRuntimeIsolatedSPI;
import com.espertech.esper.core.EPRuntimeSPI;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

import java.util.Map;

public class TestTimeControlEvent extends TestCase
{
    private EPServiceProvider epService;
    private EPRuntimeSPI runtimeSPI;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getViewResources().setShareViews(false);
        configuration.addEventType("SupportBean", SupportBean.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        runtimeSPI = (EPRuntimeSPI) epService.getEPRuntime();
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
