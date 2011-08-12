/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.view.stat;

import com.espertech.esper.view.ViewFactoryContext;
import junit.framework.TestCase;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.view.ViewFieldEnum;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.std.FirstElementView;

public class TestCorrelationViewFactory extends TestCase
{
    private CorrelationViewFactory factory;
    private ViewFactoryContext viewFactoryContext = new ViewFactoryContext(null, 1, 1, null, null);

    public void setUp()
    {
        factory = new CorrelationViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {"price", "volume"}, "price", "volume");

        tryInvalidParameter(new Object[] {"symbol", 1.1d});
        tryInvalidParameter(new Object[] {1.1d, "symbol"});
        tryInvalidParameter(new Object[] {1.1d});
        tryInvalidParameter(new Object[] {"symbol", "symbol", "symbol"});
        tryInvalidParameter(new Object[] {new String[] {"symbol", "feed"}});
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(new ViewFactoryContext(null, 1, 1, null, null), TestViewSupport.toExprListMD(new Object[] {"price", "volume"}));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
        assertFalse(factory.canReuse(new FirstElementView()));
        EventType type = CorrelationView.createEventType(SupportStatementContextFactory.makeContext(), null, 1);
        assertFalse(factory.canReuse(new CorrelationView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodeMD("volume"), SupportExprNodeFactory.makeIdentNodeMD("price"), type, null)));
        assertFalse(factory.canReuse(new CorrelationView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodeMD("feed"), SupportExprNodeFactory.makeIdentNodeMD("volume"), type, null)));
        assertTrue(factory.canReuse(new CorrelationView(SupportStatementContextFactory.makeContext(), SupportExprNodeFactory.makeIdentNodeMD("price"), SupportExprNodeFactory.makeIdentNodeMD("volume"), type, null)));
    }

    public void testAttaches() throws Exception
    {
        // Should attach to anything as long as the fields exists
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        factory.setViewParameters(viewFactoryContext, TestViewSupport.toExprListMD(new Object[] {"price", "volume"}));
        factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
        assertEquals(Double.class, factory.getEventType().getPropertyType(ViewFieldEnum.CORRELATION__CORRELATION.getName()));

        try
        {
            factory.setViewParameters(viewFactoryContext, TestViewSupport.toExprListMD(new Object[] {"symbol", "volume"}));
            factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected;
        }
    }

    private void tryInvalidParameter(Object[] params) throws Exception
    {
        try
        {
            factory.setViewParameters(viewFactoryContext, TestViewSupport.toExprListMD(params));
            factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String fieldNameX, String fieldNameY) throws Exception
    {
        factory.setViewParameters(viewFactoryContext, TestViewSupport.toExprListMD(params));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class), SupportStatementContextFactory.makeContext(), null, null);
        CorrelationView view = (CorrelationView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldNameX, view.getExpressionX().toExpressionString());
        assertEquals(fieldNameY, view.getExpressionY().toExpressionString());
    }
}
