package com.espertech.esper.regression.view;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestRowLimit extends TestCase {

	private static final Log log = LogFactory.getLog(TestOrderByEventPerGroup.class);
	private EPServiceProvider epService;
	private SupportUpdateListener testListener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.addEventTypeAlias("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        testListener = new SupportUpdateListener();
    }

    // Test with and without order by
    // Test empty or full batch
    // Test each group-by combination
    // Test iterator
    // Test OM
    // Test output snapshot
    // Test offset
    // Test variables
    // Test remove stream
    public void testNoOffsetNoOrder()
	{
        String statementString = "select * from SupportBean.win:length_batch(3) limit 1";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementString);

        runAssertion(stmt);
    }

    public void testRowLimitOffsetVariable()
	{
        epService.getEPAdministrator().createEPL("create variable int myrows = 1");
        epService.getEPAdministrator().createEPL("create variable int myoffset = 2");
        String statementString = "select * from SupportBean.win:length(5) output every 5 events limit myrows offset myoffset";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementString);

        String[] fields = "string".split(",");
        stmt.addListener(testListener);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendEvent("E1", 1);
        sendEvent("E2", 2);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2"}});

        sendEvent("E3", 3);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2"}, {"E3"}});

        sendEvent("E4", 4);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);
        sendEvent("E5", 5);

        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields, new Object[][] {{"E3"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);
    }

    public void testRowLimitNoOffsetNoOrderOM()
	{
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.createWildcard());
        model.setFromClause(FromClause.create(FilterStream.create("SupportBean").addView("win", "length_batch", 3)));
        model.setRowLimitClause(RowLimitClause.create(1));
        
        String statementString = "select * from SupportBean.win:length_batch(3) limit 1";
        assertEquals(statementString, model.toEPL());
        EPStatement stmt = epService.getEPAdministrator().create(model);
        runAssertion(stmt);
        stmt.destroy();
        testListener.reset();
        
        model = epService.getEPAdministrator().compileEPL(statementString);
        assertEquals(statementString, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        runAssertion(stmt);
    }

    private void runAssertion(EPStatement stmt)
    {
        String[] fields = "string".split(",");
        stmt.addListener(testListener);
        sendEvent("E1", 1);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}});

        sendEvent("E2", 2);
        assertFalse(testListener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}});

        sendEvent("E3", 3);
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields, new Object[][] {{"E1"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);
    }

    public void sendEvent(String string, int intPrimitive)
    {
        epService.getEPRuntime().sendEvent(new SupportBean(string, intPrimitive));
    }
}
