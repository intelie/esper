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

package com.espertech.esper.view.std;

import com.espertech.esper.support.view.SupportStatementContextFactory;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.view.ViewParameterException;
import junit.framework.TestCase;

public class TestUniqueByPropertyViewFactory extends TestCase
{
    private UniqueByPropertyViewFactory factory;

    public void setUp()
    {
        factory = new UniqueByPropertyViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter("longPrimitive", "longPrimitive");
        tryInvalidParameter(1.1d);
    }

    public void testCanReuse() throws Exception
    {
        factory.setViewParameters(null, TestViewSupport.toExprListBean(new Object[] {"intPrimitive"}));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportBean.class), SupportStatementContextFactory.makeContext(), null, null);
        assertFalse(factory.canReuse(new FirstElementView()));
        assertTrue(factory.canReuse(new UniqueByPropertyView(SupportExprNodeFactory.makeIdentNodesBean("intPrimitive"), null)));
        assertFalse(factory.canReuse(new UniqueByPropertyView(SupportExprNodeFactory.makeIdentNodesBean("intBoxed"), null)));
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            UniqueByPropertyViewFactory factory = new UniqueByPropertyViewFactory();
            factory.setViewParameters(null, TestViewSupport.toExprListBean(new Object[] {param}));
            factory.attach(SupportEventTypeFactory.createBeanType(SupportBean.class), SupportStatementContextFactory.makeContext(), null, null);
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object param, String fieldName) throws Exception
    {
        UniqueByPropertyViewFactory factory = new UniqueByPropertyViewFactory();
        factory.setViewParameters(null, TestViewSupport.toExprListBean(new Object[] {param}));
        factory.attach(SupportEventTypeFactory.createBeanType(SupportBean.class), SupportStatementContextFactory.makeContext(), null, null);
        UniqueByPropertyView view = (UniqueByPropertyView) factory.makeView(SupportStatementContextFactory.makeContext());
        assertEquals(fieldName, view.getCriteriaExpressions()[0].toExpressionString());
    }
}
