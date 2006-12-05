package net.esper.view.window;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanTimestamp;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportStreamImpl;
import net.esper.support.view.SupportViewDataChecker;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.view.ViewSupport;

public class TestExternallyTimedWindowView extends TestCase
{
    private ExternallyTimedWindowView myView;
    private SupportBeanClassView childView;

    public void setUp()
    {
        // Set up timed window view and a test child view, set the time window size to 1 second
        myView = new ExternallyTimedWindowView("timestamp", 1000);
        childView = new SupportBeanClassView(SupportBeanTimestamp.class);
        myView.addView(childView);
    }

    public void testIncorrectUse()
    {
        try
        {
            myView = new ExternallyTimedWindowView("goodie", 0);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }
    }

    public void testViewPush()
    {
        // Set up a feed for the view under test - it will have a depth of 3 trades
        SupportStreamImpl stream = new SupportStreamImpl(SupportBeanTimestamp.class, 3);
        stream.addView(myView);

        EventBean[] a = makeBeans("a", 10000, 1);
        stream.insert(a);
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { a[0] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { a[0] });

        EventBean[] b = makeBeans("b", 10500, 2);
        stream.insert(b);
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { b[0], b[1] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { a[0], b[0], b[1]  });

        EventBean[] c = makeBeans("c", 10900, 1);
        stream.insert(c);
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { c[0] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { a[0], b[0], b[1], c[0] });

        EventBean[] d = makeBeans("d", 11000, 1);
        stream.insert(d);
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { d[0] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { a[0], b[0], b[1], c[0], d[0] });

        EventBean[] e = makeBeans("e", 11001, 2);
        stream.insert(e);
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { a[0] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { e[0], e[1] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { b[0], b[1], c[0], d[0], e[0], e[1] });

        EventBean[] f = makeBeans("f", 11501, 1);
        stream.insert(f);
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { b[0], b[1] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { f[0] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { c[0], d[0], e[0], e[1], f[0] });

        EventBean[] g = makeBeans("g", 11900, 1);
        stream.insert(g);
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { g[0] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { c[0], d[0], e[0], e[1], f[0], g[0] });

        EventBean[] h = makeBeans("g", 12001, 3);
        stream.insert(h);
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { c[0], d[0] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { h[0], h[1], h[2] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { e[0], e[1], f[0], g[0], h[0], h[1], h[2] });

        EventBean[] i = makeBeans("i", 13002, 1);
        stream.insert(i);
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { e[0], e[1], f[0], g[0], h[0], h[1], h[2] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { i[0] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { i[0] });
    }

    public void testViewAttachesTo()
    {
        // Should attach to anything as long as the field name is a long value
        ExternallyTimedWindowView view = new ExternallyTimedWindowView("dummy", 20);
        SupportBeanClassView parent = new SupportBeanClassView(SupportBean.class);
        assertTrue(view.attachesTo(parent) != null);

        view = new ExternallyTimedWindowView("intPrimitive", 20);
        assertTrue(view.attachesTo(parent) != null);
        view = new ExternallyTimedWindowView("string", 20);
        assertTrue(view.attachesTo(parent) != null);
        view = new ExternallyTimedWindowView("boolean", 20);
        assertTrue(view.attachesTo(parent) != null);
        view = new ExternallyTimedWindowView("longPrimitive", 20);
        assertTrue(view.attachesTo(parent) == null);

        parent.addView(view);
        assertTrue(view.getEventType() == parent.getEventType());
    }

    public void testCopyView() throws Exception
    {
        SupportStreamImpl stream = new SupportStreamImpl(SupportBeanTimestamp.class, 3);
        stream.addView(myView);

        ExternallyTimedWindowView copied = (ExternallyTimedWindowView) ViewSupport.shallowCopyView(myView);
        assertEquals(myView.getTimestampFieldName(), copied.getTimestampFieldName());
        assertEquals(myView.getMillisecondsBeforeExpiry(),copied.getMillisecondsBeforeExpiry());
    }

    private EventBean[] makeBeans(String id, long timestamp, int numBeans)
    {
        EventBean[] beans = new EventBean[numBeans];
        for (int i = 0; i < numBeans; i++)
        {
            SupportBeanTimestamp bean = new SupportBeanTimestamp(id + i, timestamp);
            beans[i] = SupportEventBeanFactory.createObject(bean);
        }
        return beans;
    }
}