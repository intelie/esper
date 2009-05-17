package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportBean_B;
import com.espertech.esper.core.StatementType;
import com.espertech.esper.core.EPStatementSPI;

public class TestNamedWindowProcessingOrder extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("Event", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testOrderedDeleteAndSelect()
    {
        String stmtText;
        stmtText = "create window MyWindow.std:lastevent() as select * from Event";
        epService.getEPAdministrator().createEPL(stmtText);

        stmtText = "insert into MyWindow select * from Event";
        epService.getEPAdministrator().createEPL(stmtText);

        stmtText = "on MyWindow e delete from MyWindow win where win.string=e.string and e.intPrimitive = 7";
        epService.getEPAdministrator().createEPL(stmtText);

        stmtText = "on MyWindow e delete from MyWindow win where win.string=e.string and e.intPrimitive = 5";
        epService.getEPAdministrator().createEPL(stmtText);

        stmtText = "on MyWindow e insert into ResultStream select e.* from MyWindow";
        epService.getEPAdministrator().createEPL(stmtText);

        stmtText = "select * from ResultStream";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 7));
        assertFalse("E1", listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 8));
        assertEquals("E2", listener.assertOneGetNewAndReset().get("string"));

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 5));
        assertFalse("E3", listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 6));
        assertEquals("E4", listener.assertOneGetNewAndReset().get("string"));
    }
}
