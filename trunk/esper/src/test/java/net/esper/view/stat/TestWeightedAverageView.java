package net.esper.view.stat;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.util.DoubleValueAssertionUtil;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportStreamImpl;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.view.ViewFieldEnum;

import java.util.Iterator;

public class TestWeightedAverageView extends TestCase
{
    private WeightedAverageView myView;
    private SupportBeanClassView childView;

    public void setUp()
    {
        // Set up sum view and a test child view
        myView = new WeightedAverageView(SupportViewContextFactory.makeContext(), "price", "volume");
        
        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
    }

    // Check values against Microsoft Excel computed values
    public void testViewComputedValues()
    {
        // Set up feed for sum view
        SupportStreamImpl stream = new SupportStreamImpl(SupportMarketDataBean.class, 3);
        stream.addView(myView);

        // Send a first event, check values
        EventBean marketData = makeBean("IBM", 10, 1000);
        stream.insert(marketData);
        checkOld(Double.NaN);
        checkNew(10);

        // Send a second event, check values
        marketData = makeBean("IBM", 11, 2000);
        stream.insert(marketData);
        checkOld(10);
        checkNew(10.66666667);

        // Send a third event, check values
        marketData = makeBean("IBM", 10.5, 1500);
        stream.insert(marketData);
        checkOld(10.66666667);
        checkNew(10.61111111);

        // Send a 4th event, this time the first event should be gone
        marketData = makeBean("IBM", 9.5, 600);
        stream.insert(marketData);
        checkOld(10.61111111);
        checkNew(10.59756098);
    }

    public void testGetSchema()
    {
        assertTrue(myView.getEventType().getPropertyType(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.getName()) == double.class);
    }

    public void testCopyView() throws Exception
    {
        WeightedAverageView copied = (WeightedAverageView) myView.cloneView(SupportViewContextFactory.makeContext());
        assertTrue(myView.getFieldNameWeight().equals(copied.getFieldNameWeight()));
        assertTrue(myView.getFieldNameX().equals(copied.getFieldNameX()));
    }

    private void checkNew(double avgE)
    {
        Iterator<EventBean> iterator = myView.iterator();
        checkValues(iterator.next(), avgE);
        assertTrue(iterator.hasNext() == false);

        assertTrue(childView.getLastNewData().length == 1);
        EventBean childViewValues = childView.getLastNewData()[0];
        checkValues(childViewValues, avgE);
    }

    private void checkOld(double avgE)
    {
        assertTrue(childView.getLastOldData().length == 1);
        EventBean childViewValues = childView.getLastOldData()[0];
        checkValues(childViewValues, avgE);
    }

    private void checkValues(EventBean values, double avgE)
    {
        double avg = getDoubleValue(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE, values);

        assertTrue(DoubleValueAssertionUtil.equals(avg,  avgE, 6));
    }

    private double getDoubleValue(ViewFieldEnum field, EventBean eventBean)
    {
        return (Double) eventBean.get(field.getName());
    }

    private EventBean makeBean(String symbol, double price, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, "");
        return SupportEventBeanFactory.createObject(bean);
    }
}