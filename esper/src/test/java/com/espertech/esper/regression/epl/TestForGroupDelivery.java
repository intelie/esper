package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportEnum;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
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

    // TODO: split keywords? "for grouped delivery"
    // TODO: test pattern as per doc
    // TODO: test subscriber+listener; subscriber alone
    // TODO: test remove stream
    // TODO : test SODA
    // TODO : test invalid keyword; invalid criteria not in select clause, missing expression, invalid expression
    // TODO : add to GUI

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
        stmt = epService.getEPAdministrator().createEPL("select string, doubleBoxed, enumValue from SupportBean.win:time_batch(1) order by string, doubleBoxed, enumValue asc for grouped_delivery (doubleBoxed, enumValue)");
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