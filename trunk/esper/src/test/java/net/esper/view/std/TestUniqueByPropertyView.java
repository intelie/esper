package net.esper.view.std;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportStreamImpl;
import net.esper.support.view.SupportViewDataChecker;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.view.ViewSupport;

public class TestUniqueByPropertyView extends TestCase
{
    private UniqueByPropertyView myView;
    private SupportBeanClassView childView;

    public void setUp()
    {
        // Set up length window view and a test child view
        myView = new UniqueByPropertyView("symbol");
        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
    }

    public void testViewPush()
    {
        // Set up a feed for the view under test - it will have a depth of 3 trades
        SupportStreamImpl stream = new SupportStreamImpl(SupportMarketDataBean.class, 3);
        stream.addView(myView);

        EventBean[] tradeBeans = new EventBean[10];

        // Send some events
        tradeBeans[0] = makeTradeBean("IBM", 70);
        stream.insert(tradeBeans[0]);
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { tradeBeans[0] });
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { tradeBeans[0] });

        // Send 2 more events
        tradeBeans[1] = makeTradeBean("IBM", 75);
        tradeBeans[2] = makeTradeBean("CSCO", 100);
        stream.insert(new EventBean[] { tradeBeans[1], tradeBeans[2] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { tradeBeans[1], tradeBeans[2] });
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { tradeBeans[0] });
        SupportViewDataChecker.checkNewData(childView,new EventBean[] { tradeBeans[1], tradeBeans[2] });

        // And 1 more events
        tradeBeans[3] = makeTradeBean("CSCO", 99);
        stream.insert(new EventBean[] { tradeBeans[3] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { tradeBeans[1], tradeBeans[3] });
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { tradeBeans[2] });
        SupportViewDataChecker.checkNewData(childView,new EventBean[] { tradeBeans[3] });

        // And 3 more events, that throws CSCO out as the stream size was 3
        tradeBeans[4] = makeTradeBean("MSFT", 55);
        tradeBeans[5] = makeTradeBean("IBM", 77);
        tradeBeans[6] = makeTradeBean("IBM", 78);
        stream.insert(new EventBean[] { tradeBeans[4], tradeBeans[5], tradeBeans[6] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { tradeBeans[6], tradeBeans[4] });
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { tradeBeans[1], tradeBeans[5], tradeBeans[3] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { tradeBeans[4], tradeBeans[5], tradeBeans[6] });  // Yes the event is both in old and new data

        // Post as old data an event --> unique event is thrown away and posted as old data
        myView.update(null, new EventBean[] { tradeBeans[6] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(),new EventBean[] { tradeBeans[4] });
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { tradeBeans[6] });
        SupportViewDataChecker.checkNewData(childView, null);
    }

    public void testCopyView() throws Exception
    {
        SupportBeanClassView parent = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.setParent(parent);

        UniqueByPropertyView copied = (UniqueByPropertyView) myView.cloneView(SupportViewContextFactory.makeContext());
        assertEquals(myView.getUniqueFieldName(), copied.getUniqueFieldName());
    }

    private EventBean makeTradeBean(String symbol, int price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, "");
        return SupportEventBeanFactory.createObject(bean);
    }
}