package com.espertech.esper.eql.db;

import junit.framework.TestCase;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.sql.Types;

import com.espertech.esper.support.eql.SupportDatabaseService;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;

public class TestPollExecStrategyDBQuery extends TestCase
{
    private PollExecStrategyDBQuery dbPollExecStrategy;

    public void setUp() throws Exception
    {
        String sql = "select myvarchar from mytesttable where mynumeric = ? order by mybigint asc";

        DatabaseConnectionFactory databaseConnectionFactory = SupportDatabaseService.makeService().getConnectionFactory("mydb");
        ConnectionCache connectionCache = new ConnectionNoCacheImpl(databaseConnectionFactory, sql);

        Map<String, Class> resultProperties = new HashMap<String, Class>();
        resultProperties.put("myvarchar", String.class);
        EventType resultEventType = SupportEventAdapterService.getService().createAnonymousMapType(resultProperties);

        Map<String, DBOutputTypeDesc> propertiesOut = new HashMap<String, DBOutputTypeDesc>();
        propertiesOut.put("myvarchar", new DBOutputTypeDesc(Types.VARCHAR, null, null));

        dbPollExecStrategy = new PollExecStrategyDBQuery(SupportEventAdapterService.getService(),
                resultEventType, connectionCache, sql, propertiesOut);
    }

    public void testPoll()
    {
        dbPollExecStrategy.start();

        List<EventBean>[] resultRows = new LinkedList[3];
        resultRows[0] = dbPollExecStrategy.poll(new Object[] { -1 });
        resultRows[1] = dbPollExecStrategy.poll(new Object[] { 500 });
        resultRows[2] = dbPollExecStrategy.poll(new Object[] { 200 });

        // should have joined to two rows
        assertEquals(0, resultRows[0].size());
        assertEquals(2, resultRows[1].size());
        assertEquals(1, resultRows[2].size());

        EventBean event = resultRows[1].get(0);
        assertEquals("D", event.get("myvarchar"));

        event = resultRows[1].get(1);
        assertEquals("E", event.get("myvarchar"));

        event = resultRows[2].get(0);
        assertEquals("F", event.get("myvarchar"));

        dbPollExecStrategy.done();
        dbPollExecStrategy.destroy();
    }
}
