package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanNumeric;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestRowLimit extends TestCase {

	private EPServiceProvider epService;
	private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        config.addEventType("SupportBeanNumeric", SupportBeanNumeric.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testBatchNoOffsetNoOrder()
	{
        String statementString = "select irstream * from SupportBean.win:length_batch(3) limit 1";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementString);

        runAssertion(stmt);
    }

    public void testLengthOffsetVariable()
	{
        epService.getEPAdministrator().createEPL("create variable int myrows = 2");
        epService.getEPAdministrator().createEPL("create variable int myoffset = 1");
        epService.getEPAdministrator().createEPL("on SupportBeanNumeric set myrows = intOne, myoffset = intTwo");

        String statementString = "select * from SupportBean.win:length(5) output every 5 events limit myoffset, myrows";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementString);
        runAssertionVariable(stmt);
        stmt.destroy();
        listener.reset();
        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(2, 1));
        
        statementString = "select * from SupportBean.win:length(5) output every 5 events limit myrows offset myoffset";
        stmt = epService.getEPAdministrator().createEPL(statementString);
        runAssertionVariable(stmt);
        stmt.destroy();
        listener.reset();
        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(2, 1));

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(statementString);
        assertEquals(statementString, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        runAssertionVariable(stmt);
    }

    public void testOrderBy()
	{
        String statementString = "select * from SupportBean.win:length(5) output every 5 events order by intPrimitive limit 2 offset 2";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementString);

        String[] fields = "string".split(",");
        stmt.addListener(listener);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendEvent("E1", 90);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendEvent("E2", 5);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendEvent("E3", 60);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}});

        sendEvent("E4", 99);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}, {"E4"}});
        assertFalse(listener.isInvoked());

        sendEvent("E5", 6);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E3"}, {"E1"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"E3"}, {"E1"}});
    }

    private void runAssertionVariable(EPStatement stmt)
    {
        String[] fields = "string".split(",");
        stmt.addListener(listener);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendEvent("E1", 1);
        sendEvent("E2", 2);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2"}});

        sendEvent("E3", 3);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2"}, {"E3"}});

        sendEvent("E4", 4);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2"}, {"E3"}});
        assertFalse(listener.isInvoked());

        sendEvent("E5", 5);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2"}, {"E3"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"E2"}, {"E3"}});

        sendEvent("E6", 6);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E3"}, {"E4"}});
        assertFalse(listener.isInvoked());

        // change variable values
        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(2, 3));
        sendEvent("E7", 7);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E6"}, {"E7"}});
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(-1, 0));
        sendEvent("E8", 8);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E4"}, {"E5"}, {"E6"}, {"E7"}, {"E8"}});
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(10, 0));
        sendEvent("E9", 9);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E5"}, {"E6"}, {"E7"}, {"E8"}, {"E9"}});
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(6, 3));
        sendEvent("E10", 10);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E9"}, {"E10"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"E9"}, {"E10"}});

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(1, 1));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E7"}});

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(2, 1));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E7"}, {"E8"}});

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(1, 2));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E8"}});

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(6, 6));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(1, 4));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E10"}});

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric((Integer) null, null));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E6"}, {"E7"}, {"E8"}, {"E9"}, {"E10"}});

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(null, 2));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E8"}, {"E9"}, {"E10"}});

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(2, null));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E6"}, {"E7"}});

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(-1, 4));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E10"}});

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(-1, 0));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E6"}, {"E7"}, {"E8"}, {"E9"}, {"E10"}});

        epService.getEPRuntime().sendEvent(new SupportBeanNumeric(0, 0));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);
    }

    public void testBatchOffsetNoOrderOM()
	{
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.createWildcard());
        model.getSelectClause().setStreamSelector(StreamSelector.RSTREAM_ISTREAM_BOTH);
        model.setFromClause(FromClause.create(FilterStream.create("SupportBean").addView("win", "length_batch", Expressions.constant(3))));
        model.setRowLimitClause(RowLimitClause.create(1));
        
        String statementString = "select irstream * from SupportBean.win:length_batch(3) limit 1";
        assertEquals(statementString, model.toEPL());
        EPStatement stmt = epService.getEPAdministrator().create(model);
        runAssertion(stmt);
        stmt.destroy();
        listener.reset();
        
        model = epService.getEPAdministrator().compileEPL(statementString);
        assertEquals(statementString, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        runAssertion(stmt);
    }

    public void testFullyGroupedOrdered()
	{
        String statementString = "select string, sum(intPrimitive) as mysum from SupportBean.win:length(5) group by string order by sum(intPrimitive) limit 2";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementString);

        String[] fields = "string,mysum".split(",");
        stmt.addListener(listener);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendEvent("E1", 90);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1", 90}});

        sendEvent("E2", 5);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2", 5}, {"E1", 90}});

        sendEvent("E3", 60);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2", 5}, {"E3", 60}});

        sendEvent("E3", 40);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2", 5}, {"E1", 90}});

        sendEvent("E2", 1000);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1", 90}, {"E3", 100}});
    }

    public void testEventPerRowUnGrouped()
	{
        sendTimer(1000);
        String statementString = "select string, sum(intPrimitive) as mysum from SupportBean.win:length(5) output every 10 seconds order by string desc limit 2";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementString);

        String[] fields = "string,mysum".split(",");
        stmt.addListener(listener);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendEvent("E1", 10);
        sendEvent("E2", 5);
        sendEvent("E3", 20);
        sendEvent("E4", 30);

        sendTimer(11000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E4", 65}, {"E3", 35}});
    }

    public void testGroupedSnapshot()
	{
        sendTimer(1000);
        String statementString = "select string, sum(intPrimitive) as mysum from SupportBean.win:length(5) group by string output snapshot every 10 seconds order by sum(intPrimitive) desc limit 2";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementString);

        String[] fields = "string,mysum".split(",");
        stmt.addListener(listener);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendEvent("E1", 10);
        sendEvent("E2", 5);
        sendEvent("E3", 20);
        sendEvent("E1", 30);

        sendTimer(11000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E1", 40}, {"E3", 20}});
    }

    public void testGroupedSnapshotNegativeRowcount()
	{
        sendTimer(1000);
        String statementString = "select string, sum(intPrimitive) as mysum from SupportBean.win:length(5) group by string output snapshot every 10 seconds order by sum(intPrimitive) desc limit -1 offset 1";
        EPStatement stmt = epService.getEPAdministrator().createEPL(statementString);

        String[] fields = "string,mysum".split(",");
        stmt.addListener(listener);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendEvent("E1", 10);
        sendEvent("E2", 5);
        sendEvent("E3", 20);
        sendEvent("E1", 30);

        sendTimer(11000);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E3", 20}, {"E2", 5}});
    }

    public void testInvalid()
    {
        epService.getEPAdministrator().createEPL("create variable string myrows = 'abc'");
        tryInvalid("select * from SupportBean limit myrows",
                   "Error starting statement: Limit clause requires a variable of numeric type [select * from SupportBean limit myrows]");
        tryInvalid("select * from SupportBean limit 1, myrows",
                   "Error starting statement: Limit clause requires a variable of numeric type [select * from SupportBean limit 1, myrows]");
        tryInvalid("select * from SupportBean limit dummy",
                   "Error starting statement: Limit clause variable by name 'dummy' has not been declared [select * from SupportBean limit dummy]");
        tryInvalid("select * from SupportBean limit 1,dummy",
                   "Error starting statement: Limit clause variable by name 'dummy' has not been declared [select * from SupportBean limit 1,dummy]");
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void runAssertion(EPStatement stmt)
    {
        String[] fields = "string".split(",");
        stmt.addListener(listener);
        sendEvent("E1", 1);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}});

        sendEvent("E2", 2);
        assertFalse(listener.isInvoked());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1"}});

        sendEvent("E3", 3);
        ArrayAssertionUtil.assertPropsPerRow(listener.getAndResetLastNewData(), fields, new Object[][] {{"E1"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendEvent("E4", 4);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E4"}});

        sendEvent("E5", 5);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E4"}});

        sendEvent("E6", 6);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E4"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastOldData(), fields, new Object[][] {{"E1"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);
    }

    private void tryInvalid(String expression, String expected)
    {
        try
        {
            epService.getEPAdministrator().createEPL(expression);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(expected, ex.getMessage());
        }
    }

    private void sendEvent(String string, int intPrimitive)
    {
        epService.getEPRuntime().sendEvent(new SupportBean(string, intPrimitive));
    }
}
