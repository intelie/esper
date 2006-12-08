package net.esper.view.ext;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportStreamImpl;
import net.esper.support.view.SupportViewDataChecker;
import net.esper.view.ViewSupport;

public class TestSortWindowView extends TestCase
{
    private SortWindowView myView;
    private SupportBeanClassView childView;

    public void setUp()
    {
        // Set up length window view and a test child view
        myView = new SortWindowView(new String[]{"volume"}, new Boolean[] {false}, 5);
        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
    }
    
    public void testViewOneProperty()
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
    
    public void testViewTwoProperties()
    {
    	// Set up a sort windows that sorts on two properties
    	myView = new SortWindowView(new String[]{"volume", "price"}, new Boolean[] {false, true}, 5);
        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
        
        // Set up a feed for the view under test - the depth is 10 events so bean[10] will cause bean[0] to go old
        SupportStreamImpl stream = new SupportStreamImpl(SupportMarketDataBean.class, 10);
        stream.addView(myView);
        
        EventBean bean[] = new EventBean[12];
        
        bean[0] = makeBean(20d, 1000);
        stream.insert(bean[0]);
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[0] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { bean[0] });
 
        bean[1] = makeBean(19d, 800);
        bean[2] = makeBean(18d, 1200);
        stream.insert(new EventBean[] { bean[1], bean[2] });
        SupportViewDataChecker.checkOldData(childView, null);
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[1], bean[2] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { bean[1], bean[0], bean[2] });
        
        bean[3] = makeBean(17d, 1200);
        bean[4] = makeBean(16d, 1000);
        bean[5] = makeBean(15d, 1400);
        bean[6] = makeBean(14d, 1100);
        stream.insert(new EventBean[] { bean[3], bean[4], bean[5], bean[6] });
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { bean[5], bean[3] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[3], bean[4], bean[5], bean[6] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { bean[1], bean[0], bean[4], bean[6], bean[2] });

        bean[7] = makeBean(13d, 800);
        bean[8] = makeBean(12d, 700);
        bean[9] = makeBean(11d, 1200);
        stream.insert(new EventBean[] { bean[7], bean[8], bean[9] });
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { bean[9], bean[2], bean[6] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[7], bean[8], bean[9] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { bean[8], bean[1], bean[7], bean[0], bean[4] });

        bean[10] = makeBean(10d, 1050);
        stream.insert(new EventBean[] { bean[10] });       // Thus bean[0] will be old data !
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { bean[0] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[10] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { bean[8], bean[1], bean[7], bean[4], bean[10] });

        bean[11] = makeBean(2000);
        stream.insert(new EventBean[] { bean[11] });       // Thus bean[1] will be old data !
        SupportViewDataChecker.checkOldData(childView, new EventBean[] { bean[1] });
        SupportViewDataChecker.checkNewData(childView, new EventBean[] { bean[11] });
        ArrayAssertionUtil.assertEqualsExactOrder(myView.iterator(), new EventBean[] { bean[8], bean[7], bean[4], bean[10], bean[11] });
    }

    public void testCopyView() throws Exception
    {
        SupportBeanClassView parent = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.setParent(parent);

        SortWindowView copied = (SortWindowView) ViewSupport.shallowCopyView(myView);
        
        ArrayAssertionUtil.assertEqualsExactOrder(myView.getSortFieldNames(), copied.getSortFieldNames());
        assertEquals(myView.getSortWindowSize(), copied.getSortWindowSize());
        ArrayAssertionUtil.assertEqualsExactOrder(myView.getIsDescendingValues(), copied.getIsDescendingValues());
    }

    private EventBean makeBean(long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean("CSCO.O", 0, volume, "");
        return SupportEventBeanFactory.createObject(bean);
    }
    
    private EventBean makeBean(double price, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean("CSCO.O", price, volume, "");
        return SupportEventBeanFactory.createObject(bean);
    }
}