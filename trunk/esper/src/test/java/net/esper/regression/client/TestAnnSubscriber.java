package net.esper.regression.client;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.bean.SupportBean;
import net.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

public class TestAnnSubscriber extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testSimpleSubscribe()
    {
        MyAnnotatedSubscriber subscriber = new MyAnnotatedSubscriber();
        epService.getEPRuntime().write(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(subscriber.getAndReset().get(0), new String[] {"string"}, "E1");

        epService.getEPRuntime().take(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1));
        assertEquals(0, subscriber.getAndReset().size());
    }
}
