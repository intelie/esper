package net.esper.view.ext;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportStreamImpl;
import net.esper.support.view.SupportViewDataChecker;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.view.ViewSupport;

public class TestSortWindowView extends TestCase
{
    private SortWindowView myView;
    private SupportBeanClassView childView;

    public void setUp()
    {
        // Set up length window view and a test child view
        myView = new SortWindowView("volume", false, 5);
        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
    }

    public void testIncorrectUse()
    {
        try
        {
            myView = new SortWindowView("volume", false, -1);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }
    }

    public void testViewAscending()
    {
        // Set up a feed for the view under test - the depth is 10 events so bean[10] will cause bean[0] to go old
        SupportStreamImpl stream = new SupportStreamImpl(SupportMarketDataBean.class, 10);
        stream.addView(myView);

        EventBean bean[] = new EventBean[12];

        bean[0] = makeBean(1000);
        stream.insert(bean[0]);
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[0] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { bean[0] });

        bean[1] = makeBean(800);
        bean[2] = makeBean(1200);
        stream.insert(new EventBean[] { bean[1], bean[2] });
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[1], bean[2] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { bean[1], bean[0], bean[2] });

        bean[3] = makeBean(1200);
        bean[4] = makeBean(1000);
        bean[5] = makeBean(1400);
        bean[6] = makeBean(1100);
        stream.insert(new EventBean[] { bean[3], bean[4], bean[5], bean[6] });
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { bean[5], bean[2] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[3], bean[4], bean[5], bean[6] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { bean[1], bean[4], bean[0], bean[6], bean[3] });

        bean[7] = makeBean(800);
        bean[8] = makeBean(700);
        bean[9] = makeBean(1200);
        stream.insert(new EventBean[] { bean[7], bean[8], bean[9] });
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { bean[3], bean[9], bean[6] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[7], bean[8], bean[9] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { bean[8], bean[7], bean[1], bean[4], bean[0] });

        bean[10] = makeBean(1050);
        stream.insert(new EventBean[] { bean[10] });       // Thus bean[0] will be old data !
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { bean[0] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[10] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { bean[8], bean[7], bean[1], bean[4], bean[10] });

        bean[11] = makeBean(2000);
        stream.insert(new EventBean[] { bean[11] });       // Thus bean[1] will be old data !
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { bean[1] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[11] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { bean[8], bean[7], bean[4], bean[10], bean[11] });
    }

    public void testViewDescending()
    {
        myView = new SortWindowView("volume", true, 3);
        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);

        // Set up a feed for the view under test
        SupportStreamImpl stream = new SupportStreamImpl(SupportMarketDataBean.class, Integer.MAX_VALUE);
        stream.addView(myView);

        EventBean bean[] = new EventBean[10];

        bean[0] = makeBean(8);
        bean[1] = makeBean(10);
        bean[2] = makeBean(12);
        bean[3] = makeBean(10);
        stream.insert(new EventBean[] { bean[0], bean[1], bean[2], bean[3] });
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { bean[0] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[0], bean[1], bean[2], bean[3] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { bean[2], bean[3], bean[1] });

        bean[4] = makeBean(11);
        bean[5] = makeBean(13);
        bean[6] = makeBean(11);
        bean[7] = makeBean(3);
        stream.insert(new EventBean[] { bean[4], bean[5], bean[6], bean[7] });
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { bean[7], bean[1], bean[3], bean[4] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[4], bean[5], bean[6], bean[7] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { bean[5], bean[2], bean[6] });
    }

    public void testViewAttachesTo()
    {
        SupportBeanClassView parent = new SupportBeanClassView(SupportMarketDataBean.class);

        SortWindowView view = new SortWindowView("dummy", true, 1);
        assertTrue(view.attachesTo(parent) != null);

        view = new SortWindowView("symbol", true, 100);
        assertTrue(view.attachesTo(parent) == null);

        parent.addView(view);
        assertTrue(view.getEventType() == parent.getEventType());
    }

    public void testCopyView() throws Exception
    {
        SupportBeanClassView parent = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.setParent(parent);

        SortWindowView copied = (SortWindowView) ViewSupport.shallowCopyView(myView);
        assertEquals(myView.getSortFieldName(), copied.getSortFieldName());
        assertEquals(myView.getSortWindowSize(), copied.getSortWindowSize());
        assertEquals(myView.isDescending(), copied.isDescending());
    }

    private EventBean makeBean(long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean("CSCO.O", 0, volume, "");
        return SupportEventBeanFactory.createObject(bean);
    }
}