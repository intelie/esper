package net.esper.view.std;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportStreamImpl;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.view.ViewFieldEnum;
import net.esper.view.ViewSupport;
import net.esper.view.ViewServiceContext;

public class TestSizeView extends TestCase
{
    private SizeView myView;
    private SupportBeanClassView childView;

    public void setUp()
    {
        // Set up length window view and a test child view
        myView = new SizeView();
        myView.setViewServiceContext(new ViewServiceContext(null, SupportEventAdapterService.getService()));
        
        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
    }

    // Check values against Microsoft Excel computed values
    public void testViewPush()
    {
        // Set up a feed for the view under test - it will have a depth of 5 trades
        SupportStreamImpl stream = new SupportStreamImpl(SupportBean_A.class, 5);
        stream.addView(myView);

        checkIterator(0);

        // View just counts the number of events received, removing those removed in the prior view as old data
        stream.insert(makeBeans("a", 1));
        checkOldData(0);
        checkNewData(1);
        checkIterator(1);

        stream.insert(makeBeans("b", 2));
        checkOldData(1);
        checkNewData(3);
        checkIterator(3);

        // The EventStream has a depth of 3, it will expire the first message now, ie. will keep the size of 3, always
        stream.insert(makeBeans("c", 1));
        checkOldData(3);
        checkNewData(4);
        checkIterator(4);

        stream.insert(makeBeans("d", 1));
        checkOldData(4);
        checkNewData(5);
        checkIterator(5);

        stream.insert(makeBeans("e", 2));
        assertNull(childView.getLastNewData());
        assertNull(childView.getLastOldData());
        checkIterator(5);

        stream.insert(makeBeans("f", 1));
        assertNull(childView.getLastNewData());
        assertNull(childView.getLastOldData());
        checkIterator(5);
    }

    public void testUpdate()
    {
        // View should not post events if data didn't change
        myView.update(makeBeans("f", 1), null);

        checkOldData(0);
        checkNewData(1);
        childView.setLastNewData(null);
        childView.setLastOldData(null);

        myView.update(makeBeans("f", 1), makeBeans("f", 1));

        assertNull(childView.getLastNewData());
        assertNull(childView.getLastOldData());
    }

    public void testViewAttachesTo()
    {
        // Should attach to anything
        SizeView view = new SizeView();
        SupportBeanClassView parent = new SupportBeanClassView(SupportMarketDataBean.class);
        assertTrue(view.attachesTo(parent) == null);
    }

    public void testSchema()
    {
        SizeView view = new SizeView();
        view.setViewServiceContext(new ViewServiceContext(null, SupportEventAdapterService.getService()));

        EventType eventType = view.getEventType();
        assertEquals(long.class, eventType.getPropertyType(ViewFieldEnum.SIZE_VIEW__SIZE.getName()));
    }

    public void testCopyView() throws Exception
    {
        ViewSupport.shallowCopyView(myView);
    }

    private void checkNewData(long expectedSize)
    {
        EventBean[] newData = childView.getLastNewData();
        checkData(newData, expectedSize);
        childView.setLastNewData(null);
    }

    private void checkOldData(long expectedSize)
    {
        EventBean[] oldData = childView.getLastOldData();
        checkData(oldData, expectedSize);
        childView.setLastOldData(null);
    }

    private void checkData(EventBean[] data, long expectedSize)
    {
        // The view posts in its update data always just one object containing the size
        assertEquals(1, data.length);
        Long actualSize = (Long) data[0].get(ViewFieldEnum.SIZE_VIEW__SIZE.getName());
        assertEquals((long) expectedSize, (long) actualSize);
    }

    private void checkIterator(long expectedSize)
    {
        assertTrue(myView.iterator().hasNext());
        EventBean eventBean = myView.iterator().next();
        Long actualSize = (Long) eventBean.get(ViewFieldEnum.SIZE_VIEW__SIZE.getName());
        assertEquals((Long) expectedSize, (Long) actualSize);
    }

    private EventBean[] makeBeans(String id, int numTrades)
    {
        EventBean[] trades = new EventBean[numTrades];
        for (int i = 0; i < numTrades; i++)
        {
            SupportBean_A bean = new SupportBean_A(id + i);
            trades[i] = SupportEventBeanFactory.createObject(bean);
        }
        return trades;
    }
}