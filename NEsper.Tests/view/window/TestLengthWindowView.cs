using System;

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.support.view;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.window
{

	[TestFixture]
    public class TestLengthWindowView 
    {
        private LengthWindowView myView;
        private SupportBeanClassView childView;

        [SetUp]
        public virtual void setUp()
        {
            // Set up length window view and a test child view
            myView = new LengthWindowView(5);
            childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
            myView.AddView(childView);
        }

        [Test]
        public virtual void testIncorrectUse()
        {
            try
            {
                myView = new LengthWindowView(0);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }
        }

        // Check values against Microsoft Excel computed values
        [Test]
        public virtual void testViewPush()
        {
            // Set up a feed for the view under test - it will have a depth of 3 trades
            SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportBean_A), 3);
            stream.AddView(myView);

            EDictionary<String, EventBean> events = EventFactoryHelper.MakeEventMap(
            new String[]
			{
				"a0", "b0", "b1", "c0", "c1", "d0", "e0", "e1", "e2", "f0", "f1", 
				"g0", "g1", "g2", "g3", "g4", 
				"h0", "h1", "h2", "h3", "h4", "h5", "h6", 
				"i0"
			}
            );

            // Fill the window with events up to the depth of 5
            stream.Insert(makeArray(events, new String[] { "a0" }));
            SupportViewDataChecker.checkOldData(childView, null);
            SupportViewDataChecker.checkNewData(childView, makeArray(events, new String[] { "a0" }));
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), makeArray(events, new String[] { "a0" }));

            stream.Insert(makeArray(events, new String[] { "b0", "b1" }));
            SupportViewDataChecker.checkOldData(childView, null);
            SupportViewDataChecker.checkNewData(childView, makeArray(events, new String[] { "b0", "b1" }));
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), makeArray(events, new String[] { "a0", "b0", "b1" }));

            stream.Insert(makeArray(events, new String[] { "c0", "c1" }));

            SupportViewDataChecker.checkOldData(childView, null);
            SupportViewDataChecker.checkNewData(childView, makeArray(events, new String[] { "c0", "c1" }));
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), makeArray(events, new String[] { "a0", "b0", "b1", "c0", "c1" }));

            // Send further events, expect to get events back that fall out of the window (a0)
            stream.Insert(makeArray(events, new String[] { "d0" }));
            SupportViewDataChecker.checkOldData(childView, makeArray(events, new String[] { "a0" }));
            SupportViewDataChecker.checkNewData(childView, makeArray(events, new String[] { "d0" }));
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), makeArray(events, new String[] { "b0", "b1", "c0", "c1", "d0" }));

            stream.Insert(makeArray(events, new String[] { "e0", "e1", "e2" }));
            SupportViewDataChecker.checkOldData(childView, makeArray(events, new String[] { "b0", "b1", "c0" }));
            SupportViewDataChecker.checkNewData(childView, makeArray(events, new String[] { "e0", "e1", "e2" }));
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), makeArray(events, new String[] { "c1", "d0", "e0", "e1", "e2" }));

            stream.Insert(makeArray(events, new String[] { "f0", "f1" }));
            SupportViewDataChecker.checkOldData(childView, makeArray(events, new String[] { "c1", "d0" }));
            SupportViewDataChecker.checkNewData(childView, makeArray(events, new String[] { "f0", "f1" }));
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), makeArray(events, new String[] { "e0", "e1", "e2", "f0", "f1" }));

            // Push as many events as the window takes
            stream.Insert(makeArray(events, new String[] { "g0", "g1", "g2", "g3", "g4" }));
            SupportViewDataChecker.checkOldData(childView, makeArray(events, new String[] { "e0", "e1", "e2", "f0", "f1" }));
            SupportViewDataChecker.checkNewData(childView, makeArray(events, new String[] { "g0", "g1", "g2", "g3", "g4" }));
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), makeArray(events, new String[] { "g0", "g1", "g2", "g3", "g4" }));

            // Push 2 more events then the window takes
            stream.Insert(makeArray(events, new String[] { "h0", "h1", "h2", "h3", "h4", "h5", "h6" }));
            SupportViewDataChecker.checkOldData(childView, makeArray(events, new String[] { "g0", "g1", "g2", "g3", "g4", "h0", "h1" }));
            SupportViewDataChecker.checkNewData(childView, makeArray(events, new String[] { "h0", "h1", "h2", "h3", "h4", "h5", "h6" }));
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), makeArray(events, new String[] { "h2", "h3", "h4", "h5", "h6" }));

            // Push 1 last event to make sure the last overflow was handled correctly
            stream.Insert(makeArray(events, new String[] { "i0" }));
            SupportViewDataChecker.checkOldData(childView, makeArray(events, new String[] { "h2" }));
            SupportViewDataChecker.checkNewData(childView, makeArray(events, new String[] { "i0" }));
            ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), makeArray(events, new String[] { "h3", "h4", "h5", "h6", "i0" }));
        }

        [Test]
        public virtual void testViewAttachesTo()
        {
            // Should attach to anything
            LengthWindowView view = new LengthWindowView(20);
            SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
            Assert.IsTrue(view.AttachesTo(parent) == null);
            parent.AddView(view);
            Assert.IsTrue(view.EventType == parent.EventType);
        }

        [Test]
        public virtual void testCopyView()
        {
            SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
            myView.Parent = parent;

            LengthWindowView copied = (LengthWindowView)ViewSupport.shallowCopyView(myView);
            Assert.AreEqual(myView.Size, copied.Size);
        }

        private EventBean[] makeArray(EDictionary<String, EventBean> events, String[] ids)
        {
            return EventFactoryHelper.makeArray(events, ids);
        }
    }
}
