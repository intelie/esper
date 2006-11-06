package net.esper.eql.db;

import net.esper.support.eql.SupportDatabaseService;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.bean.SupportBean;
import net.esper.event.EventBean;
import net.esper.collection.MultiKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Iterator;

import junit.framework.TestCase;

public class TestDBHistoricalEventViewable extends TestCase
{
    private DBHistoricalEventViewable dbHistoricalEventViewable;

    public void setUp() throws Exception
    {
        DBStatementMetaData metaData = new DBStatementMetaData();
        metaData.addInputParam("intPrimitive", java.sql.Types.INTEGER);
        metaData.addOutputParam("name", Types.VARCHAR, null);

        DatabaseConnectionFactory databaseConnectionFactory = SupportDatabaseService.makeService().getConnectionFactory("mydb");

        String statement = "select name from customer where min = ? order by id asc";
        dbHistoricalEventViewable = new DBHistoricalEventViewable(SupportEventAdapterService.getService(),
                statement, metaData, databaseConnectionFactory, true);
    }

    public void testPoll()
    {
        Set<MultiKey<EventBean>> joinSet = new LinkedHashSet<MultiKey<EventBean>>();
        EventBean[] input = new EventBean[] {makeEvent(-1), makeEvent(500), makeEvent(200)};
        dbHistoricalEventViewable.poll(input, joinSet);

        // should have joined to two rows
        assertEquals(3, joinSet.size());
        Iterator<MultiKey<EventBean>> it = joinSet.iterator();

        MultiKey<EventBean> row = it.next();
        assertEquals(input[1], row.get(0));
        assertEquals("Henry Trull", row.get(1).get("name"));

        row = it.next();
        assertEquals(input[1], row.get(0));
        assertEquals("Poke Yui", row.get(1).get("name"));

        row = it.next();
        assertEquals(input[2], row.get(0));
        assertEquals("Gerda Getter", row.get(1).get("name"));
    }

    public void testPrepared() throws Exception
    {
        Connection connection = SupportDatabaseService.makeService().getConnectionFactory("mydb").getConnection();
        String sql = "select name, vip, max as maxAmount, min as minAmount from customer where ? = customer.id";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, "1");
        ResultSet results = stmt.executeQuery();

        while(results.next())
        {
            System.out.println("name=" + results.getString(1));
        }
    }

    private EventBean makeEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        return SupportEventAdapterService.getService().adapterForBean(bean);
    }
}
