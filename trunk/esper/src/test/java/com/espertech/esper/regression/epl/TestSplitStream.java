package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestSplitStream extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportUpdateListener[] listeners;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
        listeners = new SupportUpdateListener[10];
        for (int i = 0; i < listeners.length; i++)
        {
            listeners[i] = new SupportUpdateListener();
        }
    }

    // test support match first/each (listener+subscriber receives all/none), test listener/subscriber on statement itself
    // test statement object model
    // test use alias in select clause
    // test support subquery
    // test support variables
    // write docs

    public void testInvalid()
    {
        tryInvalid("on SupportBean select * where intPrimitive=1 insert into BStream select * where 1=2",
                   "Error starting statement: Required insert-into clause is not provided, the clause is required for split-stream syntax [on SupportBean select * where intPrimitive=1 insert into BStream select * where 1=2]");

        tryInvalid("on SupportBean insert into AStream select * where intPrimitive=1 group by string insert into BStream select * where 1=2",
                   "Error starting statement: A group-by clause, having-clause or order-by clause is not allowed for the split stream syntax [on SupportBean insert into AStream select * where intPrimitive=1 group by string insert into BStream select * where 1=2]");

        tryInvalid("on SupportBean insert into AStream select * where intPrimitive=1 insert into BStream select avg(intPrimitive) where 1=2",
                   "Error starting statement: Aggregation functions are not allowed in this context [on SupportBean insert into AStream select * where intPrimitive=1 insert into BStream select avg(intPrimitive) where 1=2]");
    }

    private void tryInvalid(String stmtText, String message)
    {
        try
        {
            epService.getEPAdministrator().createEPL(stmtText);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }

    public void test1SplitDefault()
    {
        // test wildcard
        String stmtOrigText = "on SupportBean insert into AStream select *";
        epService.getEPAdministrator().createEPL(stmtOrigText);

        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select * from AStream");
        stmtOne.addListener(listeners[0]);

        sendSupportBean("E1", 1);
        assertReceivedSingle(0, "E1");

        // test select
        stmtOrigText = "on SupportBean insert into BStream select 3*intPrimitive as value";
        EPStatement stmtOrig = epService.getEPAdministrator().createEPL(stmtOrigText);

        stmtOne = epService.getEPAdministrator().createEPL("select value from BStream");
        stmtOne.addListener(listeners[1]);

        sendSupportBean("E1", 6);
        assertEquals(18, listeners[1].assertOneGetNewAndReset().get("value"));

        // assert type is original type
        assertEquals(SupportBean.class, stmtOrig.getEventType().getUnderlyingType());
        assertFalse(stmtOrig.iterator().hasNext());
    }

    public void test2SplitNoDefaultOutputFirst()
    {
        String stmtOrigText = "on SupportBean insert into AStream select * where intPrimitive=1 insert into BStream select * where intPrimitive=2 or intPrimitive=1";
        EPStatement stmtOrig = epService.getEPAdministrator().createEPL(stmtOrigText);
        stmtOrig.addListener(listener);

        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select * from AStream");
        stmtOne.addListener(listeners[0]);
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL("select * from BStream");
        stmtTwo.addListener(listeners[1]);

        assertNotSame(stmtOne.getEventType(), stmtTwo.getEventType());
        assertSame(stmtOne.getEventType().getUnderlyingType(), stmtTwo.getEventType().getUnderlyingType());

        sendSupportBean("E1", 1);
        assertReceivedSingle(0, "E1");

        sendSupportBean("E2", 2);
        assertReceivedSingle(1, "E2");

        sendSupportBean("E3", 1);
        assertReceivedSingle(0, "E3");

        sendSupportBean("E4", -999);
        assertReceivedSingle(1, "E4");
    }

    public void test2SplitNoDefaultOutputAll()
    {
        String stmtOrigText = "on SupportBean " +
                              "insert into AStream select * where intPrimitive=1 " +
                              "insert into BStream select * where intPrimitive=1 or intPrimitive=2 " +
                              "output all";
        EPStatement stmtOrig = epService.getEPAdministrator().createEPL(stmtOrigText);
        stmtOrig.addListener(listener);

        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select * from AStream");
        stmtOne.addListener(listeners[0]);
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL("select * from BStream");
        stmtTwo.addListener(listeners[1]);

        assertNotSame(stmtOne.getEventType(), stmtTwo.getEventType());
        assertSame(stmtOne.getEventType().getUnderlyingType(), stmtTwo.getEventType().getUnderlyingType());

        sendSupportBean("E1", 1);
        assertReceivedEach(new String[] {"E1", "E1"});

        sendSupportBean("E2", 2);
        assertReceivedEach(new String[] {null, "E2"});

        sendSupportBean("E3", 1);
        assertReceivedEach(new String[] {"E3", "E3"});

        sendSupportBean("E4", -999);
        assertReceivedEach(new String[] {null, null});

        stmtOrig.destroy();
        stmtOrigText = "on SupportBean " +
                              "insert into AStream select string || '_1' where intPrimitive in (1, 2) " +
                              "insert into BStream select string || '_2' where intPrimitive in (2, 3) " +
                              "insert into CStream select string || '_3' " +
                              "output all";
        stmtOrig = epService.getEPAdministrator().createEPL(stmtOrigText);
        stmtOrig.addListener(listener);

        EPStatement stmtThree = epService.getEPAdministrator().createEPL("select * from CStream");
        stmtTwo.addListener(listeners[2]);

        sendSupportBean("E1", 2);
        assertReceivedEach(new String[] {"E1_1", "E1_2", "E1_3"});

        sendSupportBean("E2", 2);
        assertReceivedEach(new String[] {null, "E2"});

        sendSupportBean("E3", 1);
        assertReceivedEach(new String[] {"E3", "E3"});

        sendSupportBean("E4", -999);
        assertReceivedEach(new String[] {null, null});
    }

    public void test3And4SplitDefaultOutputFirst()
    {
        String stmtOrigText = "on SupportBean " +
                              "insert into AStream select string||'_1' as string where intPrimitive=1 " +
                              "insert into BStream select string||'_2' as string where intPrimitive=2 " +
                              "insert into CStream select string||'_3' as string";
        EPStatement stmtOrig = epService.getEPAdministrator().createEPL(stmtOrigText);
        stmtOrig.addListener(listener);

        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select * from AStream");
        stmtOne.addListener(listeners[0]);
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL("select * from BStream");
        stmtTwo.addListener(listeners[1]);
        EPStatement stmtThree = epService.getEPAdministrator().createEPL("select * from CStream");
        stmtThree.addListener(listeners[2]);

        assertNotSame(stmtOne.getEventType(), stmtTwo.getEventType());
        assertSame(stmtOne.getEventType().getUnderlyingType(), stmtTwo.getEventType().getUnderlyingType());

        sendSupportBean("E1", 1);
        assertReceivedSingle(0, "E1_1");

        sendSupportBean("E2", 2);
        assertReceivedSingle(1, "E2_2");

        sendSupportBean("E3", 1);
        assertReceivedSingle(0, "E3_1");

        sendSupportBean("E4", -999);
        assertReceivedSingle(2, "E4_3");

        stmtOrigText = "on SupportBean " +
                              "insert into AStream select string||'_1' as string where intPrimitive=10 " +
                              "insert into BStream select string||'_2' as string where intPrimitive=20 " +
                              "insert into CStream select string||'_3' as string where intPrimitive<0 " +
                              "insert into DStream select string||'_4' as string";
        stmtOrig.destroy();
        stmtOrig = epService.getEPAdministrator().createEPL(stmtOrigText);
        stmtOrig.addListener(listener);

        EPStatement stmtFour = epService.getEPAdministrator().createEPL("select * from DStream");
        stmtFour.addListener(listeners[3]);

        sendSupportBean("E5", -999);
        assertReceivedSingle(2, "E5_3");

        sendSupportBean("E6", 9999);
        assertReceivedSingle(3, "E6_4");

        sendSupportBean("E7", 20);
        assertReceivedSingle(1, "E7_2");

        sendSupportBean("E8", 10);
        assertReceivedSingle(0, "E8_1");
    }

    private void assertReceivedEach(String[] stringValue)
    {
        for (int i = 0; i < stringValue.length; i++)
        {
            if (stringValue[i] != null)
            {
                assertEquals(stringValue[i], listeners[i].assertOneGetNewAndReset().get("string"));
            }
            else
            {
                assertFalse(listeners[i].isInvoked());
            }
        }
    }

    private void assertReceivedSingle(int index, String stringValue)
    {
        for (int i = 0; i < listeners.length; i++)
        {
            if (i == index)
            {
                continue;
            }
            assertFalse(listeners[i].isInvoked());
        }
        assertEquals(stringValue, listeners[index].assertOneGetNewAndReset().get("string"));
    }

    private SupportBean sendSupportBean(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }
}
