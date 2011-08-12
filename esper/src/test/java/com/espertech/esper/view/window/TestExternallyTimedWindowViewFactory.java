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

package com.espertech.esper.view.window;

import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.std.FirstElementView;
import junit.framework.TestCase;

public class TestExternallyTimedWindowViewFactory extends TestCase
{
    private ExternallyTimedWindowViewFactory factory;

    public void setUp()
    {
        factory = new ExternallyTimedWindowViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {"longPrimitive", 2d}, "longPrimitive", 2000);
        tryParameter(new Object[] {"longPrimitive", 10L}, "longPrimitive", 10000);
        tryParameter(new Object[] {"longPrimitive", 11}, "longPrimitive", 11000);
        tryParameter(new Object[] {"longPrimitive", 2.2}, "longPrimitive", 2200);

        tryInvalidParameter(new Object[] {"a"});
    }

    public void testCanReuse() throws Exception
    {
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportBean.class);

        factory.setViewParameters(SupportStatementContextFactory.makeViewContext(), TestViewSupport.toExprListBean(new Object[] {"longBoxed", 1000}));
        factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
        assertFalse(factory.canReuse(new FirstElementView()));
        assertFalse(factory.canReuse(new ExternallyTimedWindowView(factory, SupportExprNodeFactory.makeIdentNodeBean("longPrimitive"), null, 1000, null, false, null)));
        assertFalse(factory.canReuse(new ExternallyTimedWindowView(factory, SupportExprNodeFactory.makeIdentNodeBean("longBoxed"), null, 999, null, false, null)));
        assertTrue(factory.canReuse(new ExternallyTimedWindowView(factory, SupportExprNodeFactory.makeIdentNodeBean("longBoxed"), null, 1000000, null, false, null)));
    }

    public void testInvalid() throws Exception
    {
        EventType parentType = SupportEventTypeFactory.createBeanType(SupportBean.class);

        try
        {
            factory.setViewParameters(null, TestViewSupport.toExprListBean(new Object[] {50, 20}));
            factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }

        try
        {
            factory.setViewParameters(null, TestViewSupport.toExprListBean(new Object[] {"string", 20}));
            factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }

        factory.setViewParameters(null, TestViewSupport.toExprListBean(new Object[] {"longPrimitive", 20}));
        factory.attach(parentType, SupportStatementContextFactory.makeContext(), null, null);

        assertSame(parentType, factory.getEventType());
    }
    
    private void tryInvalidParameter(Object[] param) throws Exception
    {
        try
        {
            ExternallyTimedWindowViewFactory factory = new ExternallyTimedWindowViewFactory();
            factory.setViewParameters(null, TestViewSupport.toExprListBean(new Object[] {param}));
            factory.attach(SupportEventTypeFactory.createBeanType(SupportBean.class), SupportStatementContextFactory.makeContext(), null, null);
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] params, String fieldName, long msec) throws Exception
    {
        ExternallyTimedWindowViewFactory factory = new ExternallyTimedWindowViewFactory();
        factory.setViewParameters(SupportStatementContextFactory.makeViewContext(), TestViewSupport.toExprListBean(params));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportBean.class), SupportStatementContextFactory.makeContext(), null, null);
        ExternallyTimedWindowView view = (ExternallyTimedWindowView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldName, view.getTimestampExpression().toExpressionString());
        assertEquals(msec, view.getMillisecondsBeforeExpiry());
    }
}
