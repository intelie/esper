package com.espertech.esper.core;

import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

import java.util.HashSet;

public class TestStatementEventTypeRef extends TestCase
{
    private StatementEventTypeRefImpl service;

    public void setUp()
    {
        service = new StatementEventTypeRefImpl();
    }

    public void testFlow()
    {
        addReference("s0", "e1");
        assertTrue(service.isInUse("e1"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e1").toArray(), new Object[] {"s0"});

        addReference("s0", "e2");
        assertTrue(service.isInUse("e2"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e2").toArray(), new Object[] {"s0"});

        addReference("s1", "e1");
        assertTrue(service.isInUse("e1"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e1").toArray(), new Object[] {"s0", "s1"});

        addReference("s1", "e1");
        assertTrue(service.isInUse("e1"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e1").toArray(), new Object[] {"s0", "s1"});

        assertFalse(service.isInUse("e3"));
        addReference("s2", "e3");
        assertTrue(service.isInUse("e3"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e3").toArray(), new Object[] {"s2"});

        service.removeReferences("s2");
        assertFalse(service.isInUse("e3"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e3").toArray(), new Object[0]);

        service.removeReferences("s0");
        assertTrue(service.isInUse("e1"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e1").toArray(), new Object[] {"s1"});

        service.removeReferences("s1");
        assertFalse(service.isInUse("e1"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e1").toArray(), new Object[0]);

        HashSet<String> values = new HashSet<String>();
        values.add("e5");
        values.add("e6");
        service.addReferences("s4", values);

        assertTrue(service.isInUse("e5"));
        assertTrue(service.isInUse("e6"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e5").toArray(), new Object[] {"s4"});

        service.removeReferences("s4");

        assertFalse(service.isInUse("e5"));
        assertFalse(service.isInUse("e6"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e5").toArray(), new Object[0]);

        assertEquals(0, service.getTypeToStmt().size());
        assertEquals(0, service.getTypeToStmt().size());
    }

    public void testInvalid()
    {
        service.removeReferences("s1");

        addReference("s2", "e2");

        assertTrue(service.isInUse("e2"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e2").toArray(), new Object[] {"s2"});

        service.removeReferences("s2");

        assertFalse(service.isInUse("e2"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e2").toArray(), new Object[0]);

        service.removeReferences("s2");

        assertFalse(service.isInUse("e2"));
        ArrayAssertionUtil.assertEqualsAnyOrder(service.getStatementNamesForType("e2").toArray(), new Object[0]);

        assertEquals(0, service.getTypeToStmt().size());
        assertEquals(0, service.getTypeToStmt().size());
    }

    private void addReference(String stmtName, String typeName)
    {
        HashSet<String> set = new HashSet<String>();
        set.add(typeName);
        service.addReferences(stmtName, set);
    }
}
