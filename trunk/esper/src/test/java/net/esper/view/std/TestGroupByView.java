package net.esper.view.std;

import java.util.List;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.view.*;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.view.EventStream;
import net.esper.view.View;
import net.esper.view.ViewServiceContext;
import net.esper.client.EPException;

public class TestGroupByView extends TestCase
{
    private GroupByView myGroupByView;
    private SupportBeanClassView ultimateChildView;
    private ViewServiceContext viewServiceContext;

    public void setUp()
    {
        viewServiceContext = SupportViewContextFactory.makeContext();
        myGroupByView = new GroupByView(new String[] {"symbol"});
        myGroupByView.setViewServiceContext(viewServiceContext);

        SupportBeanClassView childView = new SupportBeanClassView(SupportMarketDataBean.class);

        MergeView myMergeView = new MergeView(new String[]{"symbol"}, SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class));

        ultimateChildView = new SupportBeanClassView(SupportMarketDataBean.class);

        // This is the view hierarchy
        myGroupByView.addView(childView);
        childView.addView(myMergeView);
        myMergeView.addView(ultimateChildView);

        SupportBeanClassView.getInstances().clear();
    }

    public void testViewPush()
    {
        // Reset instance lists of child views
        SupportBeanClassView.getInstances().clear();
        SupportMapView.getInstances().clear();

        // Set up a feed for the view under test - it will have a depth of 3 trades
        SupportStreamImpl stream = new SupportStreamImpl(SupportMarketDataBean.class, 3);
        stream.addView(myGroupByView);

        EventBean[] tradeBeans = new EventBean[10];

        // Send an IBM symbol event
        tradeBeans[0] = makeTradeBean("IBM", 70);
        stream.insert(tradeBeans[0]);

        // Expect 1 new beanclass view instance and check its data
        assertEquals(1, SupportBeanClassView.getInstances().size());
        SupportBeanClassView child_1 = SupportBeanClassView.getInstances().get(0);
        SupportViewDataChecker.checkOldData(child_1, null);
        SupportViewDataChecker.checkNewData(child_1, new EventBean[] { tradeBeans[0] });

        // Check the data of the ultimate receiver
        SupportViewDataChecker.checkOldData(ultimateChildView, null);
        SupportViewDataChecker.checkNewData(ultimateChildView, new EventBean[] {tradeBeans[0]});
    }

    public void testUpdate()
    {
        // Set up a feed for the view under test - it will have a depth of 3 trades
        SupportStreamImpl stream = new SupportStreamImpl(SupportMarketDataBean.class, 3);
        stream.addView(myGroupByView);

        // Send old a new events
        EventBean[] newEvents = new EventBean[] { makeTradeBean("IBM", 70), makeTradeBean("GE", 10) };
        EventBean[] oldEvents = new EventBean[] { makeTradeBean("IBM", 65), makeTradeBean("GE", 9) };
        myGroupByView.update(newEvents, oldEvents);

        assertEquals(2, SupportBeanClassView.getInstances().size());
        SupportBeanClassView child_1 = SupportBeanClassView.getInstances().get(0);
        SupportBeanClassView child_2 = SupportBeanClassView.getInstances().get(1);
        SupportViewDataChecker.checkOldData(child_1, new EventBean[] { oldEvents[0] });
        SupportViewDataChecker.checkNewData(child_1, new EventBean[] { newEvents[0] });
        SupportViewDataChecker.checkOldData(child_2, new EventBean[] { oldEvents[1] });
        SupportViewDataChecker.checkNewData(child_2, new EventBean[] { newEvents[1] });

        newEvents = new EventBean[] { makeTradeBean("IBM", 71), makeTradeBean("GE", 11) };
        oldEvents = new EventBean[] { makeTradeBean("IBM", 70), makeTradeBean("GE", 10) };
        myGroupByView.update(newEvents, oldEvents);

        SupportViewDataChecker.checkOldData(child_1, new EventBean[] { oldEvents[0] });
        SupportViewDataChecker.checkNewData(child_1, new EventBean[] { newEvents[0] });
        SupportViewDataChecker.checkOldData(child_2, new EventBean[] { oldEvents[1] });
        SupportViewDataChecker.checkNewData(child_2, new EventBean[] { newEvents[1] });
    }

    public void testInvalid()
    {
        try
        {
            myGroupByView.iterator();
            assertTrue(false);
        }
        catch (UnsupportedOperationException ex)
        {
            // Expected exception
        }
    }

    public void testMakeSubviews()
    {
        EventStream eventStream = new SupportStreamImpl(SupportMarketDataBean.class, 4);
        GroupByView groupView = new GroupByView(new String[] {"symbol"});
        eventStream.addView(groupView);

        Object[] groupByValue = new Object[] {"IBM"};

        // Invalid for no child nodes
        try
        {
            GroupByView.makeSubViews(groupView, groupByValue, viewServiceContext);
            assertTrue(false);
        }
        catch (EPException ex)
        {
            // Expected exception
        }

        // Invalid for child node is a merge node - doesn't make sense to group and merge only
        MergeView mergeViewOne = new MergeView(new String[] {"symbol"}, null);
        groupView.addView(mergeViewOne);
        try
        {
            GroupByView.makeSubViews(groupView, groupByValue, viewServiceContext);
            assertTrue(false);
        }
        catch (EPException ex)
        {
            // Expected exception
        }

        // Add a size view parent of merge view
        groupView = new GroupByView(new String[] {"symbol"});
        groupView.setViewServiceContext(SupportViewContextFactory.makeContext());

        SizeView sizeView_1 = new SizeView();
        sizeView_1.setViewServiceContext(SupportViewContextFactory.makeContext());

        groupView.addView(sizeView_1);
        mergeViewOne = new MergeView(new String[] {"symbol"}, null);
        sizeView_1.addView(mergeViewOne);

        List<View> subViews = GroupByView.makeSubViews(groupView, groupByValue, viewServiceContext);

        assertTrue(subViews.size() == 1);
        assertTrue(subViews.get(0) instanceof SizeView);
        assertTrue(subViews.get(0) != sizeView_1);

        SizeView sv = (SizeView) subViews.get(0);
        assertEquals(1, sv.getViews().size());
        assertTrue(sv.getViews().get(0) instanceof AddPropertyValueView);

        AddPropertyValueView md = (AddPropertyValueView) sv.getViews().get(0);
        assertEquals(1, md.getViews().size());
        assertTrue(md.getViews().get(0) == mergeViewOne);
    }

    private EventBean makeTradeBean(String symbol, int price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, "");
        return SupportEventBeanFactory.createObject(bean);
    }
}