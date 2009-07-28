package com.espertech.esper.view.window;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.view.SupportBeanClassView;
import com.espertech.esper.support.view.SupportStreamImpl;
import com.espertech.esper.support.view.SupportViewDataChecker;
import junit.framework.TestCase;

public class TestExternallyTimedWindowView extends TestCase
{
    private ExternallyTimedWindowView myView;
    private SupportBeanClassView childView;

    public void setUp() throws Exception
    {
        // Set up timed window view and a test child view, set the time window size to 1 second
        myView = new ExternallyTimedWindowView(null, SupportExprNodeFactory.makeIdentNodeBean("longPrimitive"), 1000, null, false, null);
        childView = new SupportBeanClassView(SupportBean.class);
        myView.addView(childView);
    }

    public void testIncorrectUse() throws Exception
    {
        try
        {
            myView = new ExternallyTimedWindowView(null, SupportExprNodeFactory.makeIdentNodeBean("string"), 0, null, false, null);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }
    }

    public void testViewPush()
    {
        // Set up a feed for the view under test - it will have a depth of 3 trades
        SupportStreamImpl stream = new SupportStreamImpl(SupportBean.class, 3);
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

        EventBean[] d = makeBeans("d", 10999, 1);
        stream.insert(d);
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { d[0] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { a[0], b[0], b[1], c[0], d[0] });

        EventBean[] e = makeBeans("e", 11000, 2);
        stream.insert(e);
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { a[0] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { e[0], e[1] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { b[0], b[1], c[0], d[0], e[0], e[1] });

        EventBean[] f = makeBeans("f", 11500, 1);
        stream.insert(f);
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { b[0], b[1] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { f[0] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { c[0], d[0], e[0], e[1], f[0] });

        EventBean[] g = makeBeans("g", 11899, 1);
        stream.insert(g);
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { g[0] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { c[0], d[0], e[0], e[1], f[0], g[0] });

        EventBean[] h = makeBeans("h", 11999, 3);
        stream.insert(h);
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { c[0], d[0] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { h[0], h[1], h[2] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { e[0], e[1], f[0], g[0], h[0], h[1], h[2] });

        EventBean[] i = makeBeans("i", 13001, 1);
        stream.insert(i);
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { e[0], e[1], f[0], g[0], h[0], h[1], h[2] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { i[0] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { i[0] });
    }

    private EventBean[] makeBeans(String id, long timestamp, int numBeans)
    {
        EventBean[] beans = new EventBean[numBeans];
        for (int i = 0; i < numBeans; i++)
        {
            SupportBean bean = new SupportBean();
            bean.setLongPrimitive(timestamp);
            bean.setString(id + 1);
            beans[i] = SupportEventBeanFactory.createObject(bean);
        }
        return beans;
    }
}
