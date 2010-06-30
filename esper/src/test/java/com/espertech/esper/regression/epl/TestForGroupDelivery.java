package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportEnum;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportSubscriber;
import com.espertech.esper.support.util.SupportSubscriberMRD;
import junit.framework.TestCase;

public class TestForGroupDelivery extends TestCase
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

    public void testInvalid() {
        tryInvalid("select * from SupportBean for ",
                "Incorrect syntax near ' ' ('for' is a reserved keyword) expecting an identifier but found end of input at line 1 column 29 [select * from SupportBean for ]");

        tryInvalid("select * from SupportBean for other_keyword",
                "Error starting statement: Expected any of the [grouped_delivery, discrete_delivery] for-clause keywords after reserved keyword 'for' [select * from SupportBean for other_keyword]");

        tryInvalid("select * from SupportBean for grouped_delivery",
                "Error starting statement: The for-clause with the grouped_delivery keyword requires one or more grouping expressions [select * from SupportBean for grouped_delivery]");

        tryInvalid("select * from SupportBean for grouped_delivery()",
                "Error starting statement: The for-clause with the grouped_delivery keyword requires one or more grouping expressions [select * from SupportBean for grouped_delivery()]");

        tryInvalid("select * from SupportBean for grouped_delivery(dummy)",
                "Error starting statement: Property named 'dummy' is not valid in any stream [select * from SupportBean for grouped_delivery(dummy)]");

        tryInvalid("select * from SupportBean for discrete_delivery(dummy)", 
                "Error starting statement: The for-clause with the discrete_delivery keyword does not allow grouping expressions [select * from SupportBean for discrete_delivery(dummy)]");

        tryInvalid("select * from SupportBean for discrete_delivery for grouped_delivery(intPrimitive)",
                "Incorrect syntax near 'for' (a reserved keyword) expecting end of input but found 'for' at line 1 column 48 [select * from SupportBean for discrete_delivery for grouped_delivery(intPrimitive)]");
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

    public void testSubscriberOnly()
    {
        SupportSubscriberMRD subscriber = new SupportSubscriberMRD();
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream string,intPrimitive from SupportBean.win:time_batch(1) for discrete_delivery");
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 1));
        sendTimer(1000);
        assertEquals(3, subscriber.getInsertStreamList().size());
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getInsertStreamList().get(0)[0], new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getInsertStreamList().get(1)[0], new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getInsertStreamList().get(2)[0], new Object[] {"E3", 1});

        stmt.destroy();
        subscriber.reset();
        stmt = epService.getEPAdministrator().createEPL("select irstream string,intPrimitive from SupportBean.win:time_batch(1) for grouped_delivery(intPrimitive)");
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 1));
        sendTimer(2000);
        assertEquals(2, subscriber.getInsertStreamList().size());
        assertEquals(2, subscriber.getRemoveStreamList().size());
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getInsertStreamList().get(0)[0], new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getInsertStreamList().get(0)[1], new Object[] {"E3", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getInsertStreamList().get(1)[0], new Object[] {"E2", 2});
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getRemoveStreamList().get(0)[0], new Object[] {"E1", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getRemoveStreamList().get(0)[1], new Object[] {"E3", 1});
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getRemoveStreamList().get(1)[0], new Object[] {"E2", 2});
    }

    public void testDiscreteDelivery()
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBean.win:time_batch(1) for discrete_delivery");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 1));
        sendTimer(1000);
        assertEquals(3, listener.getNewDataList().size());
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(0), "string,intPrimitive".split(","), new Object[][] {{"E1", 1}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(1), "string,intPrimitive".split(","), new Object[][] {{"E2", 2}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(2), "string,intPrimitive".split(","), new Object[][] {{"E3", 1}});
    }

    public void testGroupDelivery()
    {
        sendTimer(0);
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBean.win:time_batch(1) for grouped_delivery (intPrimitive)");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 1));
        sendTimer(1000);
        assertEquals(2, listener.getNewDataList().size());
        assertEquals(2, listener.getNewDataList().get(0).length);
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(0), "string,intPrimitive".split(","), new Object[][] {{"E1", 1}, {"E3", 1}});
        assertEquals(1, listener.getNewDataList().get(1).length);
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(1), "string,intPrimitive".split(","), new Object[][] {{"E2", 2}});

        // test sorted
        stmt.destroy();
        stmt = epService.getEPAdministrator().createEPL("select * from SupportBean.win:time_batch(1) order by intPrimitive desc for grouped_delivery (intPrimitive)");
        stmt.addListener(listener);
        listener.reset();

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 1));
        sendTimer(2000);
        assertEquals(2, listener.getNewDataList().size());
        assertEquals(1, listener.getNewDataList().get(0).length);
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(0), "string,intPrimitive".split(","), new Object[][] {{"E2", 2}});
        assertEquals(2, listener.getNewDataList().get(1).length);
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(1), "string,intPrimitive".split(","), new Object[][] {{"E1", 1}, {"E3", 1}});

        // test multiple criteria
        stmt.destroy();
        String stmtText = "select string, doubleBoxed, enumValue from SupportBean.win:time_batch(1) order by string, doubleBoxed, enumValue for grouped_delivery(doubleBoxed, enumValue)";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);
        listener.reset();

        sendEvent("E1", 10d, SupportEnum.ENUM_VALUE_2); // A (1)
        sendEvent("E2", 11d, SupportEnum.ENUM_VALUE_1); // B (2)
        sendEvent("E3", 9d, SupportEnum.ENUM_VALUE_2);  // C (3)
        sendEvent("E4", 10d, SupportEnum.ENUM_VALUE_2); // A
        sendEvent("E5", 10d, SupportEnum.ENUM_VALUE_1); // D (4)
        sendEvent("E6", 10d, SupportEnum.ENUM_VALUE_1); // D
        sendEvent("E7", 11d, SupportEnum.ENUM_VALUE_1); // B
        sendEvent("E8", 10d, SupportEnum.ENUM_VALUE_1); // D
        sendTimer(3000);
        assertEquals(4, listener.getNewDataList().size());
        String[] fields = "string,doubleBoxed,enumValue".split(",");
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(0), fields,
                new Object[][] {{"E1", 10d, SupportEnum.ENUM_VALUE_2}, {"E4", 10d, SupportEnum.ENUM_VALUE_2}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(1), fields,
                new Object[][] {{"E2", 11d, SupportEnum.ENUM_VALUE_1}, {"E7", 11d, SupportEnum.ENUM_VALUE_1}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(2), fields,
                new Object[][] {{"E3", 9d, SupportEnum.ENUM_VALUE_2}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(3), fields,
                new Object[][] {{"E5", 10d, SupportEnum.ENUM_VALUE_1}, {"E6", 10d, SupportEnum.ENUM_VALUE_1}, {"E8", 10d, SupportEnum.ENUM_VALUE_1}});
        
        // test SODA
        stmt.destroy();
        listener.reset();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
        assertEquals(stmtText, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        
        sendEvent("E1", 10d, SupportEnum.ENUM_VALUE_2); // A (1)
        sendEvent("E2", 11d, SupportEnum.ENUM_VALUE_1); // B (2)
        sendEvent("E3", 11d, SupportEnum.ENUM_VALUE_1); // B (2)
        sendTimer(4000);
        assertEquals(2, listener.getNewDataList().size());
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(0), fields,
                new Object[][] {{"E1", 10d, SupportEnum.ENUM_VALUE_2}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataList().get(1), fields,
                new Object[][] {{"E2", 11d, SupportEnum.ENUM_VALUE_1}, {"E3", 11d, SupportEnum.ENUM_VALUE_1}});
    }

    private void sendTimer(long timeInMSec)
    {
        CurrentTimeEvent event = new CurrentTimeEvent(timeInMSec);
        EPRuntime runtime = epService.getEPRuntime();
        runtime.sendEvent(event);
    }

    private void sendEvent(String string, Double doubleBoxed, SupportEnum enumVal) {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setDoubleBoxed(doubleBoxed);
        bean.setEnumValue(enumVal);
        epService.getEPRuntime().sendEvent(bean);
    }
}