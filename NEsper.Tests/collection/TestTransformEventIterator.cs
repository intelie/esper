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

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.collection
{
	[TestFixture]
	public class TestTransformEventIterator
	{
	    [Test]
	    public void testEmpty()
	    {
	    	using(IEnumerator<EventBean> enumerator = MakeEnumerator(new int[0]))
	    	{
		        Assert.IsFalse(enumerator.MoveNext());
	    	}
	    }

	    [Test]
	    public void testOne()
	    {
	    	using(IEnumerator<EventBean> enumerator = MakeEnumerator(new int[] { 10 }))
	    	{
                Assert.IsTrue(enumerator.MoveNext());
                Assert.AreEqual(10, enumerator.Current["id"]);
                Assert.IsFalse(enumerator.MoveNext());
	    	}
	    }

	    [Test]
	    public void testTwo()
	    {
	    	using( IEnumerator<EventBean> enumerator = MakeEnumerator(new int[] { 10, 20 }) )
	    	{
                Assert.IsTrue(enumerator.MoveNext());
	    	    Assert.AreEqual(10, enumerator.Current["id"]);
                Assert.IsTrue(enumerator.MoveNext());
                Assert.AreEqual(20, enumerator.Current["id"]);
                Assert.IsFalse(enumerator.MoveNext());
	    	}
	    }

	    private IEnumerator<EventBean> MakeEnumerator(int[] values)
	    {
	        LinkedList<EventBean> events = new LinkedList<EventBean>();
	        for (int i = 0; i < values.Length; i++)
	        {
	            SupportBean bean = new SupportBean();
	            bean.SetIntPrimitive(values[i]);
	            EventBean _event = SupportEventBeanFactory.CreateObject(bean);
	            events.AddLast(_event);
	        }

	        foreach( EventBean _event in events )
	        {
	        	int value = (int) _event["intPrimitive"] ;
	            yield return SupportEventBeanFactory.CreateObject(new SupportBean_S0(value));
	        }
	    }
	}
} // End of namespace
