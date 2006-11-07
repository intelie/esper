package net.esper.eql.db;

import net.esper.eql.spec.DBStatementStreamSpec;
import net.esper.eql.db.DBHistoricalViewableFactory;
import net.esper.support.eql.SupportDatabaseService;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.view.ViewSpec;
import net.esper.view.EventCollection;

import java.util.LinkedList;
import java.math.BigDecimal;

import junit.framework.TestCase;

public class TestDBHistoricalViewableFactory extends TestCase
{
    public void testDBStatementViewFactory() throws Exception
    {
        DBStatementStreamSpec spec = new DBStatementStreamSpec("s0", new LinkedList<ViewSpec>(),
                "mydb_part", "select * from mytesttable where mybigint=${idnum}");

        EventCollection eventCollection = DBHistoricalViewableFactory.createDBStatementView(1, spec,
                SupportDatabaseService.makeService(),
                SupportEventAdapterService.getService());
        
        assertEquals(Long.class, eventCollection.getEventType().getPropertyType("mybigint"));
        assertEquals(String.class, eventCollection.getEventType().getPropertyType("myvarchar"));
        assertEquals(Boolean.class, eventCollection.getEventType().getPropertyType("mybool"));
        assertEquals(BigDecimal.class, eventCollection.getEventType().getPropertyType("mynumeric"));
        assertEquals(BigDecimal.class, eventCollection.getEventType().getPropertyType("mydecimal"));
    }
}
