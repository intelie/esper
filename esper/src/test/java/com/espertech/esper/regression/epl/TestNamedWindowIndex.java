package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestNamedWindowIndex extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }
    
    public void testWidening()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);

        // widen to long
        String stmtTextCreate = "create window MyWindowOne.win:keepall() as (f1 long, f2 string)";
        epService.getEPAdministrator().createEPL(stmtTextCreate);
        epService.getEPAdministrator().createEPL("insert into MyWindowOne(f1, f2) select longPrimitive, string from SupportBean");
        epService.getEPAdministrator().createEPL("create index MyWindowOneIndex1 on MyWindowOne(f1)");
        String fields[] = "f1,f2".split(",");

        sendEventLong("E1", 10L);

        EPOnDemandQueryResult result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f1=10");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{10L, "E1"}});

        // coerce to short
        stmtTextCreate = "create window MyWindowTwo.win:keepall() as (f1 short, f2 string)";
        epService.getEPAdministrator().createEPL(stmtTextCreate);
        epService.getEPAdministrator().createEPL("insert into MyWindowTwo(f1, f2) select shortPrimitive, string from SupportBean");
        epService.getEPAdministrator().createEPL("create index MyWindowTwoIndex1 on MyWindowTwo(f1)");

        sendEventShort("E1", (short) 2);

        result = epService.getEPRuntime().executeQuery("select * from MyWindowTwo where f1=2");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{(short)2, "E1"}});
    }

    public void testFAFPerformance()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);

        // create window one
        String stmtTextCreateOne = "create window MyWindowOne.win:keepall() as (f1 string, f2 int)";
        epService.getEPAdministrator().createEPL(stmtTextCreateOne);
        epService.getEPAdministrator().createEPL("insert into MyWindowOne(f1, f2) select string, intPrimitive from SupportBean");
        epService.getEPAdministrator().createEPL("create index MyWindowOneIndex on MyWindowOne(f1)");

        // insert X rows
        int maxRows = 100;   //for performance testing change to int maxRows = 100000;
        for (int i=0; i < maxRows; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("K" + i, i));
        }

        // fire N queries each returning 1 row
        long start = System.currentTimeMillis();
        String queryText = "select * from MyWindowOne where f1='K10'";
        EPOnDemandPreparedQuery query = epService.getEPRuntime().prepareQuery(queryText);
        int loops = 10000;  

        for (int i = 0; i < loops; i++) {
            EPOnDemandQueryResult result = query.execute();
            assertEquals(1, result.getArray().length);
            assertEquals("K10", result.getArray()[0].get("f1"));
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("delta=" + delta, delta < 200);
        
        // test no value returned
        queryText = "select * from MyWindowOne where f1='KX'";
        query = epService.getEPRuntime().prepareQuery(queryText);
        EPOnDemandQueryResult result = query.execute();
        assertEquals(0, result.getArray().length);

        // test query null
        queryText = "select * from MyWindowOne where f1=null";
        query = epService.getEPRuntime().prepareQuery(queryText);
        result = query.execute();
        assertEquals(0, result.getArray().length);
        
        // insert null and test null
        epService.getEPRuntime().sendEvent(new SupportBean(null, -2));
        result = query.execute();
        assertEquals(1, result.getArray().length);
        assertEquals(-2, result.getArray()[0].get("f2"));

        // test two values
        epService.getEPRuntime().sendEvent(new SupportBean(null, -1));
        query = epService.getEPRuntime().prepareQuery("select * from MyWindowOne where f1=null order by f2 asc");
        result = query.execute();
        assertEquals(2, result.getArray().length);
        assertEquals(-2, result.getArray()[0].get("f2"));
        assertEquals(-1, result.getArray()[1].get("f2"));
    }

    public void testCompositeIndex()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);

        String stmtTextCreate = "create window MyWindowOne.win:keepall() as (f1 string, f2 int, f3 string, f4 string)";
        epService.getEPAdministrator().createEPL(stmtTextCreate);
        epService.getEPAdministrator().createEPL("insert into MyWindowOne(f1, f2, f3, f4) select string, intPrimitive, '>'||string||'<', '?'||string||'?' from SupportBean");
        EPStatement indexOne = epService.getEPAdministrator().createEPL("create index MyWindowOneIndex on MyWindowOne(f2, f3, f1)");
        String fields[] = "f1,f2,f3,f4".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", -2));

        EPOnDemandQueryResult result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f3='>E1<'");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2, ">E1<", "?E1?"}});

        result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f3='>E1<' and f2=-2");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2, ">E1<", "?E1?"}});

        result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f3='>E1<' and f2=-2 and f1='E1'");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2, ">E1<", "?E1?"}});

        indexOne.destroy();

        // test SODA
        String create = "create index MyWindowOneIndex on MyWindowOne(f2, f3, f1)";
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(create);
        assertEquals(create, model.toEPL());
        
        EPStatement stmt = epService.getEPAdministrator().create(model);
        assertEquals(create, stmt.getText());
    }

    public void testLateCreate()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);

        String stmtTextCreateOne = "create window MyWindowOne.win:keepall() as (f1 string, f2 int, f3 string, f4 string)";
        epService.getEPAdministrator().createEPL(stmtTextCreateOne);
        epService.getEPAdministrator().createEPL("insert into MyWindowOne(f1, f2, f3, f4) select string, intPrimitive, '>'||string||'<', '?'||string||'?' from SupportBean");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", -4));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", -2));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", -3));

        epService.getEPAdministrator().createEPL("create index MyWindowOneIndex on MyWindowOne(f2, f3, f1)");
        String fields[] = "f1,f2,f3,f4".split(",");

        EPOnDemandQueryResult result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f3='>E1<' order by f2 asc");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {
                {"E1", -4, ">E1<", "?E1?"}, {"E1", -3, ">E1<", "?E1?"}, {"E1", -2, ">E1<", "?E1?"}});
    }

    public void testMultipleColumnMultipleIndex()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);

        String stmtTextCreateOne = "create window MyWindowOne.win:keepall() as (f1 string, f2 int, f3 string, f4 string)";
        epService.getEPAdministrator().createEPL(stmtTextCreateOne);
        epService.getEPAdministrator().createEPL("insert into MyWindowOne(f1, f2, f3, f4) select string, intPrimitive, '>'||string||'<', '?'||string||'?' from SupportBean");
        epService.getEPAdministrator().createEPL("create index MyWindowOneIndex1 on MyWindowOne(f2, f3, f1)");
        epService.getEPAdministrator().createEPL("create index MyWindowOneIndex2 on MyWindowOne(f2, f3)");
        epService.getEPAdministrator().createEPL("create index MyWindowOneIndex3 on MyWindowOne(f2)");
        String fields[] = "f1,f2,f3,f4".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", -2));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", -4));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", -3));

        EPOnDemandQueryResult result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f3='>E1<'");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2, ">E1<", "?E1?"}});

        result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f3='>E1<' and f2=-2");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2, ">E1<", "?E1?"}});

        result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f3='>E1<' and f2=-2 and f1='E1'");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2, ">E1<", "?E1?"}});

        result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f2=-2");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2, ">E1<", "?E1?"}});

        result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f1='E1'");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2, ">E1<", "?E1?"}});

        result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f3='>E1<' and f2=-2 and f1='E1' and f4='?E1?'");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2, ">E1<", "?E1?"}});
    }

    public void testDropCreate()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);

        String stmtTextCreateOne = "create window MyWindowOne.win:keepall() as (f1 string, f2 int, f3 string, f4 string)";
        epService.getEPAdministrator().createEPL(stmtTextCreateOne);
        epService.getEPAdministrator().createEPL("insert into MyWindowOne(f1, f2, f3, f4) select string, intPrimitive, '>'||string||'<', '?'||string||'?' from SupportBean");
        EPStatement indexOne = epService.getEPAdministrator().createEPL("create index MyWindowOneIndex1 on MyWindowOne(f1)");
        EPStatement indexTwo = epService.getEPAdministrator().createEPL("create index MyWindowOneIndex2 on MyWindowOne(f4)");
        String fields[] = "f1,f2".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", -2));

        indexOne.destroy();

        EPOnDemandQueryResult result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f1='E1'");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2}});

        result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f4='?E1?'");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2}});

        indexTwo.destroy();

        result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f1='E1'");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2}});

        result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f4='?E1?'");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2}});

        indexTwo = epService.getEPAdministrator().createEPL("create index MyWindowOneIndex2 on MyWindowOne(f4)");

        result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f1='E1'");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2}});

        result = epService.getEPRuntime().executeQuery("select * from MyWindowOne where f4='?E1?'");
        ArrayAssertionUtil.assertPropsPerRow(result.getArray(), fields, new Object[][] {{"E1", -2}});

        indexTwo.destroy();
    }

    public void testOnSelectReUse()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_S0", SupportBean_S0.class);

        String stmtTextCreateOne = "create window MyWindowOne.win:keepall() as (f1 string, f2 int)";
        epService.getEPAdministrator().createEPL(stmtTextCreateOne);
        epService.getEPAdministrator().createEPL("insert into MyWindowOne(f1, f2) select string, intPrimitive from SupportBean");
        EPStatement indexOne = epService.getEPAdministrator().createEPL("create index MyWindowOneIndex1 on MyWindowOne(f2)");
        String fields[] = "f1,f2".split(",");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));

        EPStatement stmtOnSelect = epService.getEPAdministrator().createEPL("on SupportBean_S0 s0 select nw.f1 as f1, nw.f2 as f2 from MyWindowOne nw where nw.f2 = s0.id");
        stmtOnSelect.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});

        indexOne.destroy();
        
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1});

        stmtOnSelect.destroy();
    }

    public void testInvalid() {
        epService.getEPAdministrator().createEPL("create window MyWindowOne.win:keepall() as (f1 string, f2 int)");
        epService.getEPAdministrator().createEPL("create index MyWindowOneIndex on MyWindowOne(f1)");

        tryInvalid("create index MyWindowOneIndex on MyWindowOne(f1)",
                   "Error starting statement: Index by name 'MyWindowOneIndex' already exists [create index MyWindowOneIndex on MyWindowOne(f1)]");

        tryInvalid("create index IndexTwo on MyWindowOne(fx)",
                   "Error starting statement: Property named 'fx' not found on named window 'MyWindowOne' [create index IndexTwo on MyWindowOne(fx)]");

        tryInvalid("create index IndexTwo on MyWindowOne(f1, f1)",
                   "Error starting statement: Property named 'f1' has been declared more then once [create index IndexTwo on MyWindowOne(f1, f1)]");

        tryInvalid("create index IndexTwo on MyWindowX(f1, f1)",
                   "Error starting statement: A named window by name 'MyWindowX' does not exist [create index IndexTwo on MyWindowX(f1, f1)]");
    }

    private void tryInvalid(String epl, String message) {
        try {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

    private void sendEventLong(String string, long longPrimitive) {
        SupportBean event = new SupportBean();
        event.setString(string);
        event.setLongPrimitive(longPrimitive);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendEventShort(String string, short shortPrimitive) {
        SupportBean event = new SupportBean();
        event.setString(string);
        event.setShortPrimitive(shortPrimitive);
        epService.getEPRuntime().sendEvent(event);
    }
}
