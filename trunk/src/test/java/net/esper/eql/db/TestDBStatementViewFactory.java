package net.esper.eql.db;

import net.esper.eql.spec.DBStatementStreamSpec;
import net.esper.eql.db.DBStatementViewFactory;
import net.esper.support.eql.SupportDatabaseService;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.view.ViewSpec;
import net.esper.view.EventCollection;

import java.util.LinkedList;
import java.math.BigDecimal;

import junit.framework.TestCase;

public class TestDBStatementViewFactory extends TestCase
{
    public void testDBStatementViewFactory() throws Exception
    {
        DBStatementStreamSpec spec = new DBStatementStreamSpec("s0", new LinkedList<ViewSpec>(),
                "mydbOne", "select * from customer where id=${idnum}");

        EventCollection eventCollection = DBStatementViewFactory.createDBStatementView(spec,
                SupportDatabaseService.makeService(),
                SupportEventAdapterService.getService());
        assertEquals(Long.class, eventCollection.getEventType().getPropertyType("id"));
        assertEquals(String.class, eventCollection.getEventType().getPropertyType("name"));
        assertEquals(Boolean.class, eventCollection.getEventType().getPropertyType("vip"));
        assertEquals(BigDecimal.class, eventCollection.getEventType().getPropertyType("max"));
        assertEquals(BigDecimal.class, eventCollection.getEventType().getPropertyType("min"));
    }
}
