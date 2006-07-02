package net.esper.view.stat;

import java.util.Iterator;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.DoubleValueAssertionUtil;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportStreamImpl;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.view.ViewFieldEnum;
import net.esper.view.ViewSupport;
import net.esper.view.ViewServiceContext;

public class TestCorrelationView extends TestCase
{
    CorrelationView myView;
    SupportBeanClassView childView;

    public void setUp()
    {
        // Set up sum view and a test child view
        myView = new CorrelationView("price", "volume");
        myView.setViewServiceContext(new ViewServiceContext(null, SupportEventAdapterService.getService()));
        
        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
    }

    // Check values against Microsoft Excel computed values
    public void testViewComputedValues()
    {
        // Set up feed for sum view
        SupportStreamImpl stream = new SupportStreamImpl(SupportMarketDataBean.class, 3);
        stream.addView(myView);

        // Send a first event, checkNew values
        EventBean marketData = makeBean("IBM", 70, 1000);
        stream.insert(marketData);
        checkOld(Double.NaN);
        checkNew(Double.NaN);

        // Send a second event, checkNew values
        marketData = makeBean("IBM", 70.5, 1500);
        stream.insert(marketData);
        checkOld(Double.NaN);
        checkNew(1);

        // Send a third event, checkNew values
        marketData = makeBean("IBM", 70.1, 1200);
        stream.insert(marketData);
        checkOld(1);
        checkNew(0.97622104);

        // Send a 4th event, this time the first event should be gone, checkNew values
        marketData = makeBean("IBM", 70.25, 1000);
        stream.insert(marketData);
        checkOld(0.97622104);
        checkNew(0.70463404);
    }

    public void testViewAttachesTo()
    {
        CorrelationView view = new CorrelationView("symbol", "price");

        // The symbol field in the parent is not a number, expect an error on attach
        SupportBeanClassView parent = new SupportBeanClassView(SupportMarketDataBean.class);
        assertTrue(view.attachesTo(parent) != null);

        // Try a non-existing field name
        view = new CorrelationView("price", "dummy");
        assertTrue(view.attachesTo(parent) != null);
    }

    public void testGetSchema()
    {
        assertTrue(myView.getEventType().getPropertyType(ViewFieldEnum.CORRELATION__CORRELATION.getName()) == double.class);
    }

    public void testCopyView() throws Exception
    {
        CorrelationView copied = (CorrelationView) ViewSupport.shallowCopyView(myView);
        assertTrue(myView.getFieldNameX().equals(copied.getFieldNameX()));
        assertTrue(myView.getFieldNameY().equals(copied.getFieldNameY()));
    }

    private void checkNew(double correlationE)
    {
        Iterator<EventBean> iterator = myView.iterator();
        checkValues(iterator.next(), correlationE);
        assertTrue(iterator.hasNext() == false);

        assertTrue(childView.getLastNewData().length == 1);
        EventBean childViewValues = childView.getLastNewData()[0];
        checkValues(childViewValues, correlationE);
    }

    private void checkOld(double correlationE)
    {
        assertTrue(childView.getLastOldData().length == 1);
        EventBean childViewValues = childView.getLastOldData()[0];
        checkValues(childViewValues, correlationE);
    }

    private void checkValues(EventBean values, double correlationE)
    {
        double correlation = getDoubleValue(ViewFieldEnum.CORRELATION__CORRELATION, values);
        assertTrue(DoubleValueAssertionUtil.equals(correlation,  correlationE, 6));
    }

    private double getDoubleValue(ViewFieldEnum field, EventBean values)
    {
        return (Double) values.get(field.getName());
    }

    private EventBean makeBean(String symbol, double price, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, "");
        return SupportEventBeanFactory.createObject(bean);
    }
}