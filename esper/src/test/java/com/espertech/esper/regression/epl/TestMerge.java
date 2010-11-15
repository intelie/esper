package com.espertech.esper.regression.epl;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Date;

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

    // test negative: validate single update or insert only
    // test SODA
    // test no column list insert
    // test subselect in insert and update
    // test no-insert
    // test no-update
    // test no aliases
    // test stream wildcard
    // test inner events
    // test type reference and type reference release
    // test allow prefixes on updated, inserted and selected items
    // test list of columns
    // test both update and insert required
    // test add capability to "then delete" with "and", multiple "MATCHED" clauses 

    public void testSimple() throws Exception
    {
        EPStatement createStmt = epService.getEPAdministrator().createEPL("create window MergeWindow.std:unique(string) as SupportBean");
        createStmt.addListener(nwListener);

        epService.getEPAdministrator().createEPL("insert into MergeWindow select * from SupportBean(boolPrimitive)");

        String epl =  "merge into MergeWindow as mv " +
                      "using SupportBean(boolPrimitive = false) as up " +
                      "on mv.string = up.string " +
                      "when matched then " +
                      "update set intPrimitive=up.intPrimitive, intBoxed=up.intBoxed + mv.intBoxed " +
                      "when not matched then " +
                      "insert select *";
        EPStatement merged = epService.getEPAdministrator().createEPL(epl);
        merged.addListener(mergeListener);

        runAssertion();

        // TODO
        //EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(epl);
        //assertEquals(epl, model.toEPL());
    }

    private void runAssertion() {

        String[] fields = "string,intPrimitive,intBoxed".split(",");

        sendEvent(true, "E1", 10, 200); // insert via insert-into
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10, 200});

        sendEvent(false, "E1", 11, 201);    // update via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E1", 11, 401});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E1", 10, 200});
        nwListener.reset();

        sendEvent(false, "E2", 13, 300); // insert via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 13, 300});

        sendEvent(false, "E2", 14, 301); // update via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E2", 14, 601});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E2", 13, 300});
        nwListener.reset();

        sendEvent(false, "E2", 15, 302); // update via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNew(), fields, new Object[] {"E2", 15, 903});
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetOld(), fields, new Object[] {"E2", 14, 601});
        nwListener.reset();

        sendEvent(false, "E3", 40, 400); // insert via merge
        ArrayAssertionUtil.assertProps(nwListener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 40, 400});
    }

    private void sendEvent(boolean boolPrimitive, String string, int intPrimitive, Integer intBoxed) {
        SupportBean event = new SupportBean(string, intPrimitive);
        event.setIntBoxed(intBoxed);
        event.setBoolPrimitive(boolPrimitive);
        epService.getEPRuntime().sendEvent(event);
    }


}
