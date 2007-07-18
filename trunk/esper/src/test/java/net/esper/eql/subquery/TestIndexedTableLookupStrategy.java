package net.esper.eql.subquery;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.eql.join.table.PropertyIndexedEventTable;
import junit.framework.TestCase;

public class TestIndexedTableLookupStrategy extends TestCase
{
    private IndexedTableLookupStrategy strategy;
    private PropertyIndexedEventTable table;

    public void setUp()
    {
        EventType beanType = SupportEventAdapterService.getService().addBeanType(SupportBean.class.getName(), SupportBean.class);
        EventType[] eventTypes = new EventType[] {
                beanType,
                SupportEventAdapterService.getService().addBeanType(SupportMarketDataBean.class.getName(), SupportMarketDataBean.class)
        };

        table = new PropertyIndexedEventTable(2, beanType, new String[] {"intBoxed", "longBoxed", "intPrimitive"});
        strategy = new IndexedTableLookupStrategy(eventTypes,
                new int[] {0, 1, 0},
                new String[] {"intPrimitive", "volume", "intBoxed"},
                table);
    }

    public void testFlow()
    {
        assertNull(strategy.lookup(makeLookupEventBeans(0, 0, 0)));

        EventBean[] tableBean = makeTableBean(20, 100, 5000);
        table.add(tableBean);
        assertEquals(1, strategy.lookup(makeLookupEventBeans(20, 100, 5000)).size());

        table.add(makeTableBean(20, 100, 5000));
        assertEquals(2, strategy.lookup(makeLookupEventBeans(20, 100, 5000)).size());

        table.add(makeTableBean(20, 100, 5001));
        assertEquals(1, strategy.lookup(makeLookupEventBeans(20, 100, 5001)).size());
    }

    public EventBean[] makeLookupEventBeans(int intPrimitive, long volume, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setIntBoxed(intBoxed);
        bean.setIntPrimitive(intPrimitive);
        SupportMarketDataBean market = new SupportMarketDataBean("", 0, volume, "");
        return new EventBean[] {
                SupportEventAdapterService.getService().adapterForBean(bean),
                SupportEventAdapterService.getService().adapterForBean(market)
        };
    }

    private EventBean[] makeTableBean(int intBoxed, long longBoxed, int intPrimitive)
    {
        SupportBean bean = makeBean(intBoxed, longBoxed, intPrimitive);
        return new EventBean[] {SupportEventAdapterService.getService().adapterForBean(bean)};
    }

    private SupportBean makeBean(int intBoxed, long longBoxed, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntBoxed(intBoxed);
        bean.setLongBoxed(longBoxed);
        bean.setIntPrimitive(intPrimitive);
        return bean;
    }
}
