// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;
using net.esper.support.util;

namespace net.esper.filter
{
	/// <summary>
	/// Test for multithread-safety for manageing statements, i.e. creating and stopping statements
	/// </summary>
	[TestFixture]
	public class TestFilterServiceMT
	{
	    private FilterService service;

        class MyFilterServiceImpl : FilterServiceImpl {}

	    [SetUp]
	    public void SetUp()
	    {
	        service = new MyFilterServiceImpl();
	    }

	    [Test]
	    public void TestAddRemoveFilter()
	    {
	        EventType eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
	        FilterSpecCompiled spec = SupportFilterSpecBuilder.Build(eventType, new Object[] {"string", FilterOperator.EQUAL, "HELLO"});
	        FilterValueSet filterValues = spec.GetValueSet(null);

	        Callable[] callables = new Callable[5];
	        for (int i = 0; i < callables.Length; i++)
	        {
	            callables[i] =
	                new CallableImpl(
	                    new CallableDelegate(
	                        delegate()
	                            {
	                                SupportFilterHandle handle = new SupportFilterHandle();
	                                for (int j = 0; j < 10000; j++)
	                                {
	                                    service.Add(filterValues, handle);
	                                    service.Remove(handle);
	                                }
	                                return true;
	                            }));
	        }

	        Object[] result = TryMT(callables);
	        ArrayAssertionUtil.AssertAllBooleanTrue(result);
	    }

	    private Object[] TryMT(Callable[] callables)
	    {
	        ExecutorService threadPool = Executors.NewFixedThreadPool(callables.Length);

	        Future[] futures = new Future[callables.Length];
	        for (int i = 0; i < callables.Length; i++)
	        {
	            futures[i] = threadPool.Submit(callables[i]);
	        }

	        threadPool.Shutdown();
	        threadPool.AwaitTermination(new TimeSpan(0, 0, 10));

	        Object[] results = new Object[futures.Length];
	        for (int i = 0; i < futures.Length; i++)
	        {
	            results[i] = futures[i].Get();
	        }
	        return results;
	    }

	    private interface CallableFactory
	    {
	        Callable MakeCallable(int threadNum);
	    }
	}
} // End of namespace
