package net.esper.eql.core;

import net.esper.view.ViewFactoryChain;
import net.esper.view.ViewFactory;
import net.esper.view.ViewCapability;
import net.esper.view.ViewCapabilityRandomAccess;
import net.esper.view.std.SizeViewFactory;
import net.esper.view.window.TimeWindowViewFactory;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.bean.SupportBean;

import java.util.Arrays;

import junit.framework.TestCase;

public class TestViewFactoryDelegateImpl extends TestCase
{
    private ViewFactoryDelegate delegate;

    public void setUp()
    {
        ViewFactoryChain[] factories = new ViewFactoryChain[2];

        ViewFactory factory1 = new TimeWindowViewFactory();
        ViewFactory factory2 = new SizeViewFactory();
        factories[1] = new ViewFactoryChain(SupportEventTypeFactory.createBeanType(SupportBean.class),
                Arrays.asList(new ViewFactory[] {factory1, factory2}));

        delegate = new ViewFactoryDelegateImpl(factories);
    }

    public void testRequest()
    {
        assertFalse(delegate.requestCapability(1, new SupportViewCapability(), null));
        assertTrue(delegate.requestCapability(1, new ViewCapabilityRandomAccess(1), null));
    }

    private class SupportViewCapability implements ViewCapability {}
}

