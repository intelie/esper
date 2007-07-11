///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.support.view;

namespace net.esper.view.window
{
	[TestFixture]
	public class TestExternallyTimedWindowView
	{
	    private ExternallyTimedWindowView myView;
	    private SupportBeanClassView childView;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set up timed window view and a test child view, set the time window size to 1 second
	        myView = new ExternallyTimedWindowView(null, "timestamp", 1000, null);
	        childView = new SupportBeanClassView(typeof(SupportBeanTimestamp));
	        myView.AddView(childView);
	    }

	    [Test]
	    public void testIncorrectUse()
	    {
	        try
	        {
	            myView = new ExternallyTimedWindowView(null, "goodie", 0, null);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected exception
	        }
	    }

	    [Test]
	    public void testViewPush()
	    {
	        // Set up a feed for the view under test - it will have a depth of 3 trades
	        SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportBeanTimestamp), 3);
	        stream.AddView(myView);

	        EventBean[] a = MakeBeans("a", 10000, 1);
	        stream.Insert(a);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { a[0] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { a[0] });

	        EventBean[] b = MakeBeans("b", 10500, 2);
	        stream.Insert(b);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { b[0], b[1] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { a[0], b[0], b[1]  });

	        EventBean[] c = MakeBeans("c", 10900, 1);
	        stream.Insert(c);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { c[0] });
            ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { a[0], b[0], b[1], c[0] });

	        EventBean[] d = MakeBeans("d", 10999, 1);
	        stream.Insert(d);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { d[0] });
            ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { a[0], b[0], b[1], c[0], d[0] });

	        EventBean[] e = MakeBeans("e", 11000, 2);
	        stream.Insert(e);
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { a[0] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { e[0], e[1] });
            ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { b[0], b[1], c[0], d[0], e[0], e[1] });

	        EventBean[] f = MakeBeans("f", 11500, 1);
	        stream.Insert(f);
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { b[0], b[1] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { f[0] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[] { c[0], d[0], e[0], e[1], f[0] });

	        EventBean[] g = MakeBeans("g", 11899, 1);
	        stream.Insert(g);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { g[0] });
            ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { c[0], d[0], e[0], e[1], f[0], g[0] });

	        EventBean[] h = MakeBeans("h", 11999, 3);
	        stream.Insert(h);
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { c[0], d[0] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { h[0], h[1], h[2] });
            ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { e[0], e[1], f[0], g[0], h[0], h[1], h[2] });

	        EventBean[] i = MakeBeans("i", 13001, 1);
	        stream.Insert(i);
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { e[0], e[1], f[0], g[0], h[0], h[1], h[2] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { i[0] });
            ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { i[0] });
	    }

	    private EventBean[] MakeBeans(String id, long timestamp, int numBeans)
	    {
	        EventBean[] beans = new EventBean[numBeans];
	        for (int i = 0; i < numBeans; i++)
	        {
	            SupportBeanTimestamp bean = new SupportBeanTimestamp(id + i, timestamp);
	            beans[i] = SupportEventBeanFactory.CreateObject(bean);
	        }
	        return beans;
	    }
	}
} // End of namespace
