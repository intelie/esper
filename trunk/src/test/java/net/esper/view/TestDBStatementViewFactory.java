package net.esper.view;

import net.esper.eql.spec.DBStatementStreamSpec;

import java.util.LinkedList;

import junit.framework.TestCase;

public class TestDBStatementViewFactory extends TestCase
{
    public void testDBStatementViewFactory()
    {
        DBStatementStreamSpec spec = new DBStatementStreamSpec("s0", new LinkedList<ViewSpec>(),
                "mydb", "myschema", "select * from tomtest");

        EventStream eventStream = DBStatementViewFactory.create(spec);
        assertEquals(String.class, eventStream.getEventType().getPropertyType("custtname"));
    }
}
