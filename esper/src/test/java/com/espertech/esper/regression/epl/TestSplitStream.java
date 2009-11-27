package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_S0;
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
        config.addEventType("S0", SupportBean_S0.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
        listeners = new SupportUpdateListener[10];
        for (int i = 0; i < listeners.length; i++)
        {
            listeners[i] = new SupportUpdateListener();
        }
    }

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
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtOrigText);
        stmt.addListener(listener);

        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select * from AStream");
        stmtOne.addListener(listeners[0]);

        sendSupportBean("E1", 1);
        assertReceivedSingle(0, "E1");
        assertFalse(listener.isInvoked());

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
        String stmtOrigText = "on SupportBean " +
                    "insert into AStream select *  where (intPrimitive = 1) " +
                    "insert into BStream select *  where ((intPrimitive = 1)) or ((intPrimitive = 2))";
        EPStatement stmtOrig = epService.getEPAdministrator().createEPL(stmtOrigText);
        runAssertion(stmtOrig);

        // statement object model
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setFromClause(FromClause.create(FilterStream.create("SupportBean")));
        model.setInsertInto(InsertIntoClause.create("AStream"));
        model.setSelectClause(SelectClause.createWildcard());
        model.setWhereClause(Expressions.eq("intPrimitive", 1));
        OnInsertSplitStreamClause clause = OnClause.createOnInsertSplitStream();
        model.setOnExpr(clause);
        OnInsertSplitStreamItem item = OnInsertSplitStreamItem.create(
                InsertIntoClause.create("BStream"),
                SelectClause.createWildcard(),
                Expressions.or(Expressions.eq("intPrimitive", 1), Expressions.eq("intPrimitive", 2)));
        clause.addItem(item);
        assertEquals(stmtOrigText, model.toEPL());
        stmtOrig = epService.getEPAdministrator().create(model);
        runAssertion(stmtOrig);

        EPStatementObjectModel newModel = epService.getEPAdministrator().compileEPL(stmtOrigText);
        stmtOrig = epService.getEPAdministrator().create(newModel);
        assertEquals(stmtOrigText, newModel.toEPL());
        runAssertion(stmtOrig);
    }

    public void testSubquery()
    {
        String stmtOrigText = "on SupportBean " +
                              "insert into AStream select (select p00 from S0.std:lastevent()) as string where intPrimitive=(select id from S0.std:lastevent()) " +
                              "insert into BStream select (select p01 from S0.std:lastevent()) as string where intPrimitive<>(select id from S0.std:lastevent())";
        EPStatement stmtOrig = epService.getEPAdministrator().createEPL(stmtOrigText);
        stmtOrig.addListener(listener);

        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select * from AStream");
        stmtOne.addListener(listeners[0]);
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL("select * from BStream");
        stmtTwo.addListener(listeners[1]);
        
        sendSupportBean("E1", 1);
        assertFalse(listeners[0].getAndClearIsInvoked());
        assertNull(listeners[1].assertOneGetNewAndReset().get("string"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(10, "x", "y"));

        sendSupportBean("E2", 10);
        assertEquals("x", listeners[0].assertOneGetNewAndReset().get("string"));
        assertFalse(listeners[1].getAndClearIsInvoked());

        sendSupportBean("E3", 9);
        assertFalse(listeners[0].getAndClearIsInvoked());
        assertEquals("y", listeners[1].assertOneGetNewAndReset().get("string"));
    }

    public void test2SplitNoDefaultOutputAll()
    {
        String stmtOrigText = "on SupportBean " +
                              "insert into AStream select string where intPrimitive=1 " +
                              "insert into BStream select string where intPrimitive=1 or intPrimitive=2 " +
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
        assertFalse(listener.isInvoked());

        sendSupportBean("E2", 2);
        assertReceivedEach(new String[] {null, "E2"});
        assertFalse(listener.isInvoked());

        sendSupportBean("E3", 1);
        assertReceivedEach(new String[] {"E3", "E3"});
        assertFalse(listener.isInvoked());

        sendSupportBean("E4", -999);
        assertReceivedEach(new String[] {null, null});
        assertEquals("E4", listener.assertOneGetNewAndReset().get("string"));

        stmtOrig.destroy();
        stmtOrigText = "on SupportBean " +
                              "insert into AStream select string || '_1' as string where intPrimitive in (1, 2) " +
                              "insert into BStream select string || '_2' as string where intPrimitive in (2, 3) " +
                              "insert into CStream select string || '_3' as string " +
                              "output all";
        stmtOrig = epService.getEPAdministrator().createEPL(stmtOrigText);
        stmtOrig.addListener(listener);

        EPStatement stmtThree = epService.getEPAdministrator().createEPL("select * from CStream");
        stmtThree.addListener(listeners[2]);

        sendSupportBean("E1", 2);
        assertReceivedEach(new String[] {"E1_1", "E1_2", "E1_3"});
        assertFalse(listener.isInvoked());

        sendSupportBean("E2", 1);
        assertReceivedEach(new String[] {"E2_1", null, "E2_3"});
        assertFalse(listener.isInvoked());

        sendSupportBean("E3", 3);
        assertReceivedEach(new String[] {null, "E3_2", "E3_3"});
        assertFalse(listener.isInvoked());

        sendSupportBean("E4", -999);
        assertReceivedEach(new String[] {null, null, "E4_3"});
        assertFalse(listener.isInvoked());
    }

    public void test3And4SplitDefaultOutputFirst()
    {
        String stmtOrigText = "on SupportBean as mystream " +
                              "insert into AStream select mystream.string||'_1' as string where intPrimitive=1 " +
                              "insert into BStream select mystream.string||'_2' as string where intPrimitive=2 " +
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
        assertFalse(listener.isInvoked());

        sendSupportBean("E2", 2);
        assertReceivedSingle(1, "E2_2");
        assertFalse(listener.isInvoked());

        sendSupportBean("E3", 1);
        assertReceivedSingle(0, "E3_1");
        assertFalse(listener.isInvoked());

        sendSupportBean("E4", -999);
        assertReceivedSingle(2, "E4_3");
        assertFalse(listener.isInvoked());

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
        assertFalse(listener.isInvoked());

        sendSupportBean("E6", 9999);
        assertReceivedSingle(3, "E6_4");
        assertFalse(listener.isInvoked());

        sendSupportBean("E7", 20);
        assertReceivedSingle(1, "E7_2");
        assertFalse(listener.isInvoked());

        sendSupportBean("E8", 10);
        assertReceivedSingle(0, "E8_1");
        assertFalse(listener.isInvoked());
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

    private void assertReceivedNone()
    {
        for (int i = 0; i < listeners.length; i++)
        {
            assertFalse(listeners[i].isInvoked());
        }
    }

    private SupportBean sendSupportBean(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private void runAssertion(EPStatement stmtOrig)
    {
        stmtOrig.addListener(listener);

        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select * from AStream");
        stmtOne.addListener(listeners[0]);
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL("select * from BStream");
        stmtTwo.addListener(listeners[1]);

        assertNotSame(stmtOne.getEventType(), stmtTwo.getEventType());
        assertSame(stmtOne.getEventType().getUnderlyingType(), stmtTwo.getEventType().getUnderlyingType());

        sendSupportBean("E1", 1);
        assertReceivedSingle(0, "E1");
        assertFalse(listener.isInvoked());

        sendSupportBean("E2", 2);
        assertReceivedSingle(1, "E2");
        assertFalse(listener.isInvoked());

        sendSupportBean("E3", 1);
        assertReceivedSingle(0, "E3");
        assertFalse(listener.isInvoked());

        sendSupportBean("E4", -999);
        assertReceivedNone();
        assertEquals("E4", listener.assertOneGetNewAndReset().get("string"));

        stmtOrig.destroy();
        stmtOne.destroy();
        stmtTwo.destroy();
    }
}
