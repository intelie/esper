package net.esper.view.std;

import java.util.Map;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.event.EventFactoryHelper;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportStreamImpl;
import net.esper.support.view.SupportViewDataChecker;
import net.esper.view.ViewSupport;

public class TestLastElementView extends TestCase
{
    private LastElementView myView;
    private SupportBeanClassView childView;

    public void setUp()
    {
        // Set up length window view and a test child view
        myView = new LastElementView();
        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
    }

    public void testViewPush()
    {
        // Set up a feed for the view under test - it will have a depth of 3 trades
        SupportStreamImpl stream = new SupportStreamImpl(SupportBean_A.class, 3);
        stream.addView(myView);

        Map<String, EventBean> events = EventFactoryHelper.makeEventMap(
            new String[] {"a0", "a1", "b0", "c0", "c1", "c2", "d0", "e0"});

        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, null);
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), null);

        // View should keep the last element for iteration, should report new data as it arrives
        stream.insert(new EventBean[] {events.get("a0"), events.get("a1")});
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { events.get("a0") });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { events.get("a0"), events.get("a1") });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { events.get("a1") });

        stream.insert(new EventBean[] {events.get("b0")});
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { events.get("a1") });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { events.get("b0") });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { events.get("b0") });

        stream.insert(new EventBean[] {events.get("c0"),events.get("c1"),events.get("c2")});
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { events.get("b0"), events.get("c0"), events.get("c1") });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { events.get("c0"), events.get("c1"), events.get("c2") });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { events.get("c2") });

        stream.insert(new EventBean[] {events.get("d0")});
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { events.get("c2") });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { events.get("d0") });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { events.get("d0") });

        stream.insert(new EventBean[] {events.get("e0")});
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { events.get("d0") });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { events.get("e0") });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { events.get("e0") });
    }

    public void testCopyView() throws Exception
    {
        SupportBeanClassView parent = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.setParent(parent);
        ViewSupport.shallowCopyView(myView);
    }
}