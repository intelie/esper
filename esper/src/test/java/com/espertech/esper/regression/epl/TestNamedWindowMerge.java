package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.regression.view.TestFilterPropertySimple;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.bookexample.OrderBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportSubscriberMRD;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestNamedWindowMerge extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener nwListener;
    private SupportUpdateListener mergeListener;

    protected void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        config.addEventType("SupportBean_A", SupportBean_A.class);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        nwListener = new SupportUpdateListener();
        mergeListener = new SupportUpdateListener();
    }

    public void testDocExample() throws Exception {

        String baseModule = "create schema OrderEvent as (orderId string, productId string, price double, quantity int, deletedFlag boolean)";
        epService.getEPAdministrator().getDeploymentAdmin().parseDeploy(baseModule, null, null, null);

        String appModuleOne = "create schema ProductTotalRec as (productId string, totalPrice double);" +
                "" +
                "@Name('nwProd') create window ProductWindow.std:unique(productId) as ProductTotalRec;" +
                "" +
                "on OrderEvent oe\n" +
                "merge ProductWindow pw\n" +
                "where pw.productId = oe.productId\n" +
                "when matched\n" +
                "then update set totalPrice = totalPrice + oe.price\n" +
                "when not matched\n" +
                "then insert select productId, price as totalPrice;";
        epService.getEPAdministrator().getDeploymentAdmin().parseDeploy(appModuleOne, null, null, null);
        
        String appModuleTwo = "@Name('nwOrd') create window OrderWindow.win:keepall() as OrderEvent;" +
                "" +
                "on OrderEvent oe\n" +
                "  merge OrderWindow pw\n" +
                "  where pw.orderId = oe.orderId\n" +
                "  when not matched \n" +
                "    then insert select *\n" +
                "  when matched and oe.deletedFlag=true\n" +
                "    then delete\n" +
                "  when matched\n" +
                "    then update set pw.quantity = oe.quantity, pw.price = oe.price";

        epService.getEPAdministrator().getDeploymentAdmin().parseDeploy(appModuleTwo, null, null, null);

        sendOrderEvent("O1", "P1", 10, 100, false);
        sendOrderEvent("O1", "P1", 11, 200, false);
        sendOrderEvent("O2", "P2", 3, 300, false);
        ArrayAssertionUtil.assertEqualsAnyOrder(epService.getEPAdministrator().getStatement("nwProd").iterator(), "productId,totalPrice".split(","), new Object[][] {{"P1", 21d}, {"P2", 3d}});
        ArrayAssertionUtil.assertEqualsAnyOrder(epService.getEPAdministrator().getStatement("nwOrd").iterator(), "orderId,quantity".split(","), new Object[][] {{"O1", 200}, {"O2", 300}});
    }

    private void sendOrderEvent(String orderId, String productId, double price, int quantity, boolean deletedFlag) {
        Map<String, Object> event = new HashMap<String, Object>();
        event.put("orderId", orderId);
        event.put("productId", productId);
        event.put("price", price);
        event.put("quantity", quantity);
        event.put("deletedFlag", deletedFlag);
        epService.getEPRuntime().sendEvent(event, "OrderEvent");
    }

    public void testTypeReference() {
        ConfigurationOperations configOps = epService.getEPAdministrator().getConfiguration();

        epService.getEPAdministrator().createEPL("@Name('ces') create schema EventSchema(in1 string, in2 int)");
        epService.getEPAdministrator().createEPL("@Name('cnws') create schema WindowSchema(in1 string, in2 int)");

        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"ces"}, configOps.getEventTypeNameUsedBy("EventSchema").toArray());
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"cnws"}, configOps.getEventTypeNameUsedBy("WindowSchema").toArray());
        
        epService.getEPAdministrator().createEPL("@Name('cnw') create window MyWindow.win:keepall() as WindowSchema");
        ArrayAssertionUtil.assertEqualsAnyOrder("cnws,cnw".split(","), configOps.getEventTypeNameUsedBy("WindowSchema").toArray());
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"cnw"}, configOps.getEventTypeNameUsedBy("MyWindow").toArray());

        epService.getEPAdministrator().createEPL("@Name('om') on EventSchema merge into MyWindow " +
                "when not matched then insert select in1, in2");
        ArrayAssertionUtil.assertEqualsAnyOrder("ces,om".split(","), configOps.getEventTypeNameUsedBy("EventSchema").toArray());
        ArrayAssertionUtil.assertEqualsAnyOrder("cnws,cnw".split(","), configOps.getEventTypeNameUsedBy("WindowSchema").toArray());
    }

    public void testPerformance() {
        EPStatement stmtNamedWindow = epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as (c1 string, c2 int)");

        // preload events
        EPStatement stmt = epService.getEPAdministrator().createEPL("insert into MyWindow select string as c1, intPrimitive as c2 from SupportBean");
        final int totalUpdated = 10000;
        for (int i = 0; i < totalUpdated; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("E" + i, 0));
        }
        stmt.destroy();

        String epl =  "on SupportBean sb " +
                      "merge MyWindow nw " +
                      "where nw.c1 = sb.string " +
                      "when not matched then " +
                      "insert select * " +
                      "when matched then " +
                      "update set nw.c2=sb.intPrimitive";
        stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(mergeListener);
        
        // prime
        for (int i = 0; i < 100; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("E" + i, 1));
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < totalUpdated; i++) {
            epService.getEPRuntime().sendEvent(new SupportBean("E" + i, 1));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        // verify
        Iterator<EventBean> events = stmtNamedWindow.iterator();
        int count = 0;
        for (;events.hasNext();) {
            EventBean next = events.next();
            assertEquals(1, next.get("c2"));
            count++;
        }
        assertEquals(totalUpdated, count);
        assertTrue(delta < 500);
    }

    public void testPropertyEval() {
        epService.getEPAdministrator().getConfiguration().addEventType("OrderBean", OrderBean.class);

        String[] fields = "c1,c2".split(",");
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as (c1 string, c2 string)");

        String epl =  "on OrderBean[books] " +
                      "merge MyWindow mw " +
                      "when not matched then " +
                      "insert select bookId as c1, title as c2 ";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(mergeListener);

        epService.getEPRuntime().sendEvent(TestFilterPropertySimple.makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(mergeListener.getLastNewData(), fields, new Object[][] {{"10020", "Enders Game"},
                {"10021", "Foundation 1"}, {"10022", "Stranger in a Strange Land"}});
    }

    public void testPatternMultimatch() {
        String[] fields = "c1,c2".split(",");
        EPStatement namedWindowStmt = epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as (c1 string, c2 string)");

        String epl =  "on pattern[every a=SupportBean(string like 'A%') -> b=SupportBean(string like 'B%', intPrimitive = a.intPrimitive)] me " +
                      "merge MyWindow mw " +
                      "where me.a.string = mw.c1 and me.b.string = mw.c2 " +
                      "when not matched then " +
                      "insert select me.a.string as c1, me.b.string as c2 ";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(mergeListener);

        epService.getEPRuntime().sendEvent(new SupportBean("A1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("A2", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("B1", 1));
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"A1", "B1"}, {"A2", "B1"}});

        epService.getEPRuntime().sendEvent(new SupportBean("A3", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("A4", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("B2", 2));
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"A1", "B1"}, {"A2", "B1"}, {"A3", "B2"}, {"A4", "B2"}});
    }

    public void testInnerTypeAndVariable() {
        epService.getEPAdministrator().createEPL("create schema MyInnerSchema(in1 string, in2 int)");
        epService.getEPAdministrator().createEPL("create schema MyEventSchema(col1 string, col2 MyInnerSchema)");
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as (c1 string, c2 MyInnerSchema)");
        epService.getEPAdministrator().createEPL("create variable boolean myvar");

        String epl =  "on MyEventSchema me " +
                      "merge MyWindow mw " +
                      "where me.col1 = mw.c1 " +
                      "when not matched and myvar then " +
                      "insert select col1 as c1, col2 as c2 " +
                      "when not matched and myvar = false then " +
                      "insert select 'A' as c1, null as c2 " +
                      "when not matched and myvar = null then " +
                      "insert select 'B' as c1, me.col2 as c2 " +
                      "when matched then " +
                      "delete";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(mergeListener);
        String[] fields = "c1,c2.in1,c2.in2".split(",");

        sendMyInnerSchemaEvent("X1", "Y1", 10);
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNewAndReset(), fields, new Object[] {"B", "Y1", 10});

        sendMyInnerSchemaEvent("B", "0", 0);    // delete
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOldAndReset(), fields, new Object[] {"B", "Y1", 10});

        epService.getEPRuntime().setVariableValue("myvar", true);
        sendMyInnerSchemaEvent("X2", "Y2", 11);
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNewAndReset(), fields, new Object[] {"X2", "Y2", 11});

        epService.getEPRuntime().setVariableValue("myvar", false);
        sendMyInnerSchemaEvent("X3", "Y3", 12);
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNewAndReset(), fields, new Object[] {"A", null, null});

        stmt.destroy();
        stmt = epService.getEPAdministrator().createEPL(epl);
        SupportSubscriberMRD subscriber = new SupportSubscriberMRD();
        stmt.setSubscriber(subscriber);
        epService.getEPRuntime().setVariableValue("myvar", true);

        sendMyInnerSchemaEvent("X4", "Y4", 11);
        Object[][] result = subscriber.getInsertStreamList().get(0);
        assertEquals("X4", result[0][0]);
        assertEquals("Y4", ((Map) result[0][1]).get("in1"));
    }

    public void testInvalid() {
        String epl;        
        epService.getEPAdministrator().createEPL("create window MergeWindow.std:unique(string) as SupportBean");

        epl = "on SupportBean_A as up merge MergeWindow as mv where mv.boolPrimitive=true when not matched then update set intPrimitive = 1";
        tryInvalid(epl, "Incorrect syntax near 'update' (a reserved keyword) expecting 'insert' but found 'update' at line 1 column 97 [on SupportBean_A as up merge MergeWindow as mv where mv.boolPrimitive=true when not matched then update set intPrimitive = 1]");

        epl = "on SupportBean_A as up merge MergeWindow as mv where mv.boolPrimitive=true when matched then insert select *";
        tryInvalid(epl, "Incorrect syntax near 'insert' (a reserved keyword) at line 1 column 93 [on SupportBean_A as up merge MergeWindow as mv where mv.boolPrimitive=true when matched then insert select *]");

        epl = "on SupportBean as up merge MergeWindow as mv";
        tryInvalid(epl, "Unexpected end of input string, check for an invalid identifier or missing additional keywords near 'mv' at line 1 column 42  [on SupportBean as up merge MergeWindow as mv]");

        epl = "on SupportBean as up merge MergeWindow as mv where a=b when matched";
        tryInvalid(epl, "Incorrect syntax near 'matched' (a reserved keyword) expecting 'then' but found end of input at line 1 column 60 [on SupportBean as up merge MergeWindow as mv where a=b when matched]");

        epl = "on SupportBean as up merge MergeWindow as mv where a=b when matched and then delete";
        tryInvalid(epl, "Incorrect syntax near 'then' (a reserved keyword) at line 1 column 72 [on SupportBean as up merge MergeWindow as mv where a=b when matched and then delete]");

        epl = "on SupportBean as up merge MergeWindow as mv where boolPrimitive=true when not matched then insert select *";
        tryInvalid(epl, "Error starting statement: Property named 'boolPrimitive' is ambigous as is valid for more then one stream [on SupportBean as up merge MergeWindow as mv where boolPrimitive=true when not matched then insert select *]");

        epl = "on SupportBean as up merge MergeWindow as mv where mv.boolPrimitive=true when not matched then insert select intPrimitive";
        tryInvalid(epl, "Error starting statement: Event type named 'MergeWindow' has already been declared with differing column name or type information: Type by name 'MergeWindow' is not a compatible type [on SupportBean as up merge MergeWindow as mv where mv.boolPrimitive=true when not matched then insert select intPrimitive]");

        epl = "on SupportBean_A as up merge MergeWindow as mv where mv.boolPrimitive=true when not matched then insert select intPrimitive";
        tryInvalid(epl, "Error starting statement: Property named 'intPrimitive' is not valid in any stream [on SupportBean_A as up merge MergeWindow as mv where mv.boolPrimitive=true when not matched then insert select intPrimitive]");
    }

    public void tryInvalid(String epl, String expected) {
        try {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch(EPStatementException ex) {
            assertEquals(expected, ex.getMessage());
        }
    }

    public void testSubselect() {
        String[] fields = "col1,col2".split(",");
        epService.getEPAdministrator().createEPL("create schema MyEvent as (in1 string, in2 int)");
        epService.getEPAdministrator().createEPL("create schema MySchema as (col1 string, col2 int)");
        EPStatement namedWindowStmt = epService.getEPAdministrator().createEPL("create window MyWindow.std:lastevent() as MySchema");
        epService.getEPAdministrator().createEPL("on SupportBean_A delete from MyWindow");

        String epl =  "on MyEvent me " +
                      "merge MyWindow mw " +
                      "when not matched and (select intPrimitive>0 from SupportBean(string like 'A%').std:lastevent()) then " +
                      "insert(col1, col2) select (select string from SupportBean(string like 'A%').std:lastevent()), (select intPrimitive from SupportBean(string like 'A%').std:lastevent()) " +
                      "when matched and (select intPrimitive>0 from SupportBean(string like 'B%').std:lastevent()) then " +
                      "update set col1=(select string from SupportBean(string like 'B%').std:lastevent()), col2=(select intPrimitive from SupportBean(string like 'B%').std:lastevent()) " +
                      "when matched and (select intPrimitive>0 from SupportBean(string like 'C%').std:lastevent()) then " +
                      "delete";
        epService.getEPAdministrator().createEPL(epl);

        // no action tests
        sendMyEvent("X1", 1);
        epService.getEPRuntime().sendEvent(new SupportBean("A1", 0));   // ignored
        sendMyEvent("X2", 2);
        epService.getEPRuntime().sendEvent(new SupportBean("A2", 20));
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, null);

        sendMyEvent("X3", 3);
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"A2", 20}});

        epService.getEPRuntime().sendEvent(new SupportBean_A("Y1"));
        epService.getEPRuntime().sendEvent(new SupportBean("A3", 30));
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, null);

        sendMyEvent("X4", 4);
        epService.getEPRuntime().sendEvent(new SupportBean("A4", 40));
        sendMyEvent("X5", 5);   // ignored as matched (no where clause, no B event)
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"A3", 30}});

        epService.getEPRuntime().sendEvent(new SupportBean("B1", 50));
        sendMyEvent("X6", 6);
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"B1", 50}});

        epService.getEPRuntime().sendEvent(new SupportBean("B2", 60));
        sendMyEvent("X7", 7);
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"B2", 60}});

        epService.getEPRuntime().sendEvent(new SupportBean("B2", 0));
        sendMyEvent("X8", 8);
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"B2", 60}});

        epService.getEPRuntime().sendEvent(new SupportBean("C1", 1));
        sendMyEvent("X9", 9);
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, null);

        epService.getEPRuntime().sendEvent(new SupportBean("C1", 0));
        sendMyEvent("X10", 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"A4", 40}});
    }

    public void testNoWhereClause() {
        String[] fields = "col1,col2".split(",");
        epService.getEPAdministrator().createEPL("create schema MyEvent as (in1 string, in2 int)");
        epService.getEPAdministrator().createEPL("create schema MySchema as (col1 string, col2 int)");
        EPStatement namedWindowStmt = epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as MySchema");
        epService.getEPAdministrator().createEPL("on SupportBean_A delete from MyWindow");

        String epl =  "on MyEvent me " +
                      "merge MyWindow mw " +
                      "when not matched and me.in1 like \"A%\" then " +
                      "insert(col1, col2) select me.in1, me.in2 " +
                      "when not matched and me.in1 like \"B%\" then " +
                      "insert select me.in1 as col1, me.in2 as col2 " +
                      "when matched and me.in1 like \"C%\" then " +
                      "update set col1='Z', col2=-1 " +
                      "when not matched then " +
                      "insert select \"x\" || me.in1 || \"x\" as col1, me.in2 * -1 as col2 ";
        epService.getEPAdministrator().createEPL(epl);
        
        sendMyEvent("E1", 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"xE1x", -2}});

        sendMyEvent("A1", 3);   // matched : no where clause
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"xE1x", -2}});

        epService.getEPRuntime().sendEvent(new SupportBean_A("Ax1"));
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, null);

        sendMyEvent("A1", 4);
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"A1", 4}});

        sendMyEvent("B1", 5);   // matched : no where clause
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"A1", 4}});

        epService.getEPRuntime().sendEvent(new SupportBean_A("Ax1"));
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, null);

        sendMyEvent("B1", 5);
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"B1", 5}});

        sendMyEvent("C", 6);
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"Z", -1}});
    }

    public void testMultipleInsert() {

        String[] fields = "col1,col2".split(",");
        epService.getEPAdministrator().createEPL("create schema MyEvent as (in1 string, in2 int)");
        epService.getEPAdministrator().createEPL("create schema MySchema as (col1 string, col2 int)");
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as MySchema");
        
        String epl =  "on MyEvent " +
                      "merge MyWindow " +
                      "where col1 = in1 " +
                      "when not matched and in1 like \"A%\" then " +
                      "insert(col1, col2) select in1, in2 " +
                      "when not matched and in1 like \"B%\" then " +
                      "insert select in1 as col1, in2 as col2 " +
                      "when not matched and in1 like \"C%\" then " +
                      "insert select \"Z\" as col1, -1 as col2 " +
                      "when not matched and in1 like \"D%\" then " +
                      "insert select \"x\" || in1 || \"x\" as col1, in2 * -1 as col2 ";
        EPStatement merged = epService.getEPAdministrator().createEPL(epl);
        merged.addListener(mergeListener);
        
        sendMyEvent("E1", 0);
        assertFalse(mergeListener.isInvoked());

        sendMyEvent("A1", 1);
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNewAndReset(), fields, new Object[] {"A1", 1});

        sendMyEvent("B1", 2);
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNewAndReset(), fields, new Object[] {"B1", 2});

        sendMyEvent("C1", 3);
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNewAndReset(), fields, new Object[] {"Z", -1});

        sendMyEvent("D1", 4);
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNewAndReset(), fields, new Object[] {"xD1x", -4});

        sendMyEvent("B1", 2);
        assertFalse(mergeListener.isInvoked());

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        assertEquals(epl.trim(), model.toEPL().trim());
        merged = epService.getEPAdministrator().create(model);
        assertEquals(merged.getText().trim(), model.toEPL().trim());
    }

    private void sendMyEvent(String in1, int in2) {
        Map<String, Object> event = new HashMap<String, Object>();
        event.put("in1", in1);
        event.put("in2", in2);
        epService.getEPRuntime().sendEvent(event, "MyEvent");
    }

    private void sendMyInnerSchemaEvent(String col1, String col2in1, int col2in2) {
        Map<String, Object> inner = new HashMap<String, Object>();
        inner.put("in1", col2in1);
        inner.put("in2", col2in2);
        Map<String, Object> event = new HashMap<String, Object>();
        event.put("col1", col1);
        event.put("col2", inner);
        epService.getEPRuntime().sendEvent(event, "MyEventSchema");
    }

    public void testFlow() throws Exception
    {
        String[] fields = "string,intPrimitive,intBoxed".split(",");
        EPStatement namedWindowStmt = epService.getEPAdministrator().createEPL("create window MergeWindow.std:unique(string) as SupportBean");
        namedWindowStmt.addListener(nwListener);

        epService.getEPAdministrator().createEPL("insert into MergeWindow select * from SupportBean(boolPrimitive)");
        epService.getEPAdministrator().createEPL("on SupportBean_A delete from MergeWindow");

        String epl =  "on SupportBean(boolPrimitive = false) as up " +
                      "merge MergeWindow as mv " +
                      "where mv.string = up.string " +
                      "when matched and up.intPrimitive < 0 then " +
                      "delete " +
                      "when matched and up.intPrimitive = 0 then " +
                      "update set intPrimitive = 0, intBoxed = 0 " +
                      "when matched then " +
                      "update set intPrimitive = up.intPrimitive, intBoxed = up.intBoxed + mv.intBoxed " +
                      "when not matched then " +
                      "insert select *";
        EPStatement merged = epService.getEPAdministrator().createEPL(epl);
        merged.addListener(mergeListener);

        runAssertion(namedWindowStmt, fields);

        merged.destroy();
        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        nwListener.reset();
        mergeListener.reset();
        
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        assertEquals(epl, model.toEPL().trim());
        merged = epService.getEPAdministrator().create(model);
        assertEquals(merged.getText().trim(), model.toEPL().trim());
        merged.addListener(mergeListener);
        
        runAssertion(namedWindowStmt, fields);

        // test stream wildcard
        epService.getEPRuntime().sendEvent(new SupportBean_A("A2"));
        merged.destroy();
        epl =  "on SupportBean(boolPrimitive = false) as up " +
                      "merge MergeWindow as mv " +
                      "where mv.string = up.string " +
                      "when not matched then " +
                      "insert select up.*";
        merged = epService.getEPAdministrator().createEPL(epl);
        merged.addListener(mergeListener);

        sendSupportBeanEvent(false, "E99", 2, 3); // insert via merge
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E99", 2, 3}});

        // Test ambiguous columns.
        epService.getEPAdministrator().createEPL("create schema TypeOne (id long, mylong long, mystring long)");
        epService.getEPAdministrator().createEPL("create window MyNamedWindow.std:unique(id) as select * from TypeOne");

        // The "and not matched" should not complain if "mystring" is ambiguous.
        // The "insert" should not complain as column names have been provided.
        epl =  "on TypeOne as t1 merge MyNamedWindow nm where nm.id = t1.id\n" +
                "  when not matched and mystring = 0 then insert select *\n" +
                "  when not matched then insert (id, mylong, mystring) select 0L, 0L, 0L\n" +
                " ";
        epService.getEPAdministrator().createEPL(epl);
    }

    private void runAssertion(EPStatement namedWindowStmt, String[] fields) {

        sendSupportBeanEvent(true, "E1", 10, 200); // insert via insert-into
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10, 200});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 10, 200}});
        assertFalse(mergeListener.isInvoked());

        sendSupportBeanEvent(false, "E1", 11, 201);    // update via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E1", 11, 401});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E1", 10, 200});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNew(), fields, new Object[] {"E1", 11, 401});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOld(), fields, new Object[] {"E1", 10, 200});
        mergeListener.reset();

        sendSupportBeanEvent(false, "E2", 13, 300); // insert via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 13, 300});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 13, 300}});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 13, 300});

        sendSupportBeanEvent(false, "E2", 14, 301); // update via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E2", 14, 601});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E2", 13, 300});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 14, 601}});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNew(), fields, new Object[] {"E2", 14, 601});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOld(), fields, new Object[] {"E2", 13, 300});
        mergeListener.reset();

        sendSupportBeanEvent(false, "E2", 15, 302); // update via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E2", 15, 903});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E2", 14, 601});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 15, 903}});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNew(), fields, new Object[] {"E2", 15, 903});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOld(), fields, new Object[] {"E2", 14, 601});
        mergeListener.reset();

        sendSupportBeanEvent(false, "E3", 40, 400); // insert via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 40, 400});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 15, 903}, {"E3", 40, 400}});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 40, 400});

        sendSupportBeanEvent(false, "E3", 0, 1000); // reset E3 via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E3", 0, 0});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E3", 40, 400});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 15, 903}, {"E3", 0, 0}});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNew(), fields, new Object[] {"E3", 0, 0});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOld(), fields, new Object[] {"E3", 40, 400});
        mergeListener.reset();

        sendSupportBeanEvent(false, "E2", -1, 1000); // delete E2 via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOldAndReset(), fields, new Object[] {"E2", 15, 903});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOldAndReset(), fields, new Object[] {"E2", 15, 903});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E3", 0, 0}});

        sendSupportBeanEvent(false, "E1", -1, 1000); // delete E1 via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOldAndReset(), fields, new Object[] {"E1", 11, 401});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOldAndReset(), fields, new Object[] {"E1", 11, 401});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E3", 0, 0}});
    }

    private void sendSupportBeanEvent(boolean boolPrimitive, String string, int intPrimitive, Integer intBoxed) {
        SupportBean event = new SupportBean(string, intPrimitive);
        event.setIntBoxed(intBoxed);
        event.setBoolPrimitive(boolPrimitive);
        epService.getEPRuntime().sendEvent(event);
    }


}
