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

namespace net.esper.view.ext
{
	[TestFixture]
	public class TestSortWindowView
	{
	    private SortWindowView myView;
	    private SupportBeanClassView childView;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set up length window view and a test child view
	        myView = new SortWindowView(null, new String[]{"volume"}, new Boolean[] {false}, 5, null);
	        childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.AddView(childView);
	    }

	    [Test]
	    public void testViewOneProperty()
	    {
	        // Set up a feed for the view under test - the depth is 10 events so bean[10] will cause bean[0] to go old
	        SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 10);
	        stream.AddView(myView);

	        EventBean[] bean = new EventBean[12];

	        bean[0] = MakeBean(1000);
	        stream.Insert(bean[0]);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { bean[0] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { bean[0] });

	        bean[1] = MakeBean(800);
	        bean[2] = MakeBean(1200);
	        stream.Insert(new EventBean[] { bean[1], bean[2] });
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { bean[1], bean[2] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { bean[1], bean[0], bean[2] });

	        bean[3] = MakeBean(1200);
	        bean[4] = MakeBean(1000);
	        bean[5] = MakeBean(1400);
	        bean[6] = MakeBean(1100);
	        stream.Insert(new EventBean[] { bean[3], bean[4], bean[5], bean[6] });
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { bean[5], bean[2] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { bean[3], bean[4], bean[5], bean[6] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { bean[1], bean[4], bean[0], bean[6], bean[3] });

	        bean[7] = MakeBean(800);
	        bean[8] = MakeBean(700);
	        bean[9] = MakeBean(1200);
	        stream.Insert(new EventBean[] { bean[7], bean[8], bean[9] });
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { bean[3], bean[9], bean[6] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { bean[7], bean[8], bean[9] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { bean[8], bean[7], bean[1], bean[4], bean[0] });

	        bean[10] = MakeBean(1050);
	        stream.Insert(new EventBean[] { bean[10] });       // Thus bean[0] will be old data !
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { bean[0] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { bean[10] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { bean[8], bean[7], bean[1], bean[4], bean[10] });

	        bean[11] = MakeBean(2000);
	        stream.Insert(new EventBean[] { bean[11] });       // Thus bean[1] will be old data !
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { bean[1] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { bean[11] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { bean[8], bean[7], bean[4], bean[10], bean[11] });
	    }

	    [Test]
	    public void testViewTwoProperties()
	    {
	    	// Set up a sort windows that sorts on two properties
	    	myView = new SortWindowView(null, new String[]{"volume", "price"}, new Boolean[] {false, true}, 5, null);
	        childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.AddView(childView);

	        // Set up a feed for the view under test - the depth is 10 events so bean[10] will cause bean[0] to go old
	        SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 10);
	        stream.AddView(myView);

	        EventBean[] bean = new EventBean[12];

	        bean[0] = MakeBean(20d, 1000);
	        stream.Insert(bean[0]);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { bean[0] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { bean[0] });

	        bean[1] = MakeBean(19d, 800);
	        bean[2] = MakeBean(18d, 1200);
	        stream.Insert(new EventBean[] { bean[1], bean[2] });
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { bean[1], bean[2] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { bean[1], bean[0], bean[2] });

	        bean[3] = MakeBean(17d, 1200);
	        bean[4] = MakeBean(16d, 1000);
	        bean[5] = MakeBean(15d, 1400);
	        bean[6] = MakeBean(14d, 1100);
	        stream.Insert(new EventBean[] { bean[3], bean[4], bean[5], bean[6] });
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { bean[5], bean[3] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { bean[3], bean[4], bean[5], bean[6] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { bean[1], bean[0], bean[4], bean[6], bean[2] });

	        bean[7] = MakeBean(13d, 800);
	        bean[8] = MakeBean(12d, 700);
	        bean[9] = MakeBean(11d, 1200);
	        stream.Insert(new EventBean[] { bean[7], bean[8], bean[9] });
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { bean[9], bean[2], bean[6] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { bean[7], bean[8], bean[9] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { bean[8], bean[1], bean[7], bean[0], bean[4] });

	        bean[10] = MakeBean(10d, 1050);
	        stream.Insert(new EventBean[] { bean[10] });       // Thus bean[0] will be old data !
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { bean[0] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { bean[10] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { bean[8], bean[1], bean[7], bean[4], bean[10] });

	        bean[11] = MakeBean(2000);
	        stream.Insert(new EventBean[] { bean[11] });       // Thus bean[1] will be old data !
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { bean[1] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { bean[11] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { bean[8], bean[7], bean[4], bean[10], bean[11] });
	    }

	    private EventBean MakeBean(long volume)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean("CSCO.O", 0, volume, "");
	        return SupportEventBeanFactory.CreateObject(bean);
	    }

	    private EventBean MakeBean(double price, long volume)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean("CSCO.O", price, volume, "");
	        return SupportEventBeanFactory.CreateObject(bean);
	    }
	}
} // End of namespace
