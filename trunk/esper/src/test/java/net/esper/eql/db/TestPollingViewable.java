package net.esper.eql.db;

import junit.framework.TestCase;

import java.util.*;

import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.bean.SupportBean;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.collection.MultiKey;

public class TestPollingViewable extends TestCase
{
    private PollingViewable pollingViewable;

    public void setUp() throws Exception
    {
        List<String> inputProperties = Arrays.asList(new String[] {"s0.intPrimitive"});

        DataCache dataCache = new DataCacheLRUImpl(100);

        Map<String, Class> resultProperties = new HashMap<String, Class>();
        resultProperties.put("myvarchar", String.class);
        EventType resultEventType = SupportEventAdapterService.getService().createAnonymousMapType(resultProperties);

        Map<MultiKey<Object>, List<EventBean>> pollResults = new HashMap<MultiKey<Object>, List<EventBean>>();
        pollResults.put(new MultiKey(new Object[] {-1}), new LinkedList());
        pollResults.put(new MultiKey(new Object[] {500}), new LinkedList());
        SupportPollingStrategy supportPollingStrategy = new SupportPollingStrategy(pollResults);

        pollingViewable = new PollingViewable(1, inputProperties, supportPollingStrategy, dataCache, resultEventType);

        pollingViewable.validate(new SupportStreamTypeSvc3Stream());
    }

    public void testPoll()
    {
        EventBean[][] input = new EventBean[2][2];
        input[0] = new EventBean[] {makeEvent(-1), null};
        input[1] = new EventBean[] {makeEvent(500), null};
        List<EventBean>[] resultRows = pollingViewable.poll(input);

        // should have joined to two rows
        assertEquals(2, resultRows.length);
        assertEquals(0, resultRows[0].size());
        assertEquals(0, resultRows[1].size());
    }

    private EventBean makeEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        return SupportEventAdapterService.getService().adapterForBean(bean);
    }
}
