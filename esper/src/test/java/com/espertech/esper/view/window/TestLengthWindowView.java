package com.espertech.esper.view.window;

import java.util.Map;

import junit.framework.TestCase;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.event.EventFactoryHelper;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.view.SupportBeanClassView;
import com.espertech.esper.support.view.SupportStreamImpl;
import com.espertech.esper.support.view.SupportViewDataChecker;

public class TestLengthWindowView extends TestCase
{
    private LengthWindowView myView;
    private SupportBeanClassView childView;

    public void setUp()
    {
        // Set up length window view and a test child view
        myView = new LengthWindowView(null, 5, null);
        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
    }

    public void testIncorrectUse()
    {
        try
        {
            myView = new LengthWindowView(null, 0, null);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }
    }

    // Check values against Microsoft Excel computed values
    public void testViewPush()
    {
        // Set up a feed for the view under test - it will have a depth of 3 trades
        SupportStreamImpl stream = new SupportStreamImpl(SupportBean_A.class, 3);
        stream.addView(myView);

        Map<String, EventBean> events = EventFactoryHelper.makeEventMap(
            new String[] {"a0", "b0", "b1", "c0", "c1", "d0", "e0", "e1", "e2", "f0", "f1",
            "g0", "g1", "g2", "g3", "g4",
            "h0", "h1", "h2", "h3", "h4", "h5", "h6",
            "i0"});

        // Fill the window with events up to the depth of 5
        stream.insert(makeArray(events, new String[]{"a0"}));
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, makeArray(events, new String[]{ "a0"}));
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),makeArray(events, new String[]{ "a0"}));

        stream.insert(makeArray(events, new String[]{"b0", "b1"}));
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, makeArray(events, new String[]{ "b0", "b1"}));
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),makeArray(events, new String[]{ "a0", "b0", "b1" }));

        stream.insert(makeArray(events, new String[]{"c0", "c1"}));

        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView,makeArray(events, new String[]{ "c0", "c1" }));
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),makeArray(events, new String[]{ "a0", "b0", "b1", "c0", "c1" }));

        // Send further events, expect to get events back that fall out of the window (a0)
        stream.insert(makeArray(events, new String[]{"d0"}));
        SupportViewDataChecker.checkOldData(childView, makeArray(events, new String[]{ "a0" }));
        SupportViewDataChecker.checkNewData(childView,makeArray(events, new String[]{ "d0" }));
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),makeArray(events, new String[]{ "b0", "b1", "c0", "c1", "d0" }));

        stream.insert(makeArray(events, new String[]{"e0", "e1", "e2"}));
        SupportViewDataChecker.checkOldData(childView, makeArray(events, new String[]{ "b0", "b1", "c0" }));
        SupportViewDataChecker.checkNewData(childView,makeArray(events, new String[]{ "e0", "e1", "e2" }));
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),makeArray(events, new String[]{ "c1", "d0", "e0", "e1", "e2" }));

        stream.insert(makeArray(events, new String[]{"f0", "f1"}));
        SupportViewDataChecker.checkOldData(childView, makeArray(events, new String[]{ "c1", "d0" }));
        SupportViewDataChecker.checkNewData(childView,makeArray(events, new String[]{ "f0", "f1" }));
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),makeArray(events, new String[]{ "e0", "e1", "e2", "f0", "f1" }));

        // Push as many events as the window takes
        stream.insert(makeArray(events, new String[]{"g0", "g1", "g2", "g3", "g4"}));
        SupportViewDataChecker.checkOldData(childView, makeArray(events, new String[]{ "e0", "e1", "e2", "f0", "f1" }));
        SupportViewDataChecker.checkNewData(childView,makeArray(events, new String[]{ "g0", "g1", "g2", "g3", "g4" }));
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),makeArray(events, new String[]{ "g0", "g1", "g2", "g3", "g4" }));

        // Push 2 more events then the window takes
        stream.insert(makeArray(events, new String[]{"h0", "h1", "h2", "h3", "h4", "h5", "h6"}));
        SupportViewDataChecker.checkOldData(childView, makeArray(events, new String[]{ "g0", "g1", "g2", "g3", "g4", "h0", "h1" }));
        SupportViewDataChecker.checkNewData(childView,makeArray(events, new String[]{ "h0", "h1", "h2", "h3", "h4", "h5", "h6" }));
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),makeArray(events, new String[]{ "h2", "h3", "h4", "h5", "h6" }));

        // Push 1 last event to make sure the last overflow was handled correctly
        stream.insert(makeArray(events, new String[]{"i0"}));
        SupportViewDataChecker.checkOldData(childView, makeArray(events, new String[]{ "h2" }));
        SupportViewDataChecker.checkNewData(childView,makeArray(events, new String[]{ "i0" }));
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),makeArray(events, new String[]{ "h3", "h4", "h5", "h6", "i0" }));
    }

    private EventBean[] makeArray(Map<String, EventBean> events, String[] ids)
    {
        return EventFactoryHelper.makeArray(events, ids);
    }
}