package net.esper.eql.core;

import net.esper.view.ViewFactoryChain;
import net.esper.view.ViewFactory;
import net.esper.view.ViewCapability;
import net.esper.view.ViewCapDataWindowAccess;
import net.esper.view.std.SizeViewFactory;
import net.esper.view.window.TimeWindowViewFactory;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.bean.SupportBean;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class TestViewFactoryDelegateImpl extends TestCase
{
    private ViewResourceDelegate delegate;

    public void setUp()
    {
        ViewFactoryChain[] factories = new ViewFactoryChain[2];

        ViewFactory factory1 = new TimeWindowViewFactory();
        ViewFactory factory2 = new SizeViewFactory();
        factories[0] = new ViewFactoryChain(SupportEventTypeFactory.createBeanType(SupportBean.class),
                Arrays.asList(new ViewFactory[] {factory1, factory2}));
        factories[1] = new ViewFactoryChain(SupportEventTypeFactory.createBeanType(SupportBean.class),
                Arrays.asList(new ViewFactory[] {factory1}));

        delegate = new ViewResourceDelegateImpl(factories);
    }

    public void testRequest()
    {
        ViewResourceCallback callback = new ViewResourceCallback() {

            public void setViewResource(Object resource)
            {
            }
        };
        assertFalse(delegate.requestCapability(1, new SupportViewCapability(), callback));
        assertFalse(delegate.requestCapability(0, new ViewCapDataWindowAccess(1), callback));
        assertTrue(delegate.requestCapability(1, new ViewCapDataWindowAccess(1), callback));
    }

    private class SupportViewCapability implements ViewCapability
    {
        public boolean inspect(List<ViewFactory> viewFactories)
        {
            return true;
        }
    }
}

