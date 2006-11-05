package net.esper.view;

import junit.framework.TestCase;

import java.sql.Types;
import java.util.Map;
import java.math.BigDecimal;

public class TestDBStatementMetaData extends TestCase
{
    public void testGetOutputEventType()
    {
        DBStatementMetaData meta = new DBStatementMetaData();
        meta.addOutputParam("a", Types.CHAR, null);
        meta.addOutputParam("b", Types.NUMERIC, null);
        meta.addOutputParam("c", Types.INTEGER, null);

        Map<String, Class> result = meta.getOutputEventType();
        assertEquals(3, result.size());
        assertEquals(String.class, result.get("a"));
        assertEquals(BigDecimal.class, result.get("b"));
        assertEquals(Integer.class, result.get("c"));
    }
}
