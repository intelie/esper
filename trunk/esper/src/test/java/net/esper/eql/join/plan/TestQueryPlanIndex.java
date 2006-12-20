package net.esper.eql.join.plan;

import junit.framework.TestCase;
import net.esper.eql.join.plan.QueryPlanIndex;

public class TestQueryPlanIndex extends TestCase
{
    private QueryPlanIndex indexSpec;

    public void setUp()
    {
        String[][] indexes = new String[][] {
            { "p01", "p02"},
            { "p21" },
            new String[0],
        };

        indexSpec = new QueryPlanIndex(indexes);
    }

    public void testInvalidUse()
    {
        try
        {
            new QueryPlanIndex(null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }

    public void testGetIndexNum()
    {
        assertEquals(0, indexSpec.getIndexNum(new String[] { "p01", "p02"}));
        assertEquals(1, indexSpec.getIndexNum(new String[] {"p21"}));
        assertEquals(2, indexSpec.getIndexNum(new String[0]));

        assertEquals(-1, indexSpec.getIndexNum(new String[] { "YY", "XX"}));
    }

    public void testAddIndex()
    {
        int indexNum = indexSpec.addIndex(new String[] {"a", "b"});
        assertEquals(3, indexNum);
        assertEquals(3, indexSpec.getIndexNum(new String[] { "a", "b"}));
    }
}
