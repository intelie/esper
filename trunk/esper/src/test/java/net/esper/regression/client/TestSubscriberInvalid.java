package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.bean.*;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.SupportStmtAwareUpdateListener;
import net.esper.collection.UniformPair;

import java.util.Map;

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
        tryInvalid(this, stmt, "Subscriber object does not provide a method by name 'update'");
        tryInvalid(new DummySubscriberEmptyUpd(), stmt, "Subscriber method named 'update' does not take the expected number of parameters, expecting 1 parameters but found 0");
        tryInvalid(new DummySubscriberMultipleUpdate(), stmt, "");
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

    public class DummySubscriberEmptyUpd
    {
        public void update() {}
    }

    public class DummySubscriberMultipleUpdate
    {
        public void update(long x) {}
        public void update(int x) {}
    }
}
