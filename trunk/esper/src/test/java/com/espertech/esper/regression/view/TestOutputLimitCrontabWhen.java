package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportSubscriber;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.type.FrequencyParameter;
import com.espertech.esper.type.RangeParameter;
import com.espertech.esper.type.WildcardParameter;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestOutputLimitCrontabWhen extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.addEventTypeAlias("MarketData", SupportMarketDataBean.class);
        config.addEventTypeAlias("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testOutputCrontabAt() {

        // every 15 minutes 8am to 5pm
        sendTimeEvent(1, 17, 10, 0, 0);
        String expression = "select * from MarketData.std:lastevent() output at (*/15, 8:17, *, *, *)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(expression);
        stmt.addListener(listener);
        runAssertionCrontab(1, stmt);
    }

    public void testOutputCrontabAtOMCreate() {

        // every 15 minutes 8am to 5pm
        sendTimeEvent(1, 17, 10, 0, 0);
        String expression = "select * from MarketData.std:lastevent() output at (*/15, 8:17, *, *, *)";

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.createWildcard());
        model.setFromClause(FromClause.create(FilterStream.create("MarketData").addView("std", "lastevent")));
        Object[] crontabParams = new Object[] {new FrequencyParameter(15), new RangeParameter(8, 17),
                new WildcardParameter(), new WildcardParameter(), new WildcardParameter()};
        model.setOutputLimitClause(OutputLimitClause.create(crontabParams));

        String epl = model.toEPL();
        assertEquals(expression, epl);
        EPStatement stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        runAssertionCrontab(1, stmt);
    }

    public void testOutputCrontabAtOMCompile()
    {
        // every 15 minutes 8am to 5pm
        sendTimeEvent(1, 17, 10, 0, 0);
        String expression = "select * from MarketData.std:lastevent() output at (*/15, 8:17, *, *, *)";

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(expression);
        assertEquals(expression, model.toEPL());
        EPStatement stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        runAssertionCrontab(1, stmt);
    }

    private void runAssertionCrontab(int days, EPStatement statement)
    {
        String[] fields = "symbol".split(",");
        sendEvent("S1", 0);
        assertFalse(listener.isInvoked());

        sendTimeEvent(days, 17, 14, 59, 0);
        sendEvent("S2", 0);
        assertFalse(listener.isInvoked());

        sendTimeEvent(days, 17, 15, 0, 0);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"S1"}, {"S2"}});

        sendTimeEvent(days, 17, 18, 0, 00);
        sendEvent("S3", 0);
        assertFalse(listener.isInvoked());

        sendTimeEvent(days, 17, 30, 0, 0);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"S3"}});

        sendTimeEvent(days, 17, 35, 0, 0);
        sendTimeEvent(days, 17, 45, 0, 0);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, null);

        sendEvent("S4", 0);
        sendEvent("S5", 0);
        sendTimeEvent(days, 18, 0, 0, 0);
        assertFalse(listener.isInvoked());

        sendTimeEvent(days, 18, 1, 0, 0);
        sendEvent("S6", 0);

        sendTimeEvent(days, 18, 15, 0, 0);
        assertFalse(listener.isInvoked());

        sendTimeEvent(days+1, 7, 59, 59, 0);
        assertFalse(listener.isInvoked());

        sendTimeEvent(days+1, 8, 0, 0, 0);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewDataAndReset(), fields, new Object[][] {{"S4"}, {"S5"}, {"S6"}});

        statement.destroy();
        listener.reset();
    }

    public void testOutputWhenThenExpression()
    {
        sendTimeEvent(1, 8, 0, 0, 0);
        epService.getEPAdministrator().getConfiguration().addVariable("myvar", int.class, 0);
        epService.getEPAdministrator().getConfiguration().addVariable("count_insert_var", int.class, 0);
        epService.getEPAdministrator().createEPL("on SupportBean set myvar = intPrimitive");

        String expression = "select symbol from MarketData.win:length(2) output when (myvar = 1) then set myvar = 0, count_insert_var = count_insert";
        EPStatement stmt =  epService.getEPAdministrator().createEPL(expression);
        runAssertion(1, stmt);

        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create("symbol"));
        model.setFromClause(FromClause.create(FilterStream.create("MarketData").addView("win", "length", 2)));
        model.setOutputLimitClause(OutputLimitClause.create(Expressions.eq("myvar", 1))
                                    .addThenAssignment("myvar", Expressions.constant(0))
                                    .addThenAssignment("count_insert_var", Expressions.property("count_insert")));

        String epl = model.toEPL();
        assertEquals(expression, epl);
        runAssertion(2, epService.getEPAdministrator().create(model));

        model = epService.getEPAdministrator().compileEPL(expression);
        assertEquals(expression, model.toEPL());
        runAssertion(3, epService.getEPAdministrator().create(model));        
    }

    private void runAssertion(int days, EPStatement stmt)
    {
        SupportSubscriber subscriber = new SupportSubscriber();
        stmt.setSubscriber(subscriber);

        sendEvent("S1", 0);

        // now scheduled for output
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(0, epService.getEPRuntime().getVariableValue("myvar"));
        assertFalse(subscriber.isInvoked());

        sendTimeEvent(days, 8, 0, 1, 0);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetLastNewData(), new Object[] {"S1"});
        assertEquals(0, epService.getEPRuntime().getVariableValue("myvar"));
        assertEquals(1, epService.getEPRuntime().getVariableValue("count_insert_var"));

        sendEvent("S2", 0);
        sendEvent("S3", 0);
        sendTimeEvent(days, 8, 0, 2, 0);
        sendTimeEvent(days, 8, 0, 3, 0);
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        assertEquals(0, epService.getEPRuntime().getVariableValue("myvar"));
        assertEquals(2, epService.getEPRuntime().getVariableValue("count_insert_var"));

        assertFalse(subscriber.isInvoked());
        sendTimeEvent(days, 8, 0, 4, 0);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetLastNewData(), new Object[] {"S2", "S3"});
        assertEquals(0, epService.getEPRuntime().getVariableValue("myvar"));

        sendTimeEvent(days, 8, 0, 5, 0);
        assertFalse(subscriber.isInvoked());
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(0, epService.getEPRuntime().getVariableValue("myvar"));
        assertFalse(subscriber.isInvoked());

        stmt.destroy();
    }

    public void testOutputWhenExpression()
    {
        sendTimeEvent(1, 8, 0, 0, 0);
        epService.getEPAdministrator().getConfiguration().addVariable("myint", int.class, 0);
        epService.getEPAdministrator().getConfiguration().addVariable("mystring", String.class, "");
        epService.getEPAdministrator().createEPL("on SupportBean set myint = intPrimitive, mystring = string");

        String expression = "select symbol from MarketData.win:length(2) output when myint = 1 and mystring like 'F%'";
        EPStatement stmt =  epService.getEPAdministrator().createEPL(expression);
        SupportSubscriber subscriber = new SupportSubscriber();
        stmt.setSubscriber(subscriber);

        sendEvent("S1", 0);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(1, epService.getEPRuntime().getVariableValue("myint"));
        assertEquals("E1", epService.getEPRuntime().getVariableValue("mystring"));

        sendEvent("S2", 0);
        sendTimeEvent(1, 8, 0, 1, 0);
        assertFalse(subscriber.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("F1", 0));
        assertEquals(0, epService.getEPRuntime().getVariableValue("myint"));
        assertEquals("F1", epService.getEPRuntime().getVariableValue("mystring"));

        sendTimeEvent(1, 8, 0, 2, 0);
        sendEvent("S3", 0);
        assertFalse(subscriber.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("F2", 1));
        assertEquals(1, epService.getEPRuntime().getVariableValue("myint"));
        assertEquals("F2", epService.getEPRuntime().getVariableValue("mystring"));

        sendEvent("S4", 0);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetLastNewData(), new Object[] {"S1", "S2", "S3", "S4"});
    }

    public void testOutputWhenBuiltInCountInsert()
    {
        String expression = "select symbol from MarketData.win:length(2) output when count_insert >= 3";
        EPStatement stmt =  epService.getEPAdministrator().createEPL(expression);
        SupportSubscriber subscriber = new SupportSubscriber();
        stmt.setSubscriber(subscriber);

        sendEvent("S1", 0);
        sendEvent("S2", 0);
        assertFalse(subscriber.isInvoked());

        sendEvent("S3", 0);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetLastNewData(), new Object[] {"S1", "S2", "S3"});

        sendEvent("S4", 0);
        sendEvent("S5", 0);
        assertFalse(subscriber.isInvoked());

        sendEvent("S6", 0);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetLastNewData(), new Object[] {"S4", "S5", "S6"});

        sendEvent("S7", 0);
        assertFalse(subscriber.isInvoked());
    }

    public void testOutputWhenBuiltInCountRemove()
    {
        String expression = "select symbol from MarketData.win:length(2) output when count_remove >= 2";
        EPStatement stmt =  epService.getEPAdministrator().createEPL(expression);
        SupportSubscriber subscriber = new SupportSubscriber();
        stmt.setSubscriber(subscriber);

        sendEvent("S1", 0);
        sendEvent("S2", 0);
        sendEvent("S3", 0);
        assertFalse(subscriber.isInvoked());

        sendEvent("S4", 0);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetLastNewData(), new Object[] {"S1", "S2", "S3", "S4"});

        sendEvent("S5", 0);
        assertFalse(subscriber.isInvoked());

        sendEvent("S6", 0);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetLastNewData(), new Object[] {"S5", "S6"});

        sendEvent("S7", 0);
        assertFalse(subscriber.isInvoked());
    }

    public void testOutputWhenBuiltInLastTimestamp()
    {
        sendTimeEvent(1, 8, 0, 0, 0);
        String expression = "select symbol from MarketData.win:length(2) output when current_timestamp - last_output_timestamp >= 2000";
        EPStatement stmt =  epService.getEPAdministrator().createEPL(expression);
        SupportSubscriber subscriber = new SupportSubscriber();
        stmt.setSubscriber(subscriber);

        sendEvent("S1", 0);

        sendTimeEvent(1, 8, 0, 1, 900);
        sendEvent("S2", 0);

        sendTimeEvent(1, 8, 0, 2, 0);
        assertFalse(subscriber.isInvoked());

        sendEvent("S3", 0);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetLastNewData(), new Object[] {"S1", "S2", "S3"});

        sendTimeEvent(1, 8, 0, 3, 0);
        sendEvent("S4", 0);

        sendTimeEvent(1, 8, 0, 3, 500);
        sendEvent("S5", 0);
        assertFalse(subscriber.isInvoked());

        sendTimeEvent(1, 8, 0, 4, 0);
        sendEvent("S6", 0);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetLastNewData(), new Object[] {"S4", "S5", "S6"});
    }

    private void sendEvent(String symbol, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendTimeEvent(int day, int hour, int minute, int second, int millis){
    	Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2008, 1, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, millis);
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(calendar.getTimeInMillis()));
    }

    public void testInvalid()
    {
        epService.getEPAdministrator().getConfiguration().addVariable("myvardummy", int.class, 0);
        epService.getEPAdministrator().getConfiguration().addVariable("myvarlong", long.class, 0);

        tryInvalid("select * from MarketData output when sum(price) > 0",
                   "Error validating expression: Property named 'price' is not valid in any stream [select * from MarketData output when sum(price) > 0]");

        tryInvalid("select * from MarketData output when sum(count_insert) > 0",
                   "Error validating expression: An aggregate function may not appear in a OUTPUT LIMIT clause [select * from MarketData output when sum(count_insert) > 0]");

        tryInvalid("select * from MarketData output when prev(1, count_insert) = 0",
                   "Error validating expression: Previous function cannot be used in this context [select * from MarketData output when prev(1, count_insert) = 0]");

        tryInvalid("select * from MarketData output when myvardummy",
                   "Error validating expression: The when-trigger expression in the OUTPUT WHEN clause must return a boolean-type value [select * from MarketData output when myvardummy]");

        tryInvalid("select * from MarketData output when true then set myvardummy = 'b'",
                   "Error starting view: Variable 'myvardummy' of declared type 'java.lang.Integer' cannot be assigned a value of type 'java.lang.String' [select * from MarketData output when true then set myvardummy = 'b']");

        tryInvalid("select * from MarketData output when true then set myvardummy = sum(myvardummy)",
                   "Error validating expression: An aggregate function may not appear in a OUTPUT LIMIT clause [select * from MarketData output when true then set myvardummy = sum(myvardummy)]");
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
}
