package com.espertech.esper.regression.epl;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.client.EventBean;

public class TestPatternInsertInto extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testPropsWildcard()
    {
        String stmtText =
                "insert into MyThirdStream(es0id, es1id) " +
                "select es0.id, es1.id " +
                "from " +
                "pattern [every (es0=" + SupportBean_S0.class.getName() +
                             " or es1=" + SupportBean_S1.class.getName() + ")]";
        epService.getEPAdministrator().createEPL(stmtText);

        String stmtTwoText =
                "select * from MyThirdStream";
        EPStatement statement = epService.getEPAdministrator().createEPL(stmtTwoText);

        updateListener = new SupportUpdateListener();
        statement.addListener(updateListener);

        sendEventsAndAssert();
    }

    public void testProps()
    {
        String stmtText =
                "insert into MySecondStream(s0, s1) " +
                "select es0, es1 " +
                "from " +
                "pattern [every (es0=" + SupportBean_S0.class.getName() +
                             " or es1=" + SupportBean_S1.class.getName() + ")]";
        epService.getEPAdministrator().createEPL(stmtText);

        String stmtTwoText =
                "select s0.id as es0id, s1.id as es1id from MySecondStream";
        EPStatement statement = epService.getEPAdministrator().createEPL(stmtTwoText);

        updateListener = new SupportUpdateListener();
        statement.addListener(updateListener);

        sendEventsAndAssert();
    }

    public void testNoProps()
    {
        String stmtText =
                "insert into MyStream " +
                "select es0, es1 " +
                "from " +
                "pattern [every (es0=" + SupportBean_S0.class.getName() +
                             " or es1=" + SupportBean_S1.class.getName() + ")]";
        epService.getEPAdministrator().createEPL(stmtText);

        String stmtTwoText =
                "select es0.id as es0id, es1.id as es1id from MyStream.win:length(10)";
        EPStatement statement = epService.getEPAdministrator().createEPL(stmtTwoText);

        updateListener = new SupportUpdateListener();
        statement.addListener(updateListener);

        sendEventsAndAssert();
    }

    private void sendEventsAndAssert()
    {
        sendEventS1(10, "");
        EventBean event = updateListener.assertOneGetNewAndReset();
        assertNull(event.get("es0id"));
        assertEquals(10, event.get("es1id"));

        sendEventS0(20, "");
        event = updateListener.assertOneGetNewAndReset();
        assertEquals(20, event.get("es0id"));
        assertNull(event.get("es1id"));
    }

    private SupportBean_S0 sendEventS0(int id, String p00)
    {
        SupportBean_S0 event = new SupportBean_S0(id, p00);
        epService.getEPRuntime().sendEvent(event);
        return event;
    }

    private SupportBean_S1 sendEventS1(int id, String p10)
    {
        SupportBean_S1 event = new SupportBean_S1(id, p10);
        epService.getEPRuntime().sendEvent(event);
        return event;
    }
}
