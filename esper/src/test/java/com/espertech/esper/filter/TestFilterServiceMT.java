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

package com.espertech.esper.filter;

import junit.framework.TestCase;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.filter.SupportFilterSpecBuilder;
import com.espertech.esper.support.filter.SupportFilterHandle;
import com.espertech.esper.client.EventType;

import java.util.concurrent.*;

/**
 * Test for multithread-safety for manageing statements, i.e. creating and stopping statements
 */
public class TestFilterServiceMT extends TestCase
{
    private FilterService service;

    public void setUp()
    {
        service = new FilterServiceImpl();
    }

    public void testAddRemoveFilter() throws Exception
    {
        EventType eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
        FilterSpecCompiled spec = SupportFilterSpecBuilder.build(eventType, new Object[] {"string", FilterOperator.EQUAL, "HELLO"});
        final FilterValueSet filterValues = spec.getValueSet(null);

        Callable callables[] = new Callable[5];
        for (int i = 0; i < callables.length; i++)
        {
            callables[i] = new Callable()
            {
                public Object call() throws Exception
                {
                    SupportFilterHandle handle = new SupportFilterHandle();
                    for (int i = 0; i < 10000; i++)
                    {
                        service.add(filterValues, handle);
                        service.remove(handle);
                    }
                    return true;
                }
            };
        }

        Object[] result = tryMT(callables);
        ArrayAssertionUtil.assertAllBooleanTrue(result);
    }

    private Object[] tryMT(Callable[] callables) throws Exception
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(callables.length);

        Future futures[] = new Future[callables.length];
        for (int i = 0; i < callables.length; i++)
        {
            futures[i] = threadPool.submit(callables[i]);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.SECONDS);

        Object[] results = new Object[futures.length];
        for (int i = 0; i < futures.length; i++)
        {
            results[i] = futures[i].get();
        }
        return results;
    }

    private static interface CallableFactory
    {
        public Callable makeCallable(int threadNum);
    }
}
