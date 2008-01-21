package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.SupportStmtAwareUpdateListener;
import net.esper.event.EventBean;

import java.util.List;
import java.util.ArrayList;

public class TestSubscriber extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        String pkg = SupportBean.class.getPackage().getName();
        config.addEventTypeAutoAlias(pkg);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testSimpleSelect()
    {
        String fields[] = "string,intPrimitive".split(",");

        MySimpleSubscriber subscriber = new MySimpleSubscriber();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select string, intPrimitive from " + SupportBean.class.getName());
        stmt.setSubscriber(subscriber);

        // get statement, attach listener
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 100));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateSimple(), new Object[][] {{"E1", 100}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1", 100}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 100});

        // remove listener
        stmt.removeAllListeners();

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 200));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateSimple(), new Object[][] {{"E2", 200}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2", 200}});
        assertFalse(listener.isInvoked());

        // add listener
        SupportStmtAwareUpdateListener stmtAwareListener = new SupportStmtAwareUpdateListener();
        stmt.addListener(stmtAwareListener);

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 300));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateSimple(), new Object[][] {{"E3", 300}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E3", 300}});
        ArrayAssertionUtil.assertProps(stmtAwareListener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 300});

        // send no-pass event
        epService.getEPRuntime().sendEvent(new SupportBean("E4", -1));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateSimple(), null);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E3", 300}});
        assertFalse(stmtAwareListener.isInvoked());

        // remove

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1000));
        assertEquals(0, subscriber.getAndResetIndicateSimple().size());
    }
}
