package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestMerge extends TestCase {

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

    // test multiple insert via "not matched and"
    // test column list insert
    // test subselect in insert and update and conditions
    // test no-insert
    // test no-update
    // test no-delete
    // test no aliases
    // test stream wildcard
    // test inner events
    // test type reference and type reference release
    // test allow prefixes on updated, inserted and selected items (test without prefixes) as well
    // test list of columns
    // test both update and insert required
    // test multirow insert
    // test non-matching types (ie. SupportBean is the same in below example)
    // test event type free when statement destroyed
    // test no listener attached to merge: select expr process not generating output
    // test multithreaded
    // test no where clause (last event upd)
    // test performance/index building
    // TODO doc update + EHQ support + EHA test

    public void testInvalid() {
        String epl;        
        epService.getEPAdministrator().createEPL("create window MergeWindow.std:unique(string) as SupportBean");

        epl = "on SupportBean as up merge MergeWindow as mv";
        tryInvalid(epl, "Unexpected end of input string, check for an invalid identifier or missing additional keywords near 'mv' at line 1 column 42  [on SupportBean as up merge MergeWindow as mv]");

        epl = "on SupportBean as up merge MergeWindow as mv where a=b when matched";
        tryInvalid(epl, "Incorrect syntax near 'matched' (a reserved keyword) expecting 'then' but found end of input at line 1 column 60 [on SupportBean as up merge MergeWindow as mv where a=b when matched]");

        epl = "on SupportBean as up merge MergeWindow as mv where a=b when matched and then delete";
        tryInvalid(epl, "Incorrect syntax near 'then' (a reserved keyword) at line 1 column 72 [on SupportBean as up merge MergeWindow as mv where a=b when matched and then delete]");

        epl = "on SupportBean as up merge MergeWindow as mv where boolPrimitive=true when not matched then insert select *";
        tryInvalid(epl, "Error starting statement: Property named 'boolPrimitive' is ambigous as is valid for more then one stream [on SupportBean as up merge MergeWindow as mv where boolPrimitive=true when not matched then insert select *]");

        // TODO try invalid insert-into
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

    public void testMultipleInsert() {
        epService.getEPAdministrator().createEPL("create schema MyEvent as (in1 string, in2 int)");
        epService.getEPAdministrator().createEPL("create schema MySchema as (col1 string, col2 int)");
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as MySchema");
        
        String epl =  "on MySchema " +
                      "merge MyWindow " +
                      "where col1 = in1 " +
                      "when not matched and in1 like 'A%' then " +
                      " insert (col1, col2) select in1, in2 " +
                      "when not matched and in1 like 'B%' then " +
                      " insert select in1 as col1, in2 as col2 " +
                      "when not matched and in1 like 'C%' then " +
                      " insert select 'Z' as col1, -1 as col2 " +
                      "when not matched and in1 like 'D%' then " +
                      " insert select 'x' | in1 | 'x' as col1, in2 * -1 as col2 ";
        EPStatement merged = epService.getEPAdministrator().createEPL(epl);
        merged.addListener(mergeListener);
    }

    public void testFragmentType() {
        epService.getEPAdministrator().createEPL("create schema MyInnerSchema(col1 string, col2 int");
        epService.getEPAdministrator().createEPL("create schema MySchema(col3 string, col4 MyInnerSchema");
        epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as MySchema");
        // TODO
    }

    public void testFlow() throws Exception
    {
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

        runAssertion(namedWindowStmt);

        merged.destroy();
        epService.getEPRuntime().sendEvent(new SupportBean_A("A1"));
        nwListener.reset();
        mergeListener.reset();
        
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        assertEquals(epl, model.toEPL().trim());
        merged = epService.getEPAdministrator().create(model);
        assertEquals(merged.getText().trim(), model.toEPL().trim());
        merged.addListener(mergeListener);
        
        runAssertion(namedWindowStmt);
    }

    private void runAssertion(EPStatement namedWindowStmt) {

        String[] fields = "string,intPrimitive,intBoxed".split(",");

        sendEvent(true, "E1", 10, 200); // insert via insert-into
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10, 200});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 10, 200}});
        assertFalse(mergeListener.isInvoked());

        sendEvent(false, "E1", 11, 201);    // update via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E1", 11, 401});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E1", 10, 200});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNew(), fields, new Object[] {"E1", 11, 401});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOld(), fields, new Object[] {"E1", 10, 200});
        mergeListener.reset();

        sendEvent(false, "E2", 13, 300); // insert via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 13, 300});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 13, 300}});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 13, 300});

        sendEvent(false, "E2", 14, 301); // update via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E2", 14, 601});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E2", 13, 300});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 14, 601}});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNew(), fields, new Object[] {"E2", 14, 601});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOld(), fields, new Object[] {"E2", 13, 300});
        mergeListener.reset();

        sendEvent(false, "E2", 15, 302); // update via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E2", 15, 903});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E2", 14, 601});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 15, 903}});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNew(), fields, new Object[] {"E2", 15, 903});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOld(), fields, new Object[] {"E2", 14, 601});
        mergeListener.reset();

        sendEvent(false, "E3", 40, 400); // insert via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 40, 400});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 15, 903}, {"E3", 40, 400}});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 40, 400});

        sendEvent(false, "E3", 0, 1000); // reset E3 via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E3", 0, 0});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E3", 40, 400});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 15, 903}, {"E3", 0, 0}});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetNew(), fields, new Object[] {"E3", 0, 0});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOld(), fields, new Object[] {"E3", 40, 400});
        mergeListener.reset();

        sendEvent(false, "E2", -1, 1000); // delete E2 via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOldAndReset(), fields, new Object[] {"E2", 15, 903});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOldAndReset(), fields, new Object[] {"E2", 15, 903});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E3", 0, 0}});

        sendEvent(false, "E1", -1, 1000); // delete E1 via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOldAndReset(), fields, new Object[] {"E1", 11, 401});
        ArrayAssertionUtil.assertProps(mergeListener.assertOneGetOldAndReset(), fields, new Object[] {"E1", 11, 401});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E3", 0, 0}});
    }

    private void sendEvent(boolean boolPrimitive, String string, int intPrimitive, Integer intBoxed) {
        SupportBean event = new SupportBean(string, intPrimitive);
        event.setIntBoxed(intBoxed);
        event.setBoolPrimitive(boolPrimitive);
        epService.getEPRuntime().sendEvent(event);
    }


}
