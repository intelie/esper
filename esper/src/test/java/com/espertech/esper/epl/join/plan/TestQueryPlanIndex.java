package com.espertech.esper.epl.join.plan;

import junit.framework.TestCase;

public class TestQueryPlanIndex extends TestCase
{
    private QueryPlanIndex indexSpec;

    public void setUp()
    {
        QueryPlanIndexItem itemOne = new QueryPlanIndexItem(new String[] { "p01", "p02"}, null, null, null);
        QueryPlanIndexItem itemTwo = new QueryPlanIndexItem(new String[] { "p21"}, new Class[0], null, null);
        QueryPlanIndexItem itemThree = new QueryPlanIndexItem(new String[0], new Class[0], null, null);
        indexSpec = QueryPlanIndex.makeIndex(itemOne, itemTwo, itemThree);
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
        assertNotNull(indexSpec.getIndexNum(new String[] { "p01", "p02"}, null));
        assertNotNull(indexSpec.getIndexNum(new String[] {"p21"}, null));
        assertNotNull(indexSpec.getIndexNum(new String[0], null));

        assertNull(indexSpec.getIndexNum(new String[] { "YY", "XX"}, null));
    }

    public void testAddIndex()
    {
        String indexNum = indexSpec.addIndex(new String[] {"a", "b"}, null);
        assertNotNull(indexNum);
        assertEquals(indexNum, indexSpec.getIndexNum(new String[] { "a", "b"}, null));
    }
}
