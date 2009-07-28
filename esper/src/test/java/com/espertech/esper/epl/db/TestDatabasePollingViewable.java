package com.espertech.esper.epl.db;

import junit.framework.TestCase;
import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.UnindexedEventTableList;
import com.espertech.esper.epl.join.PollResultIndexingStrategy;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.epl.SupportStreamTypeSvc3Stream;
import com.espertech.esper.support.event.SupportEventAdapterService;

import java.util.*;

public class TestDatabasePollingViewable extends TestCase
{
    private DatabasePollingViewable pollingViewable;
    private PollResultIndexingStrategy indexingStrategy;

    public void setUp() throws Exception
    {
        List<String> inputProperties = Arrays.asList(new String[] {"s0.intPrimitive"});

        DataCache dataCache = new DataCacheLRUImpl(100);

        Map<String, Object> resultProperties = new HashMap<String, Object>();
        resultProperties.put("myvarchar", String.class);
        EventType resultEventType = SupportEventAdapterService.getService().createAnonymousMapType(resultProperties);

        Map<MultiKey<Object>, List<EventBean>> pollResults = new HashMap<MultiKey<Object>, List<EventBean>>();
        pollResults.put(new MultiKey<Object>(new Object[] {-1}), new LinkedList<EventBean>());
        pollResults.put(new MultiKey<Object>(new Object[] {500}), new LinkedList<EventBean>());
        SupportPollingStrategy supportPollingStrategy = new SupportPollingStrategy(pollResults);

        pollingViewable = new DatabasePollingViewable(1, inputProperties, supportPollingStrategy, dataCache, resultEventType);

        pollingViewable.validate(new SupportStreamTypeSvc3Stream(), null, null, null, null);

        indexingStrategy = new PollResultIndexingStrategy()
        {
            public EventTable index(List<EventBean> pollResult, boolean isActiveCache)
            {
                return new UnindexedEventTableList(pollResult);
            }
        };        
    }

    public void testPoll()
    {
        EventBean[][] input = new EventBean[2][2];
        input[0] = new EventBean[] {makeEvent(-1), null};
        input[1] = new EventBean[] {makeEvent(500), null};
        EventTable[] resultRows = pollingViewable.poll(input, indexingStrategy, null);

        // should have joined to two rows
        assertEquals(2, resultRows.length);
        assertTrue(resultRows[0].isEmpty());
        assertTrue(resultRows[1].isEmpty());
    }

    private EventBean makeEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        return SupportEventAdapterService.getService().adapterForBean(bean);
    }
}
