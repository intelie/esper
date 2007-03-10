package net.esper.view.std;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportStreamImpl;
import net.esper.support.view.SupportStatementContextFactory;
import net.esper.support.view.SupportViewDataChecker;

public class TestMergeView extends TestCase
{
    private MergeView myView;
    private SupportBeanClassView childView;

    public void setUp()
    {
        // Set up length window view and a test child view
        myView = new MergeView(SupportStatementContextFactory.makeContext(),
                new String[] {"symbol"},
                SupportEventTypeFactory.createBeanType(SupportBean.class));

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
        SupportBeanClassView parent = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.setParent(parent);

        MergeView copied = (MergeView) myView.cloneView(SupportStatementContextFactory.makeContext());
        assertEquals(myView.getGroupFieldNames(), copied.getGroupFieldNames());
        assertEquals(myView.getEventType(), SupportEventTypeFactory.createBeanType(SupportBean.class));
    }

    private EventBean makeTradeBean(String symbol, int price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, "");
        return SupportEventBeanFactory.createObject(bean);
    }
}