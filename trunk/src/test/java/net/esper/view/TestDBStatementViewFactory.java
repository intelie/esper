package net.esper.view;

import net.esper.eql.spec.DBStatementStreamSpec;
import net.esper.support.eql.SupportDatabaseRefServiceFactory;

import java.util.LinkedList;

import junit.framework.TestCase;

public class TestDBStatementViewFactory extends TestCase
{
    public void testDBStatementViewFactory() throws Exception
    {
        DBStatementStreamSpec spec = new DBStatementStreamSpec("s0", new LinkedList<ViewSpec>(),
                "mydbOne", "select * from tomtest where custname=?");

        EventStream eventStream = DBStatementViewFactory.createDBEventStream(spec, SupportDatabaseRefServiceFactory.makeService());
        assertEquals(String.class, eventStream.getEventType().getPropertyType("custtname"));
    }
}
