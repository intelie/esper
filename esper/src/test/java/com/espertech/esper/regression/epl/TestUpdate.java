package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class TestUpdate extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;


    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testInvalid()
    {
        Map<String, Object> type = new HashMap<String, Object>();
        type.put("p0", long.class);
        type.put("p1", long.class);
        type.put("p2", long.class);
        epService.getEPAdministrator().getConfiguration().addEventType("MyMapType", type);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        epService.getEPAdministrator().createEPL("insert into SupportBeanStream select * from SupportBean");
        epService.getEPAdministrator().createEPL("insert into SupportBeanStreamTwo select * from pattern[a=SupportBean -> b=SupportBean]");

        tryInvalid("update SupportBeanStream set intPrimitive=longPrimitive",
                   "Error starting statement: Invalid assignment of column 'longPrimitive' of type 'long' to event property 'intPrimitive' typed as 'int', column and parameter types mismatch [update SupportBeanStream set intPrimitive=longPrimitive]");
        tryInvalid("update SupportBeanStream set xxx='abc'",
                   "Error starting statement: Property 'xxx' is not available for write access [update SupportBeanStream set xxx='abc']");
        tryInvalid("update SupportBeanStream set intPrimitive=null",
                   "Error starting statement: Invalid assignment of column 'null' of null type to event property 'intPrimitive' typed as 'int', nullable type mismatch [update SupportBeanStream set intPrimitive=null]");
        tryInvalid("update SupportBeanStreamTwo set a.intPrimitive=10",
                   "Incorrect syntax near '.' expecting an equals '=' but found a dot '.' at line 1 column 33 [update SupportBeanStreamTwo set a.intPrimitive=10]");
        /*
        tryInvalid("update SupportBeanStream set intPrimitive=null",
                   "");
        tryInvalid("update SupportBeanStream set intPrimitive=null",
                   "");
        tryInvalid("update SupportBeanStream set intPrimitive=null",
                   "");
        tryInvalid("update SupportBeanStream set intPrimitive=null",
                   "");
                   */
    }

    public void testBeanAndWhereClause() throws Exception
    {
        SupportUpdateListener listenerInsert = new SupportUpdateListener();
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("insert into MyStream select * from SupportBean");
        stmtInsert.addListener(listenerInsert);

        SupportUpdateListener listenerUpdate = new SupportUpdateListener();
        EPStatement stmtUpdOne = epService.getEPAdministrator().createEPL("update MyStream set intPrimitive=10, string='O_' || string where intPrimitive=1");
        stmtUpdOne.addListener(listenerUpdate);

        EPStatement stmtSelect = epService.getEPAdministrator().createEPL("select * from MyStream");
        stmtSelect.addListener(listener);

        String[] fields = "string,intPrimitive".split(",");
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 9));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 9});
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), fields, new Object[] {"E1", 9});
        assertFalse(listenerUpdate.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"O_E2", 10});
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), fields, new Object[] {"E2", 1});
        assertFalse(listenerUpdate.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 2});
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), fields, new Object[] {"E3", 2});
        assertFalse(listenerUpdate.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E4", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"O_E4", 10});
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), fields, new Object[] {"E4", 1});
        assertFalse(listenerUpdate.isInvoked());

        EPStatement stmtUpdTwo = epService.getEPAdministrator().createEPL("update MyStream set intPrimitive=intPrimitive + 1000 where intPrimitive=2");
        stmtUpdTwo.addListener(listenerUpdate);

        epService.getEPRuntime().sendEvent(new SupportBean("E5", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E5", 1002});
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), fields, new Object[] {"E5", 2});
        assertFalse(listenerUpdate.isInvoked());

        stmtUpdOne.destroy();

        epService.getEPRuntime().sendEvent(new SupportBean("E6", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E6", 1});
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), fields, new Object[] {"E6", 1});
        assertFalse(listenerUpdate.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E7", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E7", 1002});
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), fields, new Object[] {"E7", 2});
        assertFalse(listenerUpdate.isInvoked());
        assertFalse(stmtUpdTwo.iterator().hasNext());

        stmtUpdTwo.destroy();

        epService.getEPRuntime().sendEvent(new SupportBean("E8", 2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E8", 2});
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), fields, new Object[] {"E8", 2});
        assertFalse(listenerUpdate.isInvoked());
    }

    public void testMapNoWhereClause() throws Exception
    {
        Map<String, Object> type = new HashMap<String, Object>();
        type.put("p0", long.class);
        type.put("p1", long.class);
        type.put("p2", long.class);
        epService.getEPAdministrator().getConfiguration().addEventType("MyMapType", type);

        SupportUpdateListener listenerInsert = new SupportUpdateListener();
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("insert into MyStream select * from MyMapType");
        stmtInsert.addListener(listenerInsert);

        EPStatement stmtUpd = epService.getEPAdministrator().createEPL("update MyStream set p0=p1, p1=p0");

        EPStatement stmtSelect = epService.getEPAdministrator().createEPL("select * from MyStream");
        stmtSelect.addListener(listener);

        String[] fields = "p0,p1,p2".split(",");
        epService.getEPRuntime().sendEvent(makeMap("p0", 10, "p1", 1, "p2", 100), "MyMapType");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {1, 10, 100});
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), fields, new Object[] {10, 1, 100});

        stmtUpd.stop();
        stmtUpd.start();
        
        epService.getEPRuntime().sendEvent(makeMap("p0", 5, "p1", 4, "p2", 101), "MyMapType");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {4, 5, 101});
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), fields, new Object[] {5, 4, 101});

        stmtUpd.destroy();

        epService.getEPRuntime().sendEvent(makeMap("p0", 20, "p1", 0, "p2", 102), "MyMapType");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {20, 0, 102});
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), fields, new Object[] {20, 0, 102});
    }
    
    public void testFieldsWithPriority() throws Exception
    {
        epService.getEPAdministrator().createEPL("insert into MyStream select string, intPrimitive from SupportBean(string not like 'Z%')");
        epService.getEPAdministrator().createEPL("insert into MyStream select 'AX'||string as string, intPrimitive from SupportBean(string like 'Z%')");
        epService.getEPAdministrator().createEPL("@Name('a') @Priority(12) update MyStream set intPrimitive=-2 where intPrimitive=-1");
        epService.getEPAdministrator().createEPL("@Name('b') @Priority(11) update MyStream set intPrimitive=-1 where string like 'D%'");
        epService.getEPAdministrator().createEPL("@Name('c') @Priority(9) update MyStream set intPrimitive=9 where string like 'A%'");
        epService.getEPAdministrator().createEPL("@Name('d') @Priority(8) update MyStream set intPrimitive=8 where string like 'A%' or string like 'C%'");
        epService.getEPAdministrator().createEPL("@Name('e') @Priority(10) update MyStream set intPrimitive=10 where string like 'A%'");
        epService.getEPAdministrator().createEPL("@Name('f') @Priority(7) update MyStream set intPrimitive=7 where string like 'A%' or string like 'C%'");
        epService.getEPAdministrator().createEPL("@Name('g') @Priority(6) update MyStream set intPrimitive=6 where string like 'A%'");
        epService.getEPAdministrator().createEPL("@Name('h') @Drop update MyStream set intPrimitive=6 where string like 'B%'");

        EPStatement stmtSelect = epService.getEPAdministrator().createEPL("select * from MyStream where intPrimitive > 0");
        stmtSelect.addListener(listener);

        String[] fields = "string,intPrimitive".split(",");
        epService.getEPRuntime().sendEvent(new SupportBean("A1", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"A1", 10});

        epService.getEPRuntime().sendEvent(new SupportBean("B1", 0));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("C1", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"C1", 8});

        epService.getEPRuntime().sendEvent(new SupportBean("D1", 100));
        assertFalse(listener.isInvoked());

        stmtSelect.stop();
        stmtSelect = epService.getEPAdministrator().createEPL("select * from MyStream");
        stmtSelect.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("D1", -2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"D1", -2});

        epService.getEPRuntime().sendEvent(new SupportBean("Z1", -3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"AXZ1", 10});

        epService.getEPAdministrator().getStatement("e").stop();
        epService.getEPRuntime().sendEvent(new SupportBean("Z2", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"AXZ2", 9});

        epService.getEPAdministrator().getStatement("c").stop();
        epService.getEPAdministrator().getStatement("d").stop();
        epService.getEPAdministrator().getStatement("f").stop();
        epService.getEPAdministrator().getStatement("g").stop();
        epService.getEPRuntime().sendEvent(new SupportBean("Z3", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"AXZ3", 0});
    }

    public void testInsertIntoBeanTypeInheritance() throws Exception
    {
        Map<String, Object> type = new HashMap<String, Object>();
        type.put("p0", String.class);
        type.put("p1", String.class);
        epService.getEPAdministrator().getConfiguration().addEventType("MyMapType", type);
        epService.getEPAdministrator().getConfiguration().addEventType("BaseInterface", BaseInterface.class);
        epService.getEPAdministrator().getConfiguration().addEventType("BaseOne", BaseOne.class);
        epService.getEPAdministrator().getConfiguration().addEventType("BaseOneA", BaseOneA.class);
        epService.getEPAdministrator().getConfiguration().addEventType("BaseOneB", BaseOneB.class);
        epService.getEPAdministrator().getConfiguration().addEventType("BaseTwo", BaseTwo.class);

        // test update applies to child types via interface
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("insert into BaseOne select p0 as i, p1 as p from MyMapType");
        epService.getEPAdministrator().createEPL("@Name('a') update BaseInterface set i='XYZ' where i like 'E%'");
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL("select * from BaseOne");
        stmtSelect.addListener(listener);

        String[] fields = "i,p".split(",");
        epService.getEPRuntime().sendEvent(makeMap("p0", "E1", "p1", "E1"), "MyMapType");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"XYZ", "E1"});

        epService.getEPRuntime().sendEvent(makeMap("p0", "F1", "p1", "E2"), "MyMapType");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"F1", "E2"});

        epService.getEPAdministrator().createEPL("@Priority(2) @Name('b') update BaseOne set i='BLANK'");

        epService.getEPRuntime().sendEvent(makeMap("p0", "somevalue", "p1", "E3"), "MyMapType");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"BLANK", "E3"});

        epService.getEPAdministrator().createEPL("@Priority(3) @Name('b') update BaseOneA set i='FINAL'");

        epService.getEPRuntime().sendEvent(makeMap("p0", "somevalue", "p1", "E4"), "MyMapType");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"BLANK", "E4"});

        stmtInsert.stop();
        stmtInsert = epService.getEPAdministrator().createEPL("insert into BaseOneA select p0 as i, p1 as p, 'a' as pa from MyMapType");

        epService.getEPRuntime().sendEvent(makeMap("p0", "somevalue", "p1", "E5"), "MyMapType");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"FINAL", "E5"});

        stmtInsert.stop();
        stmtInsert = epService.getEPAdministrator().createEPL("insert into BaseOneB select p0 as i, p1 as p, 'b' as pb from MyMapType");

        epService.getEPRuntime().sendEvent(makeMap("p0", "somevalue", "p1", "E6"), "MyMapType");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"BLANK", "E6"});

        stmtInsert.stop();
        stmtInsert = epService.getEPAdministrator().createEPL("insert into BaseTwo select p0 as i, p1 as p from MyMapType");

        stmtSelect.stop();
        stmtSelect = epService.getEPAdministrator().createEPL("select * from BaseInterface");
        stmtSelect.addListener(listener);

        epService.getEPRuntime().sendEvent(makeMap("p0", "E2", "p1", "E7"), "MyMapType");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), new String[]{"i"}, new Object[] {"XYZ"});
    }

    public void testNamedWindow()
    {
        Map<String, Object> type = new HashMap<String, Object>();
        type.put("p0", String.class);
        type.put("p1", String.class);
        epService.getEPAdministrator().getConfiguration().addEventType("MyMapType", type);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);

        String[] fields = "p0,p1".split(",");
        SupportUpdateListener listenerWindow = new SupportUpdateListener();
        SupportUpdateListener listenerInsert = new SupportUpdateListener();
        SupportUpdateListener listenerOnSelect = new SupportUpdateListener();
        SupportUpdateListener listenerInsertOnSelect = new SupportUpdateListener();

        epService.getEPAdministrator().createEPL("create window AWindow.win:keepall() select * from MyMapType").addListener(listenerWindow);
        epService.getEPAdministrator().createEPL("insert into AWindow select * from MyMapType").addListener(listenerInsert);
        epService.getEPAdministrator().createEPL("update AWindow set p1='newvalue'");

        epService.getEPRuntime().sendEvent(makeMap("p0", "E1", "p1", "oldvalue"), "MyMapType");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", "newvalue"});
        ArrayAssertionUtil.assertProps(listenerInsert.assertOneGetNewAndReset(), fields, new Object[] {"E1", "oldvalue"});

        epService.getEPAdministrator().createEPL("on SupportBean(string='A') select win.* from AWindow as win").addListener(listenerOnSelect);
        epService.getEPRuntime().sendEvent(new SupportBean("A", 0));
        ArrayAssertionUtil.assertProps(listenerOnSelect.assertOneGetNewAndReset(), fields, new Object[] {"E1", "newvalue"});

        epService.getEPAdministrator().createEPL("on SupportBean(string='B') insert into MyOtherStream select win.* from AWindow as win").addListener(listenerOnSelect);
        epService.getEPRuntime().sendEvent(new SupportBean("B", 1));
        ArrayAssertionUtil.assertProps(listenerOnSelect.assertOneGetNewAndReset(), fields, new Object[] {"E1", "newvalue"});

        epService.getEPAdministrator().createEPL("update MyOtherStream set p0='a', p1='b'");
        epService.getEPAdministrator().createEPL("select * from MyOtherStream").addListener(listenerInsertOnSelect);
        epService.getEPRuntime().sendEvent(new SupportBean("B", 1));
        ArrayAssertionUtil.assertProps(listenerOnSelect.assertOneGetNewAndReset(), fields, new Object[] {"E1", "newvalue"});
        ArrayAssertionUtil.assertProps(listenerInsertOnSelect.assertOneGetNewAndReset(), fields, new Object[] {"a", "b"});
    }

    public void testTypeWidener()
    {
        String[] fields = "string,longBoxed,intBoxed".split(",");
        epService.getEPAdministrator().createEPL("insert into AStream select * from SupportBean");
        epService.getEPAdministrator().createEPL("update AStream set longBoxed=intBoxed, intBoxed=null");
        epService.getEPAdministrator().createEPL("select * from AStream").addListener(listener);

        SupportBean bean = new SupportBean("E1", 0);
        bean.setLongBoxed(888L);
        bean.setIntBoxed(999);
        epService.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 999L, null});
    }

    private Map<String, Object> makeMap(String prop1, Object val1, String prop2, Object val2, String prop3, Object val3)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(prop1, val1);
        map.put(prop2, val2);
        map.put(prop3, val3);
        return map;
    }

    private Map<String, Object> makeMap(String prop1, Object val1, String prop2, Object val2)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(prop1, val1);
        map.put(prop2, val2);
        return map;
    }

    private void tryInvalid(String expression, String message)
    {
        try
        {
            epService.getEPAdministrator().createEPL(expression);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }

    public static interface BaseInterface
    {
        public String getI();
        public void setI(String i);
    }

    public static class BaseOne implements BaseInterface, Serializable
    {
        private String i;
        private String p;

        public BaseOne()
        {
        }

        public BaseOne(String i, String p)
        {
            this.i = i;
            this.p = p;
        }

        public String getP()
        {
            return p;
        }

        public void setP(String p)
        {
            this.p = p;
        }

        public String getI()
        {
            return i;
        }

        public void setI(String i)
        {
            this.i = i;
        }
    }

    public static class BaseTwo implements BaseInterface, Serializable
    {
        private String i;
        private String p;

        public BaseTwo()
        {
        }

        public BaseTwo(String p)
        {
            this.p = p;
        }

        public void setP(String p)
        {
            this.p = p;
        }

        public String getP()
        {
            return p;
        }

        public String getI()
        {
            return i;
        }

        public void setI(String i)
        {
            this.i = i;
        }
    }

    public static class BaseOneA extends BaseOne
    {
        private String pa;

        public BaseOneA()
        {
        }

        public BaseOneA(String i, String p, String pa)
        {
            super(i, p);
            this.pa = pa;
        }

        public String getPa()
        {
            return pa;
        }

        public void setPa(String pa)
        {
            this.pa = pa;
        }
    }    

    public static class BaseOneB extends BaseOne
    {
        private String pb;

        public BaseOneB()
        {
        }

        public BaseOneB(String i, String p, String pb)
        {
            super(i, p);
            this.pb = pb;
        }

        public String getPb()
        {
            return pb;
        }

        public void setPb(String pb)
        {
            this.pb = pb;
        }
    }
}
