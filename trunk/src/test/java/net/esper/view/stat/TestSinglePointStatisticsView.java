package net.esper.view.stat;

import java.util.Iterator;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.DoubleValueAssertionUtil;
import net.esper.support.view.SupportBeanClassView;
import net.esper.support.view.SupportStreamImpl;
import net.esper.support.view.SupportViewContextFactory;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.view.ViewFieldEnum;
import net.esper.view.ViewSupport;
import net.esper.view.ViewServiceContext;

public class TestSinglePointStatisticsView extends TestCase
{
    UnivariateStatisticsView myView;
    SupportBeanClassView childView;

    public void setUp()
    {
        // Set up sum view and a test child view
        myView = new UnivariateStatisticsView("price");
        myView.setViewServiceContext(SupportViewContextFactory.makeContext());

        childView = new SupportBeanClassView(SupportMarketDataBean.class);
        myView.addView(childView);
    }

    // Check values against Microsoft Excel computed values
    public void testViewComputedValues()
    {
        // Set up feed for sum view
        SupportStreamImpl stream = new SupportStreamImpl(SupportMarketDataBean.class, 3);
        stream.addView(myView);

        // Send two events to the stream
        assertTrue(childView.getLastNewData() == null);

        // Send a first event, checkNew values
        EventBean marketData = makeBean("IBM", 10, 0);
        stream.insert(marketData);
        checkOld(0, 0, Double.NaN, Double.NaN, Double.NaN, Double.NaN);
        checkNew(1, 10, 10, 0, Double.NaN, Double.NaN);

        // Send a second event, checkNew values
        marketData = makeBean("IBM", 12, 0);
        stream.insert(marketData);
        checkOld(1, 10, 10, 0, Double.NaN, Double.NaN);
        checkNew(2, 22, 11, 1, Math.sqrt(2.0), 2);

        // Send a third event, checkNew values
        marketData = makeBean("IBM", 9.5, 0);
        stream.insert(marketData);
        checkOld(2, 22, 11, 1, Math.sqrt(2.0), 2);
        checkNew(3, 31.5, 10.5, 1.08012345, 1.322875656, 1.75);

        // Send a 4th event, this time the first event should be gone, checkNew values
        marketData = makeBean("IBM", 9, 0);
        stream.insert(marketData);
        checkOld(3, 31.5, 10.5, 1.08012345, 1.322875656, 1.75);
        checkNew(3, 30.5, 10.16666667, 1.312334646, 1.607275127, 2.583333333);
    }

    public void testViewAttachesTo()
    {
        UnivariateStatisticsView view = new UnivariateStatisticsView("symbol");

        // The symbol field in the parent is not a number, expect an error on attach
        SupportBeanClassView parent = new SupportBeanClassView(SupportMarketDataBean.class);
        assertTrue(view.attachesTo(parent) != null);

        // Try a non-existing field name
        view = new UnivariateStatisticsView("dummy");
        assertTrue(view.attachesTo(parent) != null);
    }

    public void testGetSchema()
    {
        assertTrue(myView.getEventType().getPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.getName()) == long.class);
        assertTrue(myView.getEventType().getPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.getName()) == double.class);
        assertTrue(myView.getEventType().getPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.getName()) == double.class);
        assertTrue(myView.getEventType().getPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.getName()) == double.class);
        assertTrue(myView.getEventType().getPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.getName()) == double.class);
        assertTrue(myView.getEventType().getPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.getName()) == double.class);
    }

    public void testCopyView() throws Exception
    {
        UnivariateStatisticsView copied = (UnivariateStatisticsView) ViewSupport.shallowCopyView(myView);
        assertTrue(myView.getFieldName().equals(copied.getFieldName()));
    }

    private void checkNew(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
    {
        Iterator<EventBean> iterator = myView.iterator();
        checkValues(iterator.next(), countE, sumE, avgE, stdevpaE, stdevE, varianceE);
        assertTrue(iterator.hasNext() == false);

        assertTrue(childView.getLastNewData().length == 1);
        EventBean childViewValues = childView.getLastNewData()[0];
        checkValues(childViewValues, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
    }

    private void checkOld(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
    {
        assertTrue(childView.getLastOldData().length == 1);
        EventBean childViewValues = childView.getLastOldData()[0];
        checkValues(childViewValues, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
    }

    private void checkValues(EventBean values, long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
    {
        long count = getLongValue(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT, values);
        double sum = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM, values);
        double avg = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE, values);
        double stdevpa = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA, values);
        double stdev = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV, values);
        double variance = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE, values);

        assertEquals(count, countE);
        assertEquals(sum, sumE);
        assertTrue(DoubleValueAssertionUtil.equals(avg,  avgE, 6));
        assertTrue(DoubleValueAssertionUtil.equals(stdevpa,  stdevpaE, 6));
        assertTrue(DoubleValueAssertionUtil.equals(stdev,  stdevE, 6));
        assertTrue(DoubleValueAssertionUtil.equals(variance,  varianceE, 6));
    }

    private double getDoubleValue(ViewFieldEnum field, EventBean eventBean)
    {
        return (Double) eventBean.get(field.getName());
    }

    private long getLongValue(ViewFieldEnum field, EventBean eventBean)
    {
        return (Long) eventBean.get(field.getName());
    }

    private EventBean makeBean(String symbol, double price, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, "");
        return SupportEventBeanFactory.createObject(bean);
    }
}