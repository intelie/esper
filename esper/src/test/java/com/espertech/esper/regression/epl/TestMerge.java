package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestMerge extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener nwListener;
    private SupportUpdateListener mergeListener;

    protected void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        nwListener = new SupportUpdateListener();
        mergeListener = new SupportUpdateListener();
    }

    // test negative: validate single update or insert only; no merge instructions
    // test SODA
    // test no column list insert
    // test subselect in insert and update and conditions
    // test no-insert
    // test no-update
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

    public void testSimple() throws Exception
    {
        EPStatement namedWindowStmt = epService.getEPAdministrator().createEPL("create window MergeWindow.std:unique(string) as SupportBean");
        namedWindowStmt.addListener(nwListener);

        epService.getEPAdministrator().createEPL("insert into MergeWindow select * from SupportBean(boolPrimitive)");

        /*
        on SupportBean(boolPrimitive = false) as up
        merge MergeWindow mv
        when matched        
        */
        String epl =  "merge into MergeWindow as mv " +
                      "using SupportBean(boolPrimitive = false) as up " +
                      "on mv.string = up.string " +
                      "when matched and up.intPrimitive < 0 then " +
                      "delete " +
                      "when matched and up.intPrimitive = 0 then " +
                      "update set intPrimitive=0, intBoxed=0 " +
                      "when matched then " +
                      "update set intPrimitive=up.intPrimitive, intBoxed=up.intBoxed + mv.intBoxed " +
                      "when not matched then " +
                      "insert select *";
        EPStatement merged = epService.getEPAdministrator().createEPL(epl);
        merged.addListener(mergeListener);

        runAssertion(namedWindowStmt);

        // TODO
        //EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        //assertEquals(epl, model.toEPL());
    }

    private void runAssertion(EPStatement namedWindowStmt) {

        String[] fields = "string,intPrimitive,intBoxed".split(",");

        sendEvent(true, "E1", 10, 200); // insert via insert-into
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10, 200});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 10, 200}});

        sendEvent(false, "E1", 11, 201);    // update via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E1", 11, 401});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E1", 10, 200});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}});

        sendEvent(false, "E2", 13, 300); // insert via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 13, 300});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 13, 300}});

        sendEvent(false, "E2", 14, 301); // update via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E2", 14, 601});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E2", 13, 300});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 14, 601}});

        sendEvent(false, "E2", 15, 302); // update via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E2", 15, 903});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E2", 14, 601});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 15, 903}});

        sendEvent(false, "E3", 40, 400); // insert via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 40, 400});
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 15, 903}, {"E3", 40, 400}});
        
        sendEvent(false, "E3", 0, 1000); // reset E3 via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E3", 0, 0});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E3", 40, 400});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E2", 15, 903}, {"E3", 0, 0}});

        sendEvent(false, "E2", -1, 1000); // delete E2 via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E2", 15, 903});
        nwListener.reset();
        ArrayAssertionUtil.assertEqualsAnyOrder(namedWindowStmt.iterator(), fields, new Object[][] {{"E1", 11, 401}, {"E3", 0, 0}});
    }

    private void sendEvent(boolean boolPrimitive, String string, int intPrimitive, Integer intBoxed) {
        SupportBean event = new SupportBean(string, intPrimitive);
        event.setIntBoxed(intBoxed);
        event.setBoolPrimitive(boolPrimitive);
        epService.getEPRuntime().sendEvent(event);
    }


}
