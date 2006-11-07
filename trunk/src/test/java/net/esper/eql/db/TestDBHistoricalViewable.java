package net.esper.eql.db;

import net.esper.support.eql.SupportDatabaseService;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.bean.SupportBean;
import net.esper.event.EventBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.*;

import junit.framework.TestCase;

public class TestDBHistoricalViewable extends TestCase
{
    private DBHistoricalViewable dbHistoricalViewable;

    public void setUp() throws Exception
    {
        List<DBInputParameterDesc> inputPropertyTypes = new LinkedList<DBInputParameterDesc>();
        inputPropertyTypes.add(new DBInputParameterDesc("s0.intPrimitive", java.sql.Types.INTEGER));

        Map<String, DBOutputTypeDesc> propertiesOut = new HashMap<String, DBOutputTypeDesc>();
        propertiesOut.put("myvarchar", new DBOutputTypeDesc(Types.VARCHAR, null));

        DatabaseConnectionFactory databaseConnectionFactory = SupportDatabaseService.makeService().getConnectionFactory("mydb");

        String statement = "select myvarchar from mytesttable where mynumeric = ? order by mybigint asc";
        dbHistoricalViewable = new DBHistoricalViewable(1, SupportEventAdapterService.getService(),
                statement, inputPropertyTypes, propertiesOut, databaseConnectionFactory, true);

        dbHistoricalViewable.validate(new SupportStreamTypeSvc3Stream());
    }

    public void testPoll()
    {
        EventBean[][] input = new EventBean[3][2];
        input[0] = new EventBean[] {makeEvent(-1), null};
        input[1] = new EventBean[] {makeEvent(500), null};
        input[2] = new EventBean[] {makeEvent(200), null};
        List<EventBean>[] resultRows = dbHistoricalViewable.poll(input);

        // should have joined to two rows
        assertEquals(3, resultRows.length);
        assertEquals(0, resultRows[0].size());
        assertEquals(2, resultRows[1].size());
        assertEquals(1, resultRows[2].size());

        EventBean event = resultRows[1].get(0);
        assertEquals("D", event.get("myvarchar"));

        event = resultRows[1].get(1);
        assertEquals("E", event.get("myvarchar"));

        event = resultRows[2].get(0);
        assertEquals("F", event.get("myvarchar"));
    }

    private EventBean makeEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        return SupportEventAdapterService.getService().adapterForBean(bean);
    }
}
