package net.esper.eql.core;

import net.esper.view.*;
import net.esper.view.std.SizeViewFactory;
import net.esper.view.window.TimeWindowViewFactory;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.bean.SupportBean;
import net.esper.core.StatementContext;

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

        delegate = new ViewResourceDelegateImpl(factories, null);
    }

    public void testRequest() throws Exception
    {
        ViewResourceCallback callback = new ViewResourceCallback() {

            public void setViewResource(Object resource)
            {
            }
        };
        assertFalse(delegate.requestCapability(1, new SupportViewCapability(), callback));
        assertFalse(delegate.requestCapability(0, new ViewCapDataWindowAccess(), callback));
        assertTrue(delegate.requestCapability(1, new ViewCapDataWindowAccess(), callback));
    }

    private class SupportViewCapability implements ViewCapability
    {
        public boolean inspect(int streamNumber, List<ViewFactory> viewFactories, StatementContext statementContext)
        {
            return true;
        }

        public boolean requiresChildViews()
        {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}

