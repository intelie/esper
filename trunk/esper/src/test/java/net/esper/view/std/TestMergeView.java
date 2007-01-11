package net.esper.view.std;

import junit.framework.*;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportStreamImpl;
import net.esper.support.view.SupportViewDataChecker;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.view.ViewSupport;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.View;
import net.esper.view.ViewServiceContext;

import java.util.List;
import java.util.LinkedList;

public class TestMergeView extends TestCase
{
    private MergeView myView;
    private SupportBeanClassView childView;

    public void setUp()
    {
        // Set up length window view and a test child view
        myView = new MergeView(new String[] {"symbol"}, null);
        myView.setViewServiceContext(SupportViewContextFactory.makeContext());

        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
    }

    public void testViewPush()
    {
        SupportStreamImpl stream = new SupportStreamImpl(SupportMarketDataBean.class, 2);
        stream.addView(myView);

        EventBean[] tradeBeans = new EventBean[10];

        // Send events, expect just forwarded
        tradeBeans[0] = makeTradeBean("IBM", 70);
        stream.insert(tradeBeans[0]);

        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { tradeBeans[0] });

        // Send some more events, expect forwarded
        tradeBeans[1] = makeTradeBean("GE", 90);
        tradeBeans[2] = makeTradeBean("CSCO", 20);
        stream.insert(new EventBean[] { tradeBeans[1], tradeBeans[2] });

        SupportViewDataChecker.checkOldData(childView, new EventBean[] { tradeBeans[0] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { tradeBeans[1], tradeBeans[2] });
    }

    public void testCopyView() throws Exception
    {
        EventType someEventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
        SupportBeanClassView parent = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.setParent(parent);
        myView.setEventType(someEventType);

        MergeView copied = (MergeView) ViewSupport.shallowCopyView(myView);
        assertEquals(myView.getGroupFieldNames(), copied.getGroupFieldNames());
        assertEquals(myView.getEventType(), someEventType);
    }

    private EventBean makeTradeBean(String symbol, int price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, "");
        return SupportEventBeanFactory.createObject(bean);
    }
}