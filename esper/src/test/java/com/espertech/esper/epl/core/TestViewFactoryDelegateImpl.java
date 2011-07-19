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

package com.espertech.esper.epl.core;

import com.espertech.esper.view.*;
import com.espertech.esper.view.std.FirstElementViewFactory;
import com.espertech.esper.view.window.TimeWindowViewFactory;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.core.StatementContext;

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
        ViewFactory factory2 = new FirstElementViewFactory();
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
            return false;
        }

        public boolean appliesToChildViews()
        {
            return false;
        }
    }
}

