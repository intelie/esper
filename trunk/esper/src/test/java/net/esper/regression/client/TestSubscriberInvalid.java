package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;

public class TestSubscriberInvalid extends TestCase
{
    private EPServiceProvider epService;
    private EPAdministrator epAdmin;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        String pkg = SupportBean.class.getPackage().getName();
        config.addEventTypeAutoAlias(pkg);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        epAdmin = epService.getEPAdministrator();
    }

    public void testBindWildcardJoin()
    {
        EPStatement stmt = epAdmin.createEQL("select * from SupportBean");
        tryInvalid(this, stmt, "Subscriber object does not provide a public method by name 'update'");
        tryInvalid(new DummySubscriberEmptyUpd(), stmt, "No suitable subscriber method named 'update' found, expecting a method that takes 1 parameter of type SupportBean");
        tryInvalid(new DummySubscriberMultipleUpdate(), stmt, "No suitable subscriber method named 'update' found, expecting a method that takes 1 parameter of type SupportBean");
        tryInvalid(new DummySubscriberUpdate(), stmt, "Subscriber method named 'update' for parameter number 1 is not assignable, expecting type 'SupportBean' but found type 'SupportMarketDataBean'");
        tryInvalid(new DummySubscriberPrivateUpd(), stmt, "Subscriber object does not provide a public method by name 'update'");
    }

    public void testInvocationTargetEx()
    {
        // smoke test, need to consider log file
        EPStatement stmt = epAdmin.createEQL("select * from SupportMarketDataBean");
        stmt.setSubscriber(new DummySubscriberException());

        try
        {
            epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 0, 0L, ""));
            fail();
        }
        catch (EPException ex)
        {
            // expected
            assertEquals(EPException.class.getName() + ": Invocation exception when invoking method 'update' on subscriber class 'DummySubscriberException' for parameters [SupportMarketDataBean symbol=IBM price=0.0 volume=0 feed=] : RuntimeException : DummySubscriberException-generated", ex.getMessage().trim());
        }
    }

    private void tryInvalid(Object subscriber, EPStatement stmt, String message)
    {
        try
        {
            stmt.setSubscriber(subscriber);
            fail();
        }
        catch (EPSubscriberException ex)
        {
            assertEquals(message, ex.getMessage());
        }
    }

    public class DummySubscriberException
    {
        public void update(SupportMarketDataBean bean) {
            throw new RuntimeException("DummySubscriberException-generated");
        }
    }

    public class DummySubscriberEmptyUpd
    {
        public void update() {}
    }

    public class DummySubscriberPrivateUpd
    {
        private void update(SupportBean bean) {}
    }

    public class DummySubscriberUpdate
    {
        public void update(SupportMarketDataBean dummy) {}
    }

    public class DummySubscriberMultipleUpdate
    {
        public void update(long x) {}
        public void update(int x) {}
    }
}
