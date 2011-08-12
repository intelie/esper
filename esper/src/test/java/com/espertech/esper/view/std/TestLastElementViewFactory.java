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

import junit.framework.TestCase;

import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.TestViewSupport;
import com.espertech.esper.support.view.SupportStatementContextFactory;

public class TestLastElementViewFactory extends TestCase
{
    private LastElementViewFactory factory;

    public void setUp()
    {
        factory = new LastElementViewFactory();
    }

    public void testSetParameters() throws Exception
    {
        tryParameter(new Object[] {});
        tryInvalidParameter(1.1d);
    }

    public void testCanReuse() throws Exception
    {
        assertFalse(factory.canReuse(new FirstElementView()));
        assertTrue(factory.canReuse(new LastElementView()));
    }

    private void tryInvalidParameter(Object param) throws Exception
    {
        try
        {
            LastElementViewFactory factory = new LastElementViewFactory();
            factory.setViewParameters(SupportStatementContextFactory.makeViewContext(), TestViewSupport.toExprListBean(new Object[] {param}));
            fail();
        }
        catch (ViewParameterException ex)
        {
            // expected
        }
    }

    private void tryParameter(Object[] param) throws Exception
    {
        LastElementViewFactory factory = new LastElementViewFactory();
        factory.setViewParameters(SupportStatementContextFactory.makeViewContext(), TestViewSupport.toExprListBean(param));
        assertTrue(factory.makeView(SupportStatementContextFactory.makeContext()) instanceof LastElementView);
    }
}
