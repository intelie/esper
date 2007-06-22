// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.events
{
	/// <summary>
	/// Test for multithread-safety for manageing statements, i.e. creating and stopping statements
	/// </summary>
	[TestFixture]
	public class TestEventAdapterSvcMT
	{
	    private EventAdapterService service;

	    [SetUp]
	    public void SetUp()
	    {
	        service = new EventAdapterServiceImpl();
	    }

	    [Test]
	    public void TestAddBeanTypeClass()
	    {
	        Set<EventType> types = new HashSet<EventType>();

	        Callable[] callables = new Callable[2];
	        for (int i = 0; i < callables.Length; i++)
	        {
	            callables[i] =
	                new CallableImpl(
	                    new CallableDelegate(
	                        delegate()
	                            {
	                                EventType type = service.AddBeanType("a", typeof (SupportMarketDataBean));
	                                types.Add(type);

	                                type = service.AddBeanType("b", typeof (SupportMarketDataBean));
	                                types.Add(type);
	                                return true;
	                            }));
	        }

	        Object[] result = TryMT(callables);
	        ArrayAssertionUtil.AssertAllBooleanTrue(result);
	        Assert.AreEqual(1, types.Count);
	    }

	    [Test]
	    public void TestAddMapType()
	    {
	        EDictionary<String, Type> typeOne = new HashDictionary<String, Type>();
	        typeOne.Put("f1", typeof(int?));
	        EDictionary<String, Type> typeTwo= new HashDictionary<String, Type>();
	        typeTwo.Put("f2", typeof(int?));

	        Callable[] callables = new Callable[2];
	        for (int i = 0; i < callables.Length; i++)
	        {
	            int index = i;
	            callables[i] = new CallableImpl(
	                new CallableDelegate(
	                    delegate()
	                        {
	                            try
	                            {
	                                if (index == 0)
	                                {
	                                    return service.AddMapType("A", typeOne);
	                                }
	                                else
	                                {
	                                    return service.AddMapType("A", typeTwo);
	                                }
	                            }
	                            catch (EventAdapterException ex)
	                            {
	                                return ex;
	                            }
	                        }));
	        }

	        // the result should be one exception and one type
	        Object[] results = TryMT(callables);
	        ArrayAssertionUtil.AssertTypeEqualsAnyOrder(
	        	new Type[] {typeof(EventAdapterException), typeof(MapEventType)}, results);
	    }

	    [Test]
	    public void TestAddBeanType()
	    {
	        EDictionary<String, Type> typeOne = new HashDictionary<String, Type>();
	        typeOne.Put("f1", typeof(int?));

	        Callable[] callables = new Callable[2];
	        for (int i = 0; i < callables.Length; i++)
	        {
	            int index = i;
	            callables[i] = new CallableImpl(
	                new CallableDelegate(
	                    delegate()
	                        {
	                            try
	                            {
	                                if (index == 0)
	                                {
	                                    return service.AddBeanType("X", typeof (SupportBean_S1));
	                                }
	                                else
	                                {
	                                    return service.AddBeanType("X", typeof (SupportBean_S0));
	                                }
	                            }
	                            catch (EventAdapterException ex)
	                            {
	                                return ex;
	                            }
	                        }));
	        }

	        // the result should be one exception and one type
	        Object[] results = TryMT(callables);
	        ArrayAssertionUtil.AssertTypeEqualsAnyOrder(new Type[] {typeof(EventAdapterException), typeof(BeanEventType)}, results);
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
